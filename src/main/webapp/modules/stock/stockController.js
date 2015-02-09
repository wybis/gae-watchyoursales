function stockController($rootScope, $scope, $log, employeeService, $filter) {
	$rootScope.viewName = 'Stocks';

	$scope.productsMap = employeeService.productsMap;
	$scope.items = employeeService.stocks;

	$scope.refresh = function() {
		$log.info('yet to implement...');
	};

	$scope.refresh();

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$log.debug('stockController...');
}
appControllers.controller('stockController', stockController);
