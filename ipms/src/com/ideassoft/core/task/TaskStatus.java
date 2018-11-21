package com.ideassoft.core.task;

import java.util.Date;

public class TaskStatus {
	private Date startTime;

	public static final int STATUS_RUN = 0x01;

	public static final int STATUS_PAUSE = 0x02;

	public static final int STATUS_HANGUP = 0x03;
	
	public static final int STATUS_ERROR = 0x04;

	private int status = STATUS_HANGUP;

	private int queueCount;

	private Date stopTime;

	public TaskStatus() {

	}

	public final Date getStopTime() {
		return stopTime;
	}

	public final void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public final int getQueueCount() {
		return queueCount;
	}

	public final void setQueueCount(int queueCount) {
		this.queueCount = queueCount;
	}

	public final Date getStartTime() {
		return startTime;
	}

	public final void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public final int getStatus() {
		return status;
	}

	public final void setStatus(int status) {
		this.status = status;
	}

}
