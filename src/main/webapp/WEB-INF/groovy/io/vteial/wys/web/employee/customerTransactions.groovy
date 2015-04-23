package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Tran
import io.vteial.wys.model.constants.TransactionCategory
import io.vteial.wys.model.constants.TransactionStatus
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def entitys = datastore.execute {
	from Tran.class.simpleName
	where agencyId == sessionUserDto.agencyId
	and category == TransactionCategory.CUSTOMER
	and status == TransactionStatus.COMPLETE
}

def models = []

entitys.each { entity ->
	Tran model = entity as Tran
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
