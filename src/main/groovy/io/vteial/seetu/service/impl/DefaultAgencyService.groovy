package io.vteial.seetu.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Account
import io.vteial.seetu.model.Agency
import io.vteial.seetu.model.constants.AccountStatus
import io.vteial.seetu.model.constants.AccountType
import io.vteial.seetu.service.AccountService
import io.vteial.seetu.service.AgencyService
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

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
