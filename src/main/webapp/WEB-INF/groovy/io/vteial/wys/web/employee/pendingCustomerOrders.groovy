package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Order
import io.vteial.wys.model.constants.OrderCategory
import io.vteial.wys.model.constants.OrderStatus
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def entitys = datastore.execute {
	from Order.class.simpleName
	where agencyId == sessionUserDto.agencyId
	and category == OrderCategory.CUSTOMER
	and status == OrderStatus.PENDING
}

def models = []

entitys.each { entity ->
	Order model = entity as Order
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
