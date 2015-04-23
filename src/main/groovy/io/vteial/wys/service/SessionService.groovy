package io.vteial.wys.service;

import io.vteial.wys.dto.SessionDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.model.Account
import io.vteial.wys.model.User
import io.vteial.wys.service.exceptions.InvalidCredentialException
import io.vteial.wys.service.exceptions.ModelNotFoundException

import javax.servlet.http.HttpSession

public interface SessionService {

	static String SESSION_USER_KEY = 'user'

	Map<String, Object> properties(HttpSession session)

	void resetPassword(String userId) throws ModelNotFoundException

	SessionDto login(HttpSession session, UserDto userDto)
	throws InvalidCredentialException

	void logout(HttpSession session)

	void changeDetails(SessionDto sessionUser, UserDto userDto)
	throws ModelNotFoundException

	void changePassword(SessionDto sessionUser, UserDto userDto)
	throws ModelNotFoundException, InvalidCredentialException

	List<User> employees(SessionDto sessionUser)

	List<User> customers(SessionDto sessionUser)

	List<User> dealers(SessionDto sessionUser)
	
	List<Account> stocksAndProducts(SessionDto sessionUser)
}
