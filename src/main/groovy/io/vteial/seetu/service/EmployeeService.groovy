package io.vteial.seetu.service;

import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Employee
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

interface EmployeeService {

	void add(SessionUserDto sessionUser, Employee employee) throws ModelAlreadyExistException
}
