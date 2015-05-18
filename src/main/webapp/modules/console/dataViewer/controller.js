function dataViewerController($rootScope, $scope, $log, wydNotifyService,
		dataService) {
	$log.debug('dataViewerController...');
	$rootScope.viewName = 'Data Viewer';

	$scope.items = dataService.branchs;

	$scope.resetBranch = function(id) {
		var s = 'Are you sure to reset the shop?';
		var params = {
			title : 'Confirm',
			text : s,
			type : 'warning',
			showCancelButton : true,
			confirmButtonText : 'Yes',
			cancelButtonText : 'No',
		};
		var callback = function() {
			dataService.resetBranch(id);
		};
		wydNotifyService.sweet.show(params, callback);
	};

	$scope.deleteBranch = function(id) {
		var s = 'Are you sure to delete the shop?';
		var params = {
			title : 'Confirm',
			text : s,
			type : 'warning',
			showCancelButton : true,
			confirmButtonText : 'Yes',
			cancelButtonText : 'No',
		};
		var callback = function() {
			dataService.deleteBranch(id);
		};
		wydNotifyService.sweet.show(params, callback);
	};

	$scope.refresh = function() {
		dataService.getBranchs();
	};

	$scope.refresh();
}
appControllers.controller('dataViewerController', dataViewerController);

function branchEmployeeController($rootScope, $scope, $log, dataService,
		$routeParams) {
	$log.debug('branchEmployeesController...');
	$rootScope.viewName = 'Branch Employees';

	var branch = dataService.branchsMap[$routeParams.id];
	$scope.items = branch.employees;

}
appControllers.controller('branchEmployeeController', branchEmployeeController);

function branchDealerController($rootScope, $scope, $log, dataService,
		$routeParams) {
	$log.debug('branchDealerController...');
	$rootScope.viewName = 'Branch Dealers';

	var branch = dataService.branchsMap[$routeParams.id];
	$scope.items = branch.dealers;

}
appControllers.controller('branchDealerController', branchDealerController);

function branchCustomerController($rootScope, $scope, $log, dataService,
		$routeParams) {
	$log.debug('branchCustomerController...');
	$rootScope.viewName = 'Branch Customers';

	var branch = dataService.branchsMap[$routeParams.id];
	$scope.items = branch.customers;

}
appControllers.controller('branchCustomerController', branchCustomerController);

function branchProductController($rootScope, $scope, $log, dataService,
		$routeParams) {
	$log.debug('branchProductController...');
	$rootScope.viewName = 'Branch Products';

	var branch = dataService.branchsMap[$routeParams.id];
	$scope.items = branch.products;

}
appControllers.controller('branchProductController', branchProductController);

function branchAccountController($rootScope, $scope, $log, dataService,
		$routeParams) {
	$log.debug('branchAccountController...');
	$rootScope.viewName = 'Branch Accounts';

	var branch = dataService.branchsMap[$routeParams.id];
	$scope.items = branch.accounts;

}
appControllers.controller('branchAccountController', branchAccountController);

function employeeAccountController($rootScope, $scope, $log, dataService,
		$routeParams) {
	$log.debug('employeeAccountController...');
	$rootScope.viewName = 'Employee Accounts';

	var branch = dataService.branchsMap[$routeParams.branchId];
	$scope.items = _.filter(branch.accounts, function(item) {
		return item.userId == $routeParams.userId;
	});

}
appControllers.controller('employeeAccountController',
		employeeAccountController);

function dealerAccountController($rootScope, $scope, $log, dataService,
		$routeParams) {
	$log.debug('dealerAccountController...');
	$rootScope.viewName = 'Dealer Accounts';

	var branch = dataService.branchsMap[$routeParams.branchId];
	$scope.items = _.filter(branch.accounts, function(item) {
		return item.userId == $routeParams.userId;
	});

}
appControllers.controller('dealerAccountController', dealerAccountController);

function customerAccountController($rootScope, $scope, $log, dataService,
		$routeParams) {
	$log.debug('customerAccountController...');
	$rootScope.viewName = 'Customer Accounts';

	var branch = dataService.branchsMap[$routeParams.branchId];
	$scope.items = _.filter(branch.accounts, function(item) {
		return item.userId == $routeParams.userId;
	});

}
appControllers.controller('customerAccountController',
		customerAccountController);

function productAccountController($rootScope, $scope, $log, dataService,
		$routeParams) {
	$log.debug('productAccountController...');
	$rootScope.viewName = 'Product Accounts';

	var branch = dataService.branchsMap[$routeParams.branchId];
	$scope.items = _.filter(branch.accounts, function(item) {
		return item.productId == $routeParams.productId;
	});

}
appControllers.controller('productAccountController', productAccountController);
