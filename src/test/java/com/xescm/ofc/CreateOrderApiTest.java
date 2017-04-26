package com.xescm.ofc;

import com.xescm.ofc.edas.model.dto.dpc.req.TranPlanOfcReqDto;
import com.xescm.ofc.edas.service.OfcMobileOrderEdasService;
import com.xescm.ofc.edas.service.OfcTranPlanDpcEdasServie;
import com.xescm.ofc.service.CreateOrderService;
import com.xescm.ofc.service.GoodsAmountSyncService;
import com.xescm.ofc.service.OfcPlanFedBackService;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountDetailDto;
import com.xescm.tfc.edas.model.dto.ofc.req.GoodsAmountSyncDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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


    @Resource
    GoodsAmountSyncService goodsAmountSyncService;


   /* @Test
    public void sdf(){
        GoodsAmountSyncDto goodsAmountSyncDto = new GoodsAmountSyncDto();
        goodsAmountSyncDto.setCustCode("BHHYSCSPYX");
        goodsAmountSyncDto.setCustName("胡金涛测试的公司");
        goodsAmountSyncDto.setCustOrderCode("5213123213");
        List<GoodsAmountDetailDto> list = new ArrayList<>();
        GoodsAmountDetailDto goodsAmountDetailDto = new GoodsAmountDetailDto();
        goodsAmountDetailDto.setGoodsCode("123");
        goodsAmountDetailDto.setGoodsName("123");
        goodsAmountDetailDto.setQty("888");
        list.add(goodsAmountDetailDto);
        goodsAmountDetailDto = new GoodsAmountDetailDto();
        goodsAmountDetailDto.setGoodsCode("123");
        goodsAmountDetailDto.setGoodsName("421321");
        goodsAmountDetailDto.setWeight("789");
        list.add(goodsAmountDetailDto);
        goodsAmountDetailDto = new GoodsAmountDetailDto();
        goodsAmountDetailDto.setGoodsCode("4213");
        goodsAmountDetailDto.setGoodsName("3123");
        goodsAmountDetailDto.setVolume("285");
        list.add(goodsAmountDetailDto);
        goodsAmountSyncDto.setGoodsAmountDetailDtoList(list);
        goodsAmountSyncService.GoodsAmountSync(goodsAmountSyncDto);
        System.out.println("111");
    }*/
}
