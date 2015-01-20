package io.vteial.wys.web.customer

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Customer

ResponseDto responseDto = new ResponseDto()

def entitys = datastore.execute {
	from Customer.class.simpleName
	where agencyId == params.agencyId as long
}

def models = []
entitys.each { entity ->
	Customer model = entity as Customer
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
