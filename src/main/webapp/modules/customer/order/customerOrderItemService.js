function customerOrderItemService($log, $q, wydNotifyService, sessionService,
		$http) {
	var basePath = '/sessions'
	var service = {}, customers = [], employees = [], model = {}, flag = false;

	service.customers = customers;
	service.customersMap = sessionService.customersMap;
	service.employees = employees;
	service.employeesMap = sessionService.employeesMap;

	service.productsMap = sessionService.productsMap;
	service.accountsMap = sessionService.accountsMap;

	model.customer = customers[0];
	model.employee = employees[0];

	model.isSelectedAll = false;
	model.items = [];

	service.model = model;

	service.selectOrDeSelectAll = function() {
		_.forEach(model.items, function(item) {
			item.isSelected = model.isSelectedAll;
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
		model.isSelectedAll = flag;
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

		if (customers.length == 0) {
			customers.push({
				id : 0,
				firstName : 'All'
			});
			_.forEach(sessionService.customers, function(item) {
				customers.push(item);
			});
			model.customer = customers[0];
		}

		if (employees.length == 0) {
			employees.push({
				id : 0,
				firstName : '<Select Employee>'
			});
			_.forEach(sessionService.employees, function(item) {
				employees.push(item);
			});
			model.employee = employees[0];
		}

	};

	function fail(action, data, message) {
		$log.debug(message);
		$log.info(data);
		wydNotifyService.addError(message, true);
	}

	return service;
}
appServices.factory('customerOrderItemService', customerOrderItemService);