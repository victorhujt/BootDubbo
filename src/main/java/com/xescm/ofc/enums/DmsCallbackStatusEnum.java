package com.xescm.ofc.enums;

/**
 *
 * Created by lyh on 2016/12/9.
 */
public enum DmsCallbackStatusEnum {
    /**
     分拣中心回传的卡班单状态
     10=收货、（DMS【到站卸车】线路为『提货入库』）
     20=发运、（DMS无）
     30=中转入、（DMS【到站卸车】）
     40=中转出、（DMS【装车发运】）
     50=在途、（GPS用）
     60=签收、（DMS【自提签收】签收）
     70=回单、（DMS无）
     99=异常、（DMS【上报异常】保存）
     */
    DMS_STATUS_DELIVERY("10","收货"),
    DMS_STATUS_DISPATCH("20", "发运"),
    DMS_STATUS_TRANSFER_IN("30","中转入"),
    DMS_STATUS_TRANSFER_OUT("40","中转出"),
    DMS_STATUS_TRANSIT("50","在途"),
    DMS_STATUS_SIGNED("60","签收"),
    DMS_STATUS_RECEIPT("70","回单"),
    DMS_STATUS_EXCEPTION("99","异常");
    DmsCallbackStatusEnum(String code,String description){
        this.code = code;
        this.description = description;
    };
    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
