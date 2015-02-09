function employeeController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Employees';

	$scope.items = employeeService.employees;

	$scope.refresh = function() {
		employeeService.getMyEmployees();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('employeeController...');
}
appControllers.controller('employeeController', employeeController);
