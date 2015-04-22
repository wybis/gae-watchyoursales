function rootController($rootScope, $scope, $log, $http, $filter, $timeout) {

	$log.info('rootController...');
}
appControllers.controller('rootController', rootController);

var dependents = [ 'ngRoute', 'ngSanitize' ];
dependents.push('ngStorage');
dependents.push('ngNotify');
dependents.push('app.filters');
dependents.push('app.directives');
dependents.push('app.services');
dependents.push('app.controllers');
var app = angular.module('app', dependents);

app.config(function($httpProvider) {
	$httpProvider.interceptors.push('generalHttpInterceptor');
});

app.config(function($routeProvider, $locationProvider) {

	$routeProvider.when('/', {
		redirectTo : '/home'
	});

	$routeProvider.when('/home', {
		templateUrl : 'modules/console/home/d.html',
		controller : 'homeController'
	});

	$routeProvider.when('/dataNavigator', {
		templateUrl : 'modules/console/dataNavigator/d.html',
		controller : 'dataNavigatorController'
	});

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
});

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
