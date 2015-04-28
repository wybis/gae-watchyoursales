function counterController($rootScope, $scope, $log, sessionService,
		counterService) {
	$rootScope.viewName = 'Counter';

	// sessionService.getStocks().then(function(response) {
	$scope.items = sessionService.stocks;
	// });

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
	$scope.saveReceiptAsOrder = counterService.saveReceiptAsOrder;
	$scope.saveReceiptAsTransaction = counterService.saveReceiptAsTransaction;

	$scope.printReceipt = function() {
		$log.info('print receipt comming soon...');
	};

	if ($scope.receipt.customer.id === 0) {
		counterService.init();
	}

	$log.debug('counterController...');
}
appControllers.controller('counterController', counterController);
