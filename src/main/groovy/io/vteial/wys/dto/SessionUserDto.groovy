package io.vteial.wys.dto

import groovy.transform.Canonical
import groovy.transform.ToString
import io.vteial.wys.model.Agency

@Canonical
@ToString(includeNames=true)
class SessionUserDto implements Serializable {

	String id

	String password

	String roleId

	String firstName

	String lastName

	long agencyId

	Agency agency;
}
