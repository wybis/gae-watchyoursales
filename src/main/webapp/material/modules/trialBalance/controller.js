function trialBalanceController($rootScope, $scope, $log) {
	$log.debug('trialBalanceController...');
	$rootScope.viewName = 'Trial Balance';
}
appControllers.controller('trialBalanceController', trialBalanceController);
