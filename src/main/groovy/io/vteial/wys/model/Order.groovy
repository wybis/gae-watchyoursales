package io.vteial.wys.model

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class Order implements Serializable {

	static final String ID_KEY = "orderId"

	@Key
	long id

	long receiptId

	@Ignore
	OrderReceipt orderReceipt

	long stockId

	@Ignore
	Stock stock

	String type

	@Ignore
	long baseUnit

	double unit

	double rate

	@Ignore
	double amount

	Date date

	@Ignore
	double averageRate

	String status

	String employeeId

	@Ignore
	Employee employee

	long agencyId

	@Ignore
	Agency agency

	String createBy

	String updateBy

	Date createTime

	Date updateTime

//	String toString() {
//		StringBuilder sb = new StringBuilder(Order.class.getSimpleName())
//		sb.append('[')
//
//		sb.append("id:${this.id}, ")
//
//		sb.append(']')
//		return sb.toString()
//	}

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

	void computeAmount() {
		double value = (this.rate / this.baseUnit);
		value = this.unit * value;
		this.amount = value;
	}
}
