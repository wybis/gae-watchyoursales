function agencyController($rootScope, $scope, $log, agencyService) {
	$rootScope.viewName = 'Agencys';

	$scope.items = agencyService.items;

	$scope.refresh = function() {
		agencyService.all();
	};

	$scope.refresh();

	$log.debug('agencyController...');
}
appControllers.controller('agencyController', agencyController);

function agencyAccountController($rootScope, $scope, $log, agencyService,
		$routeParams) {
	$rootScope.viewName = 'Agency Accounts';

	var agency = agencyService.itemsMap[$routeParams.id];
	$scope.items = agency.accounts;

	$scope.refresh = function() {
		agencyService.getAccounts($routeParams.id);
	};

	$scope.refresh();

	$log.debug('agencyAccountsController...');
}
appControllers.controller('agencyAccountController', agencyAccountController);

function agencyItemController($rootScope, $scope, $log, agencyService,
		$routeParams) {
	$rootScope.viewName = 'Agency Items';

	var agency = agencyService.itemsMap[$routeParams.id];
	$scope.items = agency.items;

	$scope.refresh = function() {
		agencyService.getItems($routeParams.id);
	};

	$scope.refresh();

	$log.debug('agencyItemsController...');
}
appControllers.controller('agencyItemController', agencyItemController);

function agencyEmployeeController($rootScope, $scope, $log, agencyService,
		$routeParams) {
	$rootScope.viewName = 'Agency Employees';

	var agency = agencyService.itemsMap[$routeParams.id];
	$scope.items = agency.employees;

	$scope.refresh = function() {
		agencyService.getEmployees($routeParams.id);
	};

	$scope.refresh();

	$log.debug('agencyEmployeesController...');
}
appControllers.controller('agencyEmployeeController', agencyEmployeeController);

function agencyDealerController($rootScope, $scope, $log, agencyService,
		$routeParams) {
	$rootScope.viewName = 'Agency Dealers';

	var agency = agencyService.itemsMap[$routeParams.id];
	$scope.items = agency.dealers;

	$scope.refresh = function() {
		agencyService.getDealers($routeParams.id);
	};

	$scope.refresh();

	$log.debug('agencyDealersController...');
}
appControllers.controller('agencyDealerController', agencyDealerController);

function agencyCustomerController($rootScope, $scope, $log, agencyService,
		$routeParams) {
	$rootScope.viewName = 'Agency Customers';

	var agency = agencyService.itemsMap[$routeParams.id];
	$scope.items = agency.customers;

	$scope.refresh = function() {
		agencyService.getCustomers($routeParams.id);
	};

	$scope.refresh();

	$log.debug('agencyCustomersController...');
}
appControllers.controller('agencyCustomerController', agencyCustomerController);
