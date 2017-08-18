package com.xescm.ofc.enums;


/**
 * 订单环节
 */
public enum OrderPotEnum {
    /**
     * 入库订单下单时间至入库完成
     */
    STORAGE_IN("入库", "storageIn"),
    /**
     * 出库订单下单时间至出库完成
     */
    STORAGE_OUT("出库", "storageOut"),
    /**
     * 当订单的业务类型为城配和干线时, 环节定义为下单至首次确认调度完成
     * 当业务类型为卡班(含二次配送时), 环节定义为下单至二次配送首次确认调度完成
     * 当业务类型为卡班(不含二次配送时), 环节定义为下单至最后一段卡班发运完成(最后一段卡班: 选择的卡班线路终点站TC仓覆盖订单的目的地)
     */
    DELIVERY("调度", "delivery"),
    /**
     * 当订单的业务类型为城配和干线时, 环节定义为下单至首次确认调度完成至发运完成
     * 当订单的业务类型为卡班(含二次配送时), 环节定义为最后一段卡班发运完成至到达完成
     */
    DISPATCH("发运", "dispatch"),
    /**
     * 当订单的业务类型为卡班(不含二次配送时), 环节定义为最后一段卡班发运完成至到达完成
     */
    ARRIVED("到达", "arrived"),
    /**
     * 当订单的业务类型为城配和干线时, 环节定义为发运完成至签收完成
     * 当订单的业务类型为卡班(含二次配送)时,环节定义为二次配送发运完成至签收完成
     * 当订单的业务类型为卡班(不含二次配送)时, 环节定义为到达完成至签收完成
     */
    SIGNED("签收", "signed"),
    /**
     * 当订单的业务类型为城配和干线时, 环节定义为签收完成至回单完成
     * 当订单的业务类型为卡班(含二次配送时), 环节定义为签收完成至回单完成
     * 当订单的业务类型为卡班(不含二次配送)时, 环节定义为签收完成至回单完成
     */
    RECEIPT("回单", "receipt");

    private String potDesc;
    private String potCode;

    public String getPotDesc() {
        return potDesc;
    }

    public void setPotDesc(String potDesc) {
        this.potDesc = potDesc;
    }

    public String getPotCode() {
        return potCode;
    }

    public void setPotCode(String potCode) {
        this.potCode = potCode;
    }

    OrderPotEnum(String potDesc, String potCode) {
        this.potCode = potCode;
        this.potDesc = potDesc;
    }

}
