package com.spider.rabbitmq;

/**
 * User: poplar
 * Date: 14-7-4 下午5:59
 */
public interface EventProcesser {
	public void process(Object e);
}
