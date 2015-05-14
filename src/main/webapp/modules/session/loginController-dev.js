function loginController($rootScope, $scope, $log, $window, wydNotifyService,
		$http, $timeout) {
	$log.debug('loginController...');
	$rootScope.viewName = 'SignIn';

	$scope.message = null

	$scope.model = {
		user : {
			userId : 'munmin2000@maxmoney',
			password : ''
		}
	};
	$scope.users = [];

	function signin() {
		wydNotifyService.removeAll();
		$log.info('singing in...');
		$scope.message = null;

		$log.info($scope.model.user);

		var path = 'sessions/login';
		$http.post(path, $scope.model.user).success(function(response) {
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
		});
	}
	$scope.signin = signin;

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
