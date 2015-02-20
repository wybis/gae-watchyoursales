package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Product
import io.vteial.wys.model.Stock
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface StockService {

	List<Stock> findByEmployeeId(long employeeId)

	List<Stock> findByEmployeeIdAndType(long employeeId, String type)

	Stock findOneByEmployeeIdAndType(long employeeId, String type)

	Stock findOneByEmployeeIdAndProductId(long employeeId, long productId)

	Stock findOneByAgencyIdAndEmployeeIdAndProductCode(long agencyId, long employeeId, String productCode)

	void add(SessionUserDto sessionUser, Stock stock) throws ModelAlreadyExistException

	void onProductCreate(SessionUserDto sessionUser, Product item)

	void onEmployeeCreate(SessionUserDto sessionUser, User employee)

	void onDealerCreate(SessionUserDto sessionUser, User customer)

	void onCustomerCreate(SessionUserDto sessionUser, User customer)
}
