package com.u1wan.core.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * mogodb add items
 * @author bfc
 *
 */
public class DBCursorValues {
	
	private List<Object> values = new ArrayList<Object>();

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	public void addtValues(Object value) {
		this.values.add(value);
	}
}
