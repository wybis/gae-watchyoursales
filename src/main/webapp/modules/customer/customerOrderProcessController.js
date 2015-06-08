function customerOrderProcessController($rootScope, $scope, $log,
		customerOrderService, $location) {
	$log.debug('customerOrderProcessController...');
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
appControllers.controller('customerOrderProcessController',
		customerOrderProcessController);
