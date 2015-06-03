function customerOrderProcessService($log, $q, wydNotifyService,
		sessionService, $http) {
	var basePath = '/sessions'
	var service = {}, customers = [], employees = [], model = {};

	service.customers = customers;
	service.customersMap = sessionService.customersMap;
	service.employees = employees;
	service.employeesMap = sessionService.employeesMap;

	service.productsMap = sessionService.productsMap;
	service.accountsMap = sessionService.accountsMap;

	model.customer = customers[0];
	model.employee = employees[0];

	model.isReceiptSelectedAll = false;
	model.receipts = [];

	model.isItemSelectedAll = false;
	model.items = [];

	service.model = model;

	service.selectOrDeSelectAllReceipts = function() {
		_.forEach(model.receipts, function(item) {
			item.isSelected = model.isReceiptSelectedAll;
		});
	};

	service.onReceiptSelectionChange = function() {
		var flag = true, items = model.receipts;
		for (var i = 0; i < items.length; i++) {
			if (items[i].isSelected === false) {
				flag = false;
				i = items.length;
			}
		}
		model.isReceiptSelectedAll = flag;
	};

	service.selectOrDeSelectAllItems = function() {
		_.forEach(model.items, function(item) {
			item.isSelected = model.isItemSelectedAll;
		});
	};

	service.onItemSelectionChange = function() {
		var flag = true, items = model.items;
		for (var i = 0; i < items.length; i++) {
			if (items[i].isSelected === false) {
				flag = false;
				i = items.length;
			}
		}
		model.isItemSelectedAll = flag;
	};

	service.processOrderReceipt = function(receipt) {
		var sesUserId = sessionService.context.sessionDto.id;
		receipt.isSelected = false;
		if (receipt.byUserId === sesUserId && receipt.status == 'assigned') {
			receipt.isAcceptDisabled = false;
		} else {
			receipt.isAcceptDisabled = true;
		}
	};

	function processOrderReceipts(receipts) {
		var sesUserId = sessionService.context.sessionDto.id;
		model.receipts = receipts;
		_.forEach(receipts, function(receipt) {
			service.processOrderReceipt(receipt);
		});
		model.isReceiptSelectedAll = false;
	}

	service.getPendingOrderReceipts = function() {
		var path = basePath + '/pendingCustomerOrderReceipts';
		$http.get(path).success(function(response) {
			$log.info(response);
			if (response.type === 0) {
				processOrderReceipts(response.data);
			}
		})
	};

	service.getPendingOrders = function(customerId) {
		var path = basePath + '/pendingCustomerOrders/' + customerId;
		$http.get(path).success(function(response) {
			$log.info(response);
			if (response.type === 0) {
				model.items = response.data;
			}
		})
	};

	service.assignToEmployee = function() {
		var receipts = _.filter(model.receipts, function(item) {
			return item.isSelected;
		});

		if (receipts.length === 0) {
			wydNotifyService.addError(
					'Please select minimum one order to proceed...', true);
			return;
		}

		if (model.employee.id === 0) {
			wydNotifyService.addError(
					'Please select an employee to proceed...', true);
			return;
		}

		var receiptIds = _.map(receipts, function(item) {
			return item.id;
		});
		$log.info("order assign started...")
		$log.info(receiptIds);

		var path = '/sessions/assignOrders/' + model.employee.id;
		$http.post(path, receiptIds).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				wydNotifyService.addSuccess(response.message, true);
				service.getPendingOrderReceipts();
			} else {
				fail(response.data, response.message)
			}
			$log.info('order assign finished...');
		});
	};

	service.acceptByEmployee = function(receiptId) {
		var receiptIds = [ receiptId ];
		$log.info("order accept started...")
		$log.info(receiptIds);

		var path = '/sessions/acceptOrders/' + model.employee.id;
		$http.post(path, receiptIds).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				wydNotifyService.addSuccess(response.message, true);
				_.forEach(response.data, function(item) {
					sessionService.updateAccount(item)
				});
				$log.info(response.data);
				service.getPendingOrderReceipts();
			} else {
				fail(response.data, response.message)
			}
			$log.info('order accept finished...');
		});
	};

	service.proceedToTransaction = function() {
		var items = [];
		_.forEach(model.items, function(item) {
			if (item.isSelected) {
				items.push(item.id);
			}
		});

		if (items.length === 0) {
			wydNotifyService.addError(
					'Please select minimum one order to proceed...', true);
			return;
		}

		var path = '/customers/customer/' + model.customer.id + '/tran/'
		path += items.join(',');

		$location.path(path);
	};

	service.init = function() {
		customers.push({
			id : 0,
			firstName : 'All'
		});
		_.forEach(sessionService.customers, function(item) {
			customers.push(item);
		});
		model.customer = customers[0];
		employees.push({
			id : 0,
			firstName : '<Select Employee>'
		});
		_.forEach(sessionService.employees, function(item) {
			employees.push(item);
		});
		model.employee = employees[0];
	};

	function fail(action, data, message) {
		$log.debug(message);
		$log.info(data);
		wydNotifyService.addError(message, true);
	}

	return service;
}
appServices.factory('customerOrderProcessService', customerOrderProcessService);