function customerOrderController($rootScope, $scope, $log, customerOrderService) {
	$log.debug('customerOrderController...');
	$rootScope.viewName = 'Customer Orders';

	$scope.productsMap = customerOrderService.productsMap;
	$scope.accountsMap = customerOrderService.accountsMap;

	$scope.customers = customerOrderService.customers;
	$scope.searchCriteria = customerOrderService.searchCriteria;
	$scope.searchResult = customerOrderService.searchResult;

	$scope.onCustomerChange = customerOrderService.onCustomerChange;
	$scope.selectOrDeSelectAll = customerOrderService.selectOrDeSelectAll;

	$scope.onOrderSelectionChange = customerOrderService.onOrderSelectionChange;

	$scope.refresh = customerOrderService.getPendingOrders;

	$scope.proceed = customerOrderService.proceedToProcessOrder;

	$scope.refresh();

}
appControllers.controller('customerOrderController', customerOrderController);

function processCustomerOrderController($rootScope, $scope, $log,
		customerOrderService, $location) {
	$log.debug('processCustomerOrderController...');
	$rootScope.viewName = 'Proccess Customer Orders';

	$scope.productsMap = customerOrderService.productsMap;
	$scope.accountsMap = customerOrderService.accountsMap;

	$scope.orders = customerOrderService.selectedOrders

	$scope.cancel = function() {
		$location.path('/customerOrders');
	};
}
appControllers.controller('processCustomerOrderController',
		processCustomerOrderController);
