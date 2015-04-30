function ledgersController($rootScope, $scope, $log, wydNotifyService,
		sessionService) {
	$log.debug('ledgersController...');
	$rootScope.viewName = 'Ledgers';

	$scope.accountsMap = sessionService.accountsMap;

	$scope.items = [];

	function processLedgers() {
		if (sessionService.context.sessionDto.roleId == 'manager') {
			_.forEach(sessionService.accounts, function(item) {
				if (item.type == 'cashCapital') {
					$scope.items.push(item);
				}
				if (item.type == 'cashEmployee') {
					$scope.items.push(item);
				}
				if (item.type == 'profitEmployee') {
					$scope.items.push(item);
				}
			});
		} else {
			var userId = sessionService.context.sessionDto.id
			_.forEach(sessionService.accounts, function(item) {
				if (item.userId === userId) {
					$scope.items.push(item);
				}
			});
		}
	}

	$scope.refresh = function() {
		sessionService.getLedgers().then(function(response) {
			if (response.type === 0) {
				processLedgers();
			}
		});
	};

	$scope.refresh();
}
appControllers.controller('ledgersController', ledgersController);
