function employeeController($rootScope, $scope, $log, sessionService) {
	$rootScope.viewName = 'Employees';

	$scope.items = sessionService.employees;

	$scope.refresh = function() {
		sessionService.getEmployees();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('employeeController...');
}
appControllers.controller('employeeController', employeeController);
