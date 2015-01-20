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

	double handStock

	double handStockMove

	@Ignore
	double handStockTotal

	double handStockAverage

	double virtualStockBuy

	double virtualStockSell

	double virtualStockAverage

	double availableStock

	double availableStockAverage

	long itemId

	@Ignore
	Item item

	String employeeId
	
	@Ignore
	Employee employee
	
	String agencyId

	@Ignore
	Agency agency

	protected Date createTime

	protected Date updateTime

	protected String createBy

	protected String updateBy

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

	boolean hasSufficientHandStock(double amount) {
		return amount <= this.handStock
	}

	void withdrawHandStock(double amount) {
		this.handStock -= amount
	}

	void depositHandStock(double amount) {
		this.handStock += amount
	}

	double getVirtualStock() {
		return this.virtualStockBuy - this.virtualStockSell
	}

	boolean hasSufficientVirtualStockBuy(double amount) {
		return amount <= this.virtualStockBuy
	}

	boolean hasSufficientVirtualStockSell(double amount) {
		return amount <= this.virtualStockSell
	}

	void withdrawVirtualStockBuy(double amount) {
		this.virtualStockBuy -= amount
	}

	void depositVirtualStockBuy(double amount) {
		this.virtualStockBuy += amount
	}

	void withdrawVirtualStockSell(double amount) {
		this.virtualStockSell -= amount
	}

	void depositVirtualStockSell(double amount) {
		this.virtualStockSell += amount
	}

	void computeAvailableStock() {
		this.availableStock = this.getVirtualStock() + this.getHandStockTotal()
	}

	void computeHandStockAverage(Stock stock, double unit, double rate) {
		// println("unit = " + unit)
		// println("rate = " + rate)
		// println("hStockTotal = " + stock.getHandStockTotal())
		double value1 = stock.getHandStockTotal() * (this.handStockAverage / this.item.baseUnit)
		// println("value1 = " + value1)
		double value2 = unit * (rate / this.item.baseUnit)
		// println("value2 = " + value2)
		double value3 = value1 + value2
		// println("value3 = " + value3)
		double value4 = stock.getHandStockTotal() + unit
		// println("value4 = " + value4)
		double value5 = (value3 / value4) * this.item.baseUnit
		// println("value5 = " + value5)
		this.handStockAverage = value5
	}

	void revertHandStockAverage(Stock stock, double unit, double rate) {
		double hs = stock.getHandStockTotal() - unit
		if (hs > 0) {
			// println("unit = " + unit)
			// println("rate = " + rate)
			// println("hStockTotal = " + stock.getHandStockTotal())
			double value1 = stock.getHandStockTotal() * (this.handStockAverage / this.item.baseUnit)
			// println("value1 = " + value1)
			double value2 = unit * (rate / this.item.baseUnit)
			// println("value2 = " + value2)
			double value3 = value1 - value2
			// println("value3 = " + value3)
			double value4 = stock.getHandStockTotal() - unit
			// println("value4 = " + value4)
			double value5 = (value3 / value4) * this.item.baseUnit
			// println("value5 = " + value5)
			this.handStockAverage = value5
		}
	}

	public void computeVirtualStockAverage(Stock stock, double unit, double rate) {
		// println("unit = " + unit)
		// println("rate = " + rate)
		// println("vStockBuy = stock.getVirtualStockBuy())
		double value1 = stock.getVirtualStockBuy()	* (this.virtualStockAverage / this.item.baseUnit)
		// println("value1 = " + value1)
		double value2 = unit * (rate / this.item.baseUnit)
		// println("value2 = " + value3)
		double value3 = value1 + value2
		// println("value3 = " + value3)
		double value4 = stock.getVirtualStockBuy() + unit
		// println("value4 = " + value4)
		double value5 = (value3 / value4) * this.item.baseUnit
		// println("value5 = " + value5)
		this.virtualStockAverage = value5
	}

	public void revertVirtualStockAverage(Stock stock, double unit, double rate) {
		double vsb = stock.getVirtualStockBuy() - unit
		if (vsb != 0) {
			// println("unit = " + unit)
			// println("rate = " + rate)
			// println("vStockBuy = stock.getVirtualStockBuy())
			double value1 = stock.getVirtualStockBuy()	* (this.virtualStockAverage / this.item.baseUnit)
			// println("value1 = " + value1)
			double value2 = unit * (rate / this.item.baseUnit)
			// println("value2 = " + value2)
			double value3 = value1 - value2
			// println("value3 = " + value3)
			double value4 = stock.getVirtualStockBuy() - unit
			// println("value4 = " + value4)
			double value5 = (value3 / value4) * this.item.baseUnit
			// println("value5 = " + value5)
			this.virtualStockAverage = value5
		}
	}

	public void computeAvailableStockAverage(Stock stock, double sellRate) {
		double value1 = stock.getVirtualStockBuy() * (this.virtualStockAverage / this.item.baseUnit)
		// println("value1 = " + value1)
		double value2 = stock.getHandStockTotal() * (this.handStockAverage / this.item.baseUnit)
		// println("value2 = " + value2)
		double value3 = value1 + value2
		// println("value3 = " + value3)
		double value4 = stock.getVirtualStockBuy() + stock.getHandStockTotal()
		// println("value4 = " + value4)
		double value5 = (value3 / value4) * this.item.baseUnit
		// println("value5 = " + value5)
		this.availableStockAverage = Double.isNaN(value5) ? 0 : value5
	}
}
