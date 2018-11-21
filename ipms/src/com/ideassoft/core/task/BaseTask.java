package com.ideassoft.core.task;

import com.ideassoft.core.exception.QueueException;

public abstract class BaseTask implements IManageable, Runnable {
	private TaskManager parent;

	private String taskName = null;

	protected boolean isDaemonThread = false;

	protected String taskID = "";

	protected Thread thread;

	public final String getTaskID() {
		return taskID;
	}

	public final void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public final void setDaemonThread(boolean isDaemonThread) {
		this.isDaemonThread = isDaemonThread;
	}

	public BaseTask(String name) {
		this.taskName = name;
	}

	void setParent(TaskManager parent) {
		this.parent = parent;
	}

	TaskManager getParent() {
		return this.parent;
	}

	public abstract void addTaskObj(String key, Object obj)
			throws QueueException;

	public abstract void removeTaskObj(String key);

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int startTask(long time) {
		thread = new Thread(this);
		thread.setName(this.taskName);
		thread.setDaemon(this.isDaemonThread);
		thread.start();
		return 0;
	}

	public void start() {
		startTask(0);
	}

	public void interrupt() {
		if (thread != null && thread.isAlive()) {
			thread.interrupt();
		}
	}

	public void sleep(int time) throws InterruptedException {
		if (thread != null) {
			Thread.sleep(time);
		}
	}
}
