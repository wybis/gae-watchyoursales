package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserStatus
import io.vteial.wys.service.UserService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultUserService extends AbstractService implements UserService {

	GroovyLogger log = new GroovyLogger(DefaultUserService.class.getName())

	@Override
	public void add(SessionUserDto sessionUser, User model)
	throws ModelAlreadyExistException {

		def entitys = datastore.execute {
			from User.class.simpleName
			where userId == model.userId
		}

		if(entitys.size() > 0) {
			throw new ModelAlreadyExistException()
		}

		model.status = UserStatus.ACTIVE
		model.id = autoNumberService.getNextNumber(sessionUser, User.ID_KEY)

		model.prePersist(sessionUser.id)
		model.save()
	}
}
