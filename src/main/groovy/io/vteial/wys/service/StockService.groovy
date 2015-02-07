package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Product
import io.vteial.wys.model.Stock
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface StockService {

	List<Stock> findByEmployeeId(String employeeId)

	List<Stock> findByEmployeeIdAndType(String employeeId, String type)

	Stock findOneByEmployeeIdAndType(String employeeId, String type)

	Stock findOneByEmployeeIdAndProductId(String employeeId, long productId)

	Stock findOneByAgencyIdAndEmployeeIdAndProductCode(long agencyId, String employeeId, String productCode)

	void add(SessionUserDto sessionUser, Stock stock) throws ModelAlreadyExistException

	void onProductCreate(SessionUserDto sessionUser, Product item)

	void onEmployeeCreate(SessionUserDto sessionUser, Employee employee)
}
