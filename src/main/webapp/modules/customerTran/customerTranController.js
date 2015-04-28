function customerTranController($rootScope, $scope, $log, sessionService, $http) {
	$log.debug('customerTranController...');
	$rootScope.viewName = 'Customer Transactions';

	$scope.items = [];

	function processTrans(trans) {
		$scope.items = trans;
	}

	$scope.refresh = function() {
		var path = '/sessions/customerTransactions';
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processTrans(response.data);
			}
			//$log.info(response);
		})
	};

	$scope.refresh();
}
appControllers.controller('customerTranController', customerTranController);
