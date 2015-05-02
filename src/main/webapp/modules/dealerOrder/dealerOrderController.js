function dealerOrderController($rootScope, $scope, $log, sessionService, $http) {
	$log.debug('dealerOrderController...');
	$rootScope.viewName = 'Dealer Orders';

	$scope.productsMap = sessionService.productsMap;
	$scope.accountsMap = sessionService.accountsMap;

	$scope.items = [];

	function processOrders(orders) {
		$scope.items = orders;
	}

	$scope.refresh = function() {
		var path = '/sessions/pendingDealerOrders';
		$http.get(path).success(function(response) {
			$log.info(response);
			if (response.type === 0) {
				processOrders(response.data);
			}
		})
	};

	$scope.refresh();
}
appControllers.controller('dealerOrderController', dealerOrderController);
