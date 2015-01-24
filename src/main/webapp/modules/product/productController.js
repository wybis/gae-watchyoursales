function productController($rootScope, $scope, $log, agencyService) {
	$rootScope.viewName = 'Products';

	var agencyId = $rootScope.sessionContext.sessionUser.agencyId;

	$scope.refresh = function() {
		agencyService.getProducts(agencyId).then(function(response) {
			var agency = agencyService.itemsMap[agencyId];
			$scope.items = agency.products;
		});
	};

	$scope.refresh();

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$log.debug('productController...');
}
appControllers.controller('productController', productController);
