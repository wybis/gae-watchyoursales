function ledgerController($rootScope, $scope, $log, wydNotifyService, sessionService,
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
		var receipt = $scope.receipt;
		if (!receipt.trans[0].unitRaw
				|| receipt.trans[0].unitRaw === 0) {
			wydNotifyService.addError('Invalid amount...', true);
			return
		}
		if(receipt.trans[0].account.id === 0) {
			wydNotifyService.addError('Select debit from...', true);
			return
		}
		if(receipt.trans[1].account.id === 0) {
			wydNotifyService.addError('Select credit to...', true);
			return
		}
		if(receipt.trans[0].account.id === receipt.trans[1].account.id) {
			var s = "Transaction can't be done between same ledgers";
			wydNotifyService.addError(s, true);
		}
		
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
			$scope.recentTrans = response.data;
		});
	};

	$scope.refresh();
}
appControllers.controller('ledgerController', ledgerController);
