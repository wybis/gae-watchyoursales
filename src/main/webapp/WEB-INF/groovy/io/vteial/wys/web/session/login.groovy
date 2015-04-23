package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.service.exceptions.InvalidCredentialException

ResponseDto responseDto = new ResponseDto(type : 1, message : 'Invalid User Id or Password')
request.responseDto = responseDto

UserDto userDto = jsonCategory.parseJson(request, UserDto.class)
try {
	SessionDto sessionUserDto = sessionService.login(session, userDto)
	responseDto.data = sessionService.properties(session)
	responseDto.type = 0
	responseDto.message = 'Successfully logged in...'
}
catch(InvalidCredentialException e) {
}

jsonCategory.respondWithJson(response, responseDto)