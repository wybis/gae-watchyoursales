package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.constants.OrderCategory
import io.vteial.wys.model.constants.OrderStatus
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def entitys = datastore.execute {
	from Order.class.simpleName
	where agencyId == sessionUserDto.agencyId
	and category == OrderCategory.DEALER
	and status == OrderStatus.PENDING
}

def models = []

entitys.each { entity ->
	Order model = entity as Order
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
