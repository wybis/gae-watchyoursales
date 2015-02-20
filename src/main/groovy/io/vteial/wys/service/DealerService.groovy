package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface DealerService {

	void add(SessionUserDto sessionUser, User dealer) throws ModelAlreadyExistException

	void onAgencyCreate(SessionUserDto sessionUser, Agency agency)
}
