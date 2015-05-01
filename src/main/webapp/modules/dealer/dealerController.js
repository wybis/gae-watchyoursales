function dealerController($rootScope, $scope, $log, sessionService) {
	$rootScope.viewName = 'Dealers';

	$scope.items = sessionService.dealers;

	$scope.refresh = function() {
		sessionService.getDealers();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	// $scope.refresh();

	$log.debug('dealerController...');
}
appControllers.controller('dealerController', dealerController);

function dealerViewController($rootScope, $scope, $log, $location,
		sessionService) {
	$rootScope.viewName = 'View Dealer';

	$scope.back = function() {
		$location.path('/counter');
	};

	$log.debug('dealerVeiwController...');
}
appControllers.controller('dealerViewController', dealerViewController);

function dealerSearchController($rootScope, $scope, $log, $location,
		sessionService, counterService) {
	$log.debug('dealerSearchController...');
	$rootScope.viewName = 'Search Dealer';

	$scope.items = sessionService.dealers;

	$scope.select = function(dealerId) {
		var dealer = sessionService.dealersMap[dealerId];
		counterService.setDealer(dealer);
		$scope.back();
	};

	$scope.back = function() {
		$location.path('/counter');
	};

	$scope.refresh = function() {
		sessionService.getDealers();
	};

	$scope.bottomReached = function() {
		$log.info('bottom reached...');
	}

	$scope.refresh();

}
appControllers.controller('dealerSearchController', dealerSearchController);
