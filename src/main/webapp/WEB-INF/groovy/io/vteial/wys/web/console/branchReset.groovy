package io.vteial.wys.web.console;

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Product
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt

ResponseDto responseDto = new ResponseDto()

responseDto.type = ResponseDto.SUCCESS;

try {
	def deleteEntitys = { entityName, ibranchId ->
		def keys = datastore.execute {
			select keys from entityName
			where branchId == ibranchId
		}
		keys.each {	key ->
			key.delete()
		}
	}

	long ibranchId = params.branchId as Long

	deleteEntitys(Tran.class.simpleName, ibranchId)

	deleteEntitys(TranReceipt.class.simpleName, ibranchId)

	def entitys = datastore.execute {
		from Account.class.simpleName
		where branchId == ibranchId
	}
	entitys.each {	entity ->
		Account account = entity as Account
		account.with {
			amount = 0
			handStock = 0
			virtualStockBuy = 0
			virtualStockSell = 0
			availableStock = 0
		}
		account.save()
	}

	entitys = datastore.execute {
		from Product.class.simpleName
		where branchId == ibranchId
	}
	entitys.each {	entity ->
		Product product = entity as Product
		product.with {
			amount = 0
			handStock = 0
			handStockAverage = 0
			virtualStockBuy = 0
			virtualStockSell = 0
			virtualStockAverage = 0
			availableStock = 0
			availableStockAverage = 0
		}
		product.save()
	}

	responseDto.message = "Shop '${ibranchId}' reseted...";
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)