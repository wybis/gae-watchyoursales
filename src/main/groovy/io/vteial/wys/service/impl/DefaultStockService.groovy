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

	@Override
	public List<Stock> findByEmployeeId(String employeeId) {
		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where employeeId == employeeId
		}

		entitys.each { entity ->
			Stock model = entity as Stock
			model.product = Product.get(model.productId)
			models <<  model
		}

		return models;
	}

	@Override
	public List<Stock> findByEmployeeIdAndType(String stockEmployeeId, String stockType) {
		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where employeeId == stockEmployeeId
			and type == stockType
		}

		entitys.each { entity ->
			Stock model = entity as Stock
			model.product = Product.get(model.productId)
			models <<  model
		}

		return models;
	}

	@Override
	public Stock findOneByEmployeeIdAndType(String stockEmployeeId, String stockType) {
		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where employeeId == stockEmployeeId
			and type == stockType
			limit 1
		}

		entitys.each { entity ->
			Stock model = entity as Stock
			model.product = Product.get(model.productId)
			models <<  model
		}

		return models.size > 0 ? models[0] : null;
	}

	private Product findOneByAgencyIdAndProductCode(long productAgencyId, String productCode) {
		List<Product> models = []

		def entitys = datastore.execute {
			from Product.class.simpleName
			where agencyId == productAgencyId
			and code == productCode
			limit 1
		}

		entitys.each { entity ->
			Product model = entity as Product
			models <<  model
		}

		return models.size > 0 ? models[0] : null;
	}

	@Override
	Stock findOneByAgencyIdAndEmployeeIdAndProductCode(long stockAgencyId, String stockEmployeeId, String stockProductCode) {

		Product product = this.findOneByAgencyIdAndProductCode(stockAgencyId, stockProductCode)
		if(product == null) {
			return null
		}

		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where employeeId == stockEmployeeId
			and productId == product.id
			limit 1
		}

		entitys.each { entity ->
			Stock model = entity as Stock
			model.product = product
			models <<  model
		}

		return models.size > 0 ? models[0] : null;
	}

	@Override
	Stock findOneByEmployeeIdAndProductId(String stockEmployeeId, long stockProductId) {
		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where employeeId == stockEmployeeId
			and productId == stockProductId
			limit 1
		}

		entitys.each { entity ->
			Stock model = entity as Stock
			model.product = Product.get(model.productId)
			models <<  model
		}

		return models.size > 0 ? models[0] : null;
	}

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
			model.type = product.type
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
			model.type = product.type
			model.productId = product.id
			model.employeeId = employee.id
			model.agencyId = employee.agencyId
			this.add(sessionUser, model)
		}
	}
}
