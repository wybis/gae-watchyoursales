package io.vteial.wys.service;

import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Product
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

interface AccountService {

	List<Account> findByUserId(long userId)

	List<Account> findByUserIdAndType(long userId, String accountType)

	List<Account> findByUserIdAndTypes(long userId, List<String> accountTypes)

	List<Account> findByBranchIdAndTypes(long branchId, List<String> accountTypes)

	//	Account findOneByEmployeeIdAndType(long employeeId, String type)
	//
	//	Account findOneByEmployeeIdAndProductId(long employeeId, long productId)
	//
	//	Account findOneByBranchIdAndEmployeeIdAndProductCode(long agencyId, long employeeId, String productCode)

	void add(SessionDto sessionUser, Account model) throws ModelAlreadyExistException

	void onBranchCreate(SessionDto sessionUser, Branch branch);

	void onProductCreate(SessionDto sessionUser, Product product)

	void onEmployeeCreate(SessionDto sessionUser, User employee)

	void onDealerCreate(SessionDto sessionUser, User customer)

	void onCustomerCreate(SessionDto sessionUser, User customer)
}
