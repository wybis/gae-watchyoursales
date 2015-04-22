function sessionService($log, $http, $q, $window, employeeService) {
	var basePath = 'sessions';

	var service = {
		context : {}
	};

	service.properties = function() {
		var path = basePath + '/properties';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processSessionProperties(response.data);
				deferred.resolve(response);
			}
			// $log.info(response);
		}).error(function() {
			deferred.reject("unable to authenticate...");
		});

		return deferred.promise;
	};

	function processSessionProperties(sesProps) {
		$log.debug('processing session properties started...');
		_.assign(service.context, sesProps);
		identifyUserOrEmployee();
		employeeService.updateSessionProperties(sesProps);
		employeeService.init();
		$log.debug('processing session properties finished...');
	}

	function identifyUserOrEmployee() {
		if (!_.has(service.context.sessionUser, 'roleId')) {
			return;
		}
		var roleId = service.context.sessionUser.roleId;
		if (_.startsWith(roleId, 'Sys ') || _.startsWith(roleId, 'App ')) {
			service.context.sessionUser.isAppUser = true;
		} else {
			service.context.sessionUser.isAppUser = false;
		}
		$log.info("isAppUser : " + service.context.sessionUser.isAppUser);
	}

	return service;
}
appServices.factory('sessionService', sessionService);