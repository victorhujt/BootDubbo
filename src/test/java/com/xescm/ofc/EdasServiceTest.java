package com.xescm.ofc;

import com.taobao.hsf.lightapi.ServiceFactory;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderInfoDto;
import com.xescm.ofc.edas.service.OfcOrderInfoEdasService;
import com.xescm.uam.provider.DistributedLockEdasService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description: 订单查询测试类
 * @author: nothing
 * @date: 2017/3/2 19:44
 */
public class EdasServiceTest extends HsfBaseTest {

    // 这里设置Pandora地址，参数是sar包所在目录，如这里我的sar包地址是/Users/Jason/Work/AliSoft/PandoraSar/DevSar/taobao-hsf.sar，则只取前面的地址即可
    private static ServiceFactory factory = ServiceFactory.getInstanceWithPath("H://ofcTomcat//taobao-tomcat-7.0.59//deploy//");
   // H:\ofcTomcat\taobao-tomcat-7.0.59\deploy\taobao-hsf.sar

    @Test
    public void testLock() throws Exception {
        final DistributedLockEdasService distributedLockEdasService = super.getConsumer(DistributedLockEdasService.class, "com.xescm.uam.provider.DistributedLockEdasService", "1.0", "xescm-uam-dev", null);
        final String key = "OFC:MQ:xeOrderToOfc:1234567890:000000000";

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        Wrapper<Integer> lock = distributedLockEdasService.addLock(key, 1);
                        logger.info(Thread.currentThread().getId() + " => 加锁结果" + JacksonUtil.toJson(lock));
                        if (lock.getCode() == Wrapper.SUCCESS_CODE && lock.getResult().intValue() == 1) {
                            logger.info(Thread.currentThread().getId() + "--------------------------------");
                        } else {
                            logger.info(Thread.currentThread().getId() + " => 加锁失败");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        distributedLockEdasService.clearLock(key);
                    }
                }
            };
            executorService.execute(run);
        }
        executorService.shutdown();
        executorService.awaitTermination(500, TimeUnit.SECONDS);
    }

    @Test
    public void getOrderInfoByOrderCode() throws Exception {
        OfcOrderInfoEdasService ofcOrderInfoEdasService = super.getConsumer(OfcOrderInfoEdasService.class, "com.xescm.ofc.edas.service.OfcOrderInfoEdasService", "1.0", "xescm-ofc-dev", null);
        List<String> orderList = new ArrayList<>();
        orderList.add("SO171103000001");
        orderList.add("SO171102000013");
        orderList.add("SO171102000012");
        Wrapper<List<OfcOrderInfoDto>> res = ofcOrderInfoEdasService.getOrderInfoByOrderCode(orderList);
        System.out.println(JacksonUtil.toJson(res));
    }

//    @Test
//    public void getOrderDetailData() throws Exception {
//
//        // 进行服务消费
//        factory.consumer("ofcOrderInfoEdasService")// 参数是一个标识，初始化后，下次只需调用consumer("helloConsumer")即可直接拿出对应服务
//                .service("com.xescm.ofc.edas.service.OfcOrderInfoEdasService")// 接口全类名
//                .version("1.0")// 版本号
//                .group("xescm-ofc-dev")// 组别
//                .subscribe();
//        factory.consumer("ofcOrderInfoEdasService").sync();
//        OfcOrderInfoEdasService ofcOrderInfoEdasService = (OfcOrderInfoEdasService) factory.consumer("ofcOrderInfoEdasService").subscribe();
//        String orderCode = "SO180109000014";
//        Wrapper<String> res = ofcOrderInfoEdasService.getOrderDetailData(orderCode);
//        System.out.println(JacksonUtil.toJson(res));
//    }
}
