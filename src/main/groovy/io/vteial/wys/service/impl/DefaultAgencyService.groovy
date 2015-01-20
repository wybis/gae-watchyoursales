package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Agency
import io.vteial.wys.model.constants.AccountStatus
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.AgencyService
import io.vteial.wys.service.CustomerService
import io.vteial.wys.service.DealerService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultAgencyService extends AbstractService implements AgencyService {

	GroovyLogger log = new GroovyLogger(DefaultAgencyService.class.getName())

	AccountService accountService

	DealerService dealerService

	CustomerService customerService

	@Override
	public void add(SessionUserDto sessionUser, Agency model)
	throws ModelAlreadyExistException {

		model.id = autoNumberService.getNextNumber(sessionUser, Agency.ID_KEY)

		Account account = new Account()
		account.name = "Agency-${model.name}"
		account.aliasName = "Agency-${model.name}"
		account.type = AccountType.AGENCY
		account.isMinus = false
		account.status = AccountStatus.ACTIVE
		account.agencyId = model.id

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.prePersist(sessionUser.id)
		model.save()

		dealerService.onAgencyCreate(sessionUser, model)
		customerService.onAgencyCreate(sessionUser, model)
	}
}
