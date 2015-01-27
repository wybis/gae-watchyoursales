function rootController($scope, $log, $http, $filter) {

	$scope.message = '';

	$scope.execute = function(path) {
		$http.get(path).success(function(response) {
			$scope.message = response;
		})
	};

	$scope.agencys = [];

	$http.get('/modules/init/agency.json').success(function(response) {
		// $log.info(response);
		_.forEach(response, function(item) {
			$scope.agencys.push(item);
		})
	})

	$scope.agency = {};

	$scope.onAgencyChange = function() {
		var agency = $scope.agency;
		if (agency.value) {
			return;
		}
		$log.info(agency);
		var path = '/modules/init/' + agency.id;
		$http.get(path).success(function(response) {
			$log.info(response);
			agency.value = response;
			agency.json = $filter('json')(agency.value, '    ');
			$log.info(agency.json);
		});
	};

	$scope.saveAgencyMasterTriggered = false;
	$scope.saveAgencyMaster = function() {
		var agency = $scope.agency;
		var f = $http.post('/system/saveAgencyMaster', agency.json);
		f.success(function(response) {
			$scope.message = response;
		});
	};

	$scope.saveAgencyTransTriggered = false;
	$scope.saveAgencyTrans = function() {
		var agency = $scope.agency;
		var f = $http.post('/system/saveAgencyTrans', agency.json);
		f.success(function(response) {
			$scope.message = response;
		});
	};

	$log.info('rootController...');
}
appControllers.controller('rootController', rootController);

var dependents = [ 'ngRoute', 'ngSanitize' ];
dependents.push('ngStorage');
dependents.push('ui.bootstrap');
dependents.push('app.filters');
dependents.push('app.directives');
dependents.push('app.services');
dependents.push('app.controllers');
var app = angular.module('app', dependents);

app.config(function($httpProvider) {
	$httpProvider.interceptors.push('generalHttpInterceptor');
});

function appInit($log, $window, $sessionStorage) {
	$log.info('Initialization started...');

	//_.mixin(_.str.exports());

	$log.info('Initialization finished...');
}
app.run([ '$log', '$window', '$sessionStorage', appInit ]);
