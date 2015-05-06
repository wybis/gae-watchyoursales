function homeController($rootScope, $scope, $log, wydNotifyService) {
	$log.debug('homeController...');
	$rootScope.viewName = 'Home';
}
appControllers.controller('homeController', homeController);
