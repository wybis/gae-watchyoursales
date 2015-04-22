package io.vteial.wys.dto

import groovy.transform.Canonical
import groovy.transform.ToString
import io.vteial.wys.model.Branch
import io.vteial.wys.model.User

@Canonical
@ToString(includeNames=true)
class SessionUserDto implements Serializable {

	String userId

	String password

	String firstName

	String lastName

	String type

	String roleId

	long id

	User user

	long agencyId

	Branch agency;
}
