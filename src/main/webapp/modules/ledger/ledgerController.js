function ledgerController($rootScope, $scope, $log, sessionService,
		ledgerService) {
	$log.debug('ledgerController...');
	$rootScope.viewName = 'Ledgers';

	$scope.accountsMap = sessionService.accountsMap;
	$scope.frAccounts = [];
	$scope.toAccounts = [];
	$scope.recentTrans = [];
	$scope.receipt = ledgerService.receipt;

	$scope.onTransactionUnit = ledgerService.onTransactionUnit;

	$scope.clearOrNew = ledgerService.clearOrNew;

	$scope.saveReceiptAsTransaction = function() {
		ledgerService.saveReceiptAsTransaction().then(function(response) {
			$scope.refresh();
		});
	}

	sessionService.getLedgers().then(function(response) {
		if (response.type != 0) {
			return;
		}

		$scope.frAccounts = response.data;

		_.forEach(response.data, function(item) {
			if (item.type != 'cashCapital') {
				$scope.toAccounts.push(item);
			}
		});
	});

	$scope.refresh = function() {
		ledgerService.getRecentTransactions().then(function(response) {
			$log.info(response);
			$scope.recentTrans = response.data;
		});
	};

	$scope.refresh();
}
appControllers.controller('ledgerController', ledgerController);
