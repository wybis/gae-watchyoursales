function employeeListController($rootScope, $scope, $log, sessionService) {
	$log.debug('employeeListController...');
	$rootScope.viewName = 'Employees';

	$scope.items = sessionService.employees;

	$scope.refresh = function() {
		sessionService.getEmployees();
	};

	// $scope.refresh();

}
appControllers.controller('employeeListController', employeeListController);

function employeeViewController($rootScope, $scope, $log, $location,
		sessionService) {
	$log.debug('employeeVeiwController...');
	$rootScope.viewName = 'View Employee';

	$scope.back = function() {
		$location.path('/counter');
	};
}
appControllers.controller('employeeViewController', employeeViewController);

function employeeEditController($rootScope, $scope, $log, $location,
		sessionService) {
	$log.debug('employeeEditController...');
	$rootScope.viewName = 'Edit Employee';

	$scope.back = function() {
		$location.path('/counter');
	};
}
appControllers.controller('employeeEditController', employeeEditController);

function employeeSearchController($rootScope, $scope, $log, $location,
		sessionService, counterService) {
	$log.debug('employeeSearchController...');
	$rootScope.viewName = 'Search Employee';

	$scope.items = sessionService.employees;

	$scope.isPhysicalUser = function(user) {
		return _.startsWith(user.userId, user.branchId) == false;
	};

	$scope.select = function(employeeId) {
		var employee = sessionService.employeesMap[employeeId];
		counterService.setEmployee(employee);
		$scope.back();
	};

	$scope.back = function() {
		$location.path('/counter');
	};

	$scope.refresh = function() {
		sessionService.getEmployees();
	};

	$log.info($scope.items);
	// $scope.refresh();
}
appControllers.controller('employeeSearchController', employeeSearchController);
