function customerOrderListController($rootScope, $scope, $log,
		customerOrderProcessService) {
	$log.debug('customerOrderListController...');
	$rootScope.viewName = 'Customer Orders';

	$scope.customers = customerOrderProcessService.customers;
	$scope.customersMap = customerOrderProcessService.customersMap;

	$scope.employees = customerOrderProcessService.employees;
	$scope.employeesMap = customerOrderProcessService.employeesMap;

	$scope.productsMap = customerOrderProcessService.productsMap;
	$scope.accountsMap = customerOrderProcessService.accountsMap;

	$scope.model = customerOrderProcessService.model;

	$scope.onCustomerChange = customerOrderProcessService.onCustomerChange;

	$scope.selectOrDeSelectAllReceipts = customerOrderProcessService.selectOrDeSelectAllReceipts;
	$scope.onReceiptSelectionChange = customerOrderProcessService.onReceiptSelectionChange;

	$scope.selectOrDeSelectAllItems = customerOrderProcessService.selectOrDeSelectAllItems;
	$scope.onItemSelectionChange = customerOrderProcessService.onItemSelectionChange;

	$scope.refreshReceipts = customerOrderProcessService.getPendingOrderReceipts;
	$scope.refreshItems = customerOrderProcessService.getPendingOrders;

	$scope.$on('session:properties', function(event, data) {
		customerOrderProcessService.init();
	});

	$scope.assign = customerOrderProcessService.assignToEmployee;
	$scope.accept = customerOrderProcessService.acceptByEmployee;

	$scope.proceed = customerOrderProcessService.proceedToTransaction;

	// customerOrderProcessService.init();
	$scope.refreshReceipts();
}
appControllers.controller('customerOrderListController',
		customerOrderListController);
