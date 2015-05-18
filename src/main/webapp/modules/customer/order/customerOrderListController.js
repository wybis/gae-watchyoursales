function customerOrderListController($rootScope, $scope, $log,
		customerOrderService) {
	$log.debug('customerOrderListController...');
	$rootScope.viewName = 'Customer Orders';

	$scope.productsMap = customerOrderService.productsMap;
	$scope.accountsMap = customerOrderService.accountsMap;
	$scope.customers = customerOrderService.customers;
	$scope.customersMap = customerOrderService.customersMap;
	$scope.searchCriteria = customerOrderService.searchCriteria;
	$scope.searchResult = customerOrderService.searchResult;

	$scope.onCustomerChange = customerOrderService.onCustomerChange;
	$scope.selectOrDeSelectAll = customerOrderService.selectOrDeSelectAll;

	$scope.onOrderSelectionChange = customerOrderService.onOrderSelectionChange;

	$scope.refresh = customerOrderService.getPendingOrders;

	$scope.proceed = customerOrderService.proceedToProcessOrder;

	$scope.refresh();

}
appControllers.controller('customerOrderListController',
		customerOrderListController);
