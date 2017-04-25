package com.xescm.ofc;

import com.taobao.hsf.lightapi.ServiceFactory;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderInfoDto;
import com.xescm.ofc.edas.service.OfcOrderInfoEdasService;
import com.xescm.ofc.service.GoodsAmountSyncService;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 订单查询测试类
 * @author: nothing
 * @date: 2017/3/2 19:44
 */
public class OrderInfoEdasServiceTest extends HsfBaseTest {

    @Resource
    GoodsAmountSyncService goodsAmountSyncService;

    // 这里设置Pandora地址，参数是sar包所在目录，如这里我的sar包地址是/Users/Jason/Work/AliSoft/PandoraSar/DevSar/taobao-hsf.sar，则只取前面的地址即可
    private static ServiceFactory factory = ServiceFactory.getInstanceWithPath("//Users//Jim//Software//EDAS//xescm-ofc-edas//deploy//");

    @Test
    public void getOrderInfoByOrderCode() {
        OfcOrderInfoEdasService orderInfoEdasService = super.getConsumer(OfcOrderInfoEdasService.class, null, "1.0", "xescm-ofc-dev",null);
        List<String> params = new ArrayList<>();
//        params.add("SO170303000009");
//        params.add("SO170303000008");
//        params.add("SO170303000007");
//        params.add("SO170303000006");
//        params.add("SO170303000005");
//        params.add("SO170303000004");
//        params.add("SO170303000003");
        params.add("SO170303000002");
        params.add("SO170303000001");
        params.add("SO170302000052");
        params.add("SO170302000051");
        params.add("SO170302000050");
        params.add("SO170302000049");
        params.add("SO170302000048");
        params.add("SO170302000047");
        params.add("SO170302000046");
        params.add("SO170302000045");
        params.add("SO170302000044");
        params.add("SO170302000043");
        params.add("SO170302000042");
        params.add("SO170302000041");
        params.add("SO170302000040");
        params.add("SO170302000039");
        params.add("SO170302000038");
        params.add("SO170302000037");
        params.add("SO170302000036");
        params.add("SO170302000035");
        params.add("SO170302000034");
        params.add("SO170302000033");
        params.add("SO170302000032");
        params.add("SO170302000031");
        params.add("SO170302000030");
        params.add("SO170302000029");
        params.add("SO170302000028");
        params.add("SO170302000027");
        params.add("SO170302000026");
        params.add("SO170302000025");
        params.add("SO170302000024");
        params.add("SO170302000023");
        params.add("SO170302000022");
        params.add("SO170302000021");
        params.add("SO170302000020");
        params.add("SO170302000019");
        params.add("SO170302000018");
        params.add("SO170302000017");
        params.add("SO170302000016");
        params.add("SO170302000015");
        params.add("SO170302000014");
        params.add("SO170302000013");
        params.add("SO170302000012");
        params.add("SO170302000011");
        params.add("SO170302000010");
        params.add("SO170302000009");
        params.add("SO170302000008");
        params.add("SO170302000007");
        params.add("SO170302000006");
        params.add("SO170302000005");
        params.add("SO170302000004");
        params.add("SO170302000003");
        params.add("SO170302000002");
        params.add("SO170302000001");
        params.add("SO170301000058");
        params.add("SO170301000057");
        params.add("SO170301000056");
        params.add("SO170301000055");
        params.add("SO170301000054");
        params.add("SO170301000053");
        params.add("SO170301000052");
        params.add("SO170301000051");
        params.add("SO170301000050");
        params.add("SO170301000049");
        params.add("SO170301000048");
        params.add("SO170301000047");
        params.add("SO170301000046");
        params.add("SO170301000045");
        params.add("SO170301000044");
        params.add("SO170301000043");
        params.add("SO170301000042");
        params.add("SO170301000041");
        params.add("SO170301000040");
        params.add("SO170301000039");
        params.add("SO170301000038");
        params.add("SO170301000037");
        params.add("SO170301000036");
        params.add("SO170301000035");
        params.add("SO170301000034");
        params.add("SO170301000033");
        params.add("SO170301000032");
        params.add("SO170301000031");
        params.add("SO170301000030");
        params.add("SO170301000029");
        params.add("SO170301000028");
        params.add("SO170301000027");
        params.add("SO170301000026");
        params.add("SO170301000025");
        params.add("SO170301000024");
        params.add("SO170301000023");
        params.add("SO170301000022");
        params.add("SO170301000021");
        params.add("SO170301000020");
        params.add("SO170301000019");
        params.add("SO170301000018");
        params.add("SO170301000017");
        params.add("SO170301000016");
        params.add("SO170301000015");
        Wrapper<List<OfcOrderInfoDto>> result = orderInfoEdasService.getOrderInfoByOrderCode(params);
        if (result.getCode() == Wrapper.SUCCESS_CODE) {
            int i = 1;
            for (OfcOrderInfoDto orderInfo : result.getResult()) {
                System.out.println(i + "  =>  " + orderInfo.getOrderCode());
                i++;
            }
        } else {
            System.out.println(result.getMessage());
        }
    }

    @Test
    public void updateGoodsInfoTest(){
        GoodsAmountSyncDto goodsAmountSyncDto = new GoodsAmountSyncDto();
        goodsAmountSyncDto.setCustCode("100002");
        goodsAmountSyncDto.setCustName("鲜易网");
        goodsAmountSyncDto.setCustOrderCode("D170103171129969");
        Wrapper<?> result = goodsAmountSyncService.GoodsAmountSync(null);
        System.out.println(result.getMessage());
    }
}
