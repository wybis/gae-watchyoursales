function dealerTranService($log, $http, $q, wydNotifyService, sessionService,
		$http) {
	var basePath = '/sessions', isLoggingEnabled = false;

	var service = {};

	service.init = function() {
		service.receipt = {
			id : 0,
			description : '',
			message : ''
		};
	};

	service.setForUserById = function(id) {
		var receipt = service.receipt;
		var dealer = sessionService.dealersMap[id];
		if (dealer) {
			receipt.forUser = dealer;
		}
	};

	service.onAmount = function() {
		var receipt = service.receipt;
		var unit = receipt.amount;
		if (unit == '' || _.isUndefined(unit)) {
			return;
		}
		if (!_.isNumber(unit)) {
			unit = unit.split(',').join('')
			unit = parseFloat(unit);
		}
		receipt.amountRaw = unit;
	};

	service.saveReceiptAsTran = function() {
		$log.debug('saveReceiptAsTran started...');

		$log.debug(service.receipt);

		var receipt = service.receipt;
		var reqReceipt = {
			forUserId : receipt.forUser.id,
			description : receipt.description,
			amount : receipt.amountRaw
		};

		$log.info("Tran receipt before post...")
		$log.info(reqReceipt);

		var path = basePath + '/dealerTran'
		var deferred = $q.defer();
		$http.post(path, reqReceipt).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				success(response.data, response.message)
				deferred.resolve(response);
			} else {
				fail(response.data, response.message)
			}
			$log.debug('saveReceiptAsTran finished...');
		});

		return deferred.promise;
	};

	function success(resReceipt, message) {
		$log.info("Tran receipt after post...")
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
		$log.info("Tran receipt after post...")
		$log.debug(message);
		$log.info(resReceipt);

		wydNotifyService.addError(message, true);
		service.receipt.message = message;
	}

	return service;
}
appServices.factory('dealerTranService', dealerTranService);