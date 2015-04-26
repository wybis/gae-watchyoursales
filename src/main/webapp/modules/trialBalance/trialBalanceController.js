function trialBalanceController($rootScope, $scope, $log, sessionService, $http) {
	$log.debug('trialBalanceController...');
	$rootScope.viewName = 'Trial Balance';

	function processTrialBalance(data) {
		var trialBalance = {}, tbgs = [], tbg = null, debit = 0, credit = 0;
		_.forEach(data.items, function(item) {
			tbg = trialBalance[item.type];
			if (!tbg) {
				tbg = {
					type : _.capitalize(item.type),
					debit : 0,
					credit : 0,
					items : []
				};
				trialBalance[item.type] = tbg;
				tbgs.push(tbg);
			}
			tbg.items.push(item);
			if (item.amount > 0) {
				item.debit = item.amount;
				tbg.debit += item.amount;
				debit += item.amount;
			} else {
				item.credit = item.amount;
				tbg.credit += item.amount;
				credit += item.amount;
			}
		});

		var difference = credit + debit;
		if (difference < 0) {
			difference *= -1;
		}
		$scope.difference = difference;
		$scope.tbgs = tbgs;
		$scope.debit = debit;
		$scope.credit = credit;
	}

	$scope.refresh = function() {
		var path = '/sessions/trialBalance';
		$http.get(path).success(function(response) {
			if (response.type === 0) {
				processTrialBalance(response.data);
			}
			// $log.info(response);
		})
	};

	$scope.refresh();

}
appControllers.controller('trialBalanceController', trialBalanceController);
