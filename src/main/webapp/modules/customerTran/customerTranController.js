function customerTranController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Customer Transactions';

	$scope.refresh = function() {
		employeeService.getMyCustomerTransactions().then(function(response) {
			$scope.items = response.data;
		});
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('customerTranController...');
}
appControllers.controller('customerTranController', customerTranController);
