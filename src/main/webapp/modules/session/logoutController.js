function logoutController($rootScope, $scope, $log, $http, $window) {
	$rootScope.viewName = 'SignOut';

	var path = 'sessions/logout';
	$http.get(path).success(function(response) {
		$window.location = 'index-d.html';
		// $log.info(response);
	}).error(function() {
		deferred.reject("unable to logout...");
	});

	$log.debug('logoutContoller...');
}
appControllers.controller('logoutController', logoutController);