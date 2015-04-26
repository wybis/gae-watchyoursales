package io.vteial.wys.service;

import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Product
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface ProductService {

	void add(SessionDto sessionUser, Product model) throws ModelAlreadyExistException

	void onBranchCreate(SessionDto sessionUser, Branch product)
}
