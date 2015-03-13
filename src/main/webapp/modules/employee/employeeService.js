function employeeService($log, $http, $q) {
	var basePath = '';

	var service = {
		cashAgency : 0,
		cashMine : 0,
		stockAgency : 0,
		stockMine : 0,
		cashCustomer : {
			handStock : 0
		},
		cashDealer : {
			handStock : 0
		},
		cashEmployee : {
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

	function addOrUpdateCacheX(objectx) {
		var object = service[objectx.type]
		if (object) {
			_.assign(object, objectx);
		} else {
			service[objectx.type] = objectx;
		}
		// $log.info(objectx.type);
		// $log.info(service[objectx.type]);
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

	service.updateStockAndProduct = function(stock) {
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
	};

	service.getMyCash = function() {
		var path = basePath + '/cash';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				addOrUpdateCacheX(response.data);
				service.cashMine = response.data.handStock;
				service.cashAgency = response.data.product.handStock;
				deferred.resolve(response);
			}
			// $log.info(response);
		})

		return deferred.promise;
	};

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
			product = service.productsMap[stock.productId];

			tvalue = 0;
			if (product.handStockAverage > 0) {
				tvalue = product.handStockAverage / product.baseUnit;
			}
			stockMine += stock.handStock * tvalue;

			stockAgency += product.handStock * tvalue;
		});
		service.stockMine = stockMine;
		service.stockAgency = stockAgency;
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
				service.getMyCash().then(function(response) {
					// $log.info(service);
					$log.debug('employeService initialized');
				});
			});
		});
	};

	return service;
}
appServices.factory('employeeService', employeeService);