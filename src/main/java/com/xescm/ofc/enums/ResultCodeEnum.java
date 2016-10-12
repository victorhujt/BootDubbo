package com.xescm.ofc.enums;
/**
 * 
 * <p>Title:	  ResultCodeEnum </p>
 * <p>Description 异常编码枚举 </p>
 * <p>Company:    http://www.hnxianyi.com  </p>
 * @Author        <a href="liu_zhaoming@sina.cn"/>刘兆明</a>
 * @CreateDate    2016年10月5日 上午11:23:53 <br/>
 * @version       
 * @since         JDK 1.7
 */
public enum ResultCodeEnum {
	UNDEFINED(0, "未定义错误.");

	int type;
	String name;

	ResultCodeEnum(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static String getName(int type) {
		for (ResultCodeEnum ele : ResultCodeEnum.values()) {
			if (ele.getType() == type)
				return ele.getName();
		}
		return null;
	}

	public static ResultCodeEnum getEnum(int type) {
		for (ResultCodeEnum ele : ResultCodeEnum.values()) {
			if (ele.getType() == type)
				return ele;
		}
		return ResultCodeEnum.UNDEFINED;
	}
}
