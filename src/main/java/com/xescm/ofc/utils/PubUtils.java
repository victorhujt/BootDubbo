package com.xescm.ofc.utils;

import java.util.Collection;

/**
 * 
 * <p>
 * <b>公共工具类</b>
 * 
 * <ul>
 * <li>
 * </ul>
 * 
 * <p>
 * <p>
 * 
 * @version 1.0
 * @since 1.0
 * @author WangSongTao
 * @time 2014-7-2 下午3:22:44
 */
public class PubUtils {
	// 空
	public final static String STRING_NULL = "-";

	public final static String EQ = "="; // 等于
	public final static String BG = ">"; // 大于
	public final static String BQ = ">="; // 大于等于
	public final static String LE = "<"; // 小于
	public final static String LQ = "<="; // 小于等于
	public final static String NQ = "!="; // 不等于
	public final static String DQ = "=="; // 双等于
	public final static String IN = "in";
	public final static String LIKE = "like";

	/**
	 * 匹配中文正的字符串
	 */
	public static final String REGX_CHINESE = "^[\u4e00-\u9fa5]+$";
	/**
	 * 匹配由汉字、数字和26个英文字母组成的字符串
	 */
	public static final String REGX_LETTER_NUMBER = "^[\u4e00-\u9fa5A-Za-z0-9]+$";
	/**
	 * 匹配非负整数（正整数 + 0)
	 */
	public static final String REGX_NON_NEGATIVE_INTEGERS = "^\\d+$";
	/**
	 * 匹配负浮点数
	 */
	public static final String REGX_NEGATIVE_RATIONAL_NUMBERS = "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";
	/**
	 * 匹配电话号码
	 */
	public static final String REGX_PHONENUM = "([0-9]*[-][0-9]*)|[0-9]*";
	/**
	 *匹配电话号码（带“-”，带区号，带分机号）
	 */
	public static final String REGX_PHONENUM_1="^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";
	/**
	 * 手机号和座机号一起验证（包括区号、“-”、座机号）
	 */
	public static final String REGX_CALL = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$|^((\\+86)|(86))?(0[0-9]|13|15|17|18)\\d{9}$";
	/**
	 * 匹配手机号码，支持+86和86开头
	 */
	public static final String REGX_MOBILENUM = "^((\\+86)|(86))?(13|15|17|18)\\d{9}$";
	/**
	 * 匹配日期时间
	 */
	public static final String REGX_DATETIME = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|"
			+ // 年
			"(1[02]))[\\-\\/\\s]?((0?[1-9])|"
			+ // 月
			"([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|"
			+ // 日
			"([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|"
			+ "([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|"
			+ "(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))"
			+
			// 时分秒
			"(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\:([0-5]?[0-9])))))";

	/**
	 * 匹配时间
	 */
	public static final String REGX_TIME = "((((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\:([0-5]?[0-9])))))";

	/**
	 * 匹配年月
	 */
	public static final String REGX_YEARDATE = "^([1-2]\\d{3})[\\-](0?[1-9]|10|11|12)";

	/**
	 * 匹配年
	 */
	public static final String REGX_YEAR = "^([1-2]\\d{3})";

    /**
     * 匹配邮政编码
     */
    public static final String REGX_POSTCODE = "[1-9]\\d{5}(?!\\d)";

    /**
     * 匹配邮箱帐号
     */
    public static final String REGX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

	/**
	 * 
	 * 匹配时间格式
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @param inputStr
	 * @return <p>
	 * @since 1.0
	 * @author 王松涛
	 * @time 2010-11-18 上午11:18:51
	 */
	public static Boolean isTimeFormat(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_TIME);
		}
		return false;
	}

	/**
	 * 
	 * 匹配年月格式
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @param inputStr
	 * @return <p>
	 * @since 1.0
	 * @author 王松涛
	 * @time 2010-11-18 上午11:19:03
	 */
	public static Boolean isYearDateFormat(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_YEARDATE);
		}
		return false;
	}

	/**
	 * 
	 * 匹配年格式
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @param inputStr
	 * @return <p>
	 * @since 1.0
	 * @author 王松涛
	 * @time 2010-11-18 上午11:19:03
	 */
	public static Boolean isYearFormat(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_YEAR);
		}
		return false;
	}

	/**
	 * 
	 * 匹配是否为日期时间的格式
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @param inputStr
	 * @return <p>
	 * @since 1.0
	 * @author 王松涛
	 * @time 2010-11-18 上午10:53:38
	 */
	public static Boolean isDateTimeFormat(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_DATETIME);
		}
		return false;
	}

	/**
	 * 是否非法字符校验此方法只限制中、英、数字的输入，如果是中、英和数字任意组合将返回true
	 * 例如：宅急送、宅急送123、ABD宅急送123、123、ABC 都将返回true
	 * 
	 * @since 1.0
	 * @author 刘晓辉
	 * @time 2010-9-19 下午03:10:04
	 */
	public static Boolean isEngChineseNum(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_LETTER_NUMBER);
		}
		return false;
	}

	/**
	 * 
	 * 匹配电话号码（除了数字之外可以含有一个‘-’）
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @param inputStr
	 * @return <p>
	 * @since 1.0
	 * @author 王松涛
	 * @time 2010-11-18 上午09:23:28
	 */
	public static Boolean isLocationNumber(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_PHONENUM);
		}
		return false;
	}
	/**
	 * 匹配电话号码（带“-”，带区号，带分机号）
	 * @param inputStr
	 * @return
	 */
	public static Boolean isLocationNumber_1(String inputStr){
		if(!PubUtils.isNull(inputStr)){
			return inputStr.matches(REGX_PHONENUM_1);
		}
		return false;
	}
	/***
	 * 手机号和座机号一起验证
	 * @param inputStr
	 * @return
	 */
	public static Boolean isCall(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_CALL);
		}
		return false;
	}
	/**
	 * 
	 * 匹配手机号码（先支持13，15，17，18开头的手机号码）
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @param inputStr
	 * @return <p>
	 * @since 1.0
	 * @author 王松涛
	 * @time 2010-11-18 上午09:23:28
	 */
	public static Boolean isMobileNumber(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_MOBILENUM);
		}
		return false;
	}

	/**
	 * 是否数字 如果是数字将返回true
	 * 
	 * @since 1.0
	 * @author 刘晓辉
	 * @time 2010-9-19 下午03:31:49
	 */
	public static Boolean isNumber(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_NON_NEGATIVE_INTEGERS);
		}
		return false;
	}

	/**
	 * 是否是汉字 如果是汉字将返回true
	 * 
	 * @since 1.0
	 * @author 刘晓辉
	 * @time 2010-9-19 下午03:31:49
	 */
	public static Boolean isChinese(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_CHINESE);
		}
		return false;
	}

	/**
	 * 是否是负数 包含浮点数，如果是浮点数将返回true，例如：-125.00或-125 都将返回true
	 * 
	 * @since 1.0
	 * @author 刘晓辉
	 * @time 2010-9-19 下午03:31:49
	 */
	public static Boolean isNegative(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			return inputStr.matches(REGX_NEGATIVE_RATIONAL_NUMBERS);
		}
		return false;
	}

	/**
	 * 
	 * 全角转半角方法
	 * 
	 * @author 刘晓辉
	 * @time 2010-9-19 下午03:24:21
	 */
	public static String sbcTodbcChange(String inputStr) {
		if (!PubUtils.isNull(inputStr)) {
			StringBuffer outBuffer = new StringBuffer();
			String tempStr = null;
			byte[] b = null;

			for (int i = 0; i < inputStr.length(); i++) {
				try {
					tempStr = inputStr.substring(i, i + 1);
					b = tempStr.getBytes("unicode");
				} catch (java.io.UnsupportedEncodingException e) {
					e.printStackTrace();
				}

				if (b[2] == -1) {
					b[3] = (byte) (b[3] + 32);
					b[2] = 0;

					try {
						outBuffer.append(new String(b, "unicode"));
					} catch (java.io.UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					outBuffer.append(tempStr);
				}
			}
			return outBuffer.toString();
		}
		return null;
	}

	/**
	 * 如果传入参数为null则返回空格
	 * 
	 * @param value
	 * @return " "
	 */
	public static String getString_TrimZeroLenNotAsNull(Object value) {
		if (value == null || value.toString().trim().length() == 0) {
			return " ";
		}
		return value.toString();
	}

	/**
	 * 判断一个或多个对象是否为空
	 * 
	 * @param values
	 *          可变参数，要判断的一个或多个对象
	 * @return 只有要判断的一个对象都为空则返回true,否则返回false
	 */
	public static boolean isNull(Object... values) {
		if (!PubUtils.isNotNullAndNotEmpty(values)) {
			return true;
		}
		for (Object value : values) {
			boolean flag = false;
			if (value instanceof Object[]) {
				flag = !isNotNullAndNotEmpty((Object[]) value);
			} else if (value instanceof Collection<?>) {
				flag = !isNotNullAndNotEmpty((Collection<?>) value);
			} else if (value instanceof String) {
				flag = isOEmptyOrNull(value);
			} else {
				flag = (null == value);
			}
			if (flag) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @return boolean
	 */
	public static boolean isOEmptyOrNull(Object o) {
		return o == null ? true : isSEmptyOrNull(o.toString());
	}

	/**
	 * 
	 * @return boolean
	 */
	public static boolean isSEmptyOrNull(String s) {
		return trimAndNullAsEmpty(s).length() <= 0 ? true : false;
	}

	/**
	 * 针对多个必填参数非空校验，只要有一个为空，就都算为空。
	 * @param strs 需要校验的参数列表
	 * @return
	 */
	public static boolean isStrsEmptyOrNull(String... strs){
		if(strs==null||strs.length==0){
			return true;
		}else{
			for(String str:strs){
				if(str==null|| str.trim().equals("")){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @return java.lang.String
	 *
	 */
	public static String trimAndNullAsEmpty(String s) {
		if (s != null && !s.trim().equals(STRING_NULL)) {
			return s.trim();
		} else {
			return "";
		}
		// return s == null ? "" : s.trim();
	}

	/**
	 * 判断对象数组是否为空并且数量大于0
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNotNullAndNotEmpty(Object[] value) {
		boolean bl = false;
		if (null != value && 0 < value.length) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象集合（List,Set）是否为空并且数量大于0
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNotNullAndNotEmpty(Collection<?> value) {
		boolean bl = false;
		if (null != value && 0 < value.size()) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象数组是否为空并且数量大于size值
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNotNullAndBiggerSize(Object[] value, int size) {
		boolean bl = false;
		if (null != value && size < value.length) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象集合（List,Set）是否为空并且数量大于size值
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNotNullAndBiggerSize(Collection<?> value, int size) {
		boolean bl = false;
		if (null != value && size < value.size()) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象数组是否为空并且数量等于size值
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNotNullAndEqualSize(Object[] value, int size) {
		boolean bl = false;
		if (null != value && size == value.length) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象集合（List,Set）是否为空并且数量等于size值
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNotNullAndEqualSize(Collection<?> value, int size) {
		boolean bl = false;
		if (null != value && size == value.size()) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象数组是否为空并且数量小于size值
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNotNullAndSmallerSize(Object[] value, int size) {
		boolean bl = false;
		if (null != value && size > value.length) {
			bl = true;
		}
		return bl;
	}

	/**
	 * 判断对象集合（List,Set）是否为空并且数量小于size值
	 * 
	 * @param value
	 * @return
	 */
	public static Boolean isNotNullAndSmallerSize(Collection<?> value, int size) {
		boolean bl = false;
		if (null != value && size > value.size()) {
			bl = true;
		}
		return bl;
	}

    /**
     * 判断是否符合邮编格式(不能为空、必须6位纯数字)
     * @param str
     * @return
     */
    public static boolean isCorrectPostCode(String str){
        boolean bl = true;
        if(isSEmptyOrNull(str)||str.matches(REGX_POSTCODE)||str.length()!=6){
            bl = false;
        }
        return bl;
    }

//	public static void main(String[] args) {
//		boolean o = PubUtils.isMobileNumber("13578484458");
//		System.out.println(o);
//	}
}
