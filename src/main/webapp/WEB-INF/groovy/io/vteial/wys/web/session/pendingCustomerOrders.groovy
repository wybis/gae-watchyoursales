package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Role
import io.vteial.wys.model.constants.OrderCategory
import io.vteial.wys.model.constants.OrderStatus
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto(type : 0, message : 'success...')

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]

try {
	long userId = params.forUserId as Long

	List<Order> models = []

	def entitys = null
	if(sessionDto.roleId == Role.ID_MANAGER) {
		entitys = datastore.execute {
			from Order.class.simpleName
			where category == OrderCategory.CUSTOMER
			and forUserId == userId
			and status == OrderStatus.ACCEPTED
		}
	}
	else {
		entitys = datastore.execute {
			from Order.class.simpleName
			where category == OrderCategory.CUSTOMER
			and forUserId == userId
			and byUserId == sessionDto.id
			and status == OrderStatus.ACCEPTED
		}
	}

	entitys.each { entity ->
		Order model = entity as Order
		models <<  model
	}

	responseDto.data = models
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.message = 'Fetching pending customer orders failed...';
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)
