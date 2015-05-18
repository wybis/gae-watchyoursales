function transferService($log, $q, wydNotifyService, sessionService, $http) {

	var service = {}, receipt = {}, initSize = 1;

	service.receipt = receipt;

	function addTrans(times) {
		var trans = receipt.trans;
		for (var i = 0; i < times; i++) {
			var tran = {
				type : 'sell',
				item : {
					product : {
						code : '-',
						name : '-',
						buyRate : 0,
						buyPercent : 0,
						sellRate : 0,
						sellPercent : 0,
						handStock : 0,
						handStockAverage : 0,
					},
					handStock : 0,
					handStockAverage : 0
				},
				unit : '',
				unitRaw : 0,
				rate : '',
				rateRaw : 0,
				amount : '',
				revertAmount : '',
				message : ''
			};
			trans.push(tran);
			receipt.curTran = tran;
			receipt.curTranIndex = trans.length - 1;
			//service.computeTotalAmount();
		}
	}

	service.init = function() {
		$log.debug("transferService initialize started...")

		receipt.id = 0;
		receipt.totalAmount = 0;
		receipt.totalAmountLabel = '';

		receipt.trans = [];

		addTrans(initSize);

		receipt.curTranIndex = 0;
		receipt.curTran = receipt.trans[0];

		$log.debug("transferService initialize finished...")
	};

	function submit(reqReceipt) {
		var path = '/sessions/transfer'
		$http.post(path, reqReceipt).success(function(response) {
			// $log.debug(response);
			if (response.type === 0) {
				success(response.data, response.message)
			} else {
				fail(response.data, response.message)
			}
		});
	}

	function success(resReceipt, message) {
		$log.info("Receipt after post...")
		$log.debug(message);
		$log.info(resReceipt);

		wydNotifyService.addSuccess(message, true);
		receipt.id = resReceipt.id;
	}

	function fail(resReceipt, message) {
		$log.info("Receipt after post...")
		$log.debug(message);
		$log.info(resReceipt);
		wydNotifyService.addError(message, true);
	}

	service.init();

	return service;
}
appServices.factory('transferService', transferService);