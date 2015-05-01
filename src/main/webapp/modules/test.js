function rootController($scope, $log, $window, $rootScope, wydNotifyService,
		panels) {
	$log.info('root...');

	$scope.viewSource = function() {
		var s = 'view-source:' + $rootScope.currentViewSrcUrl;
		$log.info(s);
		$window.open(s);
	};

	$scope.success = function() {
		wydNotifyService.addSuccess("Success message...", true);
	};

	$scope.info = function() {
		wydNotifyService.addInfo("Info message...", true);
	};

	$scope.warning = function() {
		wydNotifyService.addWarning("Warning message...", true);
	};

	$scope.error = function() {
		wydNotifyService.addError("Error message...", true);
	};

	$scope.openPanel = function(panelId) {
		panels.open(panelId);
	};
}
appControllers.controller('rootController', rootController);

appControllers.controller('leftPanelController', function($log, $scope) {
	$log.info('left panel...');

	$scope.message = 'Left Panel';
});
appControllers.controller('rightPanelController', function($log, $scope) {
	$log.info('right panel...');

	$scope.message = 'Right Panel';
});
appControllers.controller('topPanelController', function($log, $scope) {
	$log.info('top panel...');

	$scope.message = 'Top Panel';
});
appControllers.controller('bottomPanelController', function($log, $scope) {
	$log.info('bottom panel...');

	$scope.message = 'Bottom Panel';
});

var dependents = [ 'ngRoute', 'ngSanitize' ];
dependents.push('ngStorage');
dependents.push('green.inputmask4angular');
dependents.push('ngNotify');
dependents.push('angular.panels');
dependents.push('ui.select');
dependents.push('ui.bootstrap');
dependents.push('app.filters');
dependents.push('app.directives');
dependents.push('app.services');
dependents.push('app.controllers');
var app = angular.module('app', dependents);

app.config([ 'panelsProvider', function(panelsProvider) {

	panelsProvider.add({
		id : 'lp',
		position : 'left',
		size : '300px',
		templateUrl : 'modules/zgeneral/sideMenuLeft-d-manager.html',
		controller : 'leftPanelController'
	});

	panelsProvider.add({
		id : 'rp',
		position : 'right',
		size : '20%',
		templateUrl : 'panel.html',
		controller : 'rightPanelController'
	});

	panelsProvider.add({
		id : 'tp',
		position : 'top',
		size : '40%',
		templateUrl : 'panel.html',
		controller : 'topPanelController'
	});

	panelsProvider.add({
		id : 'bp',
		position : 'bottom',
		size : '30%',
		templateUrl : 'panel.html',
		controller : 'bottomPanelController'
	});

} ]);

function appInit($log, $rootScope, $location, $sessionStorage) {
	$log.info('Initialization started...');

	$log.info('Initialization finished...');
}
app.run([ '$log', '$rootScope', '$location', '$sessionStorage', appInit ]);
