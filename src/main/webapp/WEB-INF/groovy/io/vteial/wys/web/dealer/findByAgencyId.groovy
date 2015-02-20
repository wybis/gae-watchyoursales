package io.vteial.wys.web.dealer

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType

ResponseDto responseDto = new ResponseDto()

def entitys = datastore.execute {
	from Customer.class.simpleName
	where agencyId == params.agencyId as long
	and type == UserType.DEALER
}

def models = []
entitys.each { entity ->
	User model = entity as User
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
