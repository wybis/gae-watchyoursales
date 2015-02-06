package io.vteial.wys.model

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class OrderReceipt implements Serializable {

	static final String ID_KEY = "orderReceiptId"

	@Key
	long id

	Date date

	@Ignore
	double totalAmount

	@Ignore
	double totalSaleAmount

	String description

	String status

	long customerId

	@Ignore
	Customer customer

	@Ignore
	long customerAccountId

	@Ignore
	Account customerAccount

	String employeeId

	@Ignore
	Employee employee

	long agencyId

	@Ignore
	Agency agency

	@Ignore
	List<Order> orders

	String createBy

	String updateBy

	Date createTime

	Date updateTime

	String toString() {
		StringBuilder sb = new StringBuilder(OrderReceipt.class.getSimpleName())
		sb.append('[')

		sb.append("id:${this.id}, ")

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
