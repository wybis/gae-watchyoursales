package io.vteial.wys.service.impl

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.model.Branch
import io.vteial.wys.model.constants.BranchStatus
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.BranchService
import io.vteial.wys.service.CustomerService
import io.vteial.wys.service.DealerService
import io.vteial.wys.service.EmployeeService
import io.vteial.wys.service.ProductService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

@GaelykBindings
class DefaultBranchService extends AbstractService implements BranchService {

	GroovyLogger log = new GroovyLogger(DefaultBranchService.class.getName())

	AccountService accountService

	ProductService productService

	EmployeeService employeeService

	DealerService dealerService

	CustomerService customerService

	@Override
	public void add(SessionDto sessionUser, Branch model)
	throws ModelAlreadyExistException {

		model.id = autoNumberService.getNextNumber(sessionUser, Branch.ID_KEY)
		model.status = BranchStatus.ACTIVE

		model.prePersist(sessionUser.id)
		model.save()

		//accountService.onBranchCreate(sessionUser, model)
		productService.onBranchCreate(sessionUser, model)
		employeeService.onBranchCreate(sessionUser, model)
		dealerService.onBranchCreate(sessionUser, model)
		customerService.onBranchCreate(sessionUser, model)
	}
}
