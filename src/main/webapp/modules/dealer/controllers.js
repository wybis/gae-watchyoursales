function dealerListController($rootScope, $scope, $log, sessionService) {
	$log.debug('dealerListController...');
	$rootScope.viewName = 'Dealers';

	$scope.items = sessionService.dealers;

	$scope.refresh = function() {
		sessionService.getDealers();
	};

	// $scope.refresh();
}
appControllers.controller('dealerListController', dealerListController);

function dealerViewController($rootScope, $scope, $log, $location,
		sessionService) {
	$log.debug('dealerVeiwController...');
	$rootScope.viewName = 'View Dealer';

	$scope.back = function() {
		$location.path('/counter');
	};

}
appControllers.controller('dealerViewController', dealerViewController);

function dealerEditController($rootScope, $scope, $log, $location,
		sessionService) {
	$log.debug('dealerEditController...');
	$rootScope.viewName = 'Edit Dealer';

	$scope.back = function() {
		$location.path('/counter');
	};
}
appControllers.controller('dealerEditController', dealerEditController);

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
