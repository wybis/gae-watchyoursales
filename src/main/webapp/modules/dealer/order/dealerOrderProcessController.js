function dealerOrderProcessController($rootScope, $scope, $log,
		dealerOrderService, $location) {
	$log.debug('dealerOrderProcessController...');
	$rootScope.viewName = 'Proccess Dealer Orders';

	$scope.productsMap = dealerOrderService.productsMap;
	$scope.accountsMap = dealerOrderService.accountsMap;

	$scope.receipt = dealerOrderService.receipt;
	$scope.removeTran = dealerOrderService.removeTran;
	$scope.onTranSelect = dealerOrderService.onTranSelect;
	$scope.onTranUnit = dealerOrderService.onTranUnit;
	$scope.onCustomerAmount = dealerOrderService.onCustomerAmount;
	$scope.saveReceiptAsTransaction = dealerOrderService.saveReceiptAsTransaction;
	$scope.printReceipt = dealerOrderService.printReceipt

	$scope.cancel = function() {
		$location.path('/dealerOrders');
	};
}
appControllers.controller('dealerOrderProcessController',
		dealerOrderProcessController);
