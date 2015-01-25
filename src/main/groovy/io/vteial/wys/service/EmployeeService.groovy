package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Employee
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface EmployeeService {

	void add(SessionUserDto sessionUser, Employee employee) throws ModelAlreadyExistException

	void onAgencyCreate(SessionUserDto sessionUser, Agency agency)
}
