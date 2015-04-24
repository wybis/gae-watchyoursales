function productController($rootScope, $scope, $log, sessionService) {
	$log.debug('productController...');
	$rootScope.viewName = 'Products';

	$scope.items = sessionService.products;

	$scope.refresh = function() {
		$log.info('yet to implement...');
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();
}
appControllers.controller('productController', productController);
