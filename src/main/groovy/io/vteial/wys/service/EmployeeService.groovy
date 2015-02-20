package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Stock
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface EmployeeService {

	List<User> getMyCustomers(SessionUserDto sessionUser)

	List<User> getMyDealers(SessionUserDto sessionUser)

	Stock getMyCashStock(SessionUserDto sessionUser)

	Stock getMyProfitStock(SessionUserDto sessionUser)

	Stock getMyProductStockByProductCode(SessionUserDto sessionUser, String productCode)

	Stock getMyProductStockByProductId(SessionUserDto sessionUser, long productId)

	List<Stock> getMyProductStocks(SessionUserDto sessionUser)

	void add(SessionUserDto sessionUser, User employee) throws ModelAlreadyExistException

	void onAgencyCreate(SessionUserDto sessionUser, Agency agency)
}
