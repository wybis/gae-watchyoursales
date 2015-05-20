function indexController($rootScope, $scope, $log) {
	$log.debug('indexController...');
	$rootScope.viewName = 'Home';
}
appControllers.controller('indexController', indexController);
