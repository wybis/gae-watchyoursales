function indexController($rootScope, $scope, $log, wydNotifyService) {
	$rootScope.viewName = 'Home';

	$scope.success = function() {
		wydNotifyService.addSuccess("Success message...", true);
	};

	$scope.info = function() {
		wydNotifyService.addInfo("Info message...", true);
	};

	$scope.warning = function() {
		wydNotifyService.addWarning("Warning message...", true);
	};

	$scope.error = function() {
		wydNotifyService.addError("Error message...", true);
	};

	$log.debug('indexController...');
}
appControllers.controller('indexController', indexController);
