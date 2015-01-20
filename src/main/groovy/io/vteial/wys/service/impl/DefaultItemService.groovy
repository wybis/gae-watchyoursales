package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Item
import io.vteial.wys.model.constants.ItemStatus
import io.vteial.wys.service.ItemService
import io.vteial.wys.service.StockService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultItemService extends AbstractService implements ItemService {

	GroovyLogger log = new GroovyLogger(DefaultItemService.class.getName())

	StockService stockService

	@Override
	public void add(SessionUserDto sessionUser, Item model)
	throws ModelAlreadyExistException {

		model.id = autoNumberService.getNextNumber(sessionUser, Item.ID_KEY)
		model.status = ItemStatus.ACTIVE

		model.prePersist(sessionUser.id)
		model.save()

		stockService.onItemCreate(sessionUser, model)
	}
}
