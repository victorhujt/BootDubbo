package com.xescm.ofc;

import com.taobao.hsf.lightapi.ServiceFactory;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderDetailTypeDto;
import com.xescm.ofc.edas.model.dto.ofc.OfcTraceOrderDTO;
import com.xescm.ofc.edas.service.OfcOrderInfoEdasService;
import com.xescm.ofc.edas.service.OfcOrderStatusEdasService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by victor on 2017/6/6.
 */
public class QueryTraceOrderEdasServiceTest extends HsfBaseTest{
    private static ServiceFactory factory = ServiceFactory.getInstanceWithPath("/Users/Jim/Software/EDAS/xescm-uam-edas/deploy/");

    @Test
    public void testQueryOrderEdas() throws Exception {
        // 进行服务消费
        factory.consumer("ofcOrderStatusEdasService")// 参数是一个标识，初始化后，下次只需调用consumer("helloConsumer")即可直接拿出对应服务
                .service("com.xescm.ofc.edas.service.OfcOrderStatusEdasService")// 接口全类名
                .version("1.0")// 版本号
                .group("xescm-ofc-dev")// 组别
                .subscribe();

        factory.consumer("ofcOrderStatusEdasService").sync();// 同步等待地址推送，最多6秒。
        OfcOrderStatusEdasService ofcOrderStatusEdasService = (OfcOrderStatusEdasService) factory.consumer("ofcOrderStatusEdasService").subscribe();// 用ID取出对应服务，subscribe()方法返回对应的接口

        String code = "3453TERT";
        Wrapper orderCodeList =  ofcOrderStatusEdasService.queryOrderByCode(code);
        System.out.println(JacksonUtil.toJson(orderCodeList));
    }

    @Test
    public void testQueryTraceOrderEdas() throws Exception {
        // 进行服务消费
        factory.consumer("ofcOrderStatusEdasService")// 参数是一个标识，初始化后，下次只需调用consumer("helloConsumer")即可直接拿出对应服务
                .service("com.xescm.ofc.edas.service.OfcOrderStatusEdasService")// 接口全类名
                .version("1.0")// 版本号
                .group("xescm-ofc-dev")// 组别
                .subscribe();

        factory.consumer("ofcOrderStatusEdasService").sync();// 同步等待地址推送，最多6秒。
        OfcOrderStatusEdasService ofcOrderStatusEdasService = (OfcOrderStatusEdasService) factory.consumer("ofcOrderStatusEdasService").subscribe();// 用ID取出对应服务，subscribe()方法返回对应的接口

        String code = "SO170607000009";
        Wrapper<OfcTraceOrderDTO> ofcTraceOrderDTOs =  ofcOrderStatusEdasService.traceByOrderCode(code);
        System.out.println(JacksonUtil.toJson(ofcTraceOrderDTOs));
    }

    @Test
    public void testQueryGoodsTypeEdas() throws Exception {
        // 进行服务消费
        factory.consumer("ofcOrderInfoEdasService")// 参数是一个标识，初始化后，下次只需调用consumer("helloConsumer")即可直接拿出对应服务
            .service("com.xescm.ofc.edas.service.OfcOrderInfoEdasService")// 接口全类名
            .version("1.0")// 版本号
            .group("xescm-ofc-dev")// 组别
            .subscribe();
        factory.consumer("ofcOrderInfoEdasService").sync();
        OfcOrderInfoEdasService ofcOrderInfoEdasService = (OfcOrderInfoEdasService) factory.consumer("ofcOrderInfoEdasService").subscribe();
        List<String> codeList = new ArrayList<>();
        codeList.add("SO170919000011");
        codeList.add("SO170919000012");
        codeList.add("SO170919000055");
        Wrapper<List<OfcOrderDetailTypeDto>> result = ofcOrderInfoEdasService.getOrderGoodsTypeByOrderCode(codeList);
        if (result != null && result.getCode() == 200) {
            int i = 0;
            for (OfcOrderDetailTypeDto ofcGoodsDetailsInfo : result.getResult()) {
                System.out.println("序号: " + i + " " + ofcGoodsDetailsInfo.getOrderCode());
                for (String str : ofcGoodsDetailsInfo.getGoodsTypes()) {
                    System.out.print(" " + str);
                }
                i++;
            }
        }
    }

}
