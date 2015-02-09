package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Stock
import io.vteial.wys.model.constants.StockType
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def entitys = datastore.execute {
	from Stock.class.simpleName
	where employeeId == sessionUserDto.id
	and type == StockType.CASH
}

def models = []

entitys.each { entity ->
	Stock model = entity as Stock
	models <<  model
}

responseDto.data = models[0]

jsonCategory.respondWithJson(response, responseDto)
