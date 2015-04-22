package io.vteial.wys.service;

import io.vteial.wys.model.Branch
import io.vteial.wys.model.Product
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface ProductService {

	void add(User sessionUser, Product model) throws ModelAlreadyExistException

	void onBranchCreate(User sessionUser, Branch product)
}
