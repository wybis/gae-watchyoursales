package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Item
import io.vteial.wys.model.Stock
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface StockService {

	void add(SessionUserDto sessionUser, Stock stock) throws ModelAlreadyExistException

	void onItemCreate(SessionUserDto sessionUser, Item item)

	void onEmployeeCreate(SessionUserDto sessionUser, Employee employee)
}