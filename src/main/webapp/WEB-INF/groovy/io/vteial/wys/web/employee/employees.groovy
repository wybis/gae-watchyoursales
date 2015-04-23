package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def models = employeeService.getMyEmployees(sessionUserDto)

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
