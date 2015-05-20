function rootController($rootScope, $scope, $log, $mdSidenav) {
	$log.info('rootControllerr...');

	var gstMenus = [ {
		url : '/index',
		text : "Home"
	}, {
		url : '/signIn',
		text : "Sign In"
	} ];
	var usrMenus = [ {
		url : '/home',
		text : "Home"
	}, {
		url : '/customerOrders',
		text : "Orders"
	}, {
		url : '/customerTrans',
		text : "Transactions"
	}, {
		url : '/stocks',
		text : "Stocks"
	}, {
		url : '/products',
		text : "Products"
	}, {
		url : '/customers',
		text : "Customers"
	}, {
		url : '/trialBalance',
		text : "Trial Balance"
	}, {
		url : '/signOut',
		text : "Sign Out"
	} ];

	$scope.menuItems = gstMenus;

	$scope.toggleSidenav = function(menuId) {
		$mdSidenav(menuId).toggle();
	};

	$rootScope.$on('session:loggedin', function(event, data) {
		$log.debug('session loggedin started...');
		// panels.open('sessionResponse');
		$scope.menuItems = usrMenus;
		$log.debug('session loggedin finished...');
	});

	$rootScope.$on('session:loggedout', function(event, data) {
		$log.debug('session loggedout started...');
		// panels.open('sessionResponse');
		$scope.menuItems = gstMenus;
		$log.debug('session loggedout finished...');
	});

}
appControllers.controller('rootController', rootController);

var dependents = [ 'ngRoute', 'ngSanitize', 'ngMaterial' ];
dependents.push('ngStorage');
dependents.push('app.filters');
dependents.push('app.directives');
dependents.push('app.services');
dependents.push('app.controllers');
var app = angular.module('app', dependents);

app.config(function($routeProvider, $locationProvider) {

	$routeProvider.when('/notFound', {
		templateUrl : 'material/modules/zgeneral/notFound-m.html'
	});

	$routeProvider.when('/index', {
		templateUrl : 'material/modules/home/index-m.html',
		controller : 'indexController'
	});

	$routeProvider.when('/signIn', {
		templateUrl : 'material/modules/session/login-m.html',
		controller : 'loginController'
	});

	$routeProvider.when('/home', {
		templateUrl : 'material/modules/home/home-m.html',
		controller : 'homeController'
	});

	$routeProvider.when('/products', {
		templateUrl : 'material/modules/product/m.html',
		controller : 'productListController',
		reloadOnSearch : false
	});

	$routeProvider.when('/stocks', {
		templateUrl : 'material/modules/stock/m.html',
		controller : 'stockListController',
		reloadOnSearch : false
	});

	// customers
	$routeProvider.when('/customers', {
		templateUrl : 'material/modules/customer/m.html',
		controller : 'customerListController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customerOrders', {
		templateUrl : 'material/modules/customer/order/m.html',
		controller : 'customerOrderListController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customerTrans', {
		templateUrl : 'material/modules/customer/tran/m.html',
		controller : 'customerTranListController',
		reloadOnSearch : false
	});

	// others
	$routeProvider.when('/trialBalance', {
		templateUrl : 'material/modules/trialBalance/m.html',
		controller : 'trialBalanceController',
		reloadOnSearch : false
	});

	$routeProvider.when('/signOut', {
		templateUrl : 'material/modules/session/logout-m.html',
		controller : 'logoutController'
	});

	$routeProvider.otherwise({
		redirectTo : '/notFound'
	});

	// $locationProvider.html5Mode(true);
});

function appInit($log, $rootScope, $location, $sessionStorage) {
	$log.info('Initialization started...');

	$rootScope.isLoggedIn = false;

	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		// $log.info('Location : ', $location.path());
		var curLocPath = $location.path();
		// $log.info('After Current Location : ', curLocPath);
	});

	$rootScope.$on("$routeChangeSuccess", function(event, next, current) {
		// $log.info('Location : ', $location.path());
		var curLocPath = $location.path();
		$rootScope.curLocPath = curLocPath;
		$log.info('After Current Location : ', curLocPath);
	});

	// $location.path('/index');

	$log.info('Initialization finished...');
}
app.run([ '$log', '$rootScope', '$location', '$sessionStorage', appInit ]);
