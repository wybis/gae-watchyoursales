package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Product
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.ProductStatus
import io.vteial.wys.model.constants.ProductType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.ProductService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultProductService extends AbstractService implements ProductService {

	GroovyLogger log = new GroovyLogger(DefaultProductService.class.getName())

	AccountService accountService

	@Override
	public void add(User sessionUser, Product model)
	throws ModelAlreadyExistException {

		if(model.type == null) {
			model.type = ProductType.PRODUCT
		}
		model.status = ProductStatus.ACTIVE

		model.id = autoNumberService.getNextNumber(sessionUser, Product.ID_KEY)

		model.prePersist(sessionUser.id)
		model.save()

		accountService.onProductCreate(sessionUser, model)
	}

	@Override
	public void onBranchCreate(User sessionUser, Branch branch) {
		Product model = new Product()

		model.with {
			type = ProductType.CASH_EMPLOYEE
			code = 'CIE'
			name = 'CASH IN EMPLOYEE'
			baseUnit = 1
			denominator = 1
			buyRate = 1
			buyPercent = 1
			sellRate = 1
			sellPercent = 1
			branchId = branch.id
		}
		this.add(sessionUser, model)

		model.with {
			type = ProductType.PROFIT_EMPLOYEE
			code = 'PIE'
			name = 'PROFIT IN EMPLOYEE'
			baseUnit = 1
			denominator = 1
			buyRate = 1
			buyPercent = 1
			sellRate = 1
			sellPercent = 1
			branchId = branch.id
		}
		this.add(sessionUser, model)

		model.with {
			type = ProductType.CASH_DEALER
			code = 'CID'
			name = 'CASH IN DEALER'
			baseUnit = 1
			denominator = 1
			buyRate = 1
			buyPercent = 1
			sellRate = 1
			sellPercent = 1
			branchId = branch.id
		}
		this.add(sessionUser, model)

		model.with {
			type = ProductType.CASH_CUSTOMER
			code = 'CIC'
			name = 'CASH IN CUSTOMER'
			baseUnit = 1
			denominator = 1
			buyRate = 1
			buyPercent = 1
			sellRate = 1
			sellPercent = 1
			branchId = branch.id
		}
		this.add(sessionUser, model)
	}
}
