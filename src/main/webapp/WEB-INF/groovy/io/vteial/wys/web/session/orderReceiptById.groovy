package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.constants.OrderStatus
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionUser = session[SessionService.SESSION_USER_KEY]

try {
	long orderReceiptId = params.orderReceiptId as Long

	OrderReceipt receipt = orderService.findByOrderReceiptId(orderReceiptId)

	responseDto.data = receipt
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.message = 'fetching order receipt failed...';
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)
