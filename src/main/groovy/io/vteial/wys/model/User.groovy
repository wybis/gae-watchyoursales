package io.vteial.wys.model;

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class User implements Serializable {

	static final String ID_KEY = "userId"

	@Key
	long id

	String userId

	String password

	String identificationNumber

	String emailId

	String firstName

	String lastName

	String handPhoneNumber

	String landPhoneNumber

	long addressId

	@Ignore
	Address address

	String status

	String type

	String roleId

	@Ignore
	Role role

	long stockId

	@Ignore
	Stock stock

	long agencyId

	@Ignore
	Agency agency

	long createBy

	long updateBy

	Date createTime

	Date updateTime

	String toString() {
		StringBuilder sb = new StringBuilder(User.class.getSimpleName())
		sb.append('[')

		sb.append("id:${this.id}, ")
		sb.append("userId:${this.userId}, ")
		sb.append("type:${this.type}, ")
		sb.append("roleId:${this.roleId}, ")
		sb.append("status:${this.status}")

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