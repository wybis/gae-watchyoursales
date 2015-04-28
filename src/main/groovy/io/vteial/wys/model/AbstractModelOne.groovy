package io.vteial.wys.model;

import groovyx.gaelyk.datastore.Ignore
import groovyx.gaelyk.datastore.Key


public abstract class AbstractModelOne implements Model {

	private static final long serialVersionUID = 1L;

	long createBy

	long updateBy

	Date createTime

	Date updateTime

	@Ignore
	String errorMessage
}
