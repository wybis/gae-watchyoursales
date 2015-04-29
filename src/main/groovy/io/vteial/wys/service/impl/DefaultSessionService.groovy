package io.vteial.wys.service.impl;

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.Branch
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.AccountType
import io.vteial.wys.model.constants.UserStatus
import io.vteial.wys.model.constants.UserType
import io.vteial.wys.service.AccountService
import io.vteial.wys.service.SessionService
import io.vteial.wys.service.UserService
import io.vteial.wys.service.exceptions.InvalidCredentialException
import io.vteial.wys.service.exceptions.ModelNotFoundException

import javax.servlet.http.HttpSession

@GaelykBindings
public class DefaultSessionService extends AbstractService implements
SessionService {

	GroovyLogger log = new GroovyLogger(DefaultSessionService.class.getName())

	AccountService accountService

	UserService userService

	Map<String, Object> app = [:]

	com.google.appengine.api.users.UserService appUserService

	@Override
	public Map<String, Object> properties(HttpSession session) {
		def props             = this.app.clone()

		props.localMode       = localMode
		props.sessionDto      = session.getAttribute(SESSION_USER_KEY)
		props.sessionId       = session.id
		props.applicationUser = user

		return props;
	}

	@Override
	public void resetPassword(String userId) throws ModelNotFoundException {
	}

	@Override
	public SessionDto login(HttpSession session, UserDto userDto)
	throws InvalidCredentialException {
		SessionDto sessionDto = null

		def entitys = datastore.execute {
			from User.class.simpleName
			where userId == userDto.userId
			limit 1
		}

		if(entitys.size() == 0) {
			throw new InvalidCredentialException()
		}
		User aUser = entitys[0] as User
		if(aUser.status == UserStatus.PASSIVE) {
			throw new InvalidCredentialException()
		}
		if(!localMode && aUser.password != userDto.password) {
			throw new InvalidCredentialException()
		}

		sessionDto = new SessionDto()
		sessionDto.with {
			id = aUser.id
			userId = aUser.userId
			firstName = aUser.firstName
			lastName = aUser.lastName
			type = aUser.type
			roleId = aUser.roleId
			cashAccountId = aUser.cashAccountId
			profitAccountId = aUser.profitAccountId
			branchId = aUser.branchId
		}
		Branch branch = Branch.get(sessionDto.branchId)
		sessionDto.branchCode = branch.code
		sessionDto.branchName = branch.name
		sessionDto.branchVirtualEmployeeId = branch.virtualEmployeeId

		session.setAttribute(SESSION_USER_KEY, sessionDto)

		return sessionDto
	}

	@Override
	public void logout(HttpSession session) {
		session.removeAttribute(SESSION_USER_KEY)
	}

	@Override
	public void changeDetails(SessionDto sessionUser, UserDto userDto)
	throws ModelNotFoundException {
	}

	@Override
	public void changePassword(SessionDto sessionUser, UserDto userDto)
	throws ModelNotFoundException, InvalidCredentialException {
	}

	@Override
	public List<User> employees(SessionDto sessionUser) {
		List<User> models = []

		//		if(sessionUser.roleId == Role.ID_MANAGER) {
		//			def entitys = datastore.execute {
		//				from User.class.simpleName
		//				where branchId == sessionUser.branchId
		//				and type == UserType.EMPLOYEE
		//			}
		//
		//			entitys.each { entity ->
		//				User model = entity as User
		//				model.cashAccount = Account.get(model.cashAccountId)
		//				model.profitAccount = Account.get(model.profitAccountId)
		//				models <<  model
		//			}
		//		}
		//		else {
		//			User model = User.get(sessionUser.id)
		//			model.cashAccount = Account.get(model.cashAccountId)
		//			model.profitAccount = Account.get(model.profitAccountId)
		//			models << model
		//		}

		models = this.userService.findByBranchIdAndType(sessionUser.branchId, UserType.EMPLOYEE)

		return models;
	}

	@Override
	public List<User> customers(SessionDto sessionUser) {
		List<User> models = null

		models = this.userService.findByBranchIdAndType(sessionUser.branchId, UserType.CUSTOMER)

		return models;
	}

	@Override
	public List<User> dealers(SessionDto sessionUser) {
		List<User> models = null

		models = this.userService.findByBranchIdAndType(sessionUser.branchId, UserType.DEALER)

		return models;
	}

	@Override
	public List<Account> stocks(SessionDto sessionUser) {
		List<Account> models = null

		models = accountService.findByUserIdAndType(sessionUser.id, AccountType.PRODUCT)

		return models;
	}

	@Override
	public List<Account> ledgers(SessionDto sessionUser) {
		List<Account> models = null

		List<String> accountTypes = [AccountType.CASH_CAPITAL, AccountType.CASH_EMPLOYEE]
		models = accountService.findByBranchIdAndTypes(sessionUser.branchId, accountTypes)

		return models;
	}
}
