function ledgerTranController($rootScope, $scope, $log, sessionService) {
	$log.debug('ledgerTranController...');
	$rootScope.viewName = 'Ledger Transactionss';

	$scope.refresh = function() {
		$log.info('yet to implement...');
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();
}
appControllers.controller('ledgerTranController', ledgerTranController);
