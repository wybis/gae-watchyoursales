function dealerController($rootScope, $scope, $log, agencyService) {
	$rootScope.viewName = 'Dealers';

	var agencyId = $rootScope.sessionContext.sessionUser.agencyId;

	$scope.refresh = function() {
		agencyService.getDealers(agencyId).then(function(response) {
			var agency = agencyService.itemsMap[agencyId];
			$scope.items = agency.dealers;
		});
	};

	$scope.refresh();

	$log.debug('dealerController...');
}
appControllers.controller('dealerController', dealerController);
