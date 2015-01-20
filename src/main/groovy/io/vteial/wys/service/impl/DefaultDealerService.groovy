package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Customer
import io.vteial.wys.model.Dealer
import io.vteial.wys.model.constants.AccountStatus
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.model.constants.CustomerStatus
import io.vteial.wys.model.constants.DealerStatus
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.DealerService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

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
		account.agencyId = model.agencyId

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.id = autoNumberService.getNextNumber(sessionUser, Dealer.ID_KEY)
		model.status = DealerStatus.ACTIVE

		model.prePersist(sessionUser.id)
		model.save()
	}

	@Override
	public void onAgencyCreate(SessionUserDto sessionUser, Agency agency) {

		Dealer model = new Dealer()
		model.with {
			name = 'Guest'
			aliasName = 'Dealer'
			agencyId = agency.id
		}

		Account account = new Account()
		account.name = "Dealer-${model.name}"
		account.aliasName = "Dealer-${model.name}"
		account.type = AccountType.CUSTOMER
		account.isMinus = false
		account.status = AccountStatus.ACTIVE
		account.agencyId = model.agencyId

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.id = autoNumberService.getNextNumber(sessionUser, Dealer.ID_KEY)
		model.status = DealerStatus.ACTIVE

		model.prePersist(sessionUser.id)
		model.save()
	}
}