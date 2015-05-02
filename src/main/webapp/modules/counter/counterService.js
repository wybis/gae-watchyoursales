function counterService($log, $q, wydNotifyService, sessionService, $http) {

	var service = {}, receipt = {}, initSize = 1, defaultCustomer = {
		id : 0,
	};

	service.TRAN_TYPE_BUY = 'buy';
	service.TRAN_TYPE_SELL = 'sell';

	service.tranTypeNames = {
		'buy' : 'Buy',
		'sell' : 'Sell'
	};

	service.receipt = receipt;

	function addTransaction(times) {
		var trans = receipt.transactions;
		for (var i = 0; i < times; i++) {
			var tran = {
				type : '',
				item : {
					product : {
						code : '-',
						name : '-',
						buyRate : 0,
						buyPercent : 0,
						sellRate : 0,
						sellPercent : 0,
						handStock : 0,
						handStockAverage : 0,
					},
					handStock : 0,
					handStockAverage : 0
				},
				unit : '',
				unitRaw : 0,
				rate : '',
				rateRaw : 0,
				amount : '',
				revertAmount : '',
				message : ''
			};
			trans.push(tran);
			receipt.curTran = tran;
			receipt.curTranIndex = trans.length - 1;
			service.computeTotalAmount();
		}
	}

	service.init = function() {
		receipt.id = 0;
		receipt.totalAmount = 0;
		receipt.totalAmountLabel = '';
		receipt.customerAmount = '';
		receipt.customerAmountRaw = 0;
		receipt.customerAmountLabel = '';
		receipt.balanceAmount = 0;
		receipt.balanceAmountLabel = '';
		receipt.customer = defaultCustomer;

		var customer = _.find(sessionService.customers, function(item) {
			return item.firstName === 'Guest';
		});
		service.setCustomer(customer);

		receipt.transactions = [];
		addTransaction(initSize);

		$log.debug("counterService initialized")
	};

	service.setCustomer = function(customer) {
		if (_.isUndefined(customer)) {
			return;
		}
		// $log.info(customer);
		receipt.customer = customer;
		var productId = customer.cashAccount.productId;
		var product = sessionService.productsMap[productId];
		// $log.info(product);
		sessionService.cashCustomer = product;
		receipt.customerUrl = '/customers/customer/' + customer.id;
	};

	service.setDealer = function(dealer) {
		if (_.isUndefined(dealer)) {
			return;
		}
		// $log.info(dealer);
		receipt.customer = dealer;
		var productId = dealer.cashAccount.productId;
		var product = sessionService.productsMap[productId];
		// $log.info(product);
		sessionService.cashDealer = product;
		receipt.customerUrl = '/dealers/dealer/' + dealer.id;
	};

	service.newTransaction = function() {
		if (receipt.id > 0) {
			service.init();
		} else {
			addTransaction(1);
		}
	};

	service.removeTransaction = function(index) {
		receipt.transactions.splice(index, 1);
		receipt.curTranIndex = index - 1;
		if (receipt.transactions.length == 0) {
			service.newTransaction();
		}
		service.computeTotalAmount();
	};

	service.removeAllTransactions = function() {
		receipt.transactions.length = 0;
		receipt.curTranIndex = -1;
		receipt.curTran = {};
		service.newTransaction();
		service.computeTotalAmount();
	};

	service.onTransactionSelect = function(index) {
		if (receipt.curTranIndex === index) {
			return;
		}
		receipt.curTranIndex = index;
		receipt.curTran = receipt.transactions[receipt.curTranIndex];
		// $log.info(receipt.curTran);
	}

	service.onTransactionType = function(tran) {
		if (tran.type == '') {
			return;
		}
		if (tran.type === service.TRAN_TYPE_BUY) {
			tran.rate = tran.item.product.buyRate;
		} else {
			tran.rate = tran.item.product.sellRate;
		}
		service.computeTransactionAmount(tran);
	}

	service.onTransactionItem = function(tran) {
		if (tran.type === service.TRAN_TYPE_BUY) {
			tran.rate = tran.item.product.buyRate;
		} else {
			tran.rate = tran.item.product.sellRate;
		}
		service.computeTransactionAmount(tran);
	}

	service.onTransactionUnit = function(tran) {
		service.computeTransactionAmount(tran);
	}

	service.onTransactionRate = function(tran) {
		service.computeTransactionAmount(tran);
	}

	service.computeTransactionAmount = function(tran) {
		var unit = tran.unit;
		if (unit == '' || _.isUndefined(unit)) {
			return;
		}
		if (!_.isNumber(unit)) {
			unit = unit.split(',').join('')
			unit = parseFloat(unit);
		}
		tran.unitRaw = unit;

		var rate = tran.rate;
		if (rate == '' || _.isUndefined(rate)) {
			return;
		}
		if (!_.isNumber(rate)) {
			rate = rate.split(',').join('')
			rate = parseFloat(rate);
		}
		tran.rateRaw = rate;

		var amount = tran.unitRaw * (tran.rateRaw / tran.item.product.baseUnit);
		if (tran.type === service.TRAN_TYPE_BUY) {
			amount *= -1;
		}
		tran.amount = amount;

		service.computeTotalAmount();
	};

	service.computeTotalAmount = function() {
		var transactions = receipt.transactions;
		var totalAmount = 0, i = 0, amount = 0;
		for (i = 0; i < transactions.length; i++) {
			amount = transactions[i].amount;
			if (transactions[i].type === service.TYPE_BUY) {
				amount *= -1;
			}
			totalAmount += amount;
		}
		receipt.totalAmount = totalAmount;
		if (totalAmount < 0) {
			receipt.totalAmountLabel = 'Pay';
			receipt.customerAmountLabel = 'Pay';
		} else {
			receipt.totalAmountLabel = 'Receive';
			receipt.customerAmountLabel = 'Receive';
		}
		service.onCustomerAmount();
	};

	service.onRevertAmount = function(tran) {
		var rate = tran.rate;
		if (rate == '') {
			return;
		}
		rate = rate.split(',').join('')

		var revertAmount = tran.revertAmount;
		if (revertAmount == '') {
			return;
		}
		revertAmount = revertAmount.split(',').join('')

		var v0 = rate / tran.item.product.baseUnit;
		var v1 = revertAmount / v0;
		var v2 = v1 % tran.item.denominator;
		var v3 = v1 - v2
		tran.unit = v3;
	};

	service.onCustomerAmount = function() {
		var rawValue = receipt.customerAmount, totalAmount = receipt.totalAmount;
		if (rawValue == '' || rawValue == '0') {
			receipt.balanceAmount = 0;
			return;
		}
		rawValue = rawValue.split(',').join('');
		rawValue = parseFloat(rawValue);
		receipt.customerAmountRaw = rawValue;
		if (totalAmount < 0) {
			totalAmount *= -1;
		}
		var balanceAmount = totalAmount - rawValue;
		totalAmount = receipt.totalAmount;
		if (totalAmount < 0) {
			balanceAmount *= -1;
		}
		receipt.balanceAmount = balanceAmount;

		if (balanceAmount < 0) {
			receipt.balanceAmountLabel = 'Pay';
		} else {
			receipt.balanceAmountLabel = 'Receive';
		}
	};

	service.validateTransaction = function(tran) {
		tran.message = '';
		if (!tran.item) {
			tran.message = 'Missing Product! Please select product...';
			return;
		}
		if (tran.unitRaw <= 0) {
			tran.message = 'Unit should be greater than 0';
			return;
		}
		if (tran.rateRaw <= 0) {
			tran.message = 'Rate should be greater than 0';
			return;
		}
		if (tran.type === service.TRAN_TYPE_BUY) {
			if (tran.item.product.buyPercentageRate <= tran.rate) {
				var s = "Rate is more than ";
				s += tran.item.product.buyPercentageRate;
				tran.message = s;
			}
		} else {
			if (tran.item.product.sellPercentageRate >= tran.rate) {
				var s = "Rate is less than ";
				s += tran.item.product.sellPercentageRate;
				tran.message = s;
			}
		}
	};

	service.saveReceiptAsQuotation = function() {
		var message = 'Quotation is not yet implemented...';
		wydNotifyService.addWarning(message, true);
	};

	service.saveReceiptAsDraft = function() {
		var message = 'Draft is not yet implemented...';
		wydNotifyService.addWarning(message, true);
	};

	service.saveReceiptAsOrder = function() {
		var message = 'Order is not yet implemented...';
		wydNotifyService.addWarning(message, true);
	};

	service.saveReceiptAsTransaction = function() {
		$log.debug('saveReceiptAsTransaction started...');

		$log.info("Receipt before process...")
		$log.info(receipt);

		var reqReceipt = {
			customerId : receipt.customer.id,
			trans : []
		}, reqTran = null, totalSaleAmount = 0, rowIds = [];

		if (receipt.customer.type == 'dealer') {
			reqReceipt.category = 'dealer'
		} else {
			reqReceipt.category = 'customer'
		}

		for (var i = 0; i < receipt.transactions.length; i++) {
			var tran = receipt.transactions[i]
			service.validateTransaction(tran);
			if (tran.message != '') {
				rowIds.push(i + 1);
				continue;
			}
			reqTran = {
				category : reqReceipt.category,
				accountId : tran.item.id,
				type : tran.type,
				unit : tran.unitRaw,
				rate : tran.rateRaw,
			};
			reqReceipt.trans.push(reqTran);
		}

		if (rowIds.length > 0) {
			var msg = "Row's " + rowIds.join(', ') + ' has issues...';
			wydNotifyService.addError(msg, true);
		} else {
			$log.info("Receipt before post...")
			$log.info(reqReceipt);

			var path = '/sessions/counter'
			$http.post(path, reqReceipt).success(function(response) {
				// $log.debug(response);
				if (response.type === 0) {
					success(response.data, response.message)
				} else {
					fail(response.data, response.message)
				}
				$log.debug('saveReceiptAsTransaction finished...');
			});
		}
	};

	service.printReceipt = function() {
		var message = 'Printing receipt is not yet implemented...';
		wydNotifyService.addWarning(message, true);
	};

	function success(resReceipt, message) {
		$log.info("Receipt after post...")
		$log.debug(message);
		$log.info(resReceipt);

		wydNotifyService.addSuccess(message, true);
		receipt.id = resReceipt.id;

		_.forEach(resReceipt.trans, function(tran) {
			sessionService.updateAccount(tran.account);
		});
	}

	function fail(resReceipt, message) {
		$log.info("Receipt after post...")
		$log.debug(message);
		$log.info(resReceipt);
		wydNotifyService.addError(message, true);
	}

	service.init();

	return service;
}
appServices.factory('counterService', counterService);