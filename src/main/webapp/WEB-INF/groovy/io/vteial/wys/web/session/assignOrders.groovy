package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Transfer
import io.vteial.wys.model.constants.OrderStatus
import io.vteial.wys.model.constants.TransferStatus
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionUser = session[SessionService.SESSION_USER_KEY]

try {
	long employeeId = params.employeeId as Long

	List<Long> receiptIds = jsonCategory.parseJson(request, List.class)

	receiptIds.each { receiptId ->
		OrderReceipt receipt = orderService.findByOrderReceiptId(receiptId)

		Transfer transfer = null
		if(!receipt.transferId) {
			transfer = new Transfer()
			transfer.id = autoNumberService.getNextNumber(sessionUser, Transfer.ID_KEY)
			transfer.with {
				date = new Date()
				status = TransferStatus.PENDING
				branchId = sessionUser.branchId
			}
			receipt.transferId = transfer.id
			receipt.transfer = transfer
		}
		else {
			transfer = Transfer.get(receipt.transferId)
		}
		transfer.fromUserId = receipt.byUserId

		List<Order> orders = receipt.orders
		orders.each { order ->
			order.byUserId = employeeId
			order.status = OrderStatus.ASSIGNED

			order.preUpdate(sessionUser.id)
			order.save()
		}

		receipt.byUserId = employeeId;
		receipt.status = OrderStatus.ASSIGNED

		receipt.preUpdate(sessionUser.id)
		receipt.save();

		transfer.toUserId = receipt.byUserId

		transfer.byUserId = sessionUser.id
		transfer.branchId = sessionUser.branchId

		transfer.prePersist(sessionUser.id)
		transfer.save()
	}

	responseDto.message = "Successfully assigned...";
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.message = 'customer order assinging failed...';
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)
