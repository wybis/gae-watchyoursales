package io.vteial.wys.web.session
import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.service.SessionService
import io.vteial.wys.service.exceptions.TransactionException

import com.google.appengine.api.datastore.Transaction

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]

TranReceipt tranReceipt = jsonCategory.parseJson(request, TranReceipt.class)

if(tranReceipt.trans[0].accountId == tranReceipt.trans[1].accountId) {
	responseDto.message = "Transaction can't be done between same ledgers..."

	jsonCategory.respondWithJson(response, responseDto)

	return
}

Transaction tm = datastore.beginTransaction()

try {

	tranService.add(sessionDto, tranReceipt)

	responseDto.data = tranReceipt;
	responseDto.message = "Transaction saved successfuly..."
	tm.commit()
}
catch(TransactionException e) {
	tm.rollback()

	responseDto.type = ResponseDto.ERROR
	responseDto.message = tranReceipt.errorMessage;
}
catch(Throwable t) {
	tm.rollback()

	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message
	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)