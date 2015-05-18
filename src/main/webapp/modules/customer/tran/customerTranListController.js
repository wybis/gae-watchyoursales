function customerTranListController($rootScope, $scope, $log, sessionService,
		$http) {
	$log.debug('customerTranListController...');
	$rootScope.viewName = 'Customer Transactions';

	$scope.productsMap = sessionService.productsMap;
	$scope.accountsMap = sessionService.accountsMap;

	$scope.items = [];

	function processTrans(trans) {
		$scope.items = trans;
	}

	$scope.refresh = function() {
		var path = '/sessions/customerTrans';
		$http.get(path).success(function(response) {
			// $log.info(response);
			if (response.type === 0) {
				processTrans(response.data);
			}
		})
	};

	$scope.refresh();
}
appControllers.controller('customerTranListController',
		customerTranListController);
