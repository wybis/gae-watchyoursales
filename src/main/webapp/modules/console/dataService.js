function dataService($log, $http, $q) {
	var basePath = 'console', isLoggingEnabled = false;

	var service = {
		branchs : [],
		branchsMap : {},
		accounts : [],
		acountsMap : {},
		products : [],
		productsMap : {},
		employees : [],
		employeesMap : {},
		dealers : [],
		dealersMap : {},
		customers : [],
		customersMap : {}
	};

	function addOrUpdateCacheY(propName, objectx) {
		var objectsLst = service[propName]
		var objectsMap = service[propName + 'Map'];
		var object = objectsMap[objectx.id];
		if (object) {
			_.assign(object, objectx);
		} else {
			objectsLst.push(objectx);
			objectsMap[objectx.id] = objectx;
		}
	}

	function processAccounts(items) {
		$log.debug('processing accounts started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('accounts', objectx);
		});
		// logit();
		$log.debug('processing accounts finished...')
	}

	function processCustomers(items) {
		$log.debug('processing customers started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('customers', objectx);
		});
		// logit();
		$log.debug('processing customers finished...')
	}

	function processDealers(items) {
		$log.debug('processing dealers started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('dealers', objectx);
		});
		// logit();
		$log.debug('processing dealers finished...')
	}

	function processEmployees(items) {
		$log.debug('processing employees started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('employees', objectx);
		});
		// logit();
		$log.debug('processing employees finished...')
	}

	function processProducts(items) {
		$log.debug('processing products started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('products', objectx);
		});
		// logit();
		$log.debug('processing products finished...')
	}

	function processBranchs(items) {
		$log.debug('processing branchs started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('branchs', objectx);
			processProducts(objectx.products);
			processEmployees(objectx.employees);
			processDealers(objectx.dealers);
			processCustomers(objectx.customers);
			processAccounts(objectx.accounts);
		});
		// logit();
		$log.debug('processing branchs finished...')
	}

	service.getBranchs = function() {
		var path = basePath + '/branchs';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processBranchs(response.data);
				deferred.resolve(response);
			}
			$log.info(response);
		})

		return deferred.promise;
	};

	return service;
}
appServices.factory('dataService', dataService);