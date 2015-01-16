package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface UserService {

	void add(SessionUserDto sessionUser, User user) throws ModelAlreadyExistException
}
