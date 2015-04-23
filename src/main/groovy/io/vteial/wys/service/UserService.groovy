package io.vteial.wys.service;

import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface UserService {

	List<User> findByBranchIdAndType(long branchId, String type);

	void add(User sessionUser, User user) throws ModelAlreadyExistException
}
