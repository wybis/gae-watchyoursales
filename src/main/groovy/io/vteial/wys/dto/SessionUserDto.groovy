package io.vteial.wys.dto

import groovy.transform.Canonical
import groovy.transform.ToString
import io.vteial.wys.model.Agency
import io.vteial.wys.model.User

@Canonical
@ToString(includeNames=true)
class SessionUserDto implements Serializable {

	long id
	
	User user

	String userId

	String password

	String firstName

	String lastName

	String type

	String roleId

	long agencyId

	Agency agency;
}
