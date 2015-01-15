package io.vteial.seetu.dto

import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includeNames=true)
class SessionUserDto implements Serializable {

	String id

	String password

	String roleId
	
	String firstName
	
	String lastName
}
