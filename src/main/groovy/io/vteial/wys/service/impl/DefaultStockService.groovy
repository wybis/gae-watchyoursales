package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Product
import io.vteial.wys.model.Stock
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.ProductType
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.StockService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultStockService extends AbstractService implements StockService {

	GroovyLogger log = new GroovyLogger(DefaultStockService.class.getName())

	@Override
	public List<Stock> findByEmployeeId(long stockEmployeeId) {
		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where userId == stockEmployeeId
		}

		entitys.each { entity ->
			Stock model = entity as Stock
			model.product = Product.get(model.productId)
			models <<  model
		}

		return models;
	}

	@Override
	public List<Stock> findByEmployeeIdAndType(long stockEmployeeId, String stockType) {
		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where userId == stockEmployeeId
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
	public Stock findOneByEmployeeIdAndType(long stockEmployeeId, String stockType) {
		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where userId == stockEmployeeId
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
	Stock findOneByAgencyIdAndEmployeeIdAndProductCode(long stockAgencyId, long stockEmployeeId, String stockProductCode) {

		Product product = this.findOneByAgencyIdAndProductCode(stockAgencyId, stockProductCode)
		if(product == null) {
			return null
		}

		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where userId == stockEmployeeId
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
	Stock findOneByEmployeeIdAndProductId(long stockEmployeeId, long stockProductId) {
		List<Stock> models = []

		def entitys = datastore.execute {
			from Stock.class.simpleName
			where userId == stockEmployeeId
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
			from User.class.getSimpleName()
			where agencyId == product.agencyId
			and type == UserType.EMPLOYEE
		}

		entitys.each { entity ->
			User employee = entity as User
			Stock model = new Stock()
			model.type = product.type
			model.productId = product.id
			model.userId = employee.id
			model.agencyId = product.agencyId
			this.add(sessionUser, model)
		}
	}

	@Override
	public void onEmployeeCreate(SessionUserDto sessionUser, User employee) {
		def prdTypes = [ProductType.CASH_EMPLOYEE, ProductType.PRODUCT]
		def entitys = datastore.execute {
			from Product.class.simpleName
			where agencyId == employee.agencyId
			and type in prdTypes
		}

		entitys.each { entity ->
			Product product = entity as Product
			Stock model = new Stock()
			model.type = product.type
			model.productId = product.id
			model.userId = employee.id
			model.agencyId = employee.agencyId
			this.add(sessionUser, model)
			employee.stockId = model.id
			employee.save()
		}
	}

	@Override
	public void onDealerCreate(SessionUserDto sessionUser, User dealer) {
		def entitys = datastore.execute {
			from Product.class.simpleName
			where agencyId == dealer.agencyId
			and type == ProductType.CASH_DEALER
			limit 1
		}

		entitys.each { entity ->
			Product product = entity as Product
			Stock model = new Stock()
			model.type = product.type
			model.productId = product.id
			model.userId = dealer.id
			model.agencyId = dealer.agencyId
			this.add(sessionUser, model)
			dealer.stockId = model.id
			dealer.save()
		}
	}

	@Override
	public void onCustomerCreate(SessionUserDto sessionUser, User customer) {
		def entitys = datastore.execute {
			from Product.class.simpleName
			where agencyId == customer.agencyId
			and type == ProductType.CASH_CUSTOMER
			limit 1
		}

		entitys.each { entity ->
			Product product = entity as Product
			Stock model = new Stock()
			model.type = product.type
			model.productId = product.id
			model.userId = customer.id
			model.agencyId = customer.agencyId
			this.add(sessionUser, model)
			customer.stockId = model.id
			customer.save()
		}
	}
}
