package com.message.main.upload.exception;

import com.message.base.exception.ApplicationRuntimeException;

/**
 * 文件错误的异常
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-17 上午03:19:05
 */
public class FileErrorException extends ApplicationRuntimeException {
	private static final long serialVersionUID = 3412753933429299794L;
	
	public FileErrorException() {
        super();
    }

    public FileErrorException(Throwable cause) {
        super(cause);
    }

    public FileErrorException(String msg) {
        super(msg);
    }

    public FileErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public String getErrorCode() {
        return super.getErrorCode();
    }

    public void setErrorCode(String errorCode) {
        super.setErrorCode(errorCode);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
