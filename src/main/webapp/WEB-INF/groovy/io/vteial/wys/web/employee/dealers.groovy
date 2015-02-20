package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def entitys = datastore.execute {
	from User.class.simpleName
	where agencyId == sessionUserDto.agencyId
	and type == UserType.CUSTOMER
}

def models = []

entitys.each { entity ->
	User model = entity as User
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
