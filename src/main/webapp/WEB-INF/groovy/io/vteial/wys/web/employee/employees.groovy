package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Role
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def models = []

if(sessionUserDto.roleId == Role.AGENCY_MANAGER) {
	def entitys = Employee.findAll()
	models = entitys.findAll{ entity ->
		entity.agencyId == sessionUserDto.agencyId
	}
	//	def entitys = datastore.execute {
	//		from Employee.class.simpleName
	//		where agencyId == sessionUserDto.agencyId
	//	}
	//
	//	entitys.each { entity ->
	//		Employee model = entity as Employee
	//		models <<  model
	//	}
}
else {
	Employee model = Employee.get(sessionUserDto.id)
	models << model
}

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
