package io.vteial.wys.web.session

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.service.exceptions.InvalidCredentialException
import io.vteial.wys.service.exceptions.UnAuthorizedException

ResponseDto responseDto = new ResponseDto(type : 0, message : 'Successfully logged in...')
request.responseDto = responseDto

UserDto userDto = jsonCategory.parseJson(request, UserDto.class)
try {
	SessionDto sessionUserDto = sessionService.login(session, userDto)
	responseDto.data = sessionService.properties(session)
}
catch(InvalidCredentialException e) {
	responseDto.type = ResponseDto.ERROR
	responseDto.message = 'Invalid User Id or Password...'
}
catch(UnAuthorizedException e) {
	responseDto.type = ResponseDto.ERROR
	responseDto.message = 'UnAuthorized access...'
}
catch(Throwable t) {
	responseDto.type = ResponseDto.UNKNOWN
	responseDto.message = t.message

	StringWriter sw = new StringWriter()
	PrintWriter pw = new PrintWriter(sw)
	t.printStackTrace(pw)
	responseDto.message = 'Login failed...';
	responseDto.data = sw.toString()

	log.warning(sw.toString())
}

jsonCategory.respondWithJson(response, responseDto)