function dealerOrderListController($rootScope, $scope, $log, dealerOrderService) {
	$log.debug('dealerOrderListController...');
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
appControllers.controller('dealerOrderListController',
		dealerOrderListController);

