function dealerTranController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Dealer Transactions';

	$scope.refresh = function() {
		employeeService.getMyDealerTransactions().then(function(response) {
			$scope.items = response.data;
		});
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('dealerTranController...');
}
appControllers.controller('dealerTranController', dealerTranController);
