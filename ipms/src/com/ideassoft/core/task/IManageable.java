package com.ideassoft.core.task;

public interface IManageable {

	public int startTask(long time);

	public int stopTask();

	public int pauseTask();

	public int resumeTask();

	public int resetTask();

	public TaskStatus getStatus();

}
