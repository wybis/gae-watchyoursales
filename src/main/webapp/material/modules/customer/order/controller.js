function customerOrderListController($rootScope, $scope, $log) {
	$log.debug('customerOrderListController...');
	$rootScope.viewName = 'Customer Orders';
}
appControllers.controller('customerOrderListController',
		customerOrderListController);
