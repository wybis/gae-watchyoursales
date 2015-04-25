function ledgerController($rootScope, $scope, $log, sessionService,
		ledgerService) {
	$log.debug('ledgerController...');
	$rootScope.viewName = 'Ledgers';

	$scope.accounts = []
	$scope.receipt = ledgerService.receipt;

	$scope.clearOrNew = ledgerService.clearOrNew;
	$scope.saveReceiptAsTransaction = ledgerService.saveReceiptAsTransaction;

	sessionService.getLedgers().then(function(response) {
		if (response.type != 0) {
			return;
		}
		$scope.accounts = _.filter(sessionService.accounts, function(item) {
			return item.type == 'cashEmployee' || item.type == 'cashCapital';
		});
	});

	$scope.onLedgerSelection = function() {
		$log.info($scope.receipt);
	};

	$scope.refresh = function() {
		$log.info('yet to implement...');
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();
}
appControllers.controller('ledgerController', ledgerController);
