function customerOrderViewController($rootScope, $scope, $log,
		customerOrderProcessService, $routeParams, $http) {
	$log.debug('customerOrderViewController...');
	$rootScope.viewName = 'Customer Order View';

	$scope.customersMap = customerOrderProcessService.customersMap;

	$scope.employees = customerOrderProcessService.employees;
	$scope.employeesMap = customerOrderProcessService.employeesMap;

	$scope.productsMap = customerOrderProcessService.productsMap;
	$scope.accountsMap = customerOrderProcessService.accountsMap;

	$scope.model = customerOrderProcessService.model;

	$scope.$on('session:properties', function(event, data) {
		customerOrderProcessService.init();
	});

	$scope.assign = customerOrderProcessService.assignToEmployee;
	
	$scope.accept = customerOrderProcessService.acceptByEmployee;

	// customerOrderProcessService.init();
	// var path = '/sessions/orderReceipts/' + $routeParams.id;
	var path = '/io/vteial/wys/web/session/orderReceiptById.groovy';
	path += '?orderReceiptId=' + $routeParams.id;
	$http.get(path).success(function(response) {
		if (response.type === 0) {
			$scope.receipt = response.data;
			customerOrderProcessService.processOrderReceipt($scope.receipt);
		}
	});
}
appControllers.controller('customerOrderViewController',
		customerOrderViewController);
