function customerOrderController($rootScope, $scope, $log, customerOrderService) {
	$log.debug('customerOrderController...');
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
appControllers.controller('customerOrderController', customerOrderController);

function processCustomerOrderController($rootScope, $scope, $log,
		customerOrderService, $location) {
	$log.debug('processCustomerOrderController...');
	$rootScope.viewName = 'Proccess Customer Orders';

	$scope.productsMap = customerOrderService.productsMap;
	$scope.accountsMap = customerOrderService.accountsMap;

	$scope.receipt = customerOrderService.receipt;
	$scope.removeTran = customerOrderService.removeTran;
	$scope.onTranSelect = customerOrderService.onTranSelect;
	$scope.onTranUnit = customerOrderService.onTranUnit;
	$scope.onCustomerAmount = customerOrderService.onCustomerAmount;
	$scope.saveReceiptAsTransaction = customerOrderService.saveReceiptAsTransaction;
	$scope.printReceipt = customerOrderService.printReceipt

	$scope.cancel = function() {
		$location.path('/customerOrders');
	};
}
appControllers.controller('processCustomerOrderController',
		processCustomerOrderController);
