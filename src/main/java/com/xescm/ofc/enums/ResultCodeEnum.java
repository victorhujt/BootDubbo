package com.xescm.ofc.enums;

public enum ResultCodeEnum {
	UNDEFINED("UEC000000", "未定义错误."),

	//系统异常
	ERROROPER("SEC000001","用户操作异常"),



	//业务异常
	JUMPPAGEERROR("BEC000001","页面跳转出错"),

	OPERATIONSTOOFREQUENT("OPTF000001","操作过于频繁"),

	PARAMERROR("PAE000001","查询参数部不能空"),

	CAPTCHACODEERROR("BEC000001","验证码错误");





	String type;
	String name;

	ResultCodeEnum(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String getName(String type) {
		for (ResultCodeEnum ele : ResultCodeEnum.values()) {
			if (ele.getType() == type)
				return ele.getName();
		}
		return null;
	}

	public static ResultCodeEnum getEnum(String type) {
		for (ResultCodeEnum ele : ResultCodeEnum.values()) {
			if (ele.getType() == type)
				return ele;
		}
		return ResultCodeEnum.UNDEFINED;
	}
}
