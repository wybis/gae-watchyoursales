package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Stock
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface EmployeeService {

	Stock getCashStock(SessionUserDto sessionUser)

	Stock getProfitStock(SessionUserDto sessionUser)

	Stock getProductStockByProductCode(SessionUserDto sessionUser, String productCode)

	Stock getProductStockByProductId(SessionUserDto sessionUser, long productId)

	List<Stock> getProductStocks(SessionUserDto sessionUser)

	//List<Stock> getStocks(SessionUserDto sessionUser)

	void add(SessionUserDto sessionUser, Employee employee) throws ModelAlreadyExistException

	void onAgencyCreate(SessionUserDto sessionUser, Agency agency)
}
