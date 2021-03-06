function rootController($rootScope, $scope, $log, wydNotifyService,
		sessionService, $window, $timeout, $http, panels, presenceStates) {
	$log.info('rootController...');

	var sessionS = sessionService;
	$scope.sessionS = sessionS;

	sessionS.properties().then(
			function(response) {
				$log.info(response.message);
				$scope.$broadcast('session:properties',
						'Session properties loaded...');
			});

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

	$scope.reset = function() {
		var path = 'console/branchs/branch/';
		path += sessionS.context.sessionDto.branchId + '/reset'
		$http.get(path).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				wydNotifyService.addSuccess(response.message, true);
				$timeout(function() {
					location.reload();
				}, 2000);
			} else {
				wydNotifyService.addError(response.message, true);
				$log.error('reset branch failed...');
			}
		});
	}

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
		templateUrl : 'modules/zgeneral/d-sideMenuLeft-manager.html',
		controller : 'sideMenuLeftController'
	});

	panelsProvider.add({
		id : 'sideMenuLeft-employee',
		position : 'left',
		size : '300px',
		templateUrl : 'modules/zgeneral/d-sideMenuLeft-employee.html',
		controller : 'sideMenuLeftController'
	});

});

app.config(function($routeProvider, $locationProvider) {

	$routeProvider.otherwise({
		redirectTo : '/notFound'
	});

	$routeProvider.when('/notFound', {
		templateUrl : 'modules/zgeneral/d-notFound.html'
	});

	$routeProvider.when('/', {
		templateUrl : 'modules/home/index-d.html',
		controller : 'indexController'
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

	$routeProvider.when('/employees', {
		templateUrl : 'modules/employee/d-list.html',
		controller : 'employeeListController',
		reloadOnSearch : false
	});

	$routeProvider.when('/employees/employee', {
		templateUrl : 'modules/employee/d-edit.html',
		controller : 'employeeEditController',
		reloadOnSearch : false
	});

	$routeProvider.when('/employees/employee/:id', {
		templateUrl : 'modules/employee/d-view.html',
		controller : 'employeeViewController',
		reloadOnSearch : false
	});

	$routeProvider.when('/employees/employee/:id/edit', {
		templateUrl : 'modules/employee/d-edit.html',
		controller : 'employeeEditController',
		reloadOnSearch : false
	});

	$routeProvider.when('/employees/search', {
		templateUrl : 'modules/employee/d-search.html',
		controller : 'employeeSearchController',
		reloadOnSearch : false
	});

	// dealers
	$routeProvider.when('/dealers', {
		templateUrl : 'modules/dealer/d-list.html',
		controller : 'dealerListController',
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
		templateUrl : 'modules/dealer/order/dealerOrderList.html',
		controller : 'dealerOrderListController',
		reloadOnSearch : false
	});

	$routeProvider.when('/processDealerOrders', {
		templateUrl : 'modules/dealer/order/dealerOrderProcess.html',
		controller : 'dealerOrderProcessController',
		reloadOnSearch : false
	});

	$routeProvider.when('/dealers/dealer/:id/tran', {
		templateUrl : 'modules/dealer/tran/dealerTran.html',
		controller : 'dealerTranController',
		reloadOnSearch : false
	});

	$routeProvider.when('/dealerTrans', {
		templateUrl : 'modules/dealer/tran/dealerTranList.html',
		controller : 'dealerTranListController',
		reloadOnSearch : false
	});

	// customers
	$routeProvider.when('/customers', {
		templateUrl : 'modules/customer/d-list.html',
		controller : 'customerListController',
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

	$routeProvider.when('/customers/orders', {
		templateUrl : 'modules/customer/order/customerOrderReceiptList.html',
		controller : 'customerOrderReceiptListController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customers/orders/orderReceipt/:id', {
		templateUrl : 'modules/customer/order/customerOrderView.html',
		controller : 'customerOrderViewController',
		reloadOnSearch : false
	});
	
	$routeProvider.when('/customers/customer/:id/orders', {
		templateUrl : 'modules/customer/order/customerOrderItemList.html',
		controller : 'customerOrderItemListController',
		reloadOnSearch : false
	});

	$routeProvider.when('/customers/customer/:id/tran/:orderIds', {
		templateUrl : 'modules/customer/tran/customerTran.html',
		controller : 'customerTranController',
		reloadOnSearch : false
	});

	// $routeProvider.when('/customers/customer/:id/trans', {
	// templateUrl : 'modules/customer/tran/customerTrans.html',
	// controller : 'customerTranController',
	// reloadOnSearch : false
	// });
	//
	// $routeProvider.when('/customerTrans', {
	// templateUrl : 'modules/customer/tran/customerTranList.html',
	// controller : 'customerTranListController',
	// reloadOnSearch : false
	// });

	// ledgers
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

	// others
	$routeProvider.when('/trialBalance', {
		templateUrl : 'modules/trialBalance/d.html',
		controller : 'trialBalanceController',
		reloadOnSearch : false
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
