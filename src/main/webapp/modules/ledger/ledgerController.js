function ledgerController($rootScope, $scope, $log, wydNotifyService, sessionService,
		ledgerService) {
	$log.debug('ledgerController...');
	$rootScope.viewName = 'Ledgers';

	$scope.accountsMap = sessionService.accountsMap;
	
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
			var s = "Transaction can't be done between same ledgers...";
			wydNotifyService.addError(s, true);
			return;
		}
		
		var vrEmpId = sessionService.context.sessionDto.branchVirtualEmployeeId;
		var vrEmply = sessionService.employeesMap[vrEmpId]
		var frAcc = receipt.trans[0].account;
		var toAcc = receipt.trans[1].account;
		if(frAcc.type == 'cashCapital' && toAcc.id != vrEmply.cashAccountId) {
			var s = "Invalid credit ledger...";
			wydNotifyService.addError(s, true);
			return
		}
		
		ledgerService.saveReceiptAsTransaction().then(function(response) {
			$scope.refresh();
		});
	}

	$scope.refresh = function() {
		ledgerService.getRecentTransactions().then(function(response) {
			$scope.recentTrans = response.data;
		});
	};

	$scope.frAccounts = [];
	$scope.toAccounts = [];

	_.forEach(sessionService.accounts, function(item) {
		if (item.type == 'cashCapital') {
			$scope.frAccounts.push(item);
		}
		if (item.type == 'cashEmployee') {
			$scope.frAccounts.push(item);
			$scope.toAccounts.push(item);
		}
	});


	$scope.refresh();
}
appControllers.controller('ledgerController', ledgerController);
