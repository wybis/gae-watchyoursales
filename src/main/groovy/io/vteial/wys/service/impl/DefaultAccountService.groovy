package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Product
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.AccountStatus
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.model.constants.ProductType
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultAccountService extends AbstractService implements AccountService {

	GroovyLogger log = new GroovyLogger(DefaultAccountService.class.getName())

	@Override
	public List<Account> findByUserId(long auserId) {
		List<Account> models = []

		def entitys = datastore.execute {
			from Account.class.simpleName
			where userId == auserId
		}

		entitys.each { entity ->
			Account model = entity as Account
			if(model.productId > 0) {
				model.product = Product.get(model.productId)
			}
			models <<  model
		}

		return models;
	}

	@Override
	public List<Account> findByUserIdAndType(long auserId, String accountType) {
		List<Account> models = []

		def entitys = datastore.execute {
			from Account.class.simpleName
			where userId == auserId
			and type == accountType
		}

		entitys.each { entity ->
			Account model = entity as Account
			if(model.productId > 0) {
				model.product = Product.get(model.productId)
			}
			models <<  model
		}

		return models;
	}

	@Override
	public List<Account> findByBranchIdAndTypes(long abranchId, List<String> accountTypes) {
		List<Account> models = []

		def entitys = datastore.execute {
			from Account.class.simpleName
			where branchId == abranchId
			and type in accountTypes
		}

		entitys.each { entity ->
			Account model = entity as Account
			if(model.productId > 0) {
				model.product = Product.get(model.productId)
			}
			models <<  model
		}

		return models;
	}


	//	@Override
	//	public Account findOneByEmployeeIdAndType(long stockEmployeeId, String stockType) {
	//		List<Account> models = []
	//
	//		def entitys = datastore.execute {
	//			from Account.class.simpleName
	//			where userId == stockEmployeeId
	//			and type == stockType
	//			limit 1
	//		}
	//
	//		entitys.each { entity ->
	//			Account model = entity as Account
	//			model.product = Product.get(model.productId)
	//			models <<  model
	//		}
	//
	//		return models.size > 0 ? models[0] : null;
	//	}
	//
	//	private Product findOneByAgencyIdAndProductCode(long productAgencyId, String productCode) {
	//		List<Product> models = []
	//
	//		def entitys = datastore.execute {
	//			from Product.class.simpleName
	//			where agencyId == productAgencyId
	//			and code == productCode
	//			limit 1
	//		}
	//
	//		entitys.each { entity ->
	//			Product model = entity as Product
	//			models <<  model
	//		}
	//
	//		return models.size > 0 ? models[0] : null;
	//	}
	//
	//	@Override
	//	Account findOneByAgencyIdAndEmployeeIdAndProductCode(long stockAgencyId, long stockEmployeeId, String stockProductCode) {
	//
	//		Product product = this.findOneByAgencyIdAndProductCode(stockAgencyId, stockProductCode)
	//		if(product == null) {
	//			return null
	//		}
	//
	//		List<Account> models = []
	//
	//		def entitys = datastore.execute {
	//			from Account.class.simpleName
	//			where userId == stockEmployeeId
	//			and productId == product.id
	//			limit 1
	//		}
	//
	//		entitys.each { entity ->
	//			Account model = entity as Account
	//			model.product = product
	//			models <<  model
	//		}
	//
	//		return models.size > 0 ? models[0] : null;
	//	}
	//
	//	@Override
	//	Account findOneByEmployeeIdAndProductId(long stockEmployeeId, long stockProductId) {
	//		List<Account> models = []
	//
	//		def entitys = datastore.execute {
	//			from Account.class.simpleName
	//			where userId == stockEmployeeId
	//			and productId == stockProductId
	//			limit 1
	//		}
	//
	//		entitys.each { entity ->
	//			Account model = entity as Account
	//			model.product = Product.get(model.productId)
	//			models <<  model
	//		}
	//
	//		return models.size > 0 ? models[0] : null;
	//	}

	@Override
	public void add(SessionDto sessionUser, Account model)
	throws ModelAlreadyExistException {

		model.status = AccountStatus.ACTIVE
		model.id = autoNumberService.getNextNumber(sessionUser, Account.ID_KEY)

		model.prePersist(sessionUser.id)
		model.save()
	}


	@Override
	public void onBranchCreate(SessionDto sessionUser, Branch branch) {
	}

	@Override
	public void onProductCreate(SessionDto sessionUser, Product product) {
		def entitys = datastore.execute {
			from User.class.getSimpleName()
			where branchId == product.branchId
			and type == UserType.EMPLOYEE
		}

		entitys.each { entity ->
			User user = entity as User
			Account account = new Account()
			account.with {
				name = "$product.code-$user.firstName"
				aliasName = product.code
				type = product.type
				productId = product.id
				userId = user.id
				branchId = product.branchId
			}
			this.add(sessionUser, account)
		}
	}

	@Override
	public void onEmployeeCreate(SessionDto sessionUser, User employee) {
		def entitys = null

		if(employee.isVirtual()) {
			entitys = datastore.execute {
				from Product.class.simpleName
				where branchId == employee.branchId
				and type == ProductType.CASH_CAPITAL
				limit 1
			}
			entitys.each { entity ->
				Product product = entity as Product
				Account account = new Account()
				account.with {
					name = "$product.code-$employee.firstName"
					aliasName = product.code
					type = product.type
					isMinus = true
					productId = product.id
					userId = employee.id
					branchId = employee.branchId
				}
				this.add(sessionUser, account)
			}
		}

		entitys = datastore.execute {
			from Product.class.simpleName
			where branchId == employee.branchId
			and type == ProductType.CASH_EMPLOYEE
			limit 1
		}

		entitys.each { entity ->
			Product product = entity as Product
			Account account = new Account()
			account.with {
				name = "$product.code-$employee.firstName"
				aliasName = product.code
				type = product.type
				productId = product.id
				userId = employee.id
				branchId = employee.branchId
			}
			this.add(sessionUser, account)

			employee.cashAccount = account
			employee.cashAccountId = account.id
			employee.save()
		}

		entitys = datastore.execute {
			from Product.class.simpleName
			where branchId == employee.branchId
			and type == ProductType.PROFIT_EMPLOYEE
			limit 1
		}

		entitys.each { entity ->
			Product product = entity as Product
			Account account = new Account()
			account.with {
				name = "$product.code-$employee.firstName"
				aliasName = product.code
				type = product.type
				productId = product.id
				userId = employee.id
				branchId = employee.branchId
			}
			this.add(sessionUser, account)

			employee.profitAccount = account
			employee.profitAccountId = account.id
			employee.save()
		}

		entitys = datastore.execute {
			from Product.class.simpleName
			where branchId == employee.branchId
			and type == ProductType.PRODUCT
		}

		entitys.each { entity ->
			Product product = entity as Product
			Account account = new Account()
			account.with {
				name = "$product.code-$employee.firstName"
				aliasName = product.code
				type = product.type
				productId = product.id
				userId = employee.id
				branchId = employee.branchId
			}
			this.add(sessionUser, account)
		}
	}

	@Override
	public void onDealerCreate(SessionDto sessionUser, User dealer) {
		def entitys = datastore.execute {
			from Product.class.simpleName
			where branchId == dealer.branchId
			and type == ProductType.CASH_DEALER
			limit 1
		}

		entitys.each { entity ->
			Product product = entity as Product
			Account account = new Account()
			account.with {
				name = "$product.code-$dealer.firstName"
				aliasName = product.code
				isMinus = true
				type = product.type
				productId = product.id
				userId = dealer.id
				branchId = dealer.branchId
			}
			this.add(sessionUser, account)

			dealer.cashAccount = account
			dealer.cashAccountId = account.id
			dealer.save()
		}
	}

	@Override
	public void onCustomerCreate(SessionDto sessionUser, User customer) {
		def entitys = datastore.execute {
			from Product.class.simpleName
			where branchId == customer.branchId
			and type == ProductType.CASH_CUSTOMER
			limit 1
		}

		entitys.each { entity ->
			Product product = entity as Product
			Account account = new Account()
			account.with {
				name = "$product.code-$customer.firstName"
				aliasName = product.code
				isMinus = true
				type = product.type
				productId = product.id
				userId = customer.id
				branchId = customer.branchId
			}
			this.add(sessionUser, account)

			customer.cashAccount = account
			customer.cashAccountId = account.id
			customer.save()
		}
	}
}
