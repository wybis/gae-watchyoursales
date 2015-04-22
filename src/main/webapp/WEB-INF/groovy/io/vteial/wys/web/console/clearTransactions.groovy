package io.vteial.wys.web.console;

import io.vteial.wys.dto.ResponseDto

ResponseDto responseDto = new ResponseDto()

responseDto.type = ResponseDto.SUCCESS;

jsonCategory.respondWithJson(response, responseDto)

//println 'cleaTransactions started...'
//
//try {
//
//	def entities = Order.findAll()
//	entities.each { entity ->
//		entity.delete()
//	}
//	println entities.size() + ' orders deleted'
//
//	entities = OrderReceipt.findAll()
//	entities.each { entity ->
//		entity.delete()
//	}
//	println entities.size() + ' orderReceipts deleted'
//
//	entities = Tran.findAll()
//	entities.each { entity ->
//		entity.delete()
//	}
//	println entities.size() + ' trans deleted'
//
//	entities = TranReceipt.findAll()
//	entities.each { entity ->
//		entity.delete()
//	}
//	println entities.size() + ' tranReceipts deleted'
//}
//catch(Throwable t) {
//	t.printStackTrace(out)
//}
//
//println 'clearTransactions finished...'