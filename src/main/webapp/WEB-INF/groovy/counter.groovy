import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Stock
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.TransactionType
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

try {
	TranReceipt tranReceipt = jsonCategory.parseJson(request, TranReceipt.class)

	double totalAmount = 0, amount = 0
	tranReceipt.trans.each { tran ->
		amount = tran.unit * tran.rate
		if(tran.type == TransactionType.BUY) {
			amount *= -1
		}
		totalAmount += amount
	}

	if(totalAmount != 0) {
		Stock cashStock = employeeService.getMyCashStock(sessionUserDto)

		Tran tran = new Tran()
		tran.category = tranReceipt.category
		tran.stockId = cashStock.id
		tran.type = totalAmount > 0 ? TransactionType.BUY : TransactionType.SELL
		tran.unit = totalAmount
		tran.rate = 1
		//tran.rate = stock.product.sellRate

		tranReceipt.trans << tran

		User customer = User.get(tranReceipt.customerId);
		customer.stock = Stock.get(customer.stockId);

		tran = new Tran()
		tran.category = tranReceipt.category
		tran.stockId = customer.stock.id
		tran.type = totalAmount < 0 ? TransactionType.BUY : TransactionType.SELL
		tran.unit = totalAmount
		tran.rate = 1
		//tran.rate = stock.product.sellRate

		tranReceipt.trans << tran
	}

	tranService.add(sessionUserDto, tranReceipt)

	responseDto.data = tranReceipt;
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message
}

jsonCategory.respondWithJson(response, responseDto)