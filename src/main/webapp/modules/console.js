function rootController($rootScope, $scope, $log, $http, $filter, $timeout) {

	$log.info('rootController...');
}
appControllers.controller('rootController', rootController);

var dependents = [ 'ngRoute', 'ngSanitize' ];
dependents.push('ngStorage');
dependents.push('ngNotify');
dependents.push('ui.bootstrap');
dependents.push('app.filters');
dependents.push('app.directives');
dependents.push('app.services');
dependents.push('app.controllers');
var app = angular.module('app', dependents);

app.config(function($httpProvider) {
	$httpProvider.interceptors.push('generalHttpInterceptor');
});

function appConfig($routeProvider, $locationProvider) {

	$routeProvider.when('/', {
		redirectTo : '/home'
	});

	$routeProvider.when('/home', {
		templateUrl : 'modules/console/home/d.html',
		controller : 'homeController'
	});

	$routeProvider.when('/dataViewer', {
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
	});

	$rootScope.$on("$routeChangeSuccess", function(event, next, current) {
		// $log.info('Location : ', $location.path());
		var curLocPath = $location.path();
		// $log.info('After Current Location : ', curLocPath);
	});

	$log.info('Initialization finished...');
}
app.run([ '$log', '$rootScope', '$location', '$sessionStorage', appInit ]);
