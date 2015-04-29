function logoutController($rootScope, $scope, $log, $http, $window) {
	$log.debug('logoutContoller...');
	$rootScope.viewName = 'SignOut';

	var path = 'sessions/logout';
	$http.get(path).success(function(response) {
		$window.location = 'index';
		// $log.info(response);
	}).error(function() {
		deferred.reject("unable to signgout...");
		$window.location = 'index';
	});
}
appControllers.controller('logoutController', logoutController);