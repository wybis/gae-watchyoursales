package io.vteial.wys.web.session
import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.service.SessionService
import io.vteial.wys.service.exceptions.TransactionException

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

OrderReceipt orderReceipt = jsonCategory.parseJson(request, OrderReceipt.class)

try {
	//orderService.add(sessionDto, orderReceipt)

	responseDto.type = ResponseDto.ERROR
	responseDto.message = 'Not yet implemented...'
	responseDto.data = orderReceipt;
	//responseDto.message = "Order saved successfuly..."
}
catch(TransactionException e) {
	responseDto.type = ResponseDto.ERROR
	responseDto.message = orderReceipt.errorMessage;
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