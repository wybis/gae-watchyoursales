function ledgerService($log, $http, $q, wydNotifyService, sessionService, $http) {
	var basePath = '/sessions', isLoggingEnabled = false;

	var defaultReceipt = {
		id : 0,
		description : '',
		message : '',
		trans : [ {
			account : {
				id : 0,
				balance : ''
			},
			type : 'sell',
			unit : ''
		}, {
			account : {
				id : 0,
				balance : ''
			},
			type : 'buy',
			unit : ''
		} ]
	}, service = {};

	service.receipt = _.cloneDeep(defaultReceipt);

	service.onTransactionUnit = function() {
		var unit = service.receipt.trans[0].unit;
		if (unit == '' || _.isUndefined(unit)) {
			return;
		}
		if (!_.isNumber(unit)) {
			unit = unit.split(',').join('')
			unit = parseFloat(unit);
		}
		service.receipt.trans[0].unitRaw = unit;
	};

	service.getRecentTransactions = function() {
		var path = basePath + '/recentLedgerTransactions'

		var deferred = $q.defer();
		$http.get(path).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				deferred.resolve(response);
			} else {
				$log.error('fetching recent ledger transacitons failed...');
			}
			$log.debug('saveReceiptAsTransaction finished...');
		});

		return deferred.promise;
	};

	service.clearOrNew = function() {
		$log.debug('clearOrNew started...');

		service.receipt.id = 0;
		service.receipt.description = '';
		service.receipt.message = '';
		service.receipt.trans = _.cloneDeep(defaultReceipt.trans);

		$log.debug('clearOrNew finished...');
	};

	service.saveReceiptAsTransaction = function() {
		$log.debug('saveReceiptAsTransaction started...');
		
		$log.debug("Ledger receipt before process...")
		$log.debug(service.receipt);

		var reqReceipt = {
			description : service.receipt.description,
			trans : [ {
				category : 'ledger',
				accountId : service.receipt.trans[0].account.id,
				type : 'sell',
				unit : service.receipt.trans[0].unitRaw,
				rate : 1
			}, {
				category : 'ledger',
				accountId : service.receipt.trans[1].account.id,
				type : 'buy',
				unit : service.receipt.trans[0].unitRaw,
				rate : 1
			} ]
		};
		reqReceipt.trans[0].amount = service.receipt.trans[0].unitRaw;
		reqReceipt.trans[1].amount = service.receipt.trans[0].unitRaw;

		$log.info("Ledger receipt before post...")
		$log.info(reqReceipt);

		var path = basePath + '/ledgerTransaction'
		var deferred = $q.defer();
		$http.post(path, reqReceipt).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				success(response.data, response.message)
				deferred.resolve(response);
			} else {
				fail(response.data, response.message)
			}
			$log.debug('saveReceiptAsTransaction finished...');
		});

		return deferred.promise;
	};

	function success(resReceipt, message) {
		$log.info("Ledger receipt after post...")
		$log.debug(message);
		$log.info(resReceipt);

		wydNotifyService.addSuccess(message, true);
		service.receipt.id = resReceipt.id;
		service.receipt.message = message;

		_.forEach(resReceipt.trans, function(tran) {
			sessionService.updateAccount(tran.account);
		});
	}

	function fail(resReceipt, message) {
		$log.info("Ledger receipt after post...")
		$log.debug(message);
		$log.info(resReceipt);

		wydNotifyService.addError(message, true);
		service.receipt.message = message;
	}

	return service;
}
appServices.factory('ledgerService', ledgerService);