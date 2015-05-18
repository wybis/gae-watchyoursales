function trialBalanceController($rootScope, $scope, $log, sessionService, $http) {
	$log.debug('trialBalanceController...');
	$rootScope.viewName = 'Trial Balance';

	function processTrialBalance(data) {
		var tbs = {}, tbgs = [], tbg = null, debit = 0, credit = 0, paccts = [];
		_.forEach(data.items, function(item) {
			if (item.type == 'product') {
				paccts.push(item);
			} else {
				tbg = tbs[item.type];
				if (!tbg) {
					tbg = {
						type : item.type,
						debit : 0,
						credit : 0,
						items : []
					};
					tbs[item.type] = tbg;
					tbgs.push(tbg);
				}
				tbg.items.push(item);
				if (item.type == 'dealer' || item.type == 'customer') {
					if (item.amount < 0) {
						item.debit = item.amount;
						tbg.debit += item.amount;
						debit += item.amount;
					} else {
						item.credit = item.amount;
						tbg.credit += item.amount;
						credit += item.amount;
					}
				} else {
					if (item.amount > 0) {
						item.debit = item.amount;
						tbg.debit += item.amount;
						debit += item.amount;
					} else {
						item.credit = item.amount;
						tbg.credit += item.amount;
						credit += item.amount;
					}
				}
			}
		});

		var pacctsg = _.groupBy(paccts, function(item) {
			return item.aliasName
		});
		tbg = {
			type : 'product',
			debit : 0,
			credit : 0,
			items : []
		};
		var pcodes = _.keys(pacctsg);
		_.forEach(pcodes, function(pcode) {
			var amount = 0;
			_.forEach(pacctsg[pcode], function(item) {
				amount += item.amount;
			});
			var acct = _.clone(pacctsg[pcode][0]);
			tbg.items.push(acct);

			acct.amount = amount;
			acct.credit = acct.amount;
			tbg.credit += amount;

			credit += amount;
		});
		tbgs.push(tbg);

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
