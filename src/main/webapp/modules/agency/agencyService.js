function agencyService($log, $http, $q) {
	var basePath = 'agencys';

	var service = {
		items : [],
		itemsMap : {}
	}, omitProps = [ 'accounts', 'items', 'employees', 'dealers', 'customers' ];

	function addOrUdpateCacheX(agency, propName, objectx) {
		var objectsLst = agency[propName]
		var objectsMap = agency[propName + 'Map'];
		var object = objectsMap[objectx.id];
		if (object) {
			_.assign(object, objectx);
		} else {
			objectsLst.push(objectx);
			objectsMap[objectx.id] = objectx;
		}
	}

	function addOrUpdateCache(itemx) {
		var item = service.itemsMap[itemx.id]
		if (item) {
			var itemxx = _.omit(itemx, omitProps);
			_.assign(item, itemxx);
		} else {
			service.items.push(itemx);
			service.itemsMap[itemx.id] = itemx;

			itemx.accounts = [];
			itemx.accountsMap = {};
			itemx.items = [];
			itemx.itemsMap = {};
			itemx.employees = [];
			itemx.employeesMap = {};
			itemx.dealers = [];
			itemx.dealersMap = {};
			itemx.customers = [];
			itemx.customersMap = {};
		}
	}
	service.addOrUpdateCache = addOrUpdateCache;

	service.all = function() {
		var path = basePath;

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				_.forEach(response.data, function(itemx) {
					addOrUpdateCache(itemx)
				});
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.getAccounts = function(agencyId) {
		var path = basePath + '/' + agencyId + '/accounts';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				var agency = service.itemsMap[agencyId];
				_.forEach(response.data, function(objectx) {
					addOrUdpateCacheX(agency, 'accounts', objectx);
				});
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.getItems = function(agencyId) {
		var path = basePath + '/' + agencyId + '/items';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				var agency = service.itemsMap[agencyId];
				_.forEach(response.data, function(objectx) {
					addOrUdpateCacheX(agency, 'items', objectx);
				});
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.getEmployees = function(agencyId) {
		var path = basePath + '/' + agencyId + '/employees';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				var agency = service.itemsMap[agencyId];
				_.forEach(response.data, function(objectx) {
					addOrUdpateCacheX(agency, 'employees', objectx);
				});
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.getDealers = function(agencyId) {
		var path = basePath + '/' + agencyId + '/dealers';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				var agency = service.itemsMap[agencyId];
				_.forEach(response.data, function(objectx) {
					addOrUdpateCacheX(agency, 'dealers', objectx);
				});
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.getCustomers = function(agencyId) {
		var path = basePath + '/' + agencyId + '/customers';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				var agency = service.itemsMap[agencyId];
				_.forEach(response.data, function(objectx) {
					addOrUdpateCacheX(agency, 'customers', objectx);
				});
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	return service;
}
appServices.factory('agencyService', agencyService);