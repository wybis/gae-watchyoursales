function customerOrderItemService($log, $q, wydNotifyService, sessionService,
		$http, $location) {
	var basePath = '/sessions'
	var service = {}, customers = [], employees = [], model = {}, flag = false;

	service.customers = customers;
	service.customersMap = sessionService.customersMap;

	service.productsMap = sessionService.productsMap;
	service.accountsMap = sessionService.accountsMap;

	model.customer = customers[0];

	model.isItemSelectedAll = false;
	model.items = [];

	service.model = model;

	service.selectOrDeSelectAll = function() {
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

	service.getPendingOrders = function(customerId) {
		var path = basePath + '/pendingCustomerOrders/' + customerId;
		$http.get(path).success(function(response) {
			$log.info(response);
			if (response.type === 0) {
				model.items = response.data;
			}
		})
	};

	service.onCustomerChange = function() {
		var path = '/customers/customer/';
		path += $scope.model.customer.id;
		path += '/orders';
		$location.path(path);
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

	service.init = function(customerId) {
		if (customers.length == 0) {
			_.forEach(sessionService.customers, function(item) {
				customers.push(item);
			});
		}
		model.customer = service.customersMap[customerId];
	};

	return service;
}
appServices.factory('customerOrderItemService', customerOrderItemService);