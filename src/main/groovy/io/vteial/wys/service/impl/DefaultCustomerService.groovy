package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Branch
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.CustomerService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultCustomerService extends DefaultUserService implements CustomerService {

	GroovyLogger log = new GroovyLogger(DefaultCustomerService.class.getName())

	AccountService accountService

	@Override
	public void add(SessionDto sessionUser, User model)
	throws ModelAlreadyExistException {

		if(!model.userId) {
			Branch branch = Branch.get(model.branchId)
			model.branch = branch
			model.userId = "${model.firstName}-${model.lastName}@${branch.name}"
			model.userId = model.userId.toLowerCase()
		}
		model.type = UserType.CUSTOMER
		//model.branchId = sessionUser.branchId

		super.add(sessionUser, model)

		accountService.onCustomerCreate(sessionUser, model)
	}

	@Override
	public void onBranchCreate(SessionDto sessionUser, Branch branch) {

		User model = new User()
		model.with {
			firstName = 'Guest'
			lastName = 'Customer'
			branchId = branch.id
		}

		this.add(sessionUser, model)
	}
}
