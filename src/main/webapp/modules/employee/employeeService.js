function employeeService($log, $http, $q) {
	var basePath = '', isLoggingEnabled = false;

	var service = {
		cashAgency : {
			handStock : 0
		},
		cashMine : {
			handStock : 0
		},
		stockAgency : 0,
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

	function logProductsAndStocks() {
		if (isLoggingEnabled) {
			_.forEach(service.products, function(item) {
				$log.info(item);
			});
			$log.info('--------------------------------');
			_.forEach(service.stocks, function(item) {
				$log.info(item);
			});
		}
	}

	// function addOrUpdateCacheX(objectx) {
	// var object = service[objectx.type]
	// if (object) {
	// _.assign(object, objectx);
	// } else {
	// service[objectx.type] = objectx;
	// }
	// }

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

	service.updateStockAndProduct = function(stock) {
		$log.info(stock);
		var propName = 'stocks', objectx = null;
		var objectsLst = service[propName]
		var objectsMap = service[propName + 'Map'];
		var object = objectsMap[stock.id];
		if (object) {
			objectx = _.pick(stock, [ 'availableStock', 'handStock',
					'virtualStock', 'virtualStockBuy', 'virtualStockSell' ]);
			_.assign(object, objectx);
		} else {
			objectsLst.push(stock);
			objectsMap[stock.id] = stock;
		}
		propName = 'products';
		var objectsLst = service[propName]
		var objectsMap = service[propName + 'Map'];
		var object = objectsMap[stock.product.id];
		if (object) {
			objectx = _.pick(stock, [ 'availableStock',
					'availableStockAverage', 'handStock', 'handStockAverage',
					'handStockValue', 'virtualStock', 'virtualStockAverage',
					'virtualStockBuy', 'virtualStockSell' ]);
			_.assign(object, objectx);
		} else {
			objectsLst.push(stock.product);
			objectsMap[stock.product.id] = stock.product;
		}

		service.computeStockWorth();
	};

	service.updateSessionProperties = function(sesProps) {
		var objectx = sesProps.sessionUser.user.cashStock;

		addOrUpdateCacheY('stocks', objectx);
		addOrUpdateCacheY('products', objectx.product);
		service.cashMine = objectx;
		service.cashAgency = objectx.product;

		objectx = sesProps.sessionUser.user.profitStock;
		addOrUpdateCacheY('stocks', objectx);
		// addOrUpdateCacheY('products', objectx.product);

		logProductsAndStocks();
	}

	function processProducts(products) {
		$log.debug('processing products started...')
		_.forEach(products, function(objectx) {
			if (objectx.type == 'product') {
				addOrUdpateCacheY('products', objectx);
			} else {
				// addOrUpdateCacheX(objectx);
			}
		});
		logProductsAndStocks();
		$log.debug('processing products finished...')
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
		$log.debug('processing stocks started...')
		_.forEach(stocks, function(objectx) {
			if (objectx.type == 'product') {
				addOrUpdateCacheY('stocks', objectx);
				addOrUpdateCacheY('products', objectx.product);
			} else {
				// addOrUpdateCacheX(objectx);
			}
		});
		logProductsAndStocks();
		$log.debug('processing stocks finished...')
	}

	service.getMyStocks = function() {
		var path = basePath + '/stocks';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processStocks(response.data);
				service.computeStockWorth();
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

	service.computeStockWorth = function() {
		var stockAgency = 0, stockMine = 0, product, tvalue;
		_.forEach(service.stocks, function(stock) {
			if (stock.type === 'product') {
				product = service.productsMap[stock.productId];

				tvalue = 0;
				if (product.handStockAverage > 0) {
					tvalue = product.handStockAverage / product.baseUnit;
				}
				stockMine += stock.handStock * tvalue;

				stockAgency += product.handStock * tvalue;
			}
		});
		service.stockMine = stockMine;
		service.stockAgency = stockAgency;
	}

	function processCustomers(items) {
		$log.debug('processing customers started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('customers', objectx);
			addOrUpdateCacheY('stocks', objectx.cashStock);
			addOrUpdateCacheY('products', objectx.cashStock.product);
		});
		logProductsAndStocks();
		$log.debug('processing customers finished...')
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

	function processDealers(items) {
		$log.debug('processing dealers started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('dealers', objectx);
			addOrUpdateCacheY('stocks', objectx.cashStock);
			addOrUpdateCacheY('products', objectx.cashStock.product);
		});
		logProductsAndStocks();
		$log.debug('processing dealers finished...')
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

	service.getMyPendingDealerOrders = function() {
		var path = basePath + '/pendingDealerOrders';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				deferred.resolve(response);
			}
			// $log.info(response);
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

	function processEmployees(items) {
		$log.debug('processing employees started...')
		_.forEach(items, function(objectx) {
			addOrUpdateCacheY('employees', objectx);
			addOrUpdateCacheY('stocks', objectx.cashStock);
			addOrUpdateCacheY('stocks', objectx.profitStock);
		});
		logProductsAndStocks();
		$log.debug('processing employees finished...')
	}

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
		service.getMyStocks().then(function(response) {
			$log.debug('employeService initialized');
		});
	};

	return service;
}
appServices.factory('employeeService', employeeService);