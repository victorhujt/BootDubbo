package com.xescm.ofc;

import com.taobao.hsf.lightapi.ServiceFactory;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.edas.model.dto.ofc.OfcTraceOrderDTO;
import com.xescm.ofc.edas.service.OfcOrderStatusEdasService;
import org.junit.Test;

/**
 * Created by victor on 2017/6/6.
 */
public class QueryTraceOrderEdasServiceTest extends HsfBaseTest{
    private static ServiceFactory factory = ServiceFactory.getInstanceWithPath("H:\\ofcTomcat\\taobao-tomcat-7.0.59\\deploy\\");

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




}
