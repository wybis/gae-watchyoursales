function sessionService($log, $http, $q, agencyService) {
	var basePath = 'sessions';

	var service = {
		context : {}
	};

	service.properties = function() {
		var path = basePath + '/properties';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				_.assign(service.context, response.data);
				identifyUserOrEmployee();
				if (_.has(service.context.sessionUser, 'agency')) {
					var agency = response.data.sessionUser.agency;
					agencyService.addOrUpdateCache(agency);
				}
				deferred.resolve(response);
			}
			// $log.info(response);
		}).error(function() {
			deferred.reject("unable to authenticate...");
		});

		return deferred.promise;
	};

	service.login = function(user) {
		var path = basePath + '/login';

		var deferred = $q.defer();
		$http.post(path, user).success(
				function(response) {
					if (response.type >= 0) {
						if (response.type === 0) {
							_.assign(service.context, response.data);
							var sesUser = service.context.sessionUser;
							sesUser.cashInHand = 10;
							sesUser.stockWorth = 10;
							sesUser.totalWorth = sesUser.cashInHand
									+ sesUser.stockWorth;
							identifyUserOrEmployee();
							var agency = service.context.sessionUser.agency;
							agency.cashInHand = 10;
							agency.stockWorth = 10;
							agency.totalWorth = agency.cashInHand
									+ agency.stockWorth;
							agencyService.addOrUpdateCache(agency);
						}
						deferred.resolve(response);
					}
					$log.info(response);
				}).error(function() {
			deferred.reject("unable to authenticate...");
		});

		return deferred.promise;
	};

	service.logout = function() {
		var path = basePath + '/logout';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				_.assign(service.context, response.data);
				deferred.resolve(response);
			}
			// $log.info(response);
		}).error(function() {
			deferred.reject("unable to logout...");
		});

		return deferred.promise;
	};

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