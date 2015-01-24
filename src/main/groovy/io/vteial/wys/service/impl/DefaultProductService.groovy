package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Product
import io.vteial.wys.model.constants.ItemStatus
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
		model.status = ItemStatus.ACTIVE

		model.prePersist(sessionUser.id)
		model.save()

		stockService.onItemCreate(sessionUser, model)
	}
}
