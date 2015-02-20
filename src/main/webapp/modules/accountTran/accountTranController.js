function accountTranController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Account Transactions';

	$scope.refresh = function() {
		employeeService.getMyAccountTransactions().then(function(response) {
			$scope.items = response.data;
		});
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('accountTranController...');
}
appControllers.controller('accountTranController', accountTranController);
