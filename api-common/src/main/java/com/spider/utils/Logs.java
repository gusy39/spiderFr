/**
 * 
 */
package com.spider.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 5:52:32 PM Jul 20, 2014
 */
public abstract class Logs {

	public static final Logger unpredictableLogger = LoggerFactory
			.getLogger("unpredictable");

	public static final Logger requestInfo = LoggerFactory
			.getLogger("requestInfo");

	public static final Logger sendMsg = LoggerFactory
			.getLogger("sendMsg");
	
	public static final Logger testTask = LoggerFactory
			.getLogger("testTask");

	public static final Logger insert = LoggerFactory
			.getLogger("insert");

	public static final Logger status_302 = LoggerFactory
			.getLogger("status_302");

	public static final Logger threadStatus = LoggerFactory
			.getLogger("threadStatus");

	public static final Logger redisCache = LoggerFactory
			.getLogger("redisCache");

	/** 用于统计任务执行结果，加日志 */
	public enum StatsResult {
		
		SUCESS, /** 任务成功 */ 
		FAIL, /** 任务失败 */ 
		PARTIAL, /** 任务部分成功 */ 
		ASYNC /** 任务已提交异步处理 */
	}
}
