package com.message.base.exception;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * 系统异常类，各模块异常类都是继承于此类
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-2-24 下午10:30:49
 */
public class ApplicationRuntimeException extends NestableRuntimeException {
	private static final long serialVersionUID = 1843977652827496719L;
	
	//错误代码
	private String errorCode;
	
	public ApplicationRuntimeException(){
		
	}
	
	public ApplicationRuntimeException(final String msg){
		super(msg);
	}
	
	public ApplicationRuntimeException(final Throwable cause){
		super(cause);
	}
	
	public ApplicationRuntimeException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

}
