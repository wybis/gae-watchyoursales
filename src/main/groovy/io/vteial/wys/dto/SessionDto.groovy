package io.vteial.wys.dto

import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeNames=true)
class SessionDto implements Serializable {

	long id

	String userId

	String firstName

	String lastName

	String type

	String roleId

	long branchId
}
