function customerOrderService($log, $q, wydNotifyService, sessionService,
		$http, $location) {

	var service = {}, customers = [], searchCriteria = {}, searchResult = {};

	customers = sessionService.customers;
	customers.unshift({
		id : 0,
		firstName : 'All'
	});
	service.customers = customers;

	service.productsMap = sessionService.productsMap;
	service.accountsMap = sessionService.accountsMap;

	searchCriteria.isSelectedAll = false;
	searchCriteria.customer = customers[0];
	service.searchCriteria = searchCriteria;

	searchResult.items = [];
	service.searchResult = searchResult;

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

	service.proceedToProcessOrder = function() {
		service.selectedOrders = _.filter(searchResult.items, function(item) {
			return item.isSelected;
		});
		$log.info(service.selectedOrders);
		if (service.selectedOrders.length === 0) {
			wydNotifyService.addError(
					'Please select minimum one order to proceed...', true);
			return;
		}
		$location.path('/processCustomerOrders');
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
			// $log.info(response);
			if (response.type === 0) {
				processOrders(response.data);
			}
		})
	};

	return service;
}
appServices.factory('customerOrderService', customerOrderService);