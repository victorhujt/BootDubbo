package com.xescm.ofc.enums;

public enum ResultCodeEnum {
	UNDEFINED(10000, "未定义错误."),

	//系统异常
	ERROROPER(10001,"用户操作异常"),



	//业务异常
	JUMPPAGEERROR(100012,"页面跳转出错"),

	OPERATIONSTOOFREQUENT(10003, "操作过于频繁"),

	PARAMERROR(10004,"查询参数部不能空"),

	CAPTCHACODEERROR(10005,"验证码错误"),
	RESULTISNULL(10006,"结果集为空"),
	INTERFACEISCLOSE(10007,"查询接口已经关闭!"),
	IPISFREEZE(10008, "您的ip已经被冻结，请稍后再试"),
	IPISFORBIDDEN(10009, "您上黑名单!"),
	ISNOTSUPPORT(10010, "哎呀,暂不支持不提供运输的仓储订单!");


	private int code;
	private String msg;

	ResultCodeEnum(String msg) {
		this.msg = msg;
	}

	ResultCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String msg() {
		return msg;
	}
	public String code() {
		return this.name();
	}

	public static int getErrorCode(String code) {
		try {
			for (ResultCodeEnum ele : ResultCodeEnum.values()) {
				if (code.equals(ele.code()))
					return ele.getCode();
			}
			return 500;
		} catch (Exception e) {
			e.printStackTrace();
			return 500;
		}


	}
}
