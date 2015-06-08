package io.vteial.wys.model

import groovy.transform.Canonical
import groovy.transform.ToString
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Ignore

@Entity(unindexed=false)
@Canonical
@ToString(includeNames=true)
public class Transfer extends AbstractModel {

	static final String ID_KEY = "transferId"

	long fromUserId

	long toUserId

	Date date

	String status

	long byUserId

	@Ignore
	User byUser

	long branchId

	@Ignore
	Branch branch

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
}
