package com.message.main.menu.exception;

import com.message.base.exception.ApplicationRuntimeException;

/**
 * 菜单没有权限的异常
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 12-3-4 下午5:32
 */
public class NoPermException extends ApplicationRuntimeException {
	private static final long serialVersionUID = -448899345295497224L;

	public NoPermException() {
        super();
    }

    public NoPermException(Throwable cause) {
        super(cause);
    }

    public NoPermException(String msg) {
        super(msg);
    }

    public NoPermException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public String getErrorCode() {
        return super.getErrorCode();
    }

    public void setErrorCode(String errorCode) {
        super.setErrorCode(errorCode);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
