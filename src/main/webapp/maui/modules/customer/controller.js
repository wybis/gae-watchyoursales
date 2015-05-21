function customerListController($rootScope, $scope, $log) {
	$log.debug('customerListController...');
	$rootScope.viewName = 'Customers';
}
appControllers.controller('customerListController', customerListController);
