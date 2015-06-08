package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Role;
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.EmployeeService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultEmployeeService extends DefaultUserService implements EmployeeService {

	GroovyLogger log = new GroovyLogger(DefaultEmployeeService.class.getName())

	AccountService accountService

	@Override
	public void add(SessionDto sessionUser, User model)
	throws ModelAlreadyExistException {

		model.type = UserType.EMPLOYEE
		//model.agencyId = sessionUser.agencyId

		super.add(sessionUser, model)

		accountService.onEmployeeCreate(sessionUser, model)
	}

	@Override
	public void onBranchCreate(SessionDto sessionUser, Branch branch) {
		User model = new User()

		model.userId = branch.id + '@' + branch.code
		model.with {
			password = '123'
			firstName = branch.name
			lastName = branch.id as String
			roleId = Role.ID_MANAGER
			branchId = branch.id
		}

		this.add(sessionUser, model)

		branch.virtualEmployeeId = model.id

		model.prePersist(sessionUser.id)
		branch.save();
	}
}
