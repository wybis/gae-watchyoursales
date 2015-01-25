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

function agencyProductController($rootScope, $scope, $log, agencyService,
		$routeParams) {
	$rootScope.viewName = 'Agency Products';

	var agency = agencyService.itemsMap[$routeParams.id];
	$scope.items = agency.products;

	$scope.refresh = function() {
		agencyService.getProducts($routeParams.id);
	};

	$scope.refresh();

	$log.debug('agencyItemsController...');
}
appControllers.controller('agencyProductController', agencyProductController);

function agencyProductStockController($rootScope, $scope, $log, agencyService,
		$routeParams, $filter) {
	$rootScope.viewName = 'Product Stocks';

	var agency = agencyService.itemsMap[$routeParams.id];
	$scope.productsMap = agency.productsMap;
	$scope.items = $filter('filter')(agency.stocks, {
		productId : $routeParams.productId
	});

	$scope.refresh = function() {
		agencyService.getStocks($routeParams.id);
	};

	$scope.refresh();

	$log.debug('agencyProductStockController...');
}
appControllers.controller('agencyProductStockController',
		agencyProductStockController);

function agencyStockController($rootScope, $scope, $log, agencyService,
		$routeParams) {
	$rootScope.viewName = 'Agency Stocks';

	var agency = agencyService.itemsMap[$routeParams.id];
	$scope.productsMap = agency.productsMap;
	$scope.items = agency.stocks;

	$scope.refresh = function() {
		agencyService.getStocks($routeParams.id);
	};

	$scope.refresh();

	$log.debug('agencyItemsController...');
}
appControllers.controller('agencyStockController', agencyStockController);

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

function agencyEmployeeStockController($rootScope, $scope, $log, agencyService,
		$routeParams, $filter) {
	$rootScope.viewName = 'Employee Stocks';

	var agency = agencyService.itemsMap[$routeParams.id];
	$scope.productsMap = agency.productsMap;
	$scope.items = $filter('filter')(agency.stocks, {
		employeeId : $routeParams.employeeId
	});

	$log.debug('agencyEmployeeStockController...');
}
appControllers.controller('agencyEmployeeStockController',
		agencyEmployeeStockController);

function agencyEmployeeAccountController($rootScope, $scope, $log,
		agencyService, $routeParams, $filter) {
	$rootScope.viewName = 'Employee Accounts';

	var agency = agencyService.itemsMap[$routeParams.id];
	var employee = agency.employeesMap[$routeParams.employeeId];
	var account = agency.accountsMap[employee.accountId]
	$scope.items = [ account ];

	$log.debug('agencyEmployeeAccountController...');
}
appControllers.controller('agencyEmployeeAccountController',
		agencyEmployeeAccountController);

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

function agencyDealerAccountController($rootScope, $scope, $log, agencyService,
		$routeParams, $filter) {
	$rootScope.viewName = 'Dealer Accounts';

	var agency = agencyService.itemsMap[$routeParams.id];
	var dealer = agency.dealersMap[$routeParams.dealerId];
	var account = agency.accountsMap[dealer.accountId]
	$scope.items = [ account ];

	$log.debug('agencyDealerAccountController...');
}
appControllers.controller('agencyDealerAccountController',
		agencyDealerAccountController);

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

function agencyCustomerAccountController($rootScope, $scope, $log,
		agencyService, $routeParams, $filter) {
	$rootScope.viewName = 'Customer Accounts';

	var agency = agencyService.itemsMap[$routeParams.id];
	var customer = agency.customersMap[$routeParams.customerId];
	var account = agency.accountsMap[customer.accountId]
	$scope.items = [ account ];

	$log.debug('agencyCustomerAccountController...');
}
appControllers.controller('agencyCustomerAccountController',
		agencyCustomerAccountController);
