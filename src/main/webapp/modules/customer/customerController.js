function customerController($rootScope, $scope, $log, sessionService) {
	$rootScope.viewName = 'Customers';

	$scope.items = sessionService.customers;

	$scope.refresh = function() {
		sessionService.getCustomers();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	//$scope.refresh();

	$log.debug('customerController...');
}
appControllers.controller('customerController', customerController);

function customerViewController($rootScope, $scope, $log, $location,
		sessionService) {
	$rootScope.viewName = 'View Customer';

	$scope.back = function() {
		$location.path('/counter');
	};

	$log.debug('customerVeiwController...');
}
appControllers.controller('customerViewController', customerViewController);

function customerEditController($rootScope, $scope, $log, $location,
		sessionService) {
	$rootScope.viewName = 'Edit Customer';

	$scope.back = function() {
		$location.path('/counter');
	};

	$log.debug('customerEditController...');
}
appControllers.controller('customerEditController', customerEditController);

function customerSearchController($rootScope, $scope, $log, $location,
		sessionService, counterService) {
	$rootScope.viewName = 'Search Customer';

	$scope.items = sessionService.customers;

	$scope.select = function(customerId) {
		var customer = sessionService.customersMap[customerId];
		counterService.setCustomer(customer);
		$scope.back();
	};

	$scope.back = function() {
		$location.path('/counter');
	};

	$scope.refresh = function() {
		sessionService.getCustomers();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

	$log.debug('customerSearchController...');
}
appControllers.controller('customerSearchController', customerSearchController);
