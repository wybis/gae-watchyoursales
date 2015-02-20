package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def entitys = datastore.execute {
	from Account.class.simpleName
	where agencyId == sessionUserDto.agencyId
}

def models = []

entitys.each { entity ->
	Account model = entity as Account
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
