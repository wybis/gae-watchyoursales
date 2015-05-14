function customerOrderService($log, $q, wydNotifyService, sessionService,
		$http, $location) {

	var service = {}, customers = [], searchCriteria = {}, searchResult = {}, receipt = {};

	customers = sessionService.customers;
	customers.unshift({
		id : 0,
		firstName : 'All'
	});
	service.customers = customers;

	service.productsMap = sessionService.productsMap;
	service.accountsMap = sessionService.accountsMap;
	service.customersMap = sessionService.customersMap;

	searchCriteria.isSelectedAll = false;
	searchCriteria.customer = customers[0];
	service.searchCriteria = searchCriteria;

	searchResult.items = [];
	service.searchResult = searchResult;

	receipt.id = 0;
	service.receipt = receipt;

	service.onCustomerChange = function() {
		if (searchCriteria.customer.id === 0) {
			searchResult.items = searchResult.orders
		} else {
			var items = _.filter(searchResult.orders, function(item) {
				return item.forUserId === searchCriteria.customer.id;
			});
			searchResult.items = items;
		}
		searchCriteria.isSelectedAll = false;
	};

	service.selectOrDeSelectAll = function() {
		_.forEach(searchResult.items, function(item) {
			item.isSelected = searchCriteria.isSelectedAll;
		});
	};

	service.onOrderSelectionChange = function() {
		var flag = true;
		for (var i = 0; i < searchResult.items.length; i++) {
			if (searchResult.items[i].isSelected === false) {
				flag = false;
				i = searchResult.items.length;
			}
		}
		searchCriteria.isSelectedAll = flag;
	};

	function processOrders(items) {
		searchResult.orders = items;
		_.forEach(items, function(item) {
			item.isSelected = false;
		});
		service.onCustomerChange();
	}

	service.getPendingOrders = function() {
		var path = '/sessions/pendingCustomerOrders';
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processOrders(response.data);
			}
		})
	};

	service.proceedToProcessOrder = function() {
		var orders = _.filter(searchResult.items, function(item) {
			return item.isSelected;
		});

		 if (orders.length === 0) {
			wydNotifyService.addError(
					'Please select minimum one order to proceed...', true);
			return;
		}

		receipt.id = 0;

		receipt.totalAmount = 0;
		receipt.totalAmountLabel = '';

		receipt.customerAmount = '';
		receipt.customerAmountRaw = 0;
		receipt.customerAmountLabel = '';

		receipt.balanceAmount = 0;
		receipt.balanceAmountLabel = '';

		receipt.forUser = searchCriteria.customer
		receipt.forUserLabel = 'Customer';
		receipt.forUserUrl = '/customers/customer/' + receipt.forUser.id;

		receipt.trans = [];

		addTransaction(orders);

		$location.path('/processCustomerOrders');
	};

	function addTransaction(orders) {
		var trans = receipt.trans;
		for (var i = 0; i < orders.length; i++) {
			var order = orders[i];
			var tran = {
				order : order,
				item : service.accountsMap[order.accountId],
				type : order.type,
				unit : '' + order.unit,
				unitRaw : order.unit,
				rate : '' + order.rate,
				rateRaw : order.rate,
				message : ''
			};
			trans.push(tran);
			service.computeTranAmount(tran);
			receipt.curTran = tran;
			receipt.curTranIndex = trans.length - 1;
		}
	}

	service.removeTran = function(index) {
		var tran = receipt.curTran;
		tran.unit = '' + tran.order.unit;
		tran.unitRaw = tran.order.unit;
		service.computeTotalAmount();
	};

	service.onTranSelect = function(index) {
		if (receipt.curTranIndex === index) {
			return;
		}
		receipt.curTranIndex = index;
		receipt.curTran = receipt.trans[receipt.curTranIndex];
	}

	service.onTranUnit = function(tran) {
		var unit = tran.unit;
		if (unit == '' || _.isUndefined(unit)) {
			return;
		}
		if (!_.isNumber(unit)) {
			unit = unit.split(',').join('')
			unit = parseFloat(unit);
		}
		tran.unitRaw = unit;
		service.computeTranAmount(tran);
	}

	service.computeTranAmount = function(tran) {
		var amount = tran.unitRaw * (tran.rateRaw / tran.item.product.baseUnit);
		if (tran.type === 'buy') {
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
			// if (trans[i].type === 'buy') {
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

	service.saveReceiptAsTransaction = function() {
		$log.debug('saveReceiptAsTransaction started...');

		$log.info("Receipt before process...")
		$log.info(receipt);

		var reqReceipt = {
			forUserId : receipt.forUser.id,
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
				orderId : tran.order.id,
				accountId : tran.item.id,
				type : tran.type,
				unit : tran.unitRaw,
				rate : tran.rateRaw,
			};
			if (tran.orderId) {
				reqTran.orderId = tran.order.id;
			}
			reqReceipt.trans.push(reqTran);
		}

		if (rowIds.length > 0) {
			var msg = "Row's " + rowIds.join(', ') + ' has issues...';
			wydNotifyService.addError(msg, true);
			return;
		}

		$log.info("Receipt before post...")
		$log.info(reqReceipt);

		submit(reqReceipt);
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

		for (var i = 0; i < resReceipt.trans.length; i++) {
			var tran = resReceipt.trans[i];
			sessionService.updateAccount(tran.account);
			if (tran.order) {
				receipt.trans[i].order.unit = tran.order.unit;
			}
		}
		sessionService.computeStockWorth();
		service.getPendingOrders();
	}

	function fail(resReceipt, message) {
		$log.info("Receipt after post...")
		$log.debug(message);
		$log.info(resReceipt);
		wydNotifyService.addError(message, true);
	}

	return service;
}
appServices.factory('customerOrderService', customerOrderService);