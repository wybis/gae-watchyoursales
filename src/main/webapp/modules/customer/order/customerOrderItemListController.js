function customerOrderItemListController($rootScope, $scope, $log,
		customerOrderItemService, $routeParams) {
	$log.debug('customerOrderItemListController...');
	$rootScope.viewName = 'Customer Orders';

	$scope.customers = customerOrderItemService.customers;
	$scope.customersMap = customerOrderItemService.customersMap;

	$scope.productsMap = customerOrderItemService.productsMap;
	$scope.accountsMap = customerOrderItemService.accountsMap;

	$scope.model = customerOrderItemService.model;

	$scope.selectOrDeSelectAll = customerOrderItemService.selectOrDeSelectAll;
	$scope.onItemSelectionChange = customerOrderItemService.onItemSelectionChange;

	$scope.onCustomerChange = customerOrderItemService.onCustomerChange;

	$scope.proceed = customerOrderItemService.proceedToTransaction;

	$scope.refresh = function() {
		customerOrderItemService.getPendingOrders($routeParams.id);
	};

	$scope.$on('session:properties', function(event, data) {
		customerOrderItemService.init($routeParams.id);
	});

	$scope.model.isItemSelectedAll = false;
	$scope.refresh();

	customerOrderItemService.init($routeParams.id);
}
appControllers.controller('customerOrderItemListController',
		customerOrderItemListController);
