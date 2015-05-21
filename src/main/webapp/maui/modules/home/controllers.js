function indexController($rootScope, $scope, $log) {
	$log.debug('indexController...');
	$rootScope.viewName = 'Home';
}
appControllers.controller('indexController', indexController);

function homeController($rootScope, $scope, $log) {
	$log.debug('homeController...');
	$rootScope.viewName = 'Home';
}
appControllers.controller('homeController', homeController);
