function rootController($rootScope, $scope, $log, $window) {
	$log.info('rootController...');

	$scope.viewSource = function() {
		var s = 'view-source:localhost:1111/' + $rootScope.currentViewSrcUrl;
		$log.info(s);
		$window.open(s);
	};
}
appControllers.controller('rootController', rootController);

var dependents = [ 'ngRoute', 'ngSanitize' ];
dependents.push('ngStorage');
dependents.push('ngNotify');
dependents.push('hSweetAlert');
dependents.push('green.inputmask4angular');
// dependents.push('ngInputDate');
dependents.push('blockUI');
// dependents.push('angular.panels');
// dependents.push('presence');
dependents.push('ui.select');
dependents.push('ui.bootstrap');
dependents.push('app.filters');
dependents.push('app.directives');
dependents.push('app.services');
dependents.push('app.controllers');
var app = angular.module('app', dependents);

app.config(function(uiSelectConfig) {
	uiSelectConfig.theme = 'selectize';
	// uiSelectConfig.theme = 'select2';
	// uiSelectConfig.theme = 'bootstrap';
});

app.config(function($httpProvider) {
	$httpProvider.interceptors.push('generalHttpInterceptor');
});

app.config(function(blockUIConfig) {
	// blockUIConfig.autoBlock = false;
});

function appConfig($routeProvider, $locationProvider) {

	$routeProvider.when('/', {
		redirectTo : '/home'
	});

	$routeProvider.when('/home', {
		templateUrl : 'modules/console/home/d.html',
		controller : 'homeController'
	});

	$routeProvider.when('/shops', {
		templateUrl : 'modules/console/dataViewer/d.html',
		controller : 'dataViewerController'
	});

	$routeProvider.when('/dataViewer/branchs/branch/:id/employees', {
		templateUrl : 'modules/console/dataViewer/d-employees.html',
		controller : 'branchEmployeeController',
	});

	$routeProvider.when('/dataViewer/branchs/branch/:id/dealers', {
		templateUrl : 'modules/console/dataViewer/d-dealers.html',
		controller : 'branchDealerController',
	});

	$routeProvider.when('/dataViewer/branchs/branch/:id/customers', {
		templateUrl : 'modules/console/dataViewer/d-customers.html',
		controller : 'branchCustomerController',
	});

	$routeProvider.when('/dataViewer/branchs/branch/:id/products', {
		templateUrl : 'modules/console/dataViewer/d-products.html',
		controller : 'branchProductController',
	});

	$routeProvider.when('/dataViewer/branchs/branch/:id/accounts', {
		templateUrl : 'modules/console/dataViewer/d-accounts.html',
		controller : 'branchAccountController',
	});

	var path = '/dataViewer/branchs/branch/:branchId/employees/employee/:userId/accounts';
	var objt = {
		templateUrl : 'modules/console/dataViewer/d-accounts.html',
		controller : 'employeeAccountController',
	};
	$routeProvider.when(path, objt);

	path = '/dataViewer/branchs/branch/:branchId/dealers/dealer/:userId/accounts';
	objt = {
		templateUrl : 'modules/console/dataViewer/d-accounts.html',
		controller : 'dealerAccountController',
	};
	$routeProvider.when(path, objt);

	path = '/dataViewer/branchs/branch/:branchId/customers/customer/:userId/accounts';
	objt = {
		templateUrl : 'modules/console/dataViewer/d-accounts.html',
		controller : 'customerAccountController',
	};
	$routeProvider.when(path, objt);

	path = '/dataViewer/branchs/branch/:branchId/products/product/:productId/accounts';
	objt = {
		templateUrl : 'modules/console/dataViewer/d-accounts.html',
		controller : 'productAccountController',
	};
	$routeProvider.when(path, objt);

	$routeProvider.when('/system/:pathId', {
		templateUrl : 'modules/console/system/d.html',
		controller : 'systemController'
	});

	$routeProvider.when('/notFound', {
		templateUrl : 'modules/zgeneral/notFound-d.html'
	});

	$routeProvider.otherwise({
		redirectTo : '/notFound'
	});
};
app.config(appConfig);

function appInit($log, $rootScope, $location, $sessionStorage) {
	$log.info('Initialization started...');

	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		// $log.info('Location : ', $location.path());
		var curLocPath = $location.path();
		// $log.info('Before Current Location : ', curLocPath);
		var srcUrl = $location.absUrl().indexOf('console');
		srcUrl = $location.absUrl().substring(0, srcUrl);
		srcUrl = srcUrl + next.templateUrl;
		$rootScope.currentViewSrcUrl = srcUrl;
		// $log.info('srcUrl = ' + srcUrl);
	});

	$rootScope.$on("$routeChangeSuccess", function(event, next, current) {
		// $log.info('Location : ', $location.path());
		var curLocPath = $location.path();
		// $log.info('After Current Location : ', curLocPath);
	});

	$log.info('Initialization finished...');
}
app.run([ '$log', '$rootScope', '$location', '$sessionStorage', appInit ]);
