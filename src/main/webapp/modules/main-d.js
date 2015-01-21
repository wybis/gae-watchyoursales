function rootController($scope, $log, $window, $rootScope, sessionService,
		$aside) {

	$rootScope.sessionContext = sessionService.context;

	sessionService.properties();

	$scope.viewSource = function() {
		var s = 'view-source:' + $rootScope.currentViewSrcUrl;
		$log.info(s);
		$window.open(s);
	};

	$scope.showLeftMenu = function() {
		$aside.open({
			templateUrl : 'modules/zgeneral/sideBarLeft-d.html',
			placement : 'left',
			size : 'sm',
			backdrop : false,
			controller : function($scope, $modalInstance, $location) {

				$scope.routeTo = function(e, uri) {
					$modalInstance.close();
					e.stopPropagation();
					$location.path(uri);
				};

				$scope.close = function(e) {
					$modalInstance.close();
					e.stopPropagation();
				};
			}
		})
	};

	$log.info('root...');
}
appControllers.controller('rootController', rootController);

var dependents = [ 'ngRoute', 'ngSanitize' ];
dependents.push('ngStorage');
dependents.push('green.inputmask4angular');
// dependents.push('ngInputDate');
// dependents.push('ngNotify');
dependents.push('ngAside');
dependents.push('ui.select');
dependents.push('ui.bootstrap');
dependents.push('app.filters');
dependents.push('app.directives');
dependents.push('app.services');
dependents.push('app.controllers');
var app = angular.module('app', dependents);

app.config(function(uiSelectConfig) {
	uiSelectConfig.theme = 'select2';
});

app.config(function($httpProvider) {
	$httpProvider.interceptors.push('generalHttpInterceptor');
});

app.config(function($routeProvider, $locationProvider) {
	$routeProvider.when('/', {
		templateUrl : 'modules/home/index-d.html',
		controller : 'indexController'
	});

	$routeProvider.when('/notFound', {
		templateUrl : 'modules/zgeneral/notFound-d.html'
	});

	$routeProvider.when('/index', {
		templateUrl : 'modules/home/index-d.html',
		controller : 'indexController'
	});

	$routeProvider.when('/signin', {
		templateUrl : 'modules/session/login-d.html',
		controller : 'loginController',
		reloadOnSearch : false
	});

	$routeProvider.when('/signout', {
		templateUrl : 'modules/session/logout-d.html',
		controller : 'logoutController',
		reloadOnSearch : false
	});

	$routeProvider.when('/settings', {
		templateUrl : 'modules/session/setting-d.html',
		controller : 'settingController',
		reloadOnSearch : false
	});

	$routeProvider.when('/home', {
		templateUrl : 'modules/home/home-d.html',
		controller : 'homeController',
		reloadOnSearch : false
	});

	$routeProvider.when('/accounts', {
		templateUrl : 'modules/account/d.html',
		controller : 'accountController',
		reloadOnSearch : false
	});

	$routeProvider.when('/users', {
		templateUrl : 'modules/user/d.html',
		controller : 'userController',
		reloadOnSearch : false
	});

	$routeProvider.when('/items', {
		templateUrl : 'modules/item/d.html',
		controller : 'itemController',
		reloadOnSearch : false
	});

	$routeProvider.when('/stocks', {
		templateUrl : 'modules/stock/d.html',
		controller : 'stockController',
		reloadOnSearch : false
	});

	$routeProvider.when('/employees', {
		templateUrl : 'modules/employee/d.html',
		controller : 'employeeController',
		reloadOnSearch : false
	});

	$routeProvider.when('/dealers', {
		templateUrl : 'modules/dealer/d.html',
		controller : 'dealerController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customers', {
		templateUrl : 'modules/customer/d.html',
		controller : 'customerController',
		reloadOnSearch : false
	});

	$routeProvider.when('/agencys', {
		templateUrl : 'modules/agency/d.html',
		controller : 'agencyController',
		reloadOnSearch : false
	});

	$routeProvider.when('/agencys/:id/accounts', {
		templateUrl : 'modules/agency/d-accounts.html',
		controller : 'agencyAccountController',
		reloadOnSearch : false
	});

	$routeProvider.when('/agencys/:id/items', {
		templateUrl : 'modules/agency/d-items.html',
		controller : 'agencyItemController',
		reloadOnSearch : false
	});
	
	$routeProvider.when('/agencys/:id/employees', {
		templateUrl : 'modules/agency/d-employees.html',
		controller : 'agencyEmployeeController',
		reloadOnSearch : false
	});

	$routeProvider.when('/agencys/:id/dealers', {
		templateUrl : 'modules/agency/d-dealers.html',
		controller : 'agencyDealerController',
		reloadOnSearch : false
	});

	$routeProvider.when('/agencys/:id/customers', {
		templateUrl : 'modules/agency/d-customers.html',
		controller : 'agencyCustomerController',
		reloadOnSearch : false
	});

	$routeProvider.otherwise({
		redirectTo : '/notFound'
	});

	// $locationProvider.html5Mode(true);
});

function appInit($log, $rootScope, $location, $sessionStorage) {
	$log.info('Initialization started...');

	_.mixin(_.str.exports());

	$rootScope.$on("$routeChangeStart", function(event, next, current) {
		// $log.info('Location : ', $location.path());
		var curLocPath = $location.path();
		$log.info('Before Current Location : ', curLocPath);
		if (curLocPath == '/notFound' || curLocPath == '/signin'
				|| curLocPath == '/signout') {
			return;
		}
		$sessionStorage.seetuCLP = curLocPath;
		// $log.info('Stored Location : ', $sessionStorage.seetuCLP);

		var srcUrl = $location.absUrl().indexOf('index');
		srcUrl = $location.absUrl().substring(0, srcUrl);
		srcUrl = srcUrl + next.templateUrl
		$rootScope.currentViewSrcUrl = srcUrl;
		$log.info('srcUrl = ' + srcUrl);
	});

	$rootScope.$on("$routeChangeSuccess", function(event, next, current) {
		// $log.info('Location : ', $location.path());
		var curLocPath = $location.path();
		// $log.info('After Current Location : ', curLocPath);
	});

	$rootScope.isLoggedIn = false;
	$rootScope.homeView = '/index';

	var path = $sessionStorage.seetuCLP;
	if (!path) {
		path = '/index';
	}
	$location.path(path);

	$log.info('Initialization finished...');
}
app.run([ '$log', '$rootScope', '$location', '$sessionStorage', appInit ]);
