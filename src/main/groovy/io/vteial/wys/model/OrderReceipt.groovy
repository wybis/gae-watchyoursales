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

	String category

	Date date

	@Ignore
	double totalAmount

	@Ignore
	double totalSaleAmount

	String description

	String status

	long customerId

	@Ignore
	User customer

	long employeeId

	@Ignore
	User employee

	long agencyId

	@Ignore
	Agency agency

	@Ignore
	List<Order> orders

	long createBy

	long updateBy

	Date createTime

	Date updateTime

	String toString() {
		StringBuilder sb = new StringBuilder(OrderReceipt.class.getSimpleName())
		sb.append('[')

		sb.append("id:${this.id}, ")

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
