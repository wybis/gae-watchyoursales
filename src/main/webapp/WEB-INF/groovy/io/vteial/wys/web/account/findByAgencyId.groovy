package io.vteial.wys.web.account

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Account

ResponseDto responseDto = new ResponseDto()

def entitys = datastore.execute {
	from Account.class.simpleName
	where agencyId == params.agencyId as long
}

def models = []
entitys.each { entity ->
	Account model = entity as Account
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
