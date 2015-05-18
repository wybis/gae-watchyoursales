function customerTranController($rootScope, $scope, $log, $location,
		$routeParams, customerTranService) {
	$log.debug('customerTranController...');
	$rootScope.viewName = 'Customer Pay Or Collect';

	customerTranService.init();
	$scope.receipt = customerTranService.receipt;
	customerTranService.setForUserById($routeParams.id);

	$scope.onAmount = customerTranService.onAmount;
	$scope.saveReceiptAsTran = customerTranService.saveReceiptAsTran;

	$scope.back = function() {
		$location.path('/counter');
	};
}
appControllers.controller('customerTranController', customerTranController);
