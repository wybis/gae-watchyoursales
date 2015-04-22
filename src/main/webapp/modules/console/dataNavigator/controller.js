function dataNavigatorController($rootScope, $scope, $log, dataService) {
	$log.debug('dataNavigatorController...');
	$rootScope.viewName = 'Data Navigator';

	$scope.items = dataService.branchs;

	$scope.refresh = function() {
		dataService.getBranchs();
	};

	$scope.refresh();
}
appControllers.controller('dataNavigatorController', dataNavigatorController);
