function sessionService($log, $http, $q, $rootScope) {
	var basePath = 'sessions';

	var defaultService = {
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
		accounts : [],
		accountsMap : {},
		dealers : [],
		dealersMap : {},
		customers : [],
		customersMap : {},
		employees : [],
		employeesMap : {}
	}, service = {};

	function addOrUpdateCacheX(propName1, propName2, objectx) {
		var objectsLst = service[propName1]
		var objectsMap = service[propName2 + 'Map'];
		var object = objectsMap[objectx.id];
		if (object) {
			_.assign(object, objectx);
		} else {
			objectsLst.push(objectx);
			objectsMap[objectx.id] = objectx;
		}
	}

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

	service.updateAccount = function(objectx) {
		addOrUpdateCacheY('accounts', objectx);
		if (objectx.product) {
			addOrUpdateCacheY('products', objectx.product);
		}
	};

	service.computeStockWorth = function() {
		var stockMine = 0, stockBranch = 0;
		_.forEach(service.stocks, function(item) {
			stockMine += item.amount
			stockBranch += item.product.amount;
		});
		service.stockMine = stockMine;
		service.stockBranch = stockBranch;
	};

	service.computeCashWorth = function() {
		var accId = service.context.sessionDto.cashAccountId;
		service.cashMine = service.accountsMap[accId];

		service.cashCustomer = _.find(service.products, function(item) {
			return item.code === 'CIC';
		});
		service.cashDealer = _.find(service.products, function(item) {
			return item.code === 'CID';
		});
		service.cashBranch = _.find(service.products, function(item) {
			return item.code === 'CIE';
		});
	};

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
			// $log.info(response);
			if (response.type === 0) {
				processLedgers(response.data);
				// service.computeStockWorth();
				deferred.resolve(response);
			}
		})

		return deferred.promise;
	};

	function processStocks(stocks) {
		$log.debug('processing stocks started...')
		_.forEach(stocks, function(objectx) {
			addOrUpdateCacheX('stocks', 'accounts', objectx);
			addOrUpdateCacheY('products', objectx.product);
		});
		$log.debug('processing stocks finished...')
	}

	service.getStocks = function() {
		var path = basePath + '/stocks';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			// $log.info(response);
			if (response.type === 0) {
				processStocks(response.data);
				// service.computeStockWorth();
				deferred.resolve(response);
			}
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
			// $log.info(response);
			if (response.type === 0) {
				processCustomers(response.data);
				deferred.resolve(response);
			}
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
			// $log.info(response);
			if (response.type === 0) {
				processDealers(response.data);
				deferred.resolve(response);
			}
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
			// $log.info(response);
			if (response.type === 0) {
				processEmployees(response.data);
				deferred.resolve(response);
			}
		})

		return deferred.promise;
	};

	function processProps(props) {
		$log.debug('processing session properties started...');
		_.assign(service.context, props);
		if (props.sessionDto && props.sessionDto.userId) {
			$rootScope.xUserId = props.sessionDto.userId;
			// $log.info('Session User Id = ' + $rootScope.xUserId);
		}
		$log.debug('processing session properties finished...');
	}

	function processModel(model) {
		$log.debug('processing session properties started...');
		processStocks(model.stocks);
		processLedgers(model.ledgers);
		processEmployees(model.employees);
		processDealers(model.dealers);
		processCustomers(model.customers);
		service.computeStockWorth();
		service.computeCashWorth();
		$log.debug('processing session properties finished...');
	}

	service.properties = function(flag) {
		var path = basePath + '/properties';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			// $log.info(response);
			if (response.type === 0) {
				processProps(response.data);
				if (response.model) {
					processModel(response.model);
				}
				deferred.resolve(response);
			}
		}).error(function() {
			deferred.reject("unable to authenticate...");
		});

		return deferred.promise;
	};

	service.init = function() {
		_.assign(service, defaultService);
	};

	service.init();

	return service;
}
appServices.factory('sessionService', sessionService);