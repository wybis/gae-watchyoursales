package io.vteial.wys.service.impl;

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.GroovyLogger
import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.model.Branch
import io.vteial.wys.model.Product
import io.vteial.wys.model.Account
import io.vteial.wys.model.User
import io.vteial.wys.model.constants.UserStatus
import io.vteial.wys.service.SessionService
import io.vteial.wys.service.exceptions.InvalidCredentialException
import io.vteial.wys.service.exceptions.ModelNotFoundException

import javax.servlet.http.HttpSession

@GaelykBindings
public class DefaultSessionService extends AbstractService implements
SessionService {

	GroovyLogger log = new GroovyLogger(DefaultSessionService.class.getName())

	Map<String, Object> app = [:]

	com.google.appengine.api.users.UserService appUserService

	@Override
	public Map<String, Object> properties(HttpSession session) {
		def props             = this.app.clone()

		props.localMode       = localMode
		props.applicationUser = user
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

		aUser.cashStock = Account.get(aUser.cashStockId)
		aUser.cashStock.product = Product.get(aUser.cashStock.productId)
		aUser.profitStock = Account.get(aUser.profitStockId)
		aUser.profitStock.product = Product.get(aUser.profitStock.productId)
		aUser.agency = Branch.get(aUser.agencyId)
		sessionUser = new SessionUserDto()
		sessionUser.with {
			userId = aUser.userId
			firstName = aUser.firstName
			lastName = aUser.lastName
			roleId = aUser.roleId
			type = aUser.type
			id = aUser.id
			user = aUser
			agencyId = aUser.agencyId
			agency = aUser.agency
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
}
