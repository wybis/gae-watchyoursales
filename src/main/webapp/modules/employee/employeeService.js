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

	service.getProducts = function() {
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

	service.getStocks = function() {
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

	service.init = function() {
		service.getProducts().then(function(response) {
			service.getStocks().then(function(response) {
				service.computeStockWorth();
			});
		});
	};

	return service;
}
appServices.factory('employeeService', employeeService);