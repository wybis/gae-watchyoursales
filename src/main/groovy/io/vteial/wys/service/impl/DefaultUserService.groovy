package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.AccountStatus
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.UserService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultUserService extends AbstractService implements UserService {

	GroovyLogger log = new GroovyLogger(DefaultUserService.class.getName())

	AccountService accountService

	@Override
	public void add(SessionUserDto sessionUser, User user)
	throws ModelAlreadyExistException {

		User entity = User.get(user.id)
		if(entity) {
			throw new ModelAlreadyExistException()
		}

		Account account = new Account()
		account.name = "User-${user.id}"
		account.aliasName = "User-${user.firstName}"
		account.type = AccountType.USER
		account.isMinus = false
		account.status = AccountStatus.ACTIVE
		account.agencyId = 0
		accountService.add(sessionUser, account);

		user.account = account
		user.accountId = account.id

		user.prePersist(sessionUser.id)
		user.save()
	}
}
