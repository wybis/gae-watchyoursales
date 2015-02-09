function customerController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Customers';

	$scope.items = employeeService.customers;

	$scope.refresh = function() {
		employeeService.getMyCustomers();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('customerController...');
}
appControllers.controller('customerController', customerController);
