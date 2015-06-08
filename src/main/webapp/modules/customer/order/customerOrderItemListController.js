function customerOrderItemListController($rootScope, $scope, $log,
		customerOrderItemService, $routeParams, $location) {
	$log.debug('customerOrderItemListController...');
	$rootScope.viewName = 'Customer Orders';

	$scope.customers = customerOrderItemService.customers;
	$scope.customersMap = customerOrderItemService.customersMap;

	$scope.employees = customerOrderItemService.employees;
	$scope.employeesMap = customerOrderItemService.employeesMap;

	$scope.productsMap = customerOrderItemService.productsMap;
	$scope.accountsMap = customerOrderItemService.accountsMap;

	$scope.model = customerOrderItemService.model;

	$scope.selectOrDeSelectAll = customerOrderItemService.selectOrDeSelectAll;
	$scope.onItemSelectionChange = customerOrderItemService.onItemSelectionChange;

	$scope.proceed = customerOrderItemService.proceedToTransaction;

	$scope.refresh = function() {
		customerOrderItemService.getPendingOrders($routeParams.id);
	};

	$scope.onCustomerChange = function() {
		var path = '';
		if ($scope.model.customer.id === 0) {
			path = '/customers/orders';
		} else {
			path = '/customers/customer/';
			path += $scope.model.customer.id;
			path += '/orders';
		}
		$location.path(path);
	};

	// customerOrderItemService.init();
	$scope.$on('session:properties', function(event, data) {
		customerOrderItemService.init();
	});

	$scope.model.isItemSelectedAll = false;
	$scope.refresh();

	customerOrderItemService.init();
}
appControllers.controller('customerOrderItemListController',
		customerOrderItemListController);
