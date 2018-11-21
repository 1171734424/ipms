package com.ideassoft.core.task;

import java.text.ParseException;

import org.quartz.CronExpression;

import com.ideassoft.core.exception.QueueException;

public abstract class ScheduledTask extends BaseTask {
	public ScheduledTask(String name) {
		super(name);
	}

	public static final int MODE_SCHEDULE_STEP = 0x01;

	public static final int MODE_SCHEDULE_ROLL = 0x02;

	private int mode = MODE_SCHEDULE_ROLL;

	private String cronExpression;

	private long stepVal;

	CronExpression cronEx;

	protected long startTime;

	protected long nextExecuteTime;

	protected boolean enable = true;
	
	TaskStatus status = new TaskStatus();

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public final long getNextExecuteTime() {
		return nextExecuteTime;
	}

	public final void setNextExecuteTime(long nextExecuteTime) {
		this.nextExecuteTime = nextExecuteTime;
	}

	public final long getStartTime() {
		return startTime;
	}

	public final void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public final long getStepVal() {
		return stepVal;
	}

	public final void setStepVal(long stepVal) {
		this.stepVal = stepVal;
	}

	public void run() {
		
	}

	public final int getMode() {
		return mode;
	}

	public final void setMode(int mode) {
		this.mode = mode;
	}

	public abstract void initScheduledData();

	public void setCronExpression(String cron) {
		try {
			cronEx = new CronExpression(cron);
			this.cronExpression = cron;
		} catch (ParseException e) {
			throw new IllegalArgumentException("crontab expression occurs error");
		}
	}

	public void addTaskObj(String key, Object obj) throws QueueException {

	}

	public void removeTaskObj(String key) {

	}

	public TaskStatus getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status.setStatus(status);
	}

	public int pauseTask() {
		return 0;
	}

	public synchronized void startSchedule() {
		start();
	}

	public void updateNextStartTime(long startTime, long nextStartTime) {
	}

	public final String getCronExpression() {
		return cronExpression;
	}

	public int resetTask() {
		return 0;
	}

	public int resumeTask() {
		return 0;
	}

	public int stopTask() {
		return 0;
	}
}
