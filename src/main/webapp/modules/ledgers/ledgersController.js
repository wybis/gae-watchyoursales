function ledgersController($rootScope, $scope, $log, wydNotifyService,
		sessionService) {
	$log.debug('ledgersController...');
	$rootScope.viewName = 'Ledgers';

	$scope.refresh = function() {
	};

	$scope.refresh();
}
appControllers.controller('ledgersController', ledgersController);
