package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType

ResponseDto responseDto = new ResponseDto()

def models = []

def entitys = datastore.execute {
	from User.class.simpleName
	where agencyId == sessionUserDto.agencyId
	and type == UserType.EMPLOYEE
}

entitys.each { entity ->
	User model = entity as User
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
