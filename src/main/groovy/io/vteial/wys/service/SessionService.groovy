package io.vteial.wys.service;

import io.vteial.wys.dto.SessionUserDto
import io.vteial.wys.dto.UserDto
import io.vteial.wys.service.exceptions.InvalidCredentialException
import io.vteial.wys.service.exceptions.ModelNotFoundException

import javax.servlet.http.HttpSession

public interface SessionService {

	static String SESSION_USER_KEY = 'user'

	Map<String, Object> properties(HttpSession session, com.google.appengine.api.users.User appUser)

	void resetPassword(String userId) throws ModelNotFoundException

	SessionUserDto login(HttpSession session, UserDto userDto)
	throws InvalidCredentialException

	void logout(HttpSession session)

	void changeDetails(SessionUserDto sessionUser, UserDto userDto)
	throws ModelNotFoundException

	void changePassword(SessionUserDto sessionUser, UserDto userDto)
	throws ModelNotFoundException, InvalidCredentialException
}
