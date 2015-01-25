package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Product
import io.vteial.wys.model.Stock
import io.vteial.wys.service.StockService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultStockService extends AbstractService implements StockService {

	GroovyLogger log = new GroovyLogger(DefaultStockService.class.getName())

	boolean initMode

	@Override
	public void add(SessionUserDto sessionUser, Stock model)
	throws ModelAlreadyExistException {

		model.id = autoNumberService.getNextNumber(sessionUser, Stock.ID_KEY)

		model.prePersist(sessionUser.id)
		model.save()
	}


	@Override
	public void onProductCreate(SessionUserDto sessionUser, Product product) {
		def entitys = datastore.execute {
			from Employee.class.getSimpleName()
			where agencyId == product.agencyId
		}

		entitys.each { entity ->
			Employee employee = entity as Employee
			Stock model = new Stock()
			model.productId = product.id
			model.employeeId = employee.id
			model.agencyId = product.agencyId
			this.add(sessionUser, model)
		}
	}

	@Override
	public void onEmployeeCreate(SessionUserDto sessionUser, Employee employee) {
		def entitys = datastore.execute {
			from Product.class.simpleName
			where agencyId == employee.agencyId
		}

		entitys.each { entity ->
			Product product = entity as Product
			Stock model = new Stock()
			model.productId = product.id
			model.employeeId = employee.id
			model.agencyId = employee.agencyId
			this.add(sessionUser, model)
		}
	}
}
