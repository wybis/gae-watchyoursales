package io.vteial.seetu.service.impl

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.seetu.dto.SessionUserDto
import io.vteial.seetu.model.Account
import io.vteial.seetu.model.Employee
import io.vteial.seetu.model.constants.AccountStatus
import io.vteial.seetu.model.constants.AccountType
import io.vteial.seetu.service.AccountService
import io.vteial.seetu.service.EmployeeService
import io.vteial.seetu.service.exceptions.ModelAlreadyExistException

class DefaultEmployeeService extends AbstractService implements EmployeeService {

	GroovyLogger log = new GroovyLogger(DefaultEmployeeService.class.getName())

	AccountService accountService

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
	}
}
