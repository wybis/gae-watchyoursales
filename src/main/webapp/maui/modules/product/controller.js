function productListController($rootScope, $scope, $log) {
	$log.debug('productListController...');
	$rootScope.viewName = 'Products';
}
appControllers.controller('productListController', productListController);
