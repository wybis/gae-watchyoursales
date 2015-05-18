function customerListController($rootScope, $scope, $log, sessionService) {
	$log.debug('customerListController...');
	$rootScope.viewName = 'Customers';

	$scope.items = sessionService.customers;

	$scope.refresh = function() {
		sessionService.getCustomers();
	};

	// $scope.refresh();
}
appControllers.controller('customerListController', customerListController);

function customerViewController($rootScope, $scope, $log, $location,
		sessionService) {
	$log.debug('customerVeiwController...');
	$rootScope.viewName = 'View Customer';

	$scope.back = function() {
		$location.path('/counter');
	};
}
appControllers.controller('customerViewController', customerViewController);

function customerEditController($rootScope, $scope, $log, $location,
		sessionService) {
	$log.debug('customerEditController...');
	$rootScope.viewName = 'Edit Customer';

	$scope.back = function() {
		$location.path('/counter');
	};
}
appControllers.controller('customerEditController', customerEditController);

function customerSearchController($rootScope, $scope, $log, $location,
		sessionService, counterService) {
	$log.debug('customerSearchController...');
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

	// $scope.refresh();
}
appControllers.controller('customerSearchController', customerSearchController);
