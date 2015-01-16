package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Item
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface ItemService {

	void add(SessionUserDto sessionUser, Item item) throws ModelAlreadyExistException
}
