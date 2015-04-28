function ledgerTranController($rootScope, $scope, $log, sessionService, $http) {
	$log.debug('ledgerTranController...');
	$rootScope.viewName = 'Ledger Transactionss';

	$scope.accountsMap = sessionService.accountsMap;
	$scope.items = [];

	function processTrans(trans) {
		$scope.items = trans;
	}

	$scope.refresh = function() {
		var path = '/sessions/ledgerTransactions';
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processTrans(response.data);
			}
			// $log.info(response);
		})
	};

	$scope.refresh();
}
appControllers.controller('ledgerTranController', ledgerTranController);
