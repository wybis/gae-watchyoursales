package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.TransactionCategory
import io.vteial.wys.model.constants.TransactionType
import io.vteial.wys.service.SessionService
import io.vteial.wys.service.exceptions.TransactionException

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]

TranReceipt tranReceipt = jsonCategory.parseJson(request, TranReceipt.class)

try {
	if(tranReceipt.amount <= 0) {
		responseDto.type = ResponseDto.ERROR
		responseDto.message = "Transaction amount should be greater then zero..."
		jsonCategory.respondWithJson(response, responseDto)
		return
	}
	tranReceipt.trans = []
	tranReceipt.category = TransactionCategory.LEDGER;

	User cus = User.get(tranReceipt.forUserId);
	cus.cashAccount = Account.get(cus.cashAccountId);

	User emp = User.get(sessionDto.id);
	emp.cashAccount = Account.get(emp.cashAccountId);


	if(cus.cashAccount.handStock > 0) {
		Tran tran = new Tran()
		tran.accountId = cus.cashAccountId
		tran.type = TransactionType.SELL
		tran.unit = tranReceipt.amount
		tran.rate = 1
		tranReceipt.trans << tran

		tran = new Tran()
		tran.accountId = emp.cashAccountId
		tran.type = TransactionType.SELL
		tran.unit = tranReceipt.amount;
		tran.rate = 1
		tranReceipt.trans << tran
	}
	else {
		Tran tran = new Tran()
		tran.accountId = cus.cashAccountId
		tran.type = TransactionType.BUY
		tran.unit = tranReceipt.amount
		tran.rate = 1
		tranReceipt.trans << tran

		tran = new Tran()
		tran.accountId = emp.cashAccountId
		tran.type = TransactionType.BUY
		tran.unit = tranReceipt.amount;
		tran.rate = 1
		tranReceipt.trans << tran
	}

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