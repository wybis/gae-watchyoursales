package io.vteial.wys.model

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
class Account extends AbstractModel {

	static final String ID_KEY = "accountId"

	String name;

	String aliasName;

	String type

	boolean isMinus;

	double amount;

	String status;

	double handStock

	double virtualStockBuy

	double virtualStockSell

	double availableStock

	long productId

	@Ignore
	Product product

	long userId

	@Ignore
	User user

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
		this.createTime = now
		this.updateTime = now
	}

	// domain operations

	boolean hasSufficientBalance(double amount) {

		if (this.isMinus) {
			return true
		}

		return this.amount >= amount
	}

	boolean hasSufficientAmount(double amount) {
		return amount <= this.amount
	}

	void withdrawAmount(double amount) {
		this.amount -= amount
		this.product?.withdrawAmount(amount)
	}

	void depositAmount(double amount) {
		this.amount += amount
		this.product?.depositAmount(amount)
	}

	boolean hasSufficientHandStock(double unit) {
		return unit <= this.handStock
	}

	void withdrawHandStock(double unit) {
		this.handStock -= unit
		this.product.withdrawHandStock(unit)
	}

	void depositHandStock(double unit) {
		this.handStock += unit
		this.product.depositHandStock(unit)
	}

	double getVirtualStock() {
		return this.virtualStockBuy - this.virtualStockSell
	}

	boolean hasSufficientVirtualStockBuy(double unit) {
		return unit <= this.virtualStockBuy
	}

	boolean hasSufficientVirtualStockSell(double unit) {
		return unit <= this.virtualStockSell
	}

	void withdrawVirtualStockBuy(double unit) {
		this.virtualStockBuy -= unit
		this.product.withdrawVirtualStockBuy(unit)
	}

	void depositVirtualStockBuy(double unit) {
		this.virtualStockBuy += unit
		this.product.depositVirtualStockBuy(unit)
	}

	void withdrawVirtualStockSell(double unit) {
		this.virtualStockSell -= unit
		this.product.withdrawVirtualStockSell(unit)
	}

	void depositVirtualStockSell(double unit) {
		this.virtualStockSell += unit
		this.product.depositVirtualStockSell(unit)
	}

	void computeAvailableStock() {
		this.availableStock = this.getVirtualStock() + this.handStock
		this.product.computeAvailableStock()
	}
}
