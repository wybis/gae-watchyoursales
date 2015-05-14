function rootController($scope, $log, $window, $rootScope, sessionService,
		panels, presenceStates) {
	$log.info('rootController...');

	$rootScope.sessionContext = sessionService.context;

	$scope.sessionS = sessionService;

	sessionService.properties();

	$scope.presenceStates = presenceStates;

	// presenceStates.onChange(function(state) {
	// $log.debug('Current Presence State : ' + state.text);
	// });

	presenceStates.LONGAWAY.onEnter(function() {
		$log.debug('presence timout started...');
		// panels.open('sessionResponse');
		$scope.$broadcast('session:response', 'Session timeout...');
		$log.debug('presence timout started...');
	});

	$rootScope.$on('session:invalid', function(event, data) {
		$log.debug('session invalid started...');
		// panels.open('sessionResponse');
		$scope.$broadcast('session:response', data);
		$log.debug('session invalid finished...');
	});

	$scope.showLeftMenu = function() {
		var sideMenuId = 'sideMenuLeft-employee';
		if (sessionService.context.sessionDto.roleId == 'manager') {
			sideMenuId = 'sideMenuLeft-manager';
		}
		panels.open(sideMenuId);
	};

	$scope.viewSource = function() {
		var s = 'view-source:localhost:1111/' + $rootScope.currentViewSrcUrl;
		$log.info(s);
		$window.open(s);
	};

}
appControllers.controller('rootController', rootController);

appControllers.controller('sideMenuLeftController', function($log, $scope) {
	$log.info('sideMenuLeftController...');
});

var dependents = [ 'ngRoute', 'ngSanitize' ];
dependents.push('ngStorage');
dependents.push('ngNotify');
dependents.push('hSweetAlert');
dependents.push('green.inputmask4angular');
// dependents.push('ngInputDate');
dependents.push('blockUI');
dependents.push('angular.panels');
dependents.push('presence');
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

app.config(function(blockUIConfig) {
	// blockUIConfig.autoBlock = false;
});

app.config(function(panelsProvider) {

	panelsProvider.add({
		id : 'sessionResponse',
		position : 'top',
		size : '100%',
		templateUrl : 'modules/session/sessionResponse.html',
		controller : 'sessionResponseController'
	});

	panelsProvider.add({
		id : 'sideMenuLeft-manager',
		position : 'left',
		size : '300px',
		templateUrl : 'modules/zgeneral/sideMenuLeft-d-manager.html',
		controller : 'sideMenuLeftController'
	});

	panelsProvider.add({
		id : 'sideMenuLeft-employee',
		position : 'left',
		size : '300px',
		templateUrl : 'modules/zgeneral/sideMenuLeft-d-employee.html',
		controller : 'sideMenuLeftController'
	});

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

	$routeProvider.when('/counter', {
		templateUrl : 'modules/counter/d.html',
		controller : 'counterController',
		reloadOnSearch : false
	});

	$routeProvider.when('/transfer', {
		templateUrl : 'modules/transfer/d.html',
		controller : 'transferController',
		reloadOnSearch : false
	});

	$routeProvider.when('/users', {
		templateUrl : 'modules/user/d.html',
		controller : 'userController',
		reloadOnSearch : false
	});

	$routeProvider.when('/products', {
		templateUrl : 'modules/product/d.html',
		controller : 'productController',
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
	// dealers
	$routeProvider.when('/dealers', {
		templateUrl : 'modules/dealer/d.html',
		controller : 'dealerController',
		reloadOnSearch : false
	});

	$routeProvider.when('/dealers/dealer', {
		templateUrl : 'modules/dealer/d-edit.html',
		controller : 'dealerEditController',
		reloadOnSearch : false
	});

	$routeProvider.when('/dealers/dealer/:id', {
		templateUrl : 'modules/dealer/d-view.html',
		controller : 'dealerViewController',
		reloadOnSearch : false
	});

	$routeProvider.when('/dealers/dealer/:id/edit', {
		templateUrl : 'modules/dealer/d-edit.html',
		controller : 'dealerEditController',
		reloadOnSearch : false
	});

	$routeProvider.when('/dealers/search', {
		templateUrl : 'modules/dealer/d-search.html',
		controller : 'dealerSearchController',
		reloadOnSearch : false
	});

	$routeProvider.when('/dealerOrders', {
		templateUrl : 'modules/dealerOrder/d.html',
		controller : 'dealerOrderController',
		reloadOnSearch : false
	});

	$routeProvider.when('/processDealerOrders', {
		templateUrl : 'modules/dealerOrder/d-processDealerOrder.html',
		controller : 'processDealerOrderController',
		reloadOnSearch : false
	});

	$routeProvider.when('/dealerTrans', {
		templateUrl : 'modules/dealerTran/d.html',
		controller : 'dealerTranController',
		reloadOnSearch : false
	});
	// customers
	$routeProvider.when('/customers', {
		templateUrl : 'modules/customer/d.html',
		controller : 'customerController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customers/customer', {
		templateUrl : 'modules/customer/d-edit.html',
		controller : 'customerEditController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customers/customer/:id', {
		templateUrl : 'modules/customer/d-view.html',
		controller : 'customerViewController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customers/customer/:id/edit', {
		templateUrl : 'modules/customer/d-edit.html',
		controller : 'customerEditController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customers/search', {
		templateUrl : 'modules/customer/d-search.html',
		controller : 'customerSearchController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customerOrders', {
		templateUrl : 'modules/customerOrder/d.html',
		controller : 'customerOrderController',
		reloadOnSearch : false
	});

	$routeProvider.when('/processCustomerOrders', {
		templateUrl : 'modules/customerOrder/d-processCustomerOrder.html',
		controller : 'processCustomerOrderController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customerTrans', {
		templateUrl : 'modules/customerTran/d.html',
		controller : 'customerTranController',
		reloadOnSearch : false
	});

	$routeProvider.when('/ledgers', {
		templateUrl : 'modules/ledgers/d.html',
		controller : 'ledgersController',
		reloadOnSearch : false
	});

	$routeProvider.when('/ledger', {
		templateUrl : 'modules/ledger/d.html',
		controller : 'ledgerController',
		reloadOnSearch : false
	});

	$routeProvider.when('/ledgerTrans', {
		templateUrl : 'modules/ledgerTran/d.html',
		controller : 'ledgerTranController',
		reloadOnSearch : false
	});

	$routeProvider.when('/trialBalance', {
		templateUrl : 'modules/trialBalance/d.html',
		controller : 'trialBalanceController',
		reloadOnSearch : false
	});

	$routeProvider.otherwise({
		redirectTo : '/notFound'
	});

	// $locationProvider.html5Mode(true);
});

function appInit($log, $rootScope, $location, $sessionStorage, panels) {
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

		var srcUrl = $location.absUrl().indexOf('index');
		srcUrl = $location.absUrl().substring(0, srcUrl);
		srcUrl = srcUrl + next.templateUrl;
		$rootScope.currentViewSrcUrl = srcUrl;
		// $log.info('srcUrl = ' + srcUrl);
	});

	$rootScope.$on("$routeChangeSuccess", function(event, next, current) {
		// $log.info('Location : ', $location.path());
		var curLocPath = $location.path();
		// $log.info('After Current Location : ', curLocPath);

		panels.close();
	});

	var path = $sessionStorage.wysCLP;
	if (!path) {
		path = '/home';
	}
	$location.path(path);
	// $location.path('/home');

	$log.info('Initialization finished...');
}
app.run([ '$log', '$rootScope', '$location', '$sessionStorage', 'panels',
		appInit ]);
