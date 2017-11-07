package com.xescm.ofc.enums;

public enum SerialNoEnum {

    /**
     * 附件
     */
    AD("AD", "附件流水号", 8),

    /**
     * 商品类别
     */

    GOOD_TYPE("GT","商品类别",14),

    /**
     * 客户流水号
     */
    CUSTOMER_CODE("CU", "客户流水号", 6),
    /**
     * 联系人流水号
     */
    CONTACT_CODE("CO", "联系人流水号", 6),
    /**
     * 联系人公司流水号
     */
    CONTACT_COMPANY_CODE("CP", "联系人公司流水号", 6),
    /**
     * 供应商流水号
     */
    SUPPLIER_ID("SU", "供应商流水号", 6),
    /**
     * 供应商联系人流水号
     */
    SUPPLIER_CONTACTS_ID("SC", "供应商联系人流水号", 6),
    /**
     * 店铺流水号
     */
    STORE_ID("ST", "店铺流水号", 6),
    /**
     * 货品流水号
     */
    GOODS_ID("GO", "货品流水号", 6),

    /**
     * 客户认证流水号
     */
    CUST_AUTH_ID("CA", "客户认证流水号", 6),

    /**
     * 客户认证流水号
     */
    PACKAGE_ID("PA", "客户认证流水号", 6);


    String type;
    String name;
    int length;

    SerialNoEnum(String type, String name, int length) {
        this.type = type;
        this.name = name;
        this.length = length;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public static String getName(String type) {
        for (SerialNoEnum ele : SerialNoEnum.values()) {
            if (type.equals(ele.getType()))
                return ele.getName();
        }
        return null;
    }

    public static Integer getLength(String type) {
        for (SerialNoEnum ele : SerialNoEnum.values()) {
            if (type.equals(ele.getType()))
                return ele.getLength();
        }
        return null;
    }

    public static SerialNoEnum getEnum(String type) {
        for (SerialNoEnum ele : SerialNoEnum.values()) {
            if (type.equals(ele.getType()))
                return ele;
        }
        return null;
    }
}
