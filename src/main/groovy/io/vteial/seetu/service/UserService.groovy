package io.vteial.seetu.service;

import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.User
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

interface UserService {

	void add(SessionUserDto sessionUser, User user) throws ModelAlreadyExistException
}
