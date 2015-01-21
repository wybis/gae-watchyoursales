var appControllers = angular.module('app.controllers', []);

function rootController($scope, $log, $window, Detector) {

	var result = Detector.getResult();

	$log.info(result.browser);
	$log.info(result.device);
	$log.info(result.os);
	$log.info(result.os.version);
	$log.info(result.engine.name);
	$log.info(result.cpu.architecture);

	$log.info('rootController...');
}
appControllers.controller('rootController', rootController);

var dependents = [ 'angular-detector' ];
dependents.push('app.controllers');
var app = angular.module('app', dependents);

function appInit($log) {
	$log.info('Initialization started...');

	$log.info('Initialization finished...');
}
app.run([ '$log', appInit ]);
