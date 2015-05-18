function dataService($log, $http, $q, wydNotifyService) {
	var basePath = 'console', isLoggingEnabled = false;

	var service = {
		branchs : [],
		branchsMap : {},
		accounts : [],
		accountsMap : {},
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
		$log.debug('processing accounts finished...')
	}

	function processCustomers(items) {
		$log.debug('processing customers started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('customers', objectx);
		});
		$log.debug('processing customers finished...')
	}

	function processDealers(items) {
		$log.debug('processing dealers started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('dealers', objectx);
		});
		$log.debug('processing dealers finished...')
	}

	function processEmployees(items) {
		$log.debug('processing employees started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('employees', objectx);
		});
		$log.debug('processing employees finished...')
	}

	function processProducts(items) {
		$log.debug('processing products started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('products', objectx);
		});
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
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.resetBranch = function(id) {
		var path = basePath + '/branchs/branch/' + id + '/reset'

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				wydNotifyService.addSuccess(response.message, true);
				deferred.resolve(response);
			} else {
				$log.error('reset branch failed...');
			}
		});

		return deferred.promise;
	};

	service.deleteBranch = function(id) {
		var path = basePath + '/branchs/branch/' + id

		var deferred = $q.defer();
		$http['delete'](path).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				wydNotifyService.addSuccess(response.message, true);
				deferred.resolve(response);
			} else {
				$log.error('deleting branch failed...');
			}
		});

		return deferred.promise;
	};

	service.getBranchs();

	return service;
}
appServices.factory('dataService', dataService);