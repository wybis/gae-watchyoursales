package io.vteial.wys.model;

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Key

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class Country implements Serializable {

	@Key
	String id

	String twoLetterCode

	String threeLetterCode

	int numericCode

	String name

	long createBy

	long updateBy

	Date createTime

	Date updateTime

	String toString() {
		StringBuilder sb = new StringBuilder(Country.class.getSimpleName())
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
