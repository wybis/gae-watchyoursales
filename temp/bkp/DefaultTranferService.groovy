package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Transfer
import io.vteial.wys.model.constants.TransferStatus
import io.vteial.wys.service.TransferService
import io.vteial.wys.service.exceptions.TransactionException

@GaelykBindings
class DefaultTranferService extends AbstractService implements TransferService {

	GroovyLogger log = new GroovyLogger(DefaultTranferService.class.getName())

	@Override
	public void add(SessionDto sessionUser, Transfer transfer) throws TransactionException {
		Date now = new Date()
		transfer.id = autoNumberService.getNextNumber(sessionUser, Transfer.ID_KEY)
		transfer.date = now
		if(!transfer.status) {
			transfer.status = TransferStatus.COMPLETED
		}
		transfer.byUserId = sessionUser.id
		transfer.branchId = sessionUser.branchId

		transfer.prePersist(sessionUser.id)
		transfer.save()
	}
}
