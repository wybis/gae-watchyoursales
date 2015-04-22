package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.model.AutoNumber
import io.vteial.wys.model.User
import io.vteial.wys.service.AutoNumberService

@GaelykBindings
class DefaultAutoNumberService implements AutoNumberService {

	GroovyLogger log = new GroovyLogger(DefaultAutoNumberService.class.getName())

	@Override
	public long getNextNumber(User sessionUser, String key) {
		AutoNumber an = AutoNumber.get(key)

		if(an) {
			an.preUpdate(sessionUser.id)
		}
		else {
			an = new AutoNumber()
			an.id = key
			an.prePersist(sessionUser.id)
		}
		//log.info an.toString()
		an.value += 1
		an.save()
		//log.info an.toString()

		return an.value
	}
}
