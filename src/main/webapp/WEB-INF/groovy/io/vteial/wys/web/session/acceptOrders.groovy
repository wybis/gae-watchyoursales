package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Product
import io.vteial.wys.model.Transfer
import io.vteial.wys.model.constants.OrderStatus
import io.vteial.wys.model.constants.OrderType
import io.vteial.wys.service.SessionService

def findByCodeAndBranchId = { prdCode, prdBranchId ->
	def entitys = datastore.execute {
		from Product.class.simpleName
		where code == prdCode
		and branchId == prdBranchId
	}

	Product product = null

	entitys.each { entity ->
		product = entity as Product
	}

	return product
}

def findByProductIdAndUserId = { accProductId, accUserId ->
	def entitys = datastore.execute {
		from Account.class.simpleName
		where productId == accProductId
		and userId == accUserId
	}

	Account account = null

	entitys.each { entity ->
		account = entity as Account
	}

	return account
}

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionUser = session[SessionService.SESSION_USER_KEY]

try {
	List<Account> accounts = []

	long employeeId = params.employeeId as Long

	List<Long> receiptIds = jsonCategory.parseJson(request, List.class)

	receiptIds.each { receiptId ->
		OrderReceipt receipt = orderService.findByOrderReceiptId(receiptId)

		Transfer transfer = Transfer.get(receipt.transferId)
		receipt.transfer = transfer

		List<Order> orders = receipt.orders
		orders.each { order ->
			order.status = OrderStatus.ACCEPTED

			if(order.type == OrderType.SELL) {
				Product product = findByCodeAndBranchId(order.productCode, order.branchId)

				Account frAccount = findByProductIdAndUserId(product.id, transfer.fromUserId)
				frAccount.product = product

				frAccount.withdrawHandStock(order.unit)

				frAccount.preUpdate(sessionUser.id)
				frAccount.save()

				Account toAccount = findByProductIdAndUserId(product.id, transfer.toUserId)
				toAccount.product = product

				toAccount.depositHandStock(order.unit)

				toAccount.preUpdate(sessionUser.id)
				toAccount.save()

				accounts << toAccount
			}

			order.preUpdate(sessionUser.id)
			order.save()
		}

		receipt.status = OrderStatus.ACCEPTED

		receipt.preUpdate(sessionUser.id)
		receipt.save()
	}

	responseDto.message = "Successfully accepted...";
	responseDto.data = accounts
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.message = 'customer order accepting failed...';
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)
