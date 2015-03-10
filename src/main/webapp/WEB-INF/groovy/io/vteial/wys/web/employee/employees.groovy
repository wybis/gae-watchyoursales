package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Role
import io.vteial.wys.model.Stock
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def models = []

if(sessionUserDto.roleId == Role.AGENCY_MANAGER) {
	def entitys = datastore.execute {
		from User.class.simpleName
		where agencyId == sessionUserDto.agencyId
		and type == UserType.EMPLOYEE
	}

	entitys.each { entity ->
		User model = entity as User
		model.stock = Stock.get(model.stockId)
		models <<  model
	}
}
else {
	User model = User.get(sessionUserDto.id)
	models << model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
