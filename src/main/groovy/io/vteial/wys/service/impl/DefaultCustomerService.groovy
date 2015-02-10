package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Customer
import io.vteial.wys.model.constants.AccountStatus
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.model.constants.CustomerStatus
import io.vteial.wys.model.constants.CustomerType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.CustomerService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultCustomerService extends AbstractService implements CustomerService {

	GroovyLogger log = new GroovyLogger(DefaultCustomerService.class.getName())

	AccountService accountService

	@Override
	public List<Customer> findByAgencyIdAndType(long customerAgencyId, String customerType) {
		List<Customer> models = []

		def entitys = datastore.execute {
			from Customer.class.simpleName
			where agencyId == customerAgencyId
			and type == customerType
		}

		entitys.each { entity ->
			Customer model = entity as Customer
			model.account = Account.get(model.accountId)
			models <<  model
		}

		return models;
	}

	@Override
	public void add(SessionUserDto sessionUser, Customer model)
	throws ModelAlreadyExistException {

		Account account = new Account()
		account.name = "Customer-${model.firstName}"
		account.aliasName = "Customer-${model.lastName}"
		account.type = AccountType.CUSTOMER
		account.isMinus = true
		account.status = AccountStatus.ACTIVE
		account.agencyId = model.agencyId

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.id = autoNumberService.getNextNumber(sessionUser, Customer.ID_KEY)
		model.type = CustomerType.CUSTOMER
		model.status = CustomerStatus.ACTIVE

		model.prePersist(sessionUser.id)
		model.save()
	}

	@Override
	public void onAgencyCreate(SessionUserDto sessionUser, Agency agency) {

		Customer model = new Customer()
		model.with {
			firstName = 'Guest'
			lastName = 'Customer'
			agencyId = agency.id
		}

		Account account = new Account()
		account.name = "Customer-${model.firstName}"
		account.aliasName = "Customer-${model.firstName}"
		account.type = AccountType.CUSTOMER
		account.isMinus = false
		account.status = AccountStatus.ACTIVE
		account.agencyId = model.agencyId

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.id = autoNumberService.getNextNumber(sessionUser, Customer.ID_KEY)
		model.type = CustomerType.CUSTOMER
		model.status = CustomerStatus.ACTIVE

		model.prePersist(agency.createBy)
		model.save()
	}
}
