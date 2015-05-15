function dealerController($rootScope, $scope, $log, sessionService) {
	$log.debug('dealerController...');
	$rootScope.viewName = 'Dealers';

	$scope.items = sessionService.dealers;

	$scope.refresh = function() {
		sessionService.getDealers();
	};

	// $scope.refresh();
}
appControllers.controller('dealerController', dealerController);

function dealerViewController($rootScope, $scope, $log, $location,
		sessionService) {
	$log.debug('dealerVeiwController...');
	$rootScope.viewName = 'View Dealer';

	$scope.back = function() {
		$location.path('/counter');
	};

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

	// $scope.refresh();
}
appControllers.controller('dealerSearchController', dealerSearchController);

function dealerPayOrCollectController($rootScope, $scope, $log, $location,
		sessionService) {
	$log.debug('dealerPayOrCollectController...');
	$rootScope.viewName = 'Dealer Pay Or Collect';

	$scope.back = function() {
		$location.path('/counter');
	};

}
appControllers.controller('dealerPayOrCollectController', dealerPayOrCollectController);
