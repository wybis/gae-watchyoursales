function ledgerController($rootScope, $scope, $log, sessionService) {
	$log.debug('ledgerController...');
	$rootScope.viewName = 'Ledgers';

	$scope.refresh = function() {
		$log.info('yet to implement...');
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();
}
appControllers.controller('ledgerController', ledgerController);
