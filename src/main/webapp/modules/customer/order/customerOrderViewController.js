function customerOrderViewController($rootScope, $scope, $log,
		customerOrderReceiptService, $routeParams, $http, sessionService) {
	$log.debug('customerOrderViewController...');
	$rootScope.viewName = 'Customer Order View';

	if (sessionService.context.sessionDto) {
		$scope.roleId = sessionService.context.sessionDto.roleId;
	}

	$scope.customersMap = customerOrderReceiptService.customersMap;

	$scope.employees = customerOrderReceiptService.employees;
	$scope.employeesMap = customerOrderReceiptService.employeesMap;

	$scope.productsMap = customerOrderReceiptService.productsMap;
	$scope.accountsMap = customerOrderReceiptService.accountsMap;

	$scope.model = customerOrderReceiptService.model;

	$scope.$on('session:properties', function(event, data) {
		customerOrderReceiptService.init();
	});

	$scope.assign = customerOrderReceiptService.assignToEmployee;

	$scope.accept = customerOrderReceiptService.acceptByEmployee;

	// var path = '/sessions/orderReceipts/' + $routeParams.id;
	var path = '/io/vteial/wys/web/session/orderReceiptById.groovy';
	path += '?orderReceiptId=' + $routeParams.id;
	$http.get(path).success(function(response) {
		if (response.type === 0) {
			$scope.receipt = response.data;
			customerOrderReceiptService.processOrderReceipt($scope.receipt);
		}
	});
}
appControllers.controller('customerOrderViewController',
		customerOrderViewController);
