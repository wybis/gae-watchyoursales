package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Stock
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.StockType
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.CustomerService
import io.vteial.wys.service.EmployeeService
import io.vteial.wys.service.StockService
import io.vteial.wys.service.UserService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultEmployeeService extends AbstractService implements EmployeeService {

	GroovyLogger log = new GroovyLogger(DefaultEmployeeService.class.getName())

	UserService userService

	CustomerService customerService

	StockService stockService

	@Override
	public List<User> getMyCustomers(SessionUserDto sessionUser) {
		List<User> models = null

		models = customerService.findByAgencyIdAndType(sessionUser.agencyId, UserType.CUSTOMER)

		return models;
	}

	@Override
	public List<User> getMyDealers(SessionUserDto sessionUser) {
		List<User> models = null

		models = customerService.findByAgencyIdAndType(sessionUser.agencyId, UserType.DEALER)

		return models;
	}

	@Override
	public Stock getMyCashStock(SessionUserDto sessionUser) {
		Stock model = null

		model = stockService.findOneByEmployeeIdAndType(sessionUser.id, StockType.CASH_EMPLOYEE)

		return model
	}

	@Override
	public Stock getMyProfitStock(SessionUserDto sessionUser) {
		Stock model = null

		model = stockService.findOneByEmployeeIdAndType(sessionUser.id, StockType.PROFIT_EMPLOYEE)

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
	public void add(SessionUserDto sessionUser, User model)
	throws ModelAlreadyExistException {

		model.type = UserType.EMPLOYEE
		//model.agencyId = sessionUser.agencyId

		userService.add(sessionUser, model)

		stockService.onEmployeeCreate(sessionUser, model)
	}

	@Override
	public void onAgencyCreate(SessionUserDto sessionUser, Agency agency) {
		User model = new User()

		model.userId = agency.id + '@' + agency.name
		model.with {
			firstName = agency.name
			lastName = agency.id as String
			agencyId = agency.id
		}

		this.add(sessionUser, model)
	}
}
