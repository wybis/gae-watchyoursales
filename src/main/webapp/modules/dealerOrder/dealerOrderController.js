function dealerOrderController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Dealer Orders';

	$scope.refresh = function() {
		employeeService.getMyPendingDealerOrders().then(function(response) {
			$scope.items = response.data;
		});
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('dealerOrderController...');
}
appControllers.controller('dealerOrderController', dealerOrderController);
