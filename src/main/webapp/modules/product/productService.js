function stockService($rootScope, $log, $http, $q, agencyService) {

	var service = {
		sessionUser : $rootScope.sessionContext.sessionUser,	
		items : [],
		itemsMap : {}
	};

	service.getProducts = function() {

	}

	return service;
}
appServices.factory('stockService', stockService);