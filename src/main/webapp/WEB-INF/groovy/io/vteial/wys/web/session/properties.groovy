package io.vteial.wys.web.session;

import io.vteial.wys.dto.ResponseDto
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.service.SessionService

ResponseDto responseDto = request.responseDto

def props = sessionService.properties(session), model = [:]

if(responseDto) {
	responseDto.data = props
}
else {
	responseDto = new ResponseDto(data : props)
}

SessionDto sessionDto = session[SessionService.SESSION_USER_KEY]
if(sessionDto) {

	model['stocks'] = sessionService.stocks(sessionDto)
	model['ledgers'] = sessionService.ledgers(sessionDto)
	model['dealers'] = sessionService.dealers(sessionDto)
	model['customers'] = sessionService.customers(sessionDto)
	model['employees'] = sessionService.employees(sessionDto)

	responseDto.model = model;
}

jsonCategory.respondWithJson(response, responseDto)

