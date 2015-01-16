package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.service.exceptions.InvalidCredentialException

ResponseDto responseDto = new ResponseDto(type : 1, message : 'Invalid User Id or Password')
request.responseDto = responseDto

UserDto userDto = jsonCategory.parseJson(request, UserDto.class)
try {
	SessionUserDto sessionUserDto = sessionService.login(session, userDto)
	responseDto.data = sessionService.properties(session, user)
	responseDto.type = 0
	responseDto.message = 'Successfully logged in...'
}
catch(InvalidCredentialException e) {
}

jsonCategory.respondWithJson(response, responseDto)