package io.vteial.wys.service;

import io.vteial.wys.model.Branch
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface EmployeeService {

	//	List<User> getMyEmployees(SessionUserDto sessionUser)
	//
	//	List<User> getMyCustomers(SessionUserDto sessionUser)
	//
	//	List<User> getMyDealers(SessionUserDto sessionUser)
	//
	//	Account getMyProductStockByProductCode(SessionUserDto sessionUser, String productCode)
	//
	//	Account getMyProductStockByProductId(SessionUserDto sessionUser, long productId)
	//
	//	List<Account> getMyProductStocks(SessionUserDto sessionUser)

	void add(User sessionUser, User employee) throws ModelAlreadyExistException

	void onBranchCreate(User sessionUser, Branch branch)
}
