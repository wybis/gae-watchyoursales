function stockController($rootScope, $scope, $log, agencyService) {
	$rootScope.viewName = 'Stocks';

	var agencyId = $rootScope.sessionContext.sessionUser.agencyId;

	$scope.refresh = function() {
		agencyService.getStocks(agencyId).then(function(response) {
			var agency = agencyService.itemsMap[agencyId];
			$scope.productsMap = agency.productsMap;
			$scope.items = agency.stocks;
		});
	};

	$scope.refresh();

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$log.debug('stockController...');
}
appControllers.controller('stockController', stockController);
