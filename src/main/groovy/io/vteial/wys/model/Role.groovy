package io.vteial.wys.model;

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class Role implements Serializable {

	static final String SYS_ADMIN = 'Sys Administrator'

	static final String APP_ADMIN = 'App Administrator'

	static final String AGENCY_MANAGER = 'Manager'

	static final String AGENCY_EMPLOYEE = 'Employee'

	static final String AGNECY_CUSTOMER = 'Customer'

	static final String AGNECY_DEALER = 'Dealer'

	static final List<String> ROLES = [
		SYS_ADMIN,
		APP_ADMIN,
		AGENCY_MANAGER,
		AGENCY_EMPLOYEE,
		AGNECY_CUSTOMER,
		AGNECY_DEALER
	]

	@Key
	String id

	String name

	long createBy

	long updateBy

	Date createTime

	Date updateTime

	String toString() {
		StringBuilder sb = new StringBuilder(AutoNumber.class.getSimpleName())
		sb.append('[')

		sb.append("id:${this.id}, ")
		sb.append("name:${this.name}")

		sb.append(']')
		return sb.toString()
	}

	void preUpdate(long updateBy) {
		this.updateBy = updateBy
		this.updateTime = new Date()
	}

	void prePersist(long createAndUpdateBy) {
		this.createBy = createAndUpdateBy
		this.updateBy = createAndUpdateBy
		Date now = new Date()
		this.createTime = now;
		this.updateTime = now;
	}
}
