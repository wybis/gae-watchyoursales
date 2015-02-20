package io.vteial.wys.model

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class Tran implements Serializable {

	static final String ID_KEY = "tranId"

	@Key
	long id

	long receiptId

	@Ignore
	TranReceipt tranReceipt

	String category

	String productCode

	long stockId

	@Ignore
	Stock stock

	String type

	@Ignore
	long baseUnit

	long unit

	double rate

	@Ignore
	double amount

	Date date

	@Ignore
	double averageRate

	@Ignore
	double balanceUnit

	String status

	long orderId

	@Ignore
	Order order

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
		if(this.rate == this.balanceUnit) {
			this.amount = this.unit
		}
		else {
			double value = (this.rate / this.baseUnit);
			value = this.unit * value;
			this.amount = value;
		}
	}
}
