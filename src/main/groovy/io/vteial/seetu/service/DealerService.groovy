package io.vteial.seetu.service;

import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Dealer
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

interface DealerService {

	void add(SessionUserDto sessionUser, Dealer dealer) throws ModelAlreadyExistException
}
