package io.vteial.wys.service;

import io.vteial.wys.model.Branch
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface BranchService {

	void add(User sessionUser, Branch branch) throws ModelAlreadyExistException
}
