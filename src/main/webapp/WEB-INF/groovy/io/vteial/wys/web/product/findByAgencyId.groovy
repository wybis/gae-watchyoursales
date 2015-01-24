package io.vteial.wys.web.product

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Product

ResponseDto responseDto = new ResponseDto()

def entitys = datastore.execute {
	from Product.class.simpleName
	where agencyId == params.agencyId as long
}

def models = []
entitys.each { entity ->
	Product model = entity as Product
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
