package com.xescm.epc.utils;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Json工具类，使用jackson包处理
 * @author zjs
 * @date 2014年5月14日 下午5:16:36
 */
public class JsonUtil {
	private static ObjectMapper mapper;
	private static Logger logger = Logger.getLogger(JsonUtil.class);
	
	public static final String SERVER_ERROR_MESSAGE = "网络异常，请稍后再试。";
	/**
	 * 成功状态
	 */
	public static final int STATUS_SUCCESS = 1;
	/**
	 * 业务异常状态
	 */
	public static final int STATUS_ERR_BUSINESS = 2;
	/**
	 * 服务器异常或其他异常状态
	 */
	public static final int STATUS_ERR_SERVER = 3;
	
	/**
	 * 静态代码块，初始化mapper
	 */
	static{
		mapper = new ObjectMapper();// 设置输出包含的属性
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		//允许转义
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

	}

	/**
	 * 将对象转化为json
	 * 
	 * @param o
	 * @return
	 */
	public static String object2Json(Object o) throws Exception{
		String jsonValue = null;
		try {

			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, o);
			jsonValue = strWriter.toString();

		} catch (Exception e) {
			logger.error("对象转换为json时出错", e);
			throw e;
		}

		return jsonValue;
	}
	/**
	 * 将对象转化为json
	 * 
	 * @param
	 * @return
	 */
	public static String stringObject2Json(Object o) throws Exception{
		String jsonValue = null;
		try {
			Writer strWriter = new StringWriter();
			mapper.writeValue(strWriter, o);
			jsonValue = strWriter.toString().replace("\\", "");

		} catch (Exception e) {
			logger.error("对象转换为json时出错", e);
			throw e;
		}

		return jsonValue;
	}

	
	/**
	 * 
	 * @param jsonValue
	 *            json字符串
	 * @param classValue
	 *            object.class
	 * @return
	 */
	public static Object json2Object(String jsonValue, Class<?> classValue) throws Exception{
		Object o = null;
		try {
			if (jsonValue != null) {
				o = mapper.readValue(jsonValue, classValue);
			}

		} catch (Exception e) {
			logger.error("json转换为对象时出错", e);
			throw e;
		}
		return o;
	}
	/**
	 * 复杂类型的json转对象
	 * @param jsonValue
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static <T> Object json2Object(String jsonValue, TypeReference<T> type) throws Exception{
		Object o = null;
		try {
			if (jsonValue != null) {
				o = mapper.readValue(jsonValue, type);
			}

		} catch (Exception e) {
			logger.error("json转换为对象时出错", e);
			throw e;
		}
		return o;
	}
	/**
	 * 将字符串转化成List<?>数组
	 * 
	 * @param jsonValue
	 *            json字符串
	 * @return
	 */
	public static List<?> json2List(String jsonValue) throws Exception{
		List<?> resultArr = new ArrayList<Object>();
		TypeReference<List<Object>> typeRef = null;
		try {
			if (jsonValue != null) {

				typeRef = new TypeReference<List<Object>>() {};
				resultArr = mapper.readValue(jsonValue, typeRef);

			}
		} catch (Exception e) {
			logger.error("json转换为list时出错", e);
			throw e;
		}
		return resultArr;
	}

	/**
	 * 适用嵌套类型
	 */
	public static List<?> json2List(String jsonValue, @SuppressWarnings("rawtypes") TypeReference typeRef) throws Exception{
		List<?> resultArr = new ArrayList<Object>();
		try {
			if (jsonValue != null) {
				resultArr = mapper.readValue(jsonValue, typeRef);
			}
		} catch (Exception e) {
			logger.error("json换为list时出错", e);
			throw e;
		}
		return resultArr;
	}
	/**
	 * 将list<Object>转化成json字符串
	 * 
	 * @param objList
	 * @return
	 */
	public static String list2Json(List<?> objList) throws Exception{
		String resultJson = null;
		try {

			if (objList.isEmpty()) {
				return null;
			} else {
				resultJson = mapper.writeValueAsString(objList);
			}

		} catch (Exception e) {
			logger.error("list转换为json时出错", e);
			throw e;
		}
		return resultJson;
	}
//	public static void  main(String[] args) throws Exception {
//	}
		//将json字符转转换成实体对象
		public static Object jsonStr2Bean(String jsonStr, Class<?> clazz) {
			Object obj;

			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			obj = JSONObject.toBean(jsonObject, clazz);

			return obj;
		}
		//将json字符串转换成list
		public static List jsonStr2List(String jsonStr, Class clazz) {
			List result = new ArrayList();

			JSONArray jsonArray = JSONArray.fromObject(jsonStr);
			JSONObject jsonObj;
			Object pojoVal;

			for (int i = 0; i < jsonArray.size(); i++) {
				jsonObj = jsonArray.getJSONObject(i);
				pojoVal = JSONObject.toBean(jsonObj, clazz);
				result.add(pojoVal);
			}

			return result;
		}

	}
