package io.vteial.wys.web.dealer

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Dealer

ResponseDto responseDto = new ResponseDto()

def entitys = datastore.execute {
	from Dealer.class.simpleName
	where agencyId == params.agencyId as long
}

def models = []
entitys.each { entity ->
	Dealer model = entity as Dealer
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
