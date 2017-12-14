package com.xescm.ofc.provider;

import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.HsfBaseTest;
import com.xescm.ofc.edas.model.dto.dpc.resp.OfcOrderGoodsTempDto;
import com.xescm.ofc.edas.service.OfcOrderInfoEdasService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OfcOrderInfoEdasServiceTest extends HsfBaseTest{

    private OfcOrderInfoEdasService ofcOrderInfoEdasService = super.getConsumer(OfcOrderInfoEdasService.class, null, null, null, null);// 用ID取出对应服务，subscribe()方法返回对应的接口

    @Test
    public void getOrderGoodsTempByOrderCode() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("SO171017000013");
        list.add("SO170926000021");
        Wrapper<List<OfcOrderGoodsTempDto>> listWrapper = ofcOrderInfoEdasService.getOrderGoodsTempByOrderCode(list);
        System.out.println(listWrapper.getResult());
    }
}
