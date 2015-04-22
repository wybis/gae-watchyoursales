function systemController($rootScope, $scope, $log, $routeParams, $http) {
	$log.debug('systemController...');
	$rootScope.viewName = 'System';

	var basePath = '_admin', pathIdToTitle = {
		'info' : 'Info',
		'health' : 'Health',
		'metrics' : 'Metrics',
		'dump' : 'Dump',
		'trace' : 'Trace',
		'sessions' : 'Sessions'
	};

	$scope.view = {
		title : pathIdToTitle[$routeParams.pathId],
		content : ''
	};

	$scope.refresh = function(pathId) {
		var path = basePath + '/' + pathId;
		$http.get(path).success(function(response) {
			// $log.info(response);
			$scope.view.content = response;
		}).error(function(error) {
			$log.error(error);
		});
	};

	$scope.refresh($routeParams.pathId);
}
appControllers.controller('systemController', systemController);
