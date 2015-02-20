package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranAccount
import io.vteial.wys.model.constants.TransactionCategory
import io.vteial.wys.model.constants.TransactionStatus
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def entitys = datastore.execute {
	from TranAccount.class.simpleName
	where agencyId == sessionUserDto.agencyId
}

def models = []

entitys.each { entity ->
	TranAccount model = entity as TranAccount
	models <<  model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
