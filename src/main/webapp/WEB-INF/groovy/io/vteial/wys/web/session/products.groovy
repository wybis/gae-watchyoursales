package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Product
import io.vteial.wys.model.constants.ProductType
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]

def entitys = datastore.execute {
	from Product.class.simpleName
	where branchId == sessionDto.branchId
	and type != ProductType.PROFIT_EMPLOYEE
}

def models = []

entitys.each { entity ->
	Product model = entity as Product
	model.computeHandStockValue()
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
