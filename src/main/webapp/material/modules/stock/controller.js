function stockListController($rootScope, $scope, $log) {
	$log.debug('stockListController...');
	$rootScope.viewName = 'Stocks';
}
appControllers.controller('stockListController', stockListController);
