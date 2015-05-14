function counterService($log, $q, wydNotifyService, sessionService, $http) {

	var service = {}, receipt = {}, initSize = 1, defaultCustomer = {
		id : 0,
	};

	service.tranTypeNames = {
		'buy' : 'Buy',
		'sell' : 'Sell'
	};

	service.receipt = receipt;

	function addTransaction(times) {
		var trans = receipt.trans;
		for (var i = 0; i < times; i++) {
			var tran = {
				type : 'sell',
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
		$log.debug("counterService initialize started...")

		receipt.id = 0;
		receipt.totalAmount = 0;
		receipt.totalAmountLabel = '';
		receipt.customerAmount = '';
		receipt.customerAmountRaw = 0;
		receipt.customerAmountLabel = '';
		receipt.balanceAmount = 0;
		receipt.balanceAmountLabel = '';
		receipt.forUserLabel = 'Customer';
		receipt.forUser = defaultCustomer;

		var customer = _.find(sessionService.customers, function(item) {
			return item.firstName === 'Guest';
		});
		service.setCustomer(customer);

		receipt.trans = [];
		addTransaction(initSize);
		receipt.curTranIndex = 0;
		receipt.curTran = receipt.trans[0];

		$log.debug("counterService initialize finished...")
	};

	service.setCustomer = function(customer) {
		if (_.isUndefined(customer)) {
			return;
		}
		// $log.info(customer);
		receipt.forUser = customer;
		receipt.forUserLabel = 'Customer';
		receipt.forUserUrl = '/customers/customer/' + customer.id;
	};

	service.setDealer = function(dealer) {
		if (_.isUndefined(dealer)) {
			return;
		}
		// $log.info(dealer);
		receipt.forUser = dealer;
		receipt.forUserLabel = 'Dealer';
		receipt.forUserUrl = '/dealers/dealer/' + dealer.id;
	};

	service.newTransaction = function() {
		if (receipt.id > 0) {
			service.init();
		} else {
			addTransaction(1);
		}
	};

	service.removeTransaction = function(index) {
		receipt.trans.splice(index, 1);
		receipt.curTranIndex = index - 1;
		if (receipt.trans.length == 0) {
			service.newTransaction();
		}
		service.computeTotalAmount();
	};

	service.removeAllTransactions = function() {
		receipt.trans.length = 0;
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
		receipt.curTran = receipt.trans[receipt.curTranIndex];
	}

	service.onTransactionItem = function(tran) {
		service.onTransactionType(tran);
	}

	service.onTransactionType = function(tran) {
		if (tran.type != '') {
			if (tran.type == 'buy') {
				tran.rate = tran.item.product.buyRate + '';
			} else {
				tran.rate = tran.item.product.sellRate + '';
			}
			service.onTransactionRate(tran);
		}
	}

	service.onTransactionUnit = function(tran) {
		var unit = tran.unit;
		if (unit == '' || _.isUndefined(unit)) {
			return;
		}
		if (!_.isNumber(unit)) {
			unit = unit.split(',').join('')
			unit = parseFloat(unit);
		}
		tran.unitRaw = unit;
		service.computeTransactionAmount(tran);
	}

	service.onTransactionRate = function(tran) {
		var rate = tran.rate;
		if (rate == '' || _.isUndefined(rate)) {
			return;
		}
		if (!_.isNumber(rate)) {
			rate = rate.split(',').join('')
			rate = parseFloat(rate);
		}
		tran.rateRaw = rate;
		service.computeTransactionAmount(tran);
	}

	service.computeTransactionAmount = function(tran) {
		var amount = tran.unitRaw * (tran.rateRaw / tran.item.product.baseUnit);
		if (tran.type == 'buy') {
			amount *= -1;
		}
		tran.amount = amount;
		service.computeTotalAmount();
	};

	service.computeTotalAmount = function() {
		var trans = receipt.trans;
		var totalAmount = 0, i = 0, amount = 0;
		for (i = 0; i < trans.length; i++) {
			amount = trans[i].amount;
			// if (trans[i].type == 'buy') {
			// amount *= -1;
			// }
			totalAmount += amount;
		}
		receipt.totalAmount = totalAmount;
		if (totalAmount < 0) {
			receipt.totalAmountLabel = 'pay';
			receipt.customerAmountLabel = 'pay';
		} else {
			receipt.totalAmountLabel = 'receive';
			receipt.customerAmountLabel = 'receive';
		}
		service.onCustomerAmount();
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

	service.onRevertAmount = function() {
		var revertAmount = receipt.revertAmount;
		if (revertAmount == '' || _.isUndefined(revertAmount)) {
			return;
		}
		if (!_.isNumber(revertAmount)) {
			revertAmount = revertAmount.split(',').join('')
			revertAmount = parseFloat(revertAmount);
		}
		receipt.revertAmountRaw = revertAmount;
		var tran = receipt.curTran;
		var v0 = tran.rateRaw / tran.item.product.baseUnit;
		var v1 = revertAmount / v0;
		var v2 = v1 % tran.item.product.denominator;
		var v3 = v1 - v2
		tran.unit = v3 + '';
		service.onTransactionUnit(tran);
	};

	service.validateTransaction = function(tran) {
		tran.message = '';
		if (!tran.item.id) {
			tran.message = 'Missing Product! Please select product...';
			return;
		}
		if (tran.type === '') {
			tran.message = 'Missing Type! Please select type...';
			return;
		}
		if (tran.unit == '' || tran.unitRaw <= 0) {
			tran.message = 'Quantity should be greater than 0';
			return;
		}
		if (tran.rate == '' || tran.rateRaw <= 0) {
			tran.message = 'Rate should be greater than 0';
			return;
		}
		// if (tran.type === service.TRAN_TYPE_BUY) {
		// if (tran.item.product.buyPercentageRate <= tran.rateRaw) {
		// var s = "Rate is more than ";
		// s += tran.item.product.buyPercentageRate;
		// tran.message = s;
		// }
		// } else {
		// if (tran.item.product.sellPercentageRate >= tran.rateRaw) {
		// var s = "Rate is less than ";
		// s += tran.item.product.sellPercentageRate;
		// tran.message = s;
		// }
		// }
	};

	service.saveReceiptAsOrder = function() {
		$log.debug('saveReceiptAsOrder started...');

		if (receipt.forUser.firstName == 'Guest') {
			var message = 'Order can\'t be placed for Guest...';
			wydNotifyService.addWarning(message, true);
			return;
		}

		$log.info("Receipt before process...")
		$log.info(receipt);

		var reqReceipt = {
			forUserId : receipt.forUser.id,
			orders : []
		}, reqTran = null, totalSaleAmount = 0, rowIds = [];

		if (receipt.forUser.type == 'dealer') {
			reqReceipt.category = 'dealer'
		} else {
			reqReceipt.category = 'customer'
		}

		for (var i = 0; i < receipt.trans.length; i++) {
			var tran = receipt.trans[i]
			service.validateTransaction(tran);
			if (tran.message != '') {
				rowIds.push(i + 1);
				continue;
			}
			reqOrder = {
				category : reqReceipt.category,
				accountId : tran.item.id,
				type : tran.type,
				unit : tran.unitRaw,
				rate : tran.rateRaw,
			};
			reqReceipt.orders.push(reqOrder);
		}

		if (rowIds.length > 0) {
			var msg = "Row's " + rowIds.join(', ') + ' has issues...';
			wydNotifyService.addError(msg, true);
		} else {
			$log.info("Receipt before post...")
			$log.info(reqReceipt);

			var path = '/sessions/order'
			$http.post(path, reqReceipt).success(function(response) {
				// $log.debug(response);
				if (response.type === 0) {
					success(response.data, response.message)
				} else {
					fail(response.data, response.message)
				}
				$log.debug('saveReceiptAsOrder finished...');
			});
		}
	};

	service.saveReceiptAsTransaction = function() {
		$log.debug('saveReceiptAsTransaction started...');

		$log.info("Receipt before process...")
		$log.info(receipt);

		var reqReceipt = {
			forUserId : receipt.forUser.id,
			amount : receipt.customerAmountRaw,
			balanceAmount : receipt.balanceAmount,
			trans : []
		}, reqTran = null, totalSaleAmount = 0, rowIds = [];

		if (receipt.forUser.type == 'dealer') {
			reqReceipt.category = 'dealer'
		} else {
			reqReceipt.category = 'customer'
		}

		for (var i = 0; i < receipt.trans.length; i++) {
			var tran = receipt.trans[i]
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
			return;
		}

		var totalAmount = receipt.totalAmount;
		if (totalAmount < 0) {
			totalAmount *= -1;
		}
		if (receipt.forUser.firstName == 'Guest'
				&& receipt.customerAmountRaw < totalAmount) {
			var msg = 'Please provide the amount to ';
			msg += receipt.customerAmountLabel
			msg += ', which should be greater then or equal to '
			msg += totalAmount;
			wydNotifyService.addError(msg, true);
			return;
		}

		if (receipt.customerAmountRaw === 0) {
			var s = 'Are you sure to proceed without the amount to ';
			s += receipt.customerAmountLabel + '?';
			var params = {
				title : 'Confirm',
				text : s,
				type : 'warning',
				showCancelButton : true,
				confirmButtonText : 'Yes',
				cancelButtonText : 'No',
			};
			var callback = function() {
				submit(reqReceipt);
			};
			wydNotifyService.sweet.show(params, callback);
		} else {
			submit(reqReceipt);
		}

		$log.info("Receipt before post...")
		$log.info(reqReceipt);

	};

	service.printReceipt = function() {
		var message = 'Printing receipt is not yet implemented...';
		wydNotifyService.addWarning(message, true);
	};

	function submit(reqReceipt) {
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

	function success(resReceipt, message) {
		$log.info("Receipt after post...")
		$log.debug(message);
		$log.info(resReceipt);

		wydNotifyService.addSuccess(message, true);
		receipt.id = resReceipt.id;

		_.forEach(resReceipt.trans, function(tran) {
			sessionService.updateAccount(tran.account);
		});
		sessionService.computeStockWorth();
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