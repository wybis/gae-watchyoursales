package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Product
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface ProductService {

	void add(SessionUserDto sessionUser, Product item) throws ModelAlreadyExistException
}
