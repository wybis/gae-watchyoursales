package io.vteial.wys.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Employee
import io.vteial.wys.model.constants.AccountStatus
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.EmployeeService
import io.vteial.wys.service.StockService
import io.vteial.wys.service.exceptions.ModelAlreadyExistException

class DefaultEmployeeService extends AbstractService implements EmployeeService {

	GroovyLogger log = new GroovyLogger(DefaultEmployeeService.class.getName())

	AccountService accountService

	StockService stockService

	@Override
	public void add(SessionUserDto sessionUser, Employee model)
	throws ModelAlreadyExistException {

		Account account = new Account()
		account.name = "Employee-${model.firstName}"
		account.aliasName = "Employee-${model.lastName}"
		account.type = AccountType.EMPLOYEE
		account.isMinus = true
		account.status = AccountStatus.ACTIVE

		accountService.add(sessionUser, account)

		model.account = account
		model.accountId = account.id

		model.id = autoNumberService.getNextNumber(sessionUser, Employee.ID_KEY)

		model.prePersist(sessionUser.id)
		model.save()

		stockService.onEmployeeCreate(sessionUser, model)
	}
}
