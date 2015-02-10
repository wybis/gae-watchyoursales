package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Customer
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Stock
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface EmployeeService {

	List<Customer> getMyCustomers(SessionUserDto sessionUser)

	List<Customer> getMyDealers(SessionUserDto sessionUser)

	Stock getMyCashStock(SessionUserDto sessionUser)

	Stock getMyProfitStock(SessionUserDto sessionUser)

	Stock getMyProductStockByProductCode(SessionUserDto sessionUser, String productCode)

	Stock getMyProductStockByProductId(SessionUserDto sessionUser, long productId)

	List<Stock> getMyProductStocks(SessionUserDto sessionUser)

	//List<Stock> getMyStocks(SessionUserDto sessionUser)

	void add(SessionUserDto sessionUser, Employee employee) throws ModelAlreadyExistException

	void onAgencyCreate(SessionUserDto sessionUser, Agency agency)
}
