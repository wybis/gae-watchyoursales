package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.constants.AgencyStatus
import io.vteial.wys.service.AgencyService
import io.vteial.wys.service.CustomerService
import io.vteial.wys.service.DealerService
import io.vteial.wys.service.EmployeeService
import io.vteial.wys.service.ProductService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultAgencyService extends AbstractService implements AgencyService {

	GroovyLogger log = new GroovyLogger(DefaultAgencyService.class.getName())

	ProductService productService

	EmployeeService employeeService

	DealerService dealerService

	CustomerService customerService

	@Override
	public void add(SessionUserDto sessionUser, Agency model)
	throws ModelAlreadyExistException {

		model.id = autoNumberService.getNextNumber(sessionUser, Agency.ID_KEY)
		model.status = AgencyStatus.ACTIVE

		model.prePersist(sessionUser.id)
		model.save()

		//productService.onAgencyCreate(sessionUser, model)
		employeeService.onAgencyCreate(sessionUser, model)
		dealerService.onAgencyCreate(sessionUser, model)
		customerService.onAgencyCreate(sessionUser, model)
	}
}
