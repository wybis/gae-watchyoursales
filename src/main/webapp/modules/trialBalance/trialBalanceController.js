function trialBalanceController($rootScope, $scope, $log, sessionService, $http) {
	$log.debug('trialBalanceController...');
	$rootScope.viewName = 'Trial Balance';

}
appControllers.controller('trialBalanceController', trialBalanceController);
