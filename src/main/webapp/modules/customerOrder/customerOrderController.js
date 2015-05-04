function customerOrderController($rootScope, $scope, $log, sessionService,
	$http) {
	$log.debug('customerOrderController...');
	$rootScope.viewName = 'Customer Orders';

	$scope.productsMap = sessionService.productsMap;
	$scope.accountsMap = sessionService.accountsMap;

	$scope.items = [];

	function processOrders(orders) {
		$scope.items = orders;
	}

	$scope.refresh = function () {
		var path = '/sessions/pendingCustomerOrders';
		$http.get(path).success(function (response) {
			// $log.info(response);
			if (response.type === 0) {
				processOrders(response.data);
			}
		})
	};

	$scope.refresh();

}
appControllers.controller('customerOrderController', customerOrderController);
