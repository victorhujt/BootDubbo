package com.xescm.ofc.exception;

/**
 * <p>Title:	  BusinessException </p>
 * <p>Description 业务异常 </p>
 * <p>Company:    http://www.hnxianyi.com  </p>
 *
 * @Author <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
 * @CreateDate 2016年10月4日 上午10:23:46 <br/>
 * @since JDK 1.7
 */

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常描述
     */
    private String message;

    /**
     * 构造异常
     *
     * @param code    异常码
     * @param message 异常描述
     */
    public BusinessException(String code, String message, Throwable e) {
        super(code, e);
        this.code = code;
        this.message = message;
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * 构造异常
     *
     * @param code    异常码
     * @param message 异常描述
     */
    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return code + ": " + message;
    }
}
