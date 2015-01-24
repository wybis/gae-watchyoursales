package io.vteial.wys.model;

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class Dealer implements Serializable {

	static final String ID_KEY = "dealerId"

	@Key
	long id

	String name

	String aliasName

	String identificationNumber

	String emailId

	String handPhoneNumber

	String landPhoneNumber

	long addressId

	@Ignore
	Address address

	String status
	
	String roleId = Role.AGNECY_DEALER

	long accountId

	@Ignore
	Account account

	long agencyId

	@Ignore
	Agency agency

	String createBy

	String updateBy

	Date createTime

	Date updateTime

	String toString() {
		StringBuilder sb = new StringBuilder(Dealer.class.getSimpleName())
		sb.append('[')

		sb.append("id:${this.id}, ")
		sb.append("accountId:${this.accountId}, ")
		sb.append("agencyId:${this.agencyId}, ")
		sb.append("status:${this.status}")

		sb.append(']')
		return sb.toString()
	}

	void preUpdate(String updateBy) {
		this.updateBy = updateBy
		this.updateTime = new Date()
	}

	void prePersist(String createAndUpdateBy) {
		this.createBy = createAndUpdateBy
		this.updateBy = createAndUpdateBy
		Date now = new Date()
		this.createTime = now;
		this.updateTime = now;
	}
}