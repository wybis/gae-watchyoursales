package io.vteial.wys.web.session
import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.constants.TransactionStatus
import io.vteial.wys.service.SessionService
import io.vteial.wys.service.exceptions.TransactionException

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]

TranReceipt tranReceipt = jsonCategory.parseJson(request, TranReceipt.class)

try {
	if(tranReceipt.trans[0].unit <= 0) {
		responseDto.type = ResponseDto.ERROR
		responseDto.message = "Transaction amount should be greater then zero..."
		jsonCategory.respondWithJson(response, responseDto)
		return
	}
	if(tranReceipt.trans[0].accountId == tranReceipt.trans[1].accountId) {
		responseDto.type = ResponseDto.ERROR
		responseDto.message = "Transaction can't be done between same ledgers..."
		jsonCategory.respondWithJson(response, responseDto)
		return
	}

	//tranReceipt.status = TransactionStatus.PENDING
	tranService.add(sessionDto, tranReceipt)

	responseDto.data = tranReceipt;
	responseDto.message = "Transaction saved successfuly..."
}
catch(TransactionException e) {
	responseDto.type = ResponseDto.ERROR
	responseDto.message = tranReceipt.errorMessage;
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