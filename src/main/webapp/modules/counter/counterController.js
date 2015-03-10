function homeController($rootScope, $scope, $log) {
	$rootScope.viewName = 'Counter';

	$log.debug('counterController...');
}
appControllers.controller('counterController', homeController);
