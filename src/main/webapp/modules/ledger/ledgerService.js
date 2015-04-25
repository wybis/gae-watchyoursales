function ledgerService($log, $http, $q, wydNotifyService, sessionService, $http) {
	var basePath = '', isLoggingEnabled = false;

	var defaultReceipt = {
		id : 0,
		trans : [ {
			account : {
				id : 0,
				balance : ''
			},
			type : 'sell',
			amount : ''
		}, {
			account : {
				id : 0,
				balance : ''
			},
			type : 'buy',
			amount : ''
		} ]
	}, service = {};

	service.receipt = _.cloneDeep(defaultReceipt);

	service.clearOrNew = function() {
		$log.debug('clearOrNew started...');

		service.receipt.id = 0;
		service.receipt.trans = _.cloneDeep(defaultReceipt.trans);

		$log.debug('clearOrNew finished...');
	}

	service.saveReceiptAsTransaction = function() {
		$log.debug('saveReceiptAsTransaction started...');

		var receipt = {
			trans : [ {
				accountId : service.receipt.trans[0].account.id,
				type : 'sell',
				amount : service.receipt.trans[0].amount
			}, {
				accountId : service.receipt.trans[1].account.id,
				type : 'buy',
				amount : service.receipt.trans[0].amount
			} ]
		};
		$log.info(receipt);
		wydNotifyService.addSuccess('Not yet implemented...', true);

		$log.debug('saveReceiptAsTransaction finished...');
	}

	return service;
}
appServices.factory('ledgerService', ledgerService);