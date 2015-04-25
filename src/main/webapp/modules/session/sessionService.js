function sessionService($log, $http, $q) {
	var basePath = 'sessions';

	var service = {
		context : {},
		cashBranch : {
			handStock : 0
		},
		cashMine : {
			handStock : 0
		},
		stockBranch : 0,
		stockMine : 0,
		cashDealer : {
			handStock : 0
		},
		cashCustomer : {
			handStock : 0
		},
		products : [],
		productsMap : {},
		stocks : [],
		stocksMap : {},
		dealers : [],
		dealersMap : {},
		customers : [],
		customersMap : {},
		employees : [],
		employeesMap : {},
		accounts : [],
		accountsMap : {}
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

	function processLedgers(ledgers) {
		$log.debug('processing ledgers started...')
		_.forEach(ledgers, function(objectx) {
			addOrUpdateCacheY('accounts', objectx);
			if (objectx.product) {
				addOrUpdateCacheY('products', objectx.product);
			}
		});
		$log.debug('processing ledgers finished...')
	}

	service.getLedgers = function() {
		var path = basePath + '/ledgers';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processLedgers(response.data);
				// service.computeStockWorth();
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	function processStocks(stocks) {
		$log.debug('processing stocks started...')
		_.forEach(stocks, function(objectx) {
			addOrUpdateCacheY('stocks', objectx);
			addOrUpdateCacheY('products', objectx.product);
		});
		$log.debug('processing stocks finished...')
	}

	service.getStocks = function() {
		var path = basePath + '/stocks';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processStocks(response.data);
				// service.computeStockWorth();
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	function processCustomers(items) {
		$log.debug('processing customers started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('customers', objectx);
			addOrUpdateCacheY('accounts', objectx.cashAccount);
			addOrUpdateCacheY('products', objectx.cashAccount.product);
		});
		$log.debug('processing customers finished...')
	}

	service.getCustomers = function() {
		var path = basePath + '/customers';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processCustomers(response.data);
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	function processDealers(items) {
		$log.debug('processing dealers started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('dealers', objectx);
			addOrUpdateCacheY('accounts', objectx.cashAccount);
			addOrUpdateCacheY('products', objectx.cashAccount.product);
		});
		$log.debug('processing dealers finished...')
	}

	service.getDealers = function() {
		var path = basePath + '/dealers';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processDealers(response.data);
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	function processEmployees(items) {
		$log.debug('processing employees started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('employees', objectx);
			addOrUpdateCacheY('accounts', objectx.cashAccount);
			addOrUpdateCacheY('products', objectx.cashAccount.product);
			// addOrUpdateCacheY('accounts', objectx.profitAccount);
			// addOrUpdateCacheY('products', objectx.profitAccount.product);
		});
		$log.debug('processing employees finished...')
	}

	service.getEmployees = function() {
		var path = basePath + '/employees';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processEmployees(response.data);
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
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
		$log.debug('processing session properties finished...');
	}

	return service;
}
appServices.factory('sessionService', sessionService);