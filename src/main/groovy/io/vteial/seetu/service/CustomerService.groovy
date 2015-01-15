package io.vteial.seetu.service;

import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Customer
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

interface CustomerService {

	void add(SessionUserDto sessionUser, Customer customer) throws ModelAlreadyExistException
}
