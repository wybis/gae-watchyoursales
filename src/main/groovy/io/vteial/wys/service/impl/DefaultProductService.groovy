package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Product
import io.vteial.wys.model.constants.ProductStatus
import io.vteial.wys.service.ProductService
import io.vteial.wys.service.StockService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultProductService extends AbstractService implements ProductService {

	GroovyLogger log = new GroovyLogger(DefaultProductService.class.getName())

	StockService stockService

	@Override
	public void add(SessionUserDto sessionUser, Product model)
	throws ModelAlreadyExistException {

		model.id = autoNumberService.getNextNumber(sessionUser, Product.ID_KEY)
		model.status = ProductStatus.ACTIVE

		model.prePersist(sessionUser.id)
		model.save()

		stockService.onProductCreate(sessionUser, model)
	}

	@Override
	public void onAgencyCreate(SessionUserDto sessionUser, Agency agency) {
		Product model = new Product()

		model.with {
			code = 'CIH'
			name = 'Cash'
			baseUnit = 1
			denominator = 1
			buyRate = 1
			buyPercent = 1
			sellRate = 1
			sellPercent = 1
			agencyId = agency.id
		}

		this.add(sessionUser, model)

		model.with {
			code = 'PFT'
			name = 'Profit'
			baseUnit = 1
			denominator = 1
			buyRate = 1
			buyPercent = 1
			sellRate = 1
			sellPercent = 1
			agencyId = agency.id
		}

		this.add(sessionUser, model)
	}
}
