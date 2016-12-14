package com.xescm.ofc.model.vo.csc;

/**
 * Created by zhangfutao on 2016/11/17.
 */
public class CscGoodsTypeVo {
    /**
     * 主键
     */
    private String id;

    /**
     * 货品类别
     */
    private String goodsTypeName;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 货品类型编码
     */
    private String code;

    /**
     * 关联id
     */
    private String pid;

    /**
     * 流水号
     */
    private String serialNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getGoodsTypeName() {
        return goodsTypeName;
    }

    public void setGoodsTypeName(String goodsTypeName) {
        this.goodsTypeName = goodsTypeName;
    }
}
