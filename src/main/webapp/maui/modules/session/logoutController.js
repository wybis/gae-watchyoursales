function logoutController($rootScope, $scope, $log, $timeout, $http, $location) {
	$log.debug('logoutContoller...');
	$rootScope.viewName = 'SignOut';

	$timeout(function() {
		var path = 'sessions/logout';
		$http.get(path).success(function(response) {
			// $log.info(response);
			$location.path('/index');
			$rootScope.isLoggedIn = false;
			$rootScope.$emit('session:loggedout', 'Logged out...');
		}).error(function() {
			$location.path('/index');
			$rootScope.isLoggedIn = false;
			$rootScope.$emit('session:loggedout', 'Logged out...');
		});
	}, 1000);

}
appControllers.controller('logoutController', logoutController);