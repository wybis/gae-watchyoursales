package io.vteial.seetu.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Account
import io.vteial.seetu.model.Customer
import io.vteial.seetu.model.constants.AccountStatus
import io.vteial.seetu.model.constants.AccountType
import io.vteial.seetu.service.AccountService
import io.vteial.seetu.service.CustomerService
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

class DefaultCustomerService extends AbstractService implements CustomerService {

	GroovyLogger log = new GroovyLogger(DefaultCustomerService.class.getName())

	AccountService accountService

	@Override
	public void add(SessionUserDto sessionUser, Customer model)
	throws ModelAlreadyExistException {

		Account account = new Account()
		account.name = "Customer-${model.firstName}"
		account.aliasName = "Customer-${model.lastName}"
		account.type = AccountType.CUSTOMER
		account.isMinus = true
		account.status = AccountStatus.ACTIVE

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.id = autoNumberService.getNextNumber(sessionUser, Customer.ID_KEY)

		model.prePersist(sessionUser.id)
		model.save()
	}
}
