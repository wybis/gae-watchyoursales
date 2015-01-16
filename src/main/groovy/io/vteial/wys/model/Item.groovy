package io.vteial.wys.model

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
class Item implements Serializable {

	static final String ID_KEY = "itemId"

	@Key
	long id

	String code

	String name

	double baseUnit

	double denominator

	double buyRate

	double buyPercent

	double sellRate

	double sellPercent

	String status

	String agencyId

	@Ignore
	Agency agency

	String createBy

	String updateBy

	Date createTime

	Date updateTime

	String toString() {
		StringBuilder sb = new StringBuilder(User.class.getSimpleName())
		sb.append('[')

		sb.append("id:${this.id}, ")
		sb.append("name:${this.name}, ")
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
		this.createTime = now
		this.updateTime = now
	}

	double getBuyPercentageRate() {
		double value = this.buyRate * (this.buyPercent / 100)
		value = this.buyRate + value
		return value
	}

	double getSellPercentageRate() {
		double value = this.sellRate * (this.sellPercent / 100)
		value = this.sellRate - value
		return value
	}

	boolean isRateIsAllowableForBuy(double rate) {
		double bpr = this.getBuyPercentageRate()
		return rate <= bpr
	}

	boolean isRateIsAllowableForSell(double rate) {
		double spr = this.getSellPercentageRate()
		return rate >= spr
	}
}
