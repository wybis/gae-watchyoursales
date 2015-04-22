package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.model.Branch
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.DealerService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultDealerService extends DefaultUserService implements DealerService {

	GroovyLogger log = new GroovyLogger(DefaultDealerService.class.getName())

	AccountService accountService

	@Override
	public void add(User sessionUser, User model)
	throws ModelAlreadyExistException {

		if(!model.userId) {
			Branch branch = Branch.get(model.branchId)
			model.branch = branch
			model.userId = "${model.firstName}-${model.lastName}@${branch.name}"
			model.userId = model.userId.toLowerCase()
		}
		model.type = UserType.DEALER
		//model.branchId = sessionUser.branchId

		super.add(sessionUser, model)

		accountService.onDealerCreate(sessionUser, model)
	}

	@Override
	public void onBranchCreate(User sessionUser, Branch branch) {

		User model = new User()
		model.with {
			userId = "guest-dealer@$branch.name"
			firstName = 'Guest'
			lastName = 'Dealer'
			branchId = branch.id
		}

		this.add(sessionUser, model)
	}
}
