package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionUser = session[SessionService.SESSION_USER_KEY]

try {
	long tranReceiptId = params.tranReceiptId as Long

	TranReceipt receipt = tranService.findByTranReceiptId(tranReceiptId)

	responseDto.data = receipt
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.message = 'fetching tran receipt failed...';
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)
