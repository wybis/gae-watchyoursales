package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface AgencyService {

	void add(SessionUserDto sessionUser, Agency agent) throws ModelAlreadyExistException
}
