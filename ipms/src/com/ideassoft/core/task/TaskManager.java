package com.ideassoft.core.task;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.exception.DBAccessException;
import com.ideassoft.core.exception.TaskException;
import com.ideassoft.core.server.Server;
import com.ideassoft.core.server.Config.SubTaskConfig;

public class TaskManager extends Thread {
	private static Logger logger = Logger.getLogger(TaskManager.class);
	
	private Hashtable<String, Object> taskList = new Hashtable<String, Object>();

	public static final int STATUS_START = 0x01;

	public static final int STATUS_STOP = 0x02;

	private static TaskManager instance = null;

	private boolean stop = false;

	private int interval = 2000;

	private List<BaseTask> listeners = new ArrayList<BaseTask>();

	private TaskManager() {
		super();
		setName("TaskManager");
	}

	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}
		return instance;
	}

	public void run() {
		logger.info("Task管理线程启动...");

		loadTask();
		while (!stop) {
			try {
				sleep(interval);
			} catch (InterruptedException e) {
				stop = true;
			} catch (Exception e) {
				logger.error("TaskManager装载数据出错", e);
			} catch (Throwable t) {
				logger.error("TaskManager装载数据出错", t);
			}
		}
		clearTasks();
		logger.info("Task管理线程停止...");
	}

	private synchronized void clearTasks() {
		Enumeration<?> it = taskList.keys();
		while (it.hasMoreElements()) {
			BaseTask bt = (BaseTask) taskList.remove(it.nextElement());
			if (bt instanceof DataChangedListener) {
				listeners.remove(bt);
			}
			bt.interrupt();
			bt = null;
		}
	}

	public synchronized boolean stopAllTask() {
		Enumeration<?> it = taskList.keys();
		while (it.hasMoreElements()) {
			BaseTask bt = (BaseTask) taskList.remove(it.nextElement());
			if (bt instanceof DataChangedListener) {
				listeners.remove(bt);
			}
			bt.interrupt();
		}
		return true;
	}

	public BaseTask queryTask(String taskName) throws TaskException {
		if (!taskList.containsKey(taskName)) {
			throw new TaskException("Task" + taskName + "未找到");
		}
		BaseTask task = (BaseTask) taskList.get(taskName);
		return task;
	}

	public BaseTask createTask(String taskName, String taskCls) throws TaskException {
		BaseTask task = null;
		try {
			Class<?> clsTask = Class.forName(taskCls);
			Constructor<?> cts = clsTask.getConstructor(new Class[] { String.class });
			task = (BaseTask) cts.newInstance(new Object[] { taskName });
		} catch (Exception e) {
			throw new TaskException("类创建失败!", e);
		}
		return task;
	}

	public synchronized void registerTask(String name, BaseTask task) {
		if (!taskList.containsKey(name)) {
			taskList.put(name, task);
			task.setParent(this);
			if (task instanceof DataChangedListener) {
				listeners.add(task);
			}
		}
	}

	public void unRegisterTask(String name) {
		Object task = taskList.remove(name);
		if (task instanceof DataChangedListener) {
			listeners.remove(task);
		}
	}

	private void loadTask() {
		try {
			SubTaskConfig[] tasks = Server.config.subTaskConfigs;
			BaseTask task = null;
			if (tasks != null && tasks.length > 0) {
				for (int i = 0; i < tasks.length; i++) {
					SubTaskConfig dtask = tasks[i];
					if (dtask.isValid()) {
						try {
							task = createTask(dtask.getNameEN(), dtask.getTaskClass());
							logger.debug("装载任务：" + dtask.getNameZH() + "成功");
							
							if (dtask.isDaemon()) {
								task.setDaemonThread(true);
							}
							
							if (dtask.getAutoStart()) {
								task.startTask(0);
								logger.info("启动任务:" + dtask.getNameZH() + "成功");
							}
							
							if (SystemConstants.TaskConfig.TASK_TYPE.equals(dtask.getTaskType())) {
								if (task instanceof ScheduledTask) {
									ScheduledTask schTask = (ScheduledTask) task;
									schTask.setTaskID(dtask.getTaskId());
									schTask.setMode(Integer.parseInt(dtask.getTaskMode()));
									schTask.initScheduledData();
									if (schTask.isEnable()) {
										if (schTask.getCronExpression() == null && dtask.getScheduleExpression() != null) {
											schTask.setCronExpression(dtask.getScheduleExpression());
										}
										//schTask.setNextExecuteTime(dtask.getNextExecuteTime());
										ScheduledTaskMgr.getInstance().addTask(schTask);
										logger.info("注册定时任务" + schTask.getTaskName() + "成功");
									}
								}
							}
							registerTask(dtask.getNameEN(), task);
						} catch (TaskException e) {
							logger.error("TaskManager 加载系统任务出错,创建任务实例" + dtask.getTaskClass() + "出错", e);
						}
					}
				}
			}
		} catch (DBAccessException e) {
			logger.error("TaskManager 加载系统任务出错,数据库访问失败", e);
		} catch (Exception e) {
			logger.error("TaskManager " + e.getMessage(), e);
		}
	}

	protected void finalize() throws Throwable {
		taskList.clear();
		listeners.clear();
		super.finalize();
	}
}
