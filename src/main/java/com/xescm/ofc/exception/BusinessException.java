package com.xescm.ofc.exception;


import com.xescm.ofc.enums.ResultCodeEnum;

/**
 * 
 * <p>Title:	  BusinessException </p>
 * <p>Description 业务异常 </p>
 * <p>Company:    http://www.hnxianyi.com  </p>
 * @Author        <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
 * @CreateDate    2016年10月4日 下午10:23:46 <br/>
 * @version       
 * @since         JDK 1.7
 */
public class BusinessException extends RuntimeException {
	/**
	 * serialVersionUID:用一句话描述这个变量表示什么.
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -3048093909192022690L;
	private ResultCodeEnum code;
	
	public BusinessException(ResultCodeEnum code, String msg) {
		super(msg);
		this.code = code;
	}

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public ResultCodeEnum getCode() {
		return code;
	}

	public void setCode(ResultCodeEnum code) {
		this.code = code;
	}

}
