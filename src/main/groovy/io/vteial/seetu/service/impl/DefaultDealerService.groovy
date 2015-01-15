package io.vteial.seetu.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Account
import io.vteial.seetu.model.Dealer
import io.vteial.seetu.model.constants.AccountStatus
import io.vteial.seetu.model.constants.AccountType
import io.vteial.seetu.service.AccountService
import io.vteial.seetu.service.DealerService
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

class DefaultDealerService extends AbstractService implements DealerService {

	GroovyLogger log = new GroovyLogger(DefaultDealerService.class.getName())

	AccountService accountService

	@Override
	public void add(SessionUserDto sessionUser, Dealer model)
	throws ModelAlreadyExistException {

		Account account = new Account()
		account.name = "Dealer-${model.name}"
		account.aliasName = "Dealer-${model.aliasName}"
		account.type = AccountType.DEALER
		account.isMinus = true
		account.status = AccountStatus.ACTIVE

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.id = autoNumberService.getNextNumber(sessionUser, Dealer.ID_KEY)

		model.prePersist(sessionUser.id)
		model.save()
	}
}
