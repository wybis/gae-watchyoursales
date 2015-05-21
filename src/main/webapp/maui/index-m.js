function rootController($rootScope, $scope, $log, $window, $location,
		presenceStates, sessionService) {
	$log.info('rootController...');

	var sessionS = sessionService;
	$scope.sessionS = sessionS;

	sessionS.properties();

	$rootScope.$on('session:loggedin', function(event, data) {
		$log.debug('session loggedin started...');
		sessionS.init();
		sessionS.properties();
		$log.debug('session loggedin finished...');
	});

	$rootScope.$on('session:loggedout', function(event, data) {
		$log.debug('session loggedout started...');
		sessionS.init();
		sessionS.properties();
		$log.debug('session loggedout finished...');
	});

	$rootScope.$on('session:invalid', function(event, data) {
		$log.debug('session invalid started...');
		$rootScope.isLoggedIn = false;
		$scope.$broadcast('session:response', data);
		$log.debug('session invalid finished...');
	});

	$scope.presenceStates = presenceStates;

	// presenceStates.onChange(function(state) {
	// $log.debug('Current Presence State : ' + state.text);
	// });

	presenceStates.LONGAWAY.onEnter(function() {
		$log.debug('presence timout started...');
		$rootScope.isLoggedIn = false;
		$location.path('/signIn');
		$log.debug('presence timout started...');
	});

	$scope.viewSource = function() {
		var s = 'view-source:localhost:1111/' + $rootScope.currentViewSrcUrl;
		$log.info(s);
		$window.open(s);
	};

}
appControllers.controller('rootController', rootController);

var dependents = [ 'ngRoute', 'ngSanitize' ];
dependents.push('ngStorage');
dependents.push('green.inputmask4angular');
// dependents.push('ngInputDate');
// dependents.push('blockUI');
dependents.push('presence');
dependents.push('ui.select');
dependents.push('mobile-angular-ui')
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

// app.config(function(blockUIConfig) {
// blockUIConfig.autoBlock = false;
// });

app.config(function($routeProvider, $locationProvider) {
	var prefix = 'maui/';

	$routeProvider.when('/', {
		redirectTo : '/index'
	});

	$routeProvider.when('/notFound', {
		templateUrl : prefix + 'modules/zgeneral/m-notFound.html'
	});

	$routeProvider.when('/index', {
		templateUrl : prefix + 'modules/home/m-index.html',
		controller : 'indexController'
	});

	$routeProvider.when('/signIn', {
		templateUrl : prefix + 'modules/session/m-login.html',
		controller : 'loginController'
	});

	$routeProvider.when('/signOut', {
		templateUrl : prefix + 'modules/session/m-logout.html',
		controller : 'logoutController'
	});

	$routeProvider.when('/home', {
		templateUrl : prefix + 'modules/home/m-home.html',
		controller : 'homeController'
	});

	$routeProvider.when('/products', {
		templateUrl : prefix + 'modules/product/m.html',
		controller : 'productListController'
	});

	$routeProvider.when('/stocks', {
		templateUrl : prefix + 'modules/stock/m.html',
		controller : 'stockListController'
	});

	// customers
	$routeProvider.when('/customers', {
		templateUrl : prefix + 'modules/customer/m.html',
		controller : 'customerListController'
	});

	$routeProvider.when('/customerOrders', {
		templateUrl : prefix + 'modules/customer/order/m.html',
		controller : 'customerOrderListController'
	});

	$routeProvider.when('/customerTrans', {
		templateUrl : prefix + 'modules/customer/tran/m.html',
		controller : 'customerTranListController'
	});

	// others
	$routeProvider.when('/trialBalance', {
		templateUrl : prefix + 'modules/trialBalance/m.html',
		controller : 'trialBalanceController'
	});

});

function appInit($log, $rootScope, $location, $sessionStorage) {
	$log.info('Initialization started...');

	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		// $log.info('Location : ', $location.path());
		var curLocPath = $location.path();
		// $log.info('Before Current Location : ', curLocPath);
		if (curLocPath == '/notFound' || curLocPath == '/signin'
				|| curLocPath == '/signout') {
			return;
		}
		$sessionStorage.wysCLP = curLocPath;
		// $log.info('Stored Location : ', $sessionStorage.wysCLP);

		var srcUrl = $location.absUrl().indexOf('home');
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

	var path = $sessionStorage.wysCLP;
	if (!path) {
		path = '/index';
	}
	$location.path(path);
	// $location.path('/home');

	$log.info('Initialization finished...');
}
app.run([ '$log', '$rootScope', '$location', '$sessionStorage', appInit ]);
