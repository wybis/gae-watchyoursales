function homeController($rootScope, $scope, $log, $http, $filter, $timeout) {
	$log.debug('homeController...');
	$rootScope.viewName = 'Home';

	$scope.message = '';

	$scope.execute = function(path) {
		$http.get(path).success(function(response) {
			// $log.info(response);
			$scope.message = response;
		})
	};

	$scope.branchsJson = [ {
		id : '-',
		name : '<Select JSON File>'
	} ];
	$scope.branchJson = $scope.branchsJson[0];

	$http.get('modules/console/agency.json').success(function(response) {
		// $log.info(response);
		_.forEach(response, function(item) {
			$scope.branchsJson.push(item);
		})
	});

	$scope.branchJsonClazz = 'disabled';

	$scope.onBranchChange = function() {
		var branchJson = $scope.branchJson;
		if (branchJson.id == '-') {
			return;
		}
		$log.info(branchJson);

		$scope.branchJsonClazz = 'active';

		var path = 'modules/console/' + branchJson.id;
		$http.get(path).success(function(response) {
			$log.info(response);
			branchJson.value = response;
			branchJson.json = $filter('json')(branchJson.value, '    ');
			$log.info(branchJson.json);
			$scope.branchJsonClazz = 'disabled';
		}).error(function(error) {
			$scope.branchJsonClazz = 'disabled';
		});
	};

	$scope.saveMasterTriggered = false;
	$scope.saveMaster = function() {
		$scope.branchJsonClazz = 'active';
		var branchJson = $scope.branchJson;
		var f = $http.post('console/addBranch', branchJson.json);
		f.success(function(response) {
			$scope.message = response;
			$scope.branchJsonClazz = 'disabled';
		}).error(function(error) {
			$scope.branchJsonClazz = 'disabled';
		});
	};
}
appControllers.controller('homeController', homeController);
