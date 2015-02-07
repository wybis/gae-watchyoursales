package io.vteial.wys.model

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
class Stock implements Serializable {

	static final String ID_KEY = "stockId"

	@Key
	long id

	String type

	double handStock

	double virtualStockBuy

	double virtualStockSell

	double availableStock

	long productId

	@Ignore
	Product product

	String employeeId

	@Ignore
	Employee employee

	long agencyId

	@Ignore
	Agency agency

	Date createTime

	Date updateTime

	String createBy

	String updateBy

	void preUpdate(String updateBy) {
		this.updateBy = updateBy
		this.updateTime = new Date()
	}

	void prePersist(String createAndUpdateBy) {
		this.createBy = createAndUpdateBy
		this.updateBy = createAndUpdateBy
		Date now = new Date()
		this.createTime = now
		this.updateTime = now
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
