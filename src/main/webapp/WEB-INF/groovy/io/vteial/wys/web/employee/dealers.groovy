package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Customer
import io.vteial.wys.model.constants.CustomerType
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def entitys = datastore.execute {
	from Customer.class.simpleName
	where agencyId == sessionUserDto.agencyId
	and type == CustomerType.CUSTOMER
}

def models = []

entitys.each { entity ->
	Customer model = entity as Customer
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
