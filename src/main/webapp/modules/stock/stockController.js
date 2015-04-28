function stockController($rootScope, $scope, $log, sessionService) {
	$log.debug('stockController...');
	$rootScope.viewName = 'Stocks';

	$scope.productsMap = sessionService.productsMap;
	$scope.items = sessionService.stocks;

	$scope.refresh = function() {
		$log.info('yet to implement...');
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	//$scope.refresh();
}
appControllers.controller('stockController', stockController);
