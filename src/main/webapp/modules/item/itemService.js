function itemService($log, $http, $q) {
	var basePath = 'items';

	var service = {
		items : [],
		itemsMap : {}
	};

	function addOrUpdateCache(itemx) {
		var item = service.itemsMap[itemx.id]
		if (item) {
			_.assign(item, itemx);
		} else {
			service.items.push(itemx);
			service.itemsMap[itemx.id] = itemx;
		}
	}

	service.all = function() {
		var path = basePath + '/all';

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				_.forEach(response.data, function(itemx) {
					addOrUpdateCache(itemx)
				});
				deferred.resolve(response);
			}
			$log.info(response);
		})

		return deferred.promise;
	};

	service.list = function() {

	};

	service.setCurrentItemAsNewItem = function() {
		service.currentItem = {
			id : 0
		};
	};

	service.setCurrentItemById = function(itemId) {
		var item = service.itemsMap[itemId];
		service.currentItem = item;
	}

	service.saveCurrentItem = function() {
		if (service.currentItem.id == 0) {
			service.currentItem.id = service.items.length + 1;
		}
		addOrUpdateCache(service.currentItem);
	};

	return service;
}
appServices.factory('itemService', itemService);