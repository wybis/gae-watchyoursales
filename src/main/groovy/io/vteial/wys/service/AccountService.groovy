package io.vteial.wys.service;

import io.vteial.wys.model.Account
import io.vteial.wys.model.Product
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface AccountService {

	List<Account> findByUserId(long userId)

	List<Account> findByUserIdAndType(long userId, String type)

	//	Account findOneByEmployeeIdAndType(long employeeId, String type)
	//
	//	Account findOneByEmployeeIdAndProductId(long employeeId, long productId)
	//
	//	Account findOneByBranchIdAndEmployeeIdAndProductCode(long agencyId, long employeeId, String productCode)

	void add(User sessionUser, Account model) throws ModelAlreadyExistException

	void onProductCreate(User sessionUser, Product item)

	void onEmployeeCreate(User sessionUser, User employee)

	void onDealerCreate(User sessionUser, User customer)

	void onCustomerCreate(User sessionUser, User customer)
}
