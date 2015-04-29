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
			$log.info(response);
			if (response.type === 1) {
				$scope.message = response.message;
				wydNotifyService.addError($scope.message, true);
			} else {
				var roleId = response.data.sessionDto.roleId;
				var pathId = '/home-d-customer.html'
				if (roleId == 'manager') {
					pathId = '/home-d-manager.html'
				}
				if (roleId == 'employee') {
					pathId = '/home-d-employee.html'
				}
				if (roleId == 'dealer') {
					pathId = '/home-d-dealer.html'
				}
				$window.location = pathId;
			}
		})
	}
	$scope.signin = signin;

	// $timeout(function() {
	$scope.user.userId = 'munmin2000@maxmoney';
	// $scope.signin();
	// }, 1000);

	$log.debug('loginController...');
}
appControllers.controller('loginController', loginController);
