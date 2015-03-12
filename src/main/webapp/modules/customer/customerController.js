function customerController($rootScope, $scope, $log, employeeService) {
	$rootScope.viewName = 'Customers';

	$scope.items = employeeService.customers;

	$scope.refresh = function() {
		employeeService.getMyCustomers();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('customerController...');
}
appControllers.controller('customerController', customerController);

function customerViewController($rootScope, $scope, $log, $location,
		employeeService) {
	$rootScope.viewName = 'View Customer';

	$scope.back = function() {
		$location.path('/counter');
	};

	$log.debug('customerVeiwController...');
}
appControllers.controller('customerViewController', customerViewController);

function customerEditController($rootScope, $scope, $log, $location,
		employeeService) {
	$rootScope.viewName = 'Edit Customer';

	$scope.back = function() {
		$location.path('/counter');
	};

	$log.debug('customerEditController...');
}
appControllers.controller('customerEditController', customerEditController);

function customerSearchController($rootScope, $scope, $log, $location,
		employeeService, counterService) {
	$rootScope.viewName = 'Search Customer';

	$scope.items = employeeService.customers;

	$scope.select = function(customerId) {
		var customer = employeeService.customersMap[customerId];
		counterService.receipt.customer = customer
		$scope.back();
	};

	$scope.back = function() {
		$location.path('/counter');
	};

	$scope.refresh = function() {
		employeeService.getMyCustomers();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('customerSearchController...');
}
appControllers.controller('customerSearchController', customerSearchController);
