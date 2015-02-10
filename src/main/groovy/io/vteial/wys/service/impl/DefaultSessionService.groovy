package io.vteial.wys.service.impl;

import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.model.Agency
import io.vteial.wys.model.Customer
import io.vteial.wys.model.Employee
import io.vteial.wys.model.Role
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.CustomerType
import io.vteial.wys.service.CustomerService
import io.vteial.wys.service.SessionService
import io.vteial.wys.service.exceptions.InvalidCredentialException
import io.vteial.wys.service.exceptions.ModelNotFoundException

import javax.servlet.http.HttpSession

public class DefaultSessionService extends AbstractService implements
SessionService {

	GroovyLogger log = new GroovyLogger(DefaultSessionService.class.getName())

	boolean localMode

	Map<String, Object> app

	com.google.appengine.api.users.UserService appUserService

	@Override
	public Map<String, Object> properties(HttpSession session, com.google.appengine.api.users.User appUser) {
		def props             = this.app.clone()

		props.localMode       = this.localMode
		props.applicationUser = appUser
		props.sessionUser     = session.user
		props.sessionId       = session.id

		return props;
	}

	@Override
	public void resetPassword(String userId) throws ModelNotFoundException {
	}

	@Override
	public SessionUserDto login(HttpSession session, UserDto userDto)
	throws InvalidCredentialException {
		SessionUserDto sessionUser = null

		if(userDto.id.indexOf('@') > -1) {
			Employee employee = Employee.get(userDto.id)
			if(!employee) {
				throw new InvalidCredentialException()
			}
			if(!localMode && employee.password != userDto.password) {
				throw new InvalidCredentialException()
			}
			sessionUser = this.createSessionUser(employee)
		}
		else {
			User user = User.get(userDto.id)
			if(!user) {
				throw new InvalidCredentialException()
			}
			if(!localMode && user.password != userDto.password) {
				throw new InvalidCredentialException()
			}
			sessionUser = this.createSessionUser(user)
		}

		session.setAttribute(SESSION_USER_KEY, sessionUser)

		return sessionUser
	}

	@Override
	public void logout(HttpSession session) {
		session.removeAttribute(SESSION_USER_KEY)
	}

	@Override
	public void changeDetails(SessionUserDto sessionUser, UserDto userDto)
	throws ModelNotFoundException {
	}

	@Override
	public void changePassword(SessionUserDto sessionUser, UserDto userDto)
	throws ModelNotFoundException, InvalidCredentialException {
	}

	private SessionUserDto createSessionUser(Employee employee) {
		SessionUserDto su = new SessionUserDto(id : employee.id)

		su.firstName = employee.firstName
		su.lastName = employee.lastName

		if(employee.roleId == Role.AGENCY_MANAGER) {
			su.roleId = Role.AGENCY_MANAGER
		}
		else {
			su.roleId = Role.AGENCY_EMPLOYEE
		}

		su.agencyId = employee.agencyId

		su.agency = Agency.get(employee.agencyId);

		return su
	}

	private SessionUserDto createSessionUser(User user) {
		SessionUserDto su = new SessionUserDto(id : user.id)

		su.firstName = user.firstName
		su.lastName = user.lastName

		if(user.roleId == Role.SYS_ADMIN) {
			su.roleId = Role.SYS_ADMIN
		}
		else {
			su.roleId = Role.APP_ADMIN
		}

		su.agencyId = 0

		Agency agency = new Agency();
		agency.name = 'Watch Your Sales'
		agency.aliasName = agency.name
		su.agency = agency

		return su
	}
}
