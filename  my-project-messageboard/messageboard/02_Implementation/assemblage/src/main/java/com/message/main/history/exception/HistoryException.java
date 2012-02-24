package com.message.main.history.exception;

import com.message.base.exception.ApplicationRuntimeException;

/**
 * 历史记录的自定义异常类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-24 下午10:46:25
 */
public class HistoryException extends ApplicationRuntimeException {
	private static final long serialVersionUID = 8577380226066900797L;

	public HistoryException() {
		super();
	}

	public HistoryException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public HistoryException(String msg) {
		super(msg);
	}

	public HistoryException(Throwable cause) {
		super(cause);
	}

}
