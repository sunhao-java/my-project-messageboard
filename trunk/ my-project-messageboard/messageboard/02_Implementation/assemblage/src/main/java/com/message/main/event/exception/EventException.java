package com.message.main.event.exception;

import com.message.base.exception.ApplicationRuntimeException;

/**
 * 事件的自定义异常类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-24 下午10:26:08
 */
public class EventException extends ApplicationRuntimeException {
	private static final long serialVersionUID = -5555866187280165188L;

	public EventException() {
		super();
	}

	public EventException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public EventException(String msg) {
		super(msg);
	}

	public EventException(Throwable cause) {
		super(cause);
	}
	
	
}
