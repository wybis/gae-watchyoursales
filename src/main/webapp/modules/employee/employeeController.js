function employeeController($rootScope, $scope, $log, agencyService) {
	$rootScope.viewName = 'Employees';

	var agencyId = $rootScope.sessionContext.sessionUser.agencyId;

	$scope.refresh = function() {
		agencyService.getEmployees(agencyId).then(function(response) {
			var agency = agencyService.itemsMap[agencyId];
			$scope.items = agency.employees;
		});
	};

	$scope.refresh();

	$log.debug('employeeController...');
}
appControllers.controller('employeeController', employeeController);

