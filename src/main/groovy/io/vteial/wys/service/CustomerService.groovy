package io.vteial.wys.service;

import io.vteial.wys.model.Branch
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface CustomerService {

	void add(User sessionUser, User customer) throws ModelAlreadyExistException

	void onBranchCreate(User sessionUser, Branch branch)
}
