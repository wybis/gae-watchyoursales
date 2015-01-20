function accountController($rootScope, $scope, $log, agencyService) {
	$rootScope.viewName = 'Accounts';

	var agencyId = $rootScope.sessionContext.sessionUser.agencyId;

	$scope.refresh = function() {
		agencyService.getAccounts(agencyId).then(function(response) {
			var agency = agencyService.itemsMap[agencyId];
			$scope.items = agency.accounts;
		});
	};

	$scope.refresh();

	$log.debug('accountController...');
}
appControllers.controller('accountController', accountController);
