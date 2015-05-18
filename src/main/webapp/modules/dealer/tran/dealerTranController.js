function dealerTranController($rootScope, $scope, $log, $location,
		$routeParams, dealerTranService) {
	$log.debug('dealerTranController...');
	$rootScope.viewName = 'Dealer Pay Or Collect';

	dealerTranService.init();
	$scope.receipt = dealerTranService.receipt;
	dealerTranService.setForUserById($routeParams.id);

	$scope.onAmount = dealerTranService.onAmount;
	$scope.saveReceiptAsTran = dealerTranService.saveReceiptAsTran;

	$scope.back = function() {
		$location.path('/counter');
	};

}
appControllers.controller('dealerTranController', dealerTranController);
