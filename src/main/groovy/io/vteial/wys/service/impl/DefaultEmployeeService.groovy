package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
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
	//	public List<User> getMyEmployees(SessionUserDto sessionUser) {
	//		List<User> models = []
	//
	//		if(sessionUser.roleId == Role.AGENCY_MANAGER) {
	//			def entitys = datastore.execute {
	//				from User.class.simpleName
	//				where agencyId == sessionUser.agencyId
	//				and type == UserType.EMPLOYEE
	//			}
	//
	//			entitys.each { entity ->
	//				User model = entity as User
	//				model.cashStock = Account.get(model.cashStockId)
	//				model.profitStock = Account.get(model.profitStockId)
	//				models <<  model
	//			}
	//		}
	//		else {
	//			User model = User.get(sessionUserDto.id)
	//			model.cashStock = Account.get(model.cashStockId)
	//			model.profitStock = Account.get(model.profitStockId)
	//			models << model
	//		}
	//
	//		return models;
	//	}
	//
	//	@Override
	//	public List<User> getMyCustomers(SessionUserDto sessionUser) {
	//		List<User> models = null
	//
	//		models = this.findByAgencyIdAndType(sessionUser.agencyId, UserType.CUSTOMER)
	//
	//		return models;
	//	}
	//
	//	@Override
	//	public List<User> getMyDealers(SessionUserDto sessionUser) {
	//		List<User> models = null
	//
	//		models = this.findByAgencyIdAndType(sessionUser.agencyId, UserType.DEALER)
	//
	//		return models;
	//	}
	//
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
