function homeController($rootScope, $scope, $log) {
	$rootScope.viewName = 'Home';

	$log.debug('homeController...');
}
appControllers.controller('homeController', homeController);
