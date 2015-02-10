package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Customer
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Stock
import io.vteial.wys.model.constants.AccountStatus
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.model.constants.CustomerType
import io.vteial.wys.model.constants.EmployeeStatus
import io.vteial.wys.model.constants.StockType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.CustomerService
import io.vteial.wys.service.EmployeeService
import io.vteial.wys.service.StockService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultEmployeeService extends AbstractService implements EmployeeService {

	GroovyLogger log = new GroovyLogger(DefaultEmployeeService.class.getName())

	AccountService accountService

	StockService stockService

	CustomerService customerService

	@Override
	public List<Customer> getMyCustomers(SessionUserDto sessionUser) {
		List<Customer> models = null

		models = customerService.findByAgencyIdAndType(sessionUser.agencyId, CustomerType.CUSTOMER)

		return models;
	}

	@Override
	public List<Customer> getMyDealers(SessionUserDto sessionUser) {
		List<Customer> models = null

		models = customerService.findByAgencyIdAndType(sessionUser.agencyId, CustomerType.DEALER)

		return models;
	}

	@Override
	public Stock getMyCashStock(SessionUserDto sessionUser) {
		Stock model = null

		model = stockService.findOneByEmployeeIdAndType(sessionUser.id, StockType.CASH)

		return model
	}

	@Override
	public Stock getMyProfitStock(SessionUserDto sessionUser) {
		Stock model = null

		model = stockService.findOneByEmployeeIdAndType(sessionUser.id, StockType.PROFIT)

		return model
	}

	@Override
	public Stock getMyProductStockByProductCode(SessionUserDto sessionUser,
			String productCode) {
		Stock model = null

		model = stockService.findOneByAgencyIdAndEmployeeIdAndProductCode(sessionUser.agencyId, sessionUser.id, productCode)

		return model
	}


	@Override
	public Stock getMyProductStockByProductId(SessionUserDto sessionUser,
			long productId) {
		Stock model = null

		model = stockService.findOneByEmployeeIdAndProductId(sessionUser.id, productId)

		return model
	}

	@Override
	public List<Stock> getMyProductStocks(SessionUserDto sessionUser) {
		List<Stock> models = null

		models = stockService.findByEmployeeIdAndType(sessionUser.id, StockType.PRODUCT)

		return models;
	}

	@Override
	public void add(SessionUserDto sessionUser, Employee model)
	throws ModelAlreadyExistException {

		Account account = new Account()
		account.name = "Employee-${model.firstName}"
		account.aliasName = "Employee-${model.lastName}"
		account.type = AccountType.EMPLOYEE
		account.isMinus = false
		account.status = AccountStatus.ACTIVE
		account.agencyId = model.agencyId

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.status = EmployeeStatus.ACTIVE

		model.prePersist(sessionUser.id)
		model.save()

		//stockService.onEmployeeCreate(sessionUser, model)
	}

	@Override
	public void onAgencyCreate(SessionUserDto sessionUser, Agency agency) {
		Employee model = new Employee()

		model.id = agency.id + '@' + agency.name
		model.with {
			firstName = agency.name
			lastName = agency.id as String
			agencyId = agency.id
		}

		this.add(sessionUser, model)
	}
}
