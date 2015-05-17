function transferController($rootScope, $scope, $log, wydNotifyService,
		transferService) {
	$log.debug('transferController...');
	$rootScope.viewName = 'Transfer';

	$scope.items = sessionService.stocks;

	$scope.receipt = transferService.receipt;

	$scope.newTran = transferService.newTran;
	$scope.removeTran = transferService.removeTran;
	$scope.removeAllTrans = transferService.removeAllTrans;

}
appControllers.controller('transferController', transferController);
