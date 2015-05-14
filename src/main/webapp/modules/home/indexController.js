function indexController($rootScope, $scope, $log, wydNotifyService) {
	$log.debug('indexController...');
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

	$scope.confirm = function() {
		var params = {
			title : 'Confirm',
			text : 'Are you sure to proceed?',
			type : 'warning',
			showCancelButton : true,
			confirmButtonText : 'Yes',
			cancelButtonText : 'No',
		};
		var callback = function() {
			$log.info('yes');
		};
		wydNotifyService.sweet.show(params, callback);
	}

}
appControllers.controller('indexController', indexController);
