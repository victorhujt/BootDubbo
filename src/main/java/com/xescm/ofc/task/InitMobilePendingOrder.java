package com.xescm.ofc.task;

import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.service.OfcMobileOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.MOBILE_PENDING_ORDER_LIST;
import static com.xescm.ofc.constant.OrderConstConstant.UN_TREATED;

/**
 * @description: ${TODO}
 * @author: nothing
 * @date: 2017/3/7 14:47
 */
@Component
public class InitMobilePendingOrder implements CommandLineRunner {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OfcMobileOrderService ofcMobileOrderService;

    @Override
    public void run(String... strings) throws Exception {
        logger.info("==>手机订单号同步到redis缓存开始执行");
        MobileOrderOperForm params = new MobileOrderOperForm();
        params.setMobileOrderStatus(UN_TREATED);
        List<OfcMobileOrder> mobileOrders = ofcMobileOrderService.queryOrderList(params);
        if(!CollectionUtils.isEmpty(mobileOrders)){
            logger.info("==>查询到未受理的订单总数为{}",mobileOrders.size());
            for (int i = 0 ; i < mobileOrders.size(); i++) {
                OfcMobileOrder order = mobileOrders.get(i);
                ofcMobileOrderService.pushOrderToCache(MOBILE_PENDING_ORDER_LIST,order.getMobileOrderCode());
            }
        }else{
            logger.info("==>未查询到未受理的订单");
        }



    }
}
