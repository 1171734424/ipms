package com.ideassoft.core.task;

import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.ideassoft.core.exception.TaskException;
import com.ideassoft.util.DateUtil;


public class ScheduledTaskMgr {
	private static final ScheduledTaskMgr instance = new ScheduledTaskMgr();
	
	private Scheduler sch = null;
	
	private Logger logger = Logger.getLogger("ScheduledTaskMgr");
	
	private Hashtable<String, ScheduledTask> tasks = new Hashtable<String, ScheduledTask>();

	public static class TaskJob implements Job {
		private final Logger logger = Logger.getLogger(TaskJob.class);

		public void execute(JobExecutionContext context) throws JobExecutionException {
			JobDataMap data = context.getJobDetail().getJobDataMap();
			ScheduledTask task = (ScheduledTask) data.get("taskcls");
			logger.debug("Task:" + task);
			if (task != null) {
				task.startSchedule();
				task.setStatus(TaskStatus.STATUS_RUN);
				task.updateNextStartTime(context.getFireTime().getTime(), context.getNextFireTime().getTime());
				logger.info("TASK[" + task.getTaskName() + "]运行成功,下次启动时间：["
						+ DateUtil.utilDate2Str(context.getNextFireTime(), "yyyy-MM-dd HH:mm:ss") + "]");
			}
		}
	}

	private ScheduledTaskMgr() {
		try {
			sch = StdSchedulerFactory.getDefaultScheduler();
			sch.start();
		} catch (SchedulerException e) {
			logger.error("初始化定时任务管理器出错!", e);
		}
	}

	public static ScheduledTaskMgr getInstance() {
		return instance;
	}

	public void updateTask(String taskName) throws TaskException {
		ScheduledTask task = tasks.get(taskName);
		task.initScheduledData();
		if (task != null) {
			addTask(task, true);
		}
	}

	public void addTask(ScheduledTask task) throws TaskException {
		if (tasks.containsKey(task.getTaskName())) {
			addTask(task, true);
		} else {
			addTask(task, false);
			tasks.put(task.getTaskName(), task);
		}
	}

	public void addTask(ScheduledTask task, boolean update) throws TaskException {
		String jobname = task.getTaskName();
		Trigger trigger = null;
		try {
			if (task.getMode() == ScheduledTask.MODE_SCHEDULE_ROLL) {
				long startTime = task.getStartTime();
				long stepval = task.getStepVal();
				
				// 针对服务器重启的情景 如下次启动时间不为0,则任务已经启动过,并且不是更新操作。
				if (!update && task.getNextExecuteTime() != 0) {
					startTime = task.getNextExecuteTime();
				}
				
				trigger = new SimpleTrigger(jobname, Scheduler.DEFAULT_GROUP, new Date(startTime), null, SimpleTrigger.REPEAT_INDEFINITELY, stepval);
				trigger.setJobName(jobname);
				trigger.setJobGroup(Scheduler.DEFAULT_GROUP);
			} else {
				trigger = new CronTrigger(jobname, Scheduler.DEFAULT_GROUP, jobname, Scheduler.DEFAULT_GROUP, task.getCronExpression());
			}
			
			if (update) {
				sch.rescheduleJob(jobname, Scheduler.DEFAULT_GROUP, trigger);
				logger.info("定时任务[" + task.getTaskName() + "]更新成功,启动时间：["
						+ DateUtil.utilDate2Str(trigger.getStartTime(), "yyyy-MM-dd HH:mm:ss")
						+ "],下次执行时间：[" + DateUtil.utilDate2Str(trigger.getNextFireTime(), "yyyy-MM-dd HH:mm:ss") + "]");
			} else {
				JobDetail jobDetail = new JobDetail(jobname, Scheduler.DEFAULT_GROUP, TaskJob.class);
				JobDataMap jdm = new JobDataMap();
				jdm.put("taskcls", task);
				jobDetail.setJobDataMap(jdm);
				sch.scheduleJob(jobDetail, trigger);
				logger.info("定时任务[" + task.getTaskName() + "]创建成功,启动时间：["
						+ DateUtil.utilDate2Str(trigger.getStartTime(), "yyyy-MM-dd HH:mm:ss")
						+ "],下次执行时间：[" + DateUtil.utilDate2Str(trigger.getNextFireTime(), "yyyy-MM-dd HH:mm:ss") + "]");
			}
			task.setStatus(TaskStatus.STATUS_HANGUP);
		} catch (ParseException e) {
			task.setStatus(TaskStatus.STATUS_ERROR);
			throw new TaskException("创建定时任务出错,转换表达式出错", e);
		} catch (SchedulerException e) {
			task.setStatus(TaskStatus.STATUS_ERROR);
			throw new TaskException("创建定时任务出错,设置定时任务出错", e);
		}
	}
	
	public static void main(String[] args) {
		
	}
}
