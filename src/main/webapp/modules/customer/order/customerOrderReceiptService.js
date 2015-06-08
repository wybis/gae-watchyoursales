function customerOrderReceiptService($log, $q, wydNotifyService,
		sessionService, $http) {
	var basePath = '/sessions'
	var service = {}, model = {};

	service.customersMap = sessionService.customersMap;
	service.employees = sessionService.employees;
	service.employeesMap = sessionService.employeesMap;

	service.productsMap = sessionService.productsMap;
	service.accountsMap = sessionService.accountsMap;

	model.items = [];

	service.model = model;

	service.onEmployeeChange = function(receipt) {
		var oEmployee = service.employeesMap[receipt.byUserId];
		var nEmployee = receipt.byUser;
		$log.info(oEmployee.id + ' = ' + nEmployee.id);
		service.assignToEmployee(receipt);
	};

	service.processOrderReceipt = function(receipt) {
		var sesUserId = sessionService.context.sessionDto.id;
		if (receipt.byUserId === sesUserId && receipt.status == 'assigned') {
			receipt.isAcceptDisabled = false;
		} else {
			receipt.isAcceptDisabled = true;
		}
		receipt.byUser = service.employeesMap[receipt.byUserId]
	};

	function processOrderReceipts(items) {
		var sesUserId = sessionService.context.sessionDto.id;
		model.items = items;
		_.forEach(items, function(item) {
			service.processOrderReceipt(item);
		});
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

	service.assignToEmployee = function(receipt) {
		var oEmployee = service.employeesMap[receipt.byUserId];
		var nEmployee = receipt.byUser;
		$log.info(oEmployee.id + ' = ' + nEmployee.id);

		var receiptIds = [ receipt.id ];
		$log.info("order assign started...")
		$log.info(receiptIds);

		var path = '/sessions/assignOrders/' + receipt.byUser.id;
		$http.post(path, receiptIds).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				assignSuccess(receipt, response)
			} else {
				assignFail(receipt, response)
			}
			// service.getPendingOrderReceipts();
			$log.info('order assign finished...');
		});
	};

	function assignSuccess(receipt, response) {
		wydNotifyService.addSuccess(response.message, true);
		receipt.byUserId = receipt.byUser.id;
		receipt.status = 'assigned';
		service.processOrderReceipt(receipt);
	}

	function assignFail(receipt, response) {
		wydNotifyService.addError(response.message, true);
		receipt.byUser = service.employeesMap[receipt.byUserId];
	}

	service.acceptByEmployee = function(receipt) {
		var receiptIds = [ receipt.id ];
		$log.info("order accept started...")
		$log.info(receiptIds);

		var path = '/sessions/acceptOrders/' + receipt.byUserId;
		$http.post(path, receiptIds).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				acceptSuccess(receipt, response);
			} else {
				acceptFail(receipt, response);
			}
			// service.getPendingOrderReceipts();
			$log.info('order accept finished...');
		});
	};

	function acceptSuccess(receipt, response) {
		wydNotifyService.addSuccess(response.message, true);
		receipt.status = 'accepted';
		service.processOrderReceipt(receipt);
		_.forEach(response.data, function(item) {
			sessionService.updateAccount(item)
		});
	}

	function acceptFail(receipt, response) {
		wydNotifyService.addError(response.message, true);
	}

	service.init = function() {

	};

	return service;
}
appServices.factory('customerOrderReceiptService', customerOrderReceiptService);