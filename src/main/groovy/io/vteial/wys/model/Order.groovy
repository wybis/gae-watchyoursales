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

	String category

	String productCode

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

	long employeeId

	@Ignore
	User employee

	long agencyId

	@Ignore
	Agency agency

	long createBy

	long updateBy

	Date createTime

	Date updateTime

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

	void computeAmount() {
		double value = (this.rate / this.baseUnit);
		value = this.unit * value;
		this.amount = value;
	}
}
