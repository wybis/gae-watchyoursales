function customerOrderReceiptListController($rootScope, $scope, $log,
		customerOrderReceiptService, $location) {
	$log.debug('customerOrderReceiptListController...');
	$rootScope.viewName = 'Customer Orders';

	$scope.customers = customerOrderReceiptService.customers;
	$scope.customersMap = customerOrderReceiptService.customersMap;

	$scope.employees = customerOrderReceiptService.employees;
	$scope.employeesMap = customerOrderReceiptService.employeesMap;

	$scope.productsMap = customerOrderReceiptService.productsMap;
	$scope.accountsMap = customerOrderReceiptService.accountsMap;

	$scope.model = customerOrderReceiptService.model;

	$scope.onCustomerChange = function() {
		var path = '';
		if ($scope.model.customer.id === 0) {
			path = '/customers/orders';
		} else {
			path = '/customers/customer/';
			path += $scope.model.customer.id;
			path += '/orders';
		}
		$location.path(path);
	};

	$scope.selectOrDeSelectAll = customerOrderReceiptService.selectOrDeSelectAll;
	$scope.onItemSelectionChange = customerOrderReceiptService.onItemSelectionChange;

	$scope.assign = customerOrderReceiptService.assignToEmployee;
	$scope.accept = customerOrderReceiptService.acceptByEmployee;

	$scope.refresh = customerOrderReceiptService.getPendingOrderReceipts;

	// customerOrderReceiptService.init();
	$scope.$on('session:properties', function(event, data) {
		customerOrderReceiptService.init();
	});

	$scope.model.isSelectedAll = false;
	$scope.refresh()
}
appControllers.controller('customerOrderReceiptListController',
		customerOrderReceiptListController);
