package io.vteial.wys.service;

import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Branch
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface BranchService {

	void add(SessionDto sessionUser, Branch branch) throws ModelAlreadyExistException
}
