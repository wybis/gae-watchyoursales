function customerController($rootScope, $scope, $log, agencyService) {
	$rootScope.viewName = 'Customers';

	var agencyId = $rootScope.sessionContext.sessionUser.agencyId;

	$scope.refresh = function() {
		agencyService.getCustomers(agencyId).then(function(response) {
			var agency = agencyService.itemsMap[agencyId];
			$scope.items = agency.customers;
		});
	};

	$scope.refresh();

	$log.debug('customerController...');
}
appControllers.controller('customerController', customerController);
