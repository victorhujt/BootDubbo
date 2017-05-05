package com.xescm.ofc.task;

import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.model.dto.form.MobileOrderOperForm;
import com.xescm.ofc.service.OfcMobileOrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com.xescm.ofc.constant.OrderConstConstant.MOBILE_PENDING_ORDER_LIST;

/**
 * @description: ${TODO}
 * @author: nothing
 * @date: 2017/3/7 14:47
 */
@Component
public class InitMobilePendingOrder implements CommandLineRunner {

    @Resource
    private OfcMobileOrderService ofcMobileOrderService;

    @Override
    public void run(String... strings) throws Exception {

        MobileOrderOperForm params = new MobileOrderOperForm();
        params.setMobileOrderStatus("0");
        List<OfcMobileOrder> mobileOrders = ofcMobileOrderService.queryOrderList(params);

        for (int i = 0 ; i < mobileOrders.size(); i++) {
            OfcMobileOrder order = mobileOrders.get(i);
            ofcMobileOrderService.pushOrderToCache(MOBILE_PENDING_ORDER_LIST,order.getMobileOrderCode());
        }
    }
}
