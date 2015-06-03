package io.vteial.wys.web.session
import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.TransactionType
import io.vteial.wys.service.SessionService
import io.vteial.wys.service.exceptions.TransactionException

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

TranReceipt tranReceipt = jsonCategory.parseJson(request, TranReceipt.class)

try {

	double totalAmount = 0, absTotalAmount = 0, amount = 0
	tranReceipt.trans.each { Tran tran ->
		amount = tran.unit * tran.rate
		if(tran.type == TransactionType.BUY) {
			amount *= -1
		}
		totalAmount += amount
	}
	absTotalAmount = totalAmount < 0 ? totalAmount * -1 : totalAmount

	if(tranReceipt.amount != 0) {

		User emp = User.get(sessionUserDto.id);
		//emp.cashAccount = Account.get(emp.cashAccountId);

		Tran tran = new Tran()
		tran.accountId = emp.cashAccountId
		tran.unit = absTotalAmount
		tran.rate = 1

		if(totalAmount < 0) {
			tran.type = TransactionType.SELL
		} else {
			tran.type = TransactionType.BUY
		}

		tranReceipt.trans << tran

	} else {

		User cus = User.get(tranReceipt.forUserId);
		//cus.cashAccount = Account.get(cus.cashAccountId);

		Tran tran = new Tran()
		tran.accountId = cus.cashAccountId
		tran.unit = absTotalAmount
		tran.rate = 1

		if(totalAmount < 0) {
			tran.type = TransactionType.BUY
		}
		else {
			tran.type = TransactionType.SELL
		}

		tranReceipt.trans << tran
	}

	tranService.add(sessionUserDto, tranReceipt)

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