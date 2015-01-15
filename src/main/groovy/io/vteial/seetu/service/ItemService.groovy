package io.vteial.seetu.service;

import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Item
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

interface ItemService {

	void add(SessionUserDto sessionUser, Item item) throws ModelAlreadyExistException
}
