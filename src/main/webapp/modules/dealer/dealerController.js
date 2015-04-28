function dealerController($rootScope, $scope, $log, sessionService) {
	$rootScope.viewName = 'Dealers';

	$scope.items = sessionService.dealers;

	$scope.refresh = function() {
		sessionService.getDealers();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	//$scope.refresh();

	$log.debug('dealerController...');
}
appControllers.controller('dealerController', dealerController);
