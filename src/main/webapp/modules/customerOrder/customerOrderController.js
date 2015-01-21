function customerOrderController($rootScope, $scope, $log, agencyService) {
	$rootScope.viewName = 'Customer Orders';

	// var agencyId = $rootScope.sessionContext.sessionUser.agencyId;
	//
	// $scope.refresh = function() {
	// agencyService.getItems(agencyId).then(function(response) {
	// var agency = agencyService.itemsMap[agencyId];
	// $scope.items = agency.items;
	// });
	// };
	//
	// $scope.refresh();
	//
	// $scope.bottomReached = function() {
	// $log.info('bottom reached...');
	// }
	//
	$log.debug('customerOrderController...');
}
appControllers.controller('customerOrderController', customerOrderController);
