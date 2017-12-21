package com.xescm.ofc.scheduler;

import com.alibaba.edas.schedulerX.ProcessResult;
import com.alibaba.edas.schedulerX.ScxSimpleJobContext;
import com.alibaba.edas.schedulerX.ScxSimpleJobProcessor;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.domain.OfcInterfaceReceiveLog;
import com.xescm.ofc.edas.enums.LogBusinessTypeEnum;
import com.xescm.ofc.edas.enums.LogInterfaceTypeEnum;
import com.xescm.ofc.edas.enums.LogSourceSysEnum;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.service.OfcInterfaceReceiveLogService;
import com.xescm.ofc.service.SchedulerOfcService;
import com.xescm.ofc.service.impl.OfcInterfaceReceiveLogServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestProcessor implements ScxSimpleJobProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private SchedulerOfcService schedulerOfcServiceImpl;

    @Override
    public ProcessResult process(ScxSimpleJobContext scxSimpleJobContext) {
        try {
            logger.info("------------------------------------>>> 开始插入测试用例数据");
            String messageBody = "{\"orderCode\":\"SO171204000001\",\"feedBackOrderDetail\":[{\"orderCode\":\"SO171204000001\",\"goodsCode\":\"XXBBNMD12\",\"goodsName\":\"小龙虾\",\"goodsSpec\":null,\"unit\":\"EA\",\"quantity\":1,\"realQuantity\":1,\"unitPrice\":null,\"productionBatch\":null,\"productionTime\":null,\"invalidTime\":null,\"weight\":null,\"cubage\":null,\"totalBox\":null,\"paasLineNo\":1}]}";
            FeedBackOrderDto feedBackOrderDto = JacksonUtil.parseJson(messageBody, FeedBackOrderDto.class);
            String orderCode = feedBackOrderDto.getOrderCode();
            OfcInterfaceReceiveLog receiveLog = new OfcInterfaceReceiveLog();
            receiveLog.setLogBusinessType(LogBusinessTypeEnum.EDI_WHC_GOODS_REAL_AMOUNT.getCode());
            receiveLog.setLogFromSys(LogSourceSysEnum.WHC.getCode());
            receiveLog.setRefNo(orderCode);
            receiveLog.setLogType(LogInterfaceTypeEnum.MQ.getCode());
            receiveLog.setLogData(JacksonUtil.toJson(feedBackOrderDto));
            schedulerOfcServiceImpl.insertOfcInterfaceReceiveLogWithTask(receiveLog);
            logger.info("------------------------------------>>> 结束插入测试用例数据");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ProcessResult(true);
    }
}
