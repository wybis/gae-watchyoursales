package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.AutoNumber
import io.vteial.wys.service.AutoNumberService

class DefaultAutoNumberService implements AutoNumberService {

	GroovyLogger log = new GroovyLogger(DefaultAutoNumberService.class.getName())

	@Override
	public long getNextNumber(SessionUserDto sessionUser, String key) {
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
