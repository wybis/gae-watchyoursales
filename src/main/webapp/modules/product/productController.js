function productController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Products';

	$scope.items = employeeService.products;

	$scope.refresh = function() {
		$log.info('yet to implement...');
	};

	$scope.refresh();

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$log.debug('productController...');
}
appControllers.controller('productController', productController);
