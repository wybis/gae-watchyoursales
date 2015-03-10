package io.vteial.wys.web.employee

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.service.SessionService

ResponseDto responseDto = new ResponseDto()

SessionUserDto sessionUserDto = session[SessionService.SESSION_USER_KEY]

def models = employeeService.getMyProductStocks(sessionUserDto)

responseDto.data = models

jsonCategory.respondWithJson(response, responseDto)
