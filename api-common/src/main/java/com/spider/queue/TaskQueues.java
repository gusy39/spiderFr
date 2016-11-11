/**
 *
 */
package com.spider.queue;

/**
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 4:32:54 PM Jul 20, 2014
 */
public class TaskQueues {

	private static TaskQueue taskQueue;

	public static TaskQueue getTaskQueue() {
		return taskQueue;
	}

	public static void setParseOrderTaskQueue(TaskQueue taskQueue) {
		TaskQueues.taskQueue = taskQueue;
	}
}
