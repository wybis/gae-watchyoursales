package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Item
import io.vteial.wys.model.Stock
import io.vteial.wys.service.StockService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultStockService extends AbstractService implements StockService {

	GroovyLogger log = new GroovyLogger(DefaultStockService.class.getName())

	@Override
	public void add(SessionUserDto sessionUser, Stock model)
	throws ModelAlreadyExistException {

		model.id = autoNumberService.getNextNumber(sessionUser, Stock.ID_KEY)

		model.prePersist(sessionUser.id)
		model.save()
	}


	@Override
	public void onItemCreate(SessionUserDto sessionUser, Item item) {
	}

	@Override
	public void onEmployeeCreate(SessionUserDto sessionUser, Employee employee) {
		def entitys = datastore.execute {
			from Item.class.simpleName
			where agencyId == employee.agencyId
		}

		entitys.each { entity ->
			Item item = entity as Item
			Stock model = new Stock()
			model.itemId = item.id
			model.employeeId = employee.id
			model.agencyId = employee.agencyId
			this.add(sessionUser, model)
		}
	}
}
