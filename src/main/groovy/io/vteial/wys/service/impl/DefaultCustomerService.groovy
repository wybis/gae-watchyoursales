package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.CustomerService
import io.vteial.wys.service.StockService
import io.vteial.wys.service.UserService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultCustomerService extends AbstractService implements CustomerService {

	GroovyLogger log = new GroovyLogger(DefaultCustomerService.class.getName())

	UserService userService

	StockService stockService

	@Override
	public List<User> findByAgencyIdAndType(long customerAgencyId, String customerType) {
		List<User> models = []

		def entitys = datastore.execute {
			from User.class.simpleName
			where agencyId == customerAgencyId
			and type == customerType
		}

		entitys.each { entity ->
			User model = entity as User
			models <<  model
		}

		return models;
	}

	@Override
	public void add(SessionUserDto sessionUser, User model)
	throws ModelAlreadyExistException {

		if(!model.userId) {
			Agency agency = Agency.get(model.agencyId)
			model.agency = agency
			model.userId = "${model.firstName}-${model.lastName}@${agency.name}"
			model.userId = model.userId.toLowerCase()
		}
		model.type = UserType.CUSTOMER
		//model.agencyId = sessionUser.agencyId

		userService.add(sessionUser, model)

		stockService.onCustomerCreate(sessionUser, model)
	}

	@Override
	public void onAgencyCreate(SessionUserDto sessionUser, Agency agency) {

		User model = new User()
		model.with {
			firstName = 'Guest'
			lastName = 'Customer'
			agencyId = agency.id
		}

		this.add(sessionUser, model)
	}
}
