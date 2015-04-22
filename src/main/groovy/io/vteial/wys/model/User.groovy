package io.vteial.wys.model;

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class User extends AbstractModel {

	static final String ID_KEY = "userId"

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

	String type

	String status

	String roleId

	@Ignore
	Role role

	long branchId

	@Ignore
	Branch branch

	long cashAccountId

	@Ignore
	Account cashAccount

	long profitAccountId

	@Ignore
	Account profitAccount

	@Ignore
	List<Long> accountIds

	@Ignore
	List<Account> accounts

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

	// persistance operations

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