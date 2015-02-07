package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Stock
import io.vteial.wys.model.constants.AccountStatus
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.model.constants.EmployeeStatus
import io.vteial.wys.model.constants.StockType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.EmployeeService
import io.vteial.wys.service.StockService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultEmployeeService extends AbstractService implements EmployeeService {

	GroovyLogger log = new GroovyLogger(DefaultEmployeeService.class.getName())

	AccountService accountService

	StockService stockService

	@Override
	public Stock getCashStock(SessionUserDto sessionUser) {
		Stock model = null

		model = stockService.findOneByEmployeeIdAndType(sessionUser.id, StockType.CASH)

		return model
	}

	@Override
	public Stock getProfitStock(SessionUserDto sessionUser) {
		Stock model = null

		model = stockService.findOneByEmployeeIdAndType(sessionUser.id, StockType.PROFIT)

		return model
	}

	@Override
	public Stock getProductStockByProductCode(SessionUserDto sessionUser,
			String productCode) {
		Stock model = null

		model = stockService.findOneByAgencyIdAndEmployeeIdAndProductCode(sessionUser.agencyId, sessionUser.id, productCode)

		return model
	}


	@Override
	public Stock getProductStockByProductId(SessionUserDto sessionUser,
			long productId) {
		Stock model = null

		model = stockService.findOneByEmployeeIdAndProductId(sessionUser.id, productId)

		return model
	}

	@Override
	public List<Stock> getProductStocks(SessionUserDto sessionUser) {
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
