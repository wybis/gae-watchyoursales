function settingController($rootScope, $scope, $log) {
	$log.debug('settingController...');
	$rootScope.viewName = 'Settings';

}
appControllers.controller('settingController', settingController);
