package com.xescm.ofc.scheduler;

import com.alibaba.edas.schedulerX.ProcessResult;
import com.alibaba.edas.schedulerX.ScxSimpleJobContext;
import com.alibaba.edas.schedulerX.ScxSimpleJobProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hujintao on 2018/1/15.
 */
public class CancelOrderPushTfcProcessor  implements ScxSimpleJobProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public ProcessResult process(ScxSimpleJobContext scxSimpleJobContext) {
        logger.info("仓配订单取消状态不一致的问题订单推送到TFC ========> CancelOrderPushTfcProcessor");
        return new ProcessResult(true);
    }
}
