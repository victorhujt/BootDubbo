package com.xescm.ofc;

import com.xescm.ofc.domain.OfcPlanFedBackCondition;
import com.xescm.ofc.edas.model.dto.mobile.OfcMobileOrderDto;
import com.xescm.ofc.edas.service.OfcMobileOrderEdasService;
import com.xescm.ofc.service.OfcPlanFedBackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

/**
 * Created by hiyond on 2016/11/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = XescmOfcApplication.class)
@WebAppConfiguration
public class CreateOrderApiTest {

    {
        HsfXml.loadConsumer();
    }

    @Autowired
    private OfcPlanFedBackService ofcPlanFedBackService;

    @Autowired
    private OfcMobileOrderEdasService ofcMobileOrderEdasService;


    @Test
    public void sdf(){
        OfcPlanFedBackCondition ofcPlanFedBackCondition = new OfcPlanFedBackCondition();
        ofcPlanFedBackCondition.setTransportNo("TP161229000032");
        ofcPlanFedBackCondition.setStatus("已回单");
        ofcPlanFedBackCondition.setTraceTime(new Date());
        ofcPlanFedBackService.planFedBack(ofcPlanFedBackCondition,"123");
    }

    @Test
    public void checkTranCode(){
        OfcMobileOrderDto order=new OfcMobileOrderDto();
        order.setDingdingAccountNo("014007054332891531");
        order.setTranCode("6901721496230826");
        order.setBusinessType("600");
        ofcMobileOrderEdasService.saveMobileOrder(order);
    }
}
