package io.vteial.wys.model;

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key
import io.vteial.wys.model.constants.CustomerType

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class Customer implements Serializable {

	static final String ID_KEY = "customerId"

	@Key
	long id

	String firstName

	String lastName

	String identificationNumber

	String emailId

	String handPhoneNumber

	String landPhoneNumber

	long addressId

	@Ignore
	Address address

	String status
	
	String type = CustomerType.CUSTOMER

	String roleId = Role.AGNECY_CUSTOMER

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
		StringBuilder sb = new StringBuilder(Customer.class.getSimpleName())
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