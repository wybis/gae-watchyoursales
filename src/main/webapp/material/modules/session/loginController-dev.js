function loginController($rootScope, $scope, $log, $location, $http) {
	$log.debug('loginController...');
	$rootScope.viewName = 'SignIn';

	$scope.message = null

	$scope.user = {
		userId : 'munmin2000@maxmoney',
		password : ''
	};
	$scope.users = [];

	function signIn() {
		$log.info('singing in...');
		$scope.message = null;

		$log.info($scope.user);

		$rootScope.isLoggedIn = true;

		var path = 'sessions/login';
		$http.post(path, $scope.user).success(function(response) {
			$log.info(response);
			if (response.type === 1) {
				$scope.message = response.message;
			} else {
				var roleId = response.data.sessionDto.roleId;
				$location.path('/home');
				$rootScope.$emit('session:loggedin', 'Logged in...');
			}
		});
	}
	$scope.signIn = signIn;

	$scope.refresh = function() {
		var path = '/userIds.groovy';
		$http.get(path).success(function(response) {
			// $log.info(response);
			if (response.type === 0) {
				$scope.users = response.data;
			}
		})
	};

	$scope.refresh();
}
appControllers.controller('loginController', loginController);
