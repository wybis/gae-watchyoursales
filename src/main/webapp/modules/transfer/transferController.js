function transferController($rootScope, $scope, $log, sessionService) {
	$log.debug('transferController...');
	$rootScope.viewName = 'Transfer';

	$scope.refresh = function() {
		$log.info('yet to implement...');
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();
}
appControllers.controller('transferController', transferController);
