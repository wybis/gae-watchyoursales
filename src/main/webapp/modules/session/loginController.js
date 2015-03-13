function loginController($rootScope, $scope, $log, $window, $sessionStorage,
		wydNotifyService, $http, $timeout) {
	$rootScope.viewName = 'SignIn';

	$scope.message = null

	$scope.user = {
		userId : '',
		password : ''
	};

	function signin() {
		wydNotifyService.removeAll();
		$log.info('singing in...');
		$scope.message = null;

		var path = 'sessions/login';
		$http.post(path, $scope.user).success(function(response) {
			if (response.type === 1) {
				$scope.message = response.message;
				wydNotifyService.addError($scope.message, true);
			} else {
				$window.location = 'home-d.html';
			}
			// $log.info(response);
		}).error(function() {
			deferred.reject("unable to login...");
		});
	}
	$scope.signin = signin;

		$timeout(function() {
		$log.info('Before signin...');
		$scope.user.userId = 'munmin2000@maxmoney';
		$scope.signin();
		$log.info('After signin...');
	}, 1000);

	$log.debug('loginController...');
}
appControllers.controller('loginController', loginController);
