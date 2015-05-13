function dealerOrderController($rootScope, $scope, $log, dealerOrderService) {
	$log.debug('dealerOrderController...');
	$rootScope.viewName = 'Dealer Orders';

	$scope.productsMap = dealerOrderService.productsMap;
	$scope.accountsMap = dealerOrderService.accountsMap;
	$scope.dealers = dealerOrderService.dealers;
	$scope.dealersMap = dealerOrderService.dealersMap;
	$scope.searchCriteria = dealerOrderService.searchCriteria;
	$scope.searchResult = dealerOrderService.searchResult;

	$scope.onDealerChange = dealerOrderService.onDealerChange;
	$scope.selectOrDeSelectAll = dealerOrderService.selectOrDeSelectAll;

	$scope.onOrderSelectionChange = dealerOrderService.onOrderSelectionChange;

	$scope.refresh = dealerOrderService.getPendingOrders;

	$scope.proceed = dealerOrderService.proceedToProcessOrder;

	$scope.refresh();

}
appControllers.controller('dealerOrderController', dealerOrderController);

function processDealerOrderController($rootScope, $scope, $log,
		dealerOrderService, $location) {
	$log.debug('processDealerOrderController...');
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
appControllers.controller('processDealerOrderController',
		processDealerOrderController);
