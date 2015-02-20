function employeeService($log, $http, $q) {
	var basePath = '';

	var service = {
		agencyHandCash : 0,
		agencyStockWorth : 0,
		myHandCash : 0,
		myStockWorth : 0,
		productCash : null,
		productProfit : null,
		stockCash : null,
		stockProfit : null,
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

	function addOrUpdateCacheX(objectx) {
		var propName = objectx.type === 'cash' ? 'Cash' : 'Profit'
		if (_.has(objectx, 'code')) {
			propName = 'product' + propName;
		} else {
			propName = 'stock' + propName;
		}
		var object = service[propName]
		if (object) {
			_.assign(object, objectx);
		} else {
			service[propName] = objectx;
		}
		// $log.info(propName);
		// $log.info(service[propName]);
	}

	function addOrUdpateCacheY(propName, objectx) {
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

	function processProducts(products) {
		_.forEach(products, function(objectx) {
			if (objectx.type == 'product') {
				addOrUdpateCacheY('products', objectx);
			} else {
				addOrUpdateCacheX(objectx);
			}
		});
	}

	service.getMyProducts = function() {
		var path = basePath + '/products';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processProducts(response.data);
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	function processStocks(stocks) {
		_.forEach(stocks, function(objectx) {
			if (objectx.type == 'product') {
				addOrUdpateCacheY('stocks', objectx);
			} else {
				addOrUpdateCacheX(objectx);
			}
		});
	}

	service.getMyStocks = function() {
		var path = basePath + '/stocks';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processStocks(response.data);
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.computeStockWorth = function() {
		service.myHandCash = service.stockCash.handStock;
		// $log.info('myHandCash = ' + service.myHandCash);

		service.agencyHandCash = service.productCash.handStock;
		// $log.info('agencyHandCash = ' + service.agencyHandCash);

		var agStockWorth = 0, myStockWorth = 0, product, tvalue;
		_.forEach(service.stocks, function(stock) {
			product = service.productsMap[stock.productId];

			tvalue = 0;
			if (product.handStockAverage > 0) {
				tvalue = product.handStockAverage / product.baseUnit;
			}
			myStockWorth += stock.handStock * tvalue;

			agStockWorth += product.handStock * tvalue;
		});
		service.myStockWorth = myStockWorth;
		// $log.info('myStockWorth' + service.myStockWorth);

		service.agencyStockWorth = agStockWorth;
		// $log.info('agencyStockWorth' + service.agencyStockWorth);
	}

	function processCustomers(stocks) {
		_.forEach(stocks, function(objectx) {
			addOrUdpateCacheY('customers', objectx);
		});
	}

	service.getMyCustomers = function() {
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

	service.getMyPendingCustomerOrders = function() {
		var path = basePath + '/pendingCustomerOrders';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.getMyCustomerTransactions = function() {
		var path = basePath + '/customerTransactions';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	function processDealers(stocks) {
		_.forEach(stocks, function(objectx) {
			addOrUdpateCacheY('dealers', objectx);
		});
	}

	service.getMyDealers = function() {
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

	function processEmployees(stocks) {
		_.forEach(stocks, function(objectx) {
			addOrUdpateCacheY('employees', objectx);
		});
	}

	service.getMyPendingDealerOrders = function() {
		var path = basePath + '/pendingDealerOrders';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				deferred.resolve(response);
			}
			$log.info(response);
		})

		return deferred.promise;
	};

	service.getMyDealerTransactions = function() {
		var path = basePath + '/dealerTransactions';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.getMyEmployees = function() {
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

	service.init = function() {
		service.getMyProducts().then(function(response) {
			service.getMyStocks().then(function(response) {
				service.computeStockWorth();
			});
		});
	};

	function processAccounts(stocks) {
		_.forEach(stocks, function(objectx) {
			addOrUdpateCacheY('accounts', objectx);
		});
	}

	service.getMyAccounts = function() {
		var path = basePath + '/accounts';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processAccounts(response.data);
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.getMyAccountTransactions = function() {
		var path = basePath + '/accountTransactions';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	return service;
}
appServices.factory('employeeService', employeeService);