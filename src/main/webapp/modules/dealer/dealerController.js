function dealerController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Dealers';

	$scope.items = employeeService.dealers;

	$scope.refresh = function() {
		employeeService.getMyDealers();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('dealerController...');
}
appControllers.controller('dealerController', dealerController);
