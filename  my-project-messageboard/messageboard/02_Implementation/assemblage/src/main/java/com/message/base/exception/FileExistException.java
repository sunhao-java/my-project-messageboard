package com.message.base.exception;

/**
 * 文件夹已存在的异常
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-3-16 下午08:30:51
 */
public class FileExistException extends ApplicationRuntimeException {
	private static final long serialVersionUID = -6676669806465518670L;
	
	public FileExistException() {
        super();
    }

    public FileExistException(Throwable cause) {
        super(cause);
    }

    public FileExistException(String msg) {
        super(msg);
    }

    public FileExistException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public String getErrorCode() {
        return super.getErrorCode();
    }

    public void setErrorCode(String errorCode) {
        super.setErrorCode(errorCode); 
    }
}
