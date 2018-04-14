package com.jydp.mqListener;

/**
 * 消息队列监听接口
 * @param <T>
 */
public interface MqListener<T> {
	
	void listen(T t);

}
