package io.vteial.wys.model;

import groovyx.gaelyk.datastore.Key


public abstract class AbstractModel implements Model {

	private static final long serialVersionUID = 1L;

	@Key
	long id

	long createBy

	long updateBy

	Date createTime

	Date updateTime
}
