package io.vteial.wys.web.session
import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]

try {
	TranReceipt tranReceipt = jsonCategory.parseJson(request, TranReceipt.class)

	tranService.add(sessionDto, tranReceipt)

	responseDto.data = tranReceipt;
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