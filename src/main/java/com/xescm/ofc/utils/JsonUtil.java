package com.xescm.ofc.utils;


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

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;

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

    /*public static void main(String[] args) throws Exception {
        String json = "[{\n" +
                "        \"custOrderCode\": \"D161115107629044\",\n" +
                "        \"orderTime\": \"2016-11-15.0.8. 25. 250000000\",\n" +
                "        \"custCode\": null,\n" +
                "        \"custName\": null,\n" +
                "        \"orderType\": \"60\",\n" +
                "        \"businessType\": \"600\",\n" +
                "        \"notes\": \"订单备注\",\n" +
                "        \"storeCode\": null,\n" +
                "        \"orderSource\": \"EDI\",\n" +
                "        \"expandSaleOrg\": null,\n" +
                "        \"expandProGroup\": null,\n" +
                "        \"expandSaleDep\": null,\n" +
                "        \"expandSaleGroup\": null,\n" +
                "        \"expandSaleDepDes\": null,\n" +
                "        \"expandSaleGroupDes\": null,\n" +
                "        \"quantity\": \"1111\",\n" +
                "        \"weight\": \"9212.0\",\n" +
                "        \"cubage\": null,\n" +
                "        \"totalStandardBox\": null,\n" +
                "        \"transRequire\": null,\n" +
                "        \"pickupTime\": null,\n" +
                "        \"expectedArrivedTime\": null,\n" +
                "        \"consignorName\": \"鲜易网\",\n" +
                "        \"consignorContact\": \"鲜易网\",\n" +
                "        \"consignorPhone\": \"400-662-6366\",\n" +
                "        \"consignorFax\": null,\n" +
                "        \"consignorEmail\": null,\n" +
                "        \"consignorZip\": null,\n" +
                "        \"consignorProvince\": \"河南\",\n" +
                "        \"consignorCity\": \"郑州\",\n" +
                "        \"consignorCounty\": \"郑州新区\",\n" +
                "        \"consignorTown\": null,\n" +
                "        \"consignorAddress\": \"东风南路七里河路交叉口绿地之窗云峰座\",\n" +
                "        \"consigneeName\": \"李歌\",\n" +
                "        \"consigneeContact\": \"李歌\",\n" +
                "        \"consigneePhone\": \"18637711063\",\n" +
                "        \"consigneeFax\": null,\n" +
                "        \"consigneeEmail\": null,\n" +
                "        \"consigneeZip\": null,\n" +
                "        \"consigneeProvince\": \"河南\",\n" +
                "        \"consigneeCity\": \"南阳市\",\n" +
                "        \"consigneeCounty\": \"宛城区\",\n" +
                "        \"consigneeTown\": null,\n" +
                "        \"consigneeAddress\": \"府衙小吃街\",\n" +
                "        \"warehouseCode\": null,\n" +
                "        \"warehouseName\": null,\n" +
                "        \"provideTransport\": null,\n" +
                "        \"supportName\": null,\n" +
                "        \"supportContact\": null,\n" +
                "        \"supportPhone\": null,\n" +
                "        \"supportFax\": null,\n" +
                "        \"supportEmail\": null,\n" +
                "        \"supportZip\": null,\n" +
                "        \"supportProvince\": null,\n" +
                "        \"supportCity\": null,\n" +
                "        \"supportCounty\": null,\n" +
                "        \"supportTown\": null,\n" +
                "        \"supportAddress\": null,\n" +
                "        \"arriveTime\": null,\n" +
                "        \"plateNumber\": null,\n" +
                "        \"driverName\": null,\n" +
                "        \"contactNumber\": null,\n" +
                "        \"serviceCharge\": null,\n" +
                "        \"orderAmount\": \"1932\",\n" +
                "        \"paymentAmount\": \"1932\",\n" +
                "        \"collectLoanAmount\": \"0\",\n" +
                "        \"collectServiceCharge\": null,\n" +
                "        \"collectFlag\": null,\n" +
                "        \"printInvoice\": null,\n" +
                "        \"buyerPaymentMethod\": \"6830\",\n" +
                "        \"insure\": null,\n" +
                "        \"insureValue\": null,\n" +
                "        \"createOrderGoodsInfos\": [\n" +
                "            {\n" +
                "                \"goodsCode\": null,\n" +
                "                \"goodsName\": \"鱿鱼切半40克 4.35kg/箱\",\n" +
                "                \"goodsSpec\": \"4.35kg/箱\",\n" +
                "                \"unit\": \"箱\",\n" +
                "                \"quantity\": \"21\",\n" +
                "                \"unitPrice\": \"92\",\n" +
                "                \"productionBatch\": null,\n" +
                "                \"productionTime\": null,\n" +
                "                \"invalidTime\": null\n" +
                "            }\n" +
                "        ],\n" +
                "        \"baseId\": \"40551\"\n" +
                "    }]";

        List<CreateOrderEntity> list = (List<CreateOrderEntity>) JsonUtil.json2List(json, new TypeReference<List<CreateOrderEntity>>() {});
        System.out.println(ToStringBuilder.reflectionToString(list.get(0)));
        CreateOrderEntity createOrderEntity = list.get(0);
        createOrderEntity.setOrderTime("2016-1-18 11:09:09");
        String orderCode = "SO123456";
        CreateOrderTrans createOrderTrans = new CreateOrderTrans(createOrderEntity, orderCode);
        OfcFundamentalInformation ofcFundamentalInformation = createOrderTrans.getOfcFundamentalInformation();
        OfcDistributionBasicInfo ofcDistributionBasicInfo = createOrderTrans.getOfcDistributionBasicInfo();
        OfcFinanceInformation ofcFinanceInformation = createOrderTrans.getOfcFinanceInformation();
        OfcWarehouseInformation ofcWarehouseInformation = createOrderTrans.getOfcWarehouseInformation();
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = createOrderTrans.getOfcGoodsDetailsInfoList();
        System.out.println(ToStringBuilder.reflectionToString(ofcFundamentalInformation));
        System.out.println(ToStringBuilder.reflectionToString(ofcDistributionBasicInfo));
        System.out.println(ToStringBuilder.reflectionToString(ofcFinanceInformation));
        System.out.println(ToStringBuilder.reflectionToString(ofcWarehouseInformation));
        System.out.println(ToStringBuilder.reflectionToString(ofcGoodsDetailsInfoList));
    }*/

}
