function customerOrderController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Customer Orders';

	$scope.refresh = function() {
		employeeService.getMyPendingCustomerOrders().then(function(response) {
			$scope.items = response.data;
		});
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('customerOrderController...');
}
appControllers.controller('customerOrderController', customerOrderController);
