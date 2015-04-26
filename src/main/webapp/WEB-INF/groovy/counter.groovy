import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.TransactionType
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

try {
	TranReceipt tranReceipt = jsonCategory.parseJson(request, TranReceipt.class)

	double totalAmount = 0, actualTotalAmount = 0, amount = 0
	tranReceipt.trans.each { tran ->
		amount = tran.unit * tran.rate
		if(tran.type == TransactionType.BUY) {
			amount *= -1
		}
		totalAmount += amount
	}
	actualTotalAmount = totalAmount < 0 ? totalAmount * -1 : totalAmount

	if(totalAmount != 0) {
		User employee = User.get(sessionUserDto.id);
		//employee.cashStock = Stock.get(employee.cashStockId);
		//employee.profitStock = Stock.get(employee.profitStockId);

		Tran tran = new Tran()
		tran.category = tranReceipt.category
		tran.accountId = employee.cashAccountId
		tran.type = totalAmount < 0 ? TransactionType.SELL : TransactionType.BUY
		tran.unit = actualTotalAmount
		tran.rate = 1
		//tran.rate = account.product.sellRate

		tranReceipt.trans << tran

		User customer = User.get(tranReceipt.customerId);
		//customer.cashStock = Stock.get(customer.cashStockId);

		tran = new Tran()
		tran.category = tranReceipt.category
		tran.accountId = customer.cashAccountId
		tran.type = totalAmount < 0 ? TransactionType.BUY: TransactionType.SELL
		tran.unit = actualTotalAmount
		tran.rate = 1
		//tran.rate = account.product.sellRate

		tranReceipt.trans << tran
	}

	tranService.add(sessionUserDto, tranReceipt)

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