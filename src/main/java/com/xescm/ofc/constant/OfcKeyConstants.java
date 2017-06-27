package com.xescm.ofc.constant;

/**
 * redis生成key常量配置
 * Created by hiyond on 2016/12/14.
 */
public final class OfcKeyConstants {

    //结算中心的key以AC开头
    public static final String OFC_KEY = "OFC";

    //消费tfc推送的运输单反馈信息的redis锁
    public static final String OFC_MQ_TFC_PLAN_FED_BACK_LOCK = OFC_KEY + "_PLAN_FED_BACK";

    //消费tfc推送的调度单反馈信息的redis锁
    public static final String OFC_MQ_TFC_SCHEDUL_FED_BACK_LOCK = OFC_KEY + "_SCHEDUL_FED_BACK";

}
