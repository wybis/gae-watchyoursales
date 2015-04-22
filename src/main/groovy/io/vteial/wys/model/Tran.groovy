package io.vteial.wys.model

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class Tran extends AbstractModel {

	static final String ID_KEY = "tranId"

	long receiptId

	@Ignore
	TranReceipt tranReceipt

	String category

	String productCode

	long accountId

	@Ignore
	Account account

	String type

	long baseUnit

	long unit
	
	long balanceUnit

	double rate
	
	double averageRate

	@Ignore
	double amount

	double balanceAmount
	
	Date date

	String status

	long orderId

	@Ignore
	Order order

	long customerId

	@Ignore
	User customer

	long employeeId

	@Ignore
	User employee

	long branchId

	@Ignore
	Branch branch

	// persistence operations

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

	// domain operations

	void computeAmount() {
		if(this.rate == this.balanceUnit) {
			this.amount = this.unit;
		}
		else {
			double value = (this.rate / this.baseUnit);
			value = this.unit * value;
			this.amount = value;
		}
	}
}
