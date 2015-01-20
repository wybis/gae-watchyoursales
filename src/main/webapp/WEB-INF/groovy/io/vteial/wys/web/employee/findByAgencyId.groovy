package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.model.Employee

ResponseDto responseDto = new ResponseDto()

//def entitys = datastore.execute {
//	from Employee.class.simpleName
//	where agencyId == params.agencyId as long
//}
//def models = []
//entitys.each { entity ->
//	Employee model = entity as Employee
//	models <<  model
//}

def entitys = Employee.findAll()
def models = entitys.findAll{ it.agencyId == params.agencyId as long }

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
