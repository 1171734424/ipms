/**
 * DataChangedListener.java 
 */
package com.ideassoft.core.task;

import java.util.List;

public interface DataChangedListener {
	public static final int Data_ADD = 1;
	public static final int DATA_UPD = 2;
	public static final int DATA_DEL = 3;

	public void onChange(List<?> changedData);
}
