package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Branch
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.EmployeeService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultEmployeeService extends DefaultUserService implements EmployeeService {

	GroovyLogger log = new GroovyLogger(DefaultEmployeeService.class.getName())

	AccountService accountService

	//	@Override
	//	public Account getMyProductStockByProductCode(SessionUserDto sessionUser,
	//			String productCode) {
	//		Account model = null
	//
	//		model = accountService.findOneByAgencyIdAndEmployeeIdAndProductCode(sessionUser.agencyId, sessionUser.id, productCode)
	//
	//		return model
	//	}
	//
	//
	//	@Override
	//	public Account getMyProductStockByProductId(SessionUserDto sessionUser,
	//			long productId) {
	//		Account model = null
	//
	//		model = accountService.findOneByEmployeeIdAndProductId(sessionUser.id, productId)
	//
	//		return model
	//	}
	//
	//	@Override
	//	public List<Account> getMyProductStocks(SessionUserDto sessionUser) {
	//		List<Account> models = null
	//
	//		models = accountService.findByEmployeeIdAndType(sessionUser.id, AccountType.PRODUCT)
	//
	//		return models;
	//	}

	@Override
	public void add(User sessionUser, User model)
	throws ModelAlreadyExistException {

		model.type = UserType.EMPLOYEE
		//model.agencyId = sessionUser.agencyId

		super.add(sessionUser, model)

		accountService.onEmployeeCreate(sessionUser, model)
	}

	@Override
	public void onBranchCreate(User sessionUser, Branch branch) {
		User model = new User()

		model.userId = branch.id + '@' + branch.name
		model.with {
			firstName = branch.name
			lastName = branch.id as String
			branchId = branch.id
		}

		this.add(sessionUser, model)
	}
}
