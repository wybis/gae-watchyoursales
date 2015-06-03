package io.vteial.wys.web.session
import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.constants.OrderType
import io.vteial.wys.service.SessionService
import io.vteial.wys.service.exceptions.TransactionException

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

OrderReceipt orderReceipt = jsonCategory.parseJson(request, OrderReceipt.class)

try {

	double totalAmount = 0, actualTotalAmount = 0, amount = 0
	orderReceipt.orders.each { Order order ->
		amount = order.unit * order.rate
		if(order.type == OrderType.BUY) {
			amount *= -1
		}
		totalAmount += amount
	}
	actualTotalAmount = totalAmount < 0 ? totalAmount * -1 : totalAmount

	orderService.add(sessionUserDto, orderReceipt)

	responseDto.data = orderReceipt;
	responseDto.message = "Order saved successfuly..."
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