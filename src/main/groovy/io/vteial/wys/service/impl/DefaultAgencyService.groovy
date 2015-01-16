package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Agency
import io.vteial.wys.model.constants.AccountStatus
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.AgencyService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultAgencyService extends AbstractService implements AgencyService {

	GroovyLogger log = new GroovyLogger(DefaultAgencyService.class.getName())

	AccountService accountService

	@Override
	public void add(SessionUserDto sessionUser, Agency model)
	throws ModelAlreadyExistException {

		Account account = new Account()
		account.name = "Employee-${model.name}"
		account.aliasName = "Employee-${model.name}"
		account.type = AccountType.AGENCY
		account.isMinus = true
		account.status = AccountStatus.ACTIVE

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.id = autoNumberService.getNextNumber(sessionUser, Agency.ID_KEY)

		model.prePersist(sessionUser.id)
		model.save()
	}
}
