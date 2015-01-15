package io.vteial.seetu.service;

import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Agency
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

interface AgencyService {

	void add(SessionUserDto sessionUser, Agency agent) throws ModelAlreadyExistException
}
