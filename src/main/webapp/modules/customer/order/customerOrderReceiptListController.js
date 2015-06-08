function customerOrderReceiptListController($rootScope, $scope, $log,
		customerOrderReceiptService, sessionService) {
	$log.debug('customerOrderReceiptListController...');
	$rootScope.viewName = 'Customer Orders';

	if (sessionService.context.sessionDto) {
		$scope.roleId = sessionService.context.sessionDto.roleId;
	}

	$scope.customersMap = customerOrderReceiptService.customersMap;

	$scope.employees = customerOrderReceiptService.employees;
	$scope.employeesMap = customerOrderReceiptService.employeesMap;

	$scope.productsMap = customerOrderReceiptService.productsMap;
	$scope.accountsMap = customerOrderReceiptService.accountsMap;

	$scope.model = customerOrderReceiptService.model;

	$scope.assign = customerOrderReceiptService.assignToEmployee;
	$scope.accept = customerOrderReceiptService.acceptByEmployee;

	$scope.refresh = customerOrderReceiptService.getPendingOrderReceipts;

	$scope.$on('session:properties', function(event, data) {
		customerOrderReceiptService.init();
		$scope.roleId = sessionService.context.sessionDto.roleId;
	});

	$scope.refresh()
	// customerOrderReceiptService.init();

}
appControllers.controller('customerOrderReceiptListController',
		customerOrderReceiptListController);
