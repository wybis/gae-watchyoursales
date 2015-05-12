function counterController($rootScope, $scope, $log, sessionService,
		counterService) {
	$rootScope.viewName = 'Counter';

	$scope.items = sessionService.stocks;

	$scope.receipt = counterService.receipt;

	$scope.newTransaction = counterService.newTransaction;
	$scope.removeTransaction = counterService.removeTransaction;
	$scope.removeAllTransactions = counterService.removeAllTransactions;

	$scope.onTransactionSelect = counterService.onTransactionSelect;
	$scope.onTransactionType = counterService.onTransactionType;
	$scope.onTransactionItem = counterService.onTransactionItem;
	$scope.onTransactionUnit = counterService.onTransactionUnit;
	$scope.onTransactionRate = counterService.onTransactionRate;
	$scope.onRevertAmount = counterService.onRevertAmount;
	$scope.onCustomerAmount = counterService.onCustomerAmount;

	$scope.saveReceiptAsQuotation = counterService.saveReceiptAsQuotation;
	$scope.saveReceiptAsDraft = counterService.saveReceiptAsDraft;
	$scope.saveReceiptAsOrder = counterService.saveReceiptAsOrder;
	$scope.saveReceiptAsTransaction = counterService.saveReceiptAsTransaction;

	$scope.printReceipt = counterService.printReceipt;

	if ($scope.receipt.forUser.id === 0) {
		counterService.init();
	}

	$log.debug('counterController...');
}
appControllers.controller('counterController', counterController);
