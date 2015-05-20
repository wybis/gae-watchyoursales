function customerTranListController($rootScope, $scope, $log) {
	$log.debug('customerTranListController...');
	$rootScope.viewName = 'Customer Transactions';
}
appControllers.controller('customerTranListController',
		customerTranListController);
