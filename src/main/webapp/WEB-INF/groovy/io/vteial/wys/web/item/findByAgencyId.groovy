package io.vteial.wys.web.item

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Item

ResponseDto responseDto = new ResponseDto()

def entitys = datastore.execute {
	from Item.class.simpleName
	where agencyId == params.agencyId as long
}

def models = []
entitys.each { entity ->
	Item model = entity as Item
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
