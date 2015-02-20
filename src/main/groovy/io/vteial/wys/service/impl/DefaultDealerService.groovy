package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.DealerService
import io.vteial.wys.service.StockService
import io.vteial.wys.service.UserService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultDealerService extends AbstractService implements DealerService {

	GroovyLogger log = new GroovyLogger(DefaultDealerService.class.getName())

	UserService userService

	StockService stockService

	@Override
	public void add(SessionUserDto sessionUser, User model)
	throws ModelAlreadyExistException {

		if(!model.userId) {
			Agency agency = Agency.get(model.agencyId)
			model.agency = agency
			model.userId = "${model.firstName}-${model.lastName}@${agency.name}"
			model.userId = model.userId.toLowerCase()
		}
		model.type = UserType.DEALER
		//model.agencyId = sessionUser.agencyId

		userService.add(sessionUser, model)

		stockService.onDealerCreate(sessionUser, model)
	}

	@Override
	public void onAgencyCreate(SessionUserDto sessionUser, Agency agency) {

		User model = new User()
		model.with {
			userId = "guest-dealer@$agency.name"
			firstName = 'Guest'
			lastName = 'Dealer'
			agencyId = agency.id
		}

		this.add(sessionUser, model)
	}
}
