package io.vteial.wys.web.stock

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Stock

ResponseDto responseDto = new ResponseDto()

def entitys = datastore.execute {
	from Stock.class.simpleName
	where agencyId == params.agencyId as long
}

def models = []
entitys.each { entity ->
	Stock model = entity as Stock
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
