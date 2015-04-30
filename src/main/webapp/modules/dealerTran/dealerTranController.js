function dealerTranController($rootScope, $scope, $log, sessionService, $http) {
	$log.debug('dealerTranController...');
	$rootScope.viewName = 'Dealer Transactions';

	$scope.accountsMap = sessionService.accountsMap;
	
	$scope.items = [];

	function processTrans(trans) {
		$scope.items = trans;
	}

	$scope.refresh = function() {
		var path = '/sessions/dealerTransactions';
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processTrans(response.data);
			}
			// $log.info(response);
		})
	};

	$scope.refresh();

}
appControllers.controller('dealerTranController', dealerTranController);
