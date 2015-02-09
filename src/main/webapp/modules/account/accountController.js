function accountController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Accounts';

	$scope.items = employeeService.accounts;

	$scope.refresh = function() {
		employeeService.getMyAccounts();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('accountController...');
}
appControllers.controller('accountController', accountController);
