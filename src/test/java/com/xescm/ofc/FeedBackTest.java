package com.xescm.ofc;

import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDetailDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.service.OfcOrderStatusService;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by victor on 2017/11/3.
 */
@Component
public class FeedBackTest {
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;

    private ConcurrentHashMap MAP = new ConcurrentHashMap();
    @Test
    public void testrFeedBack() {
        FeedBackOrderDto feedBackOrderDto = new FeedBackOrderDto();
        List<FeedBackOrderDetailDto> feedBackOrderDetail = new ArrayList<>();
        FeedBackOrderDetailDto  feedBackOrderDetailDto = new FeedBackOrderDetailDto();
        feedBackOrderDetailDto.setUnit("箱包装描述");
        feedBackOrderDetailDto.setGoodsCode("CA1128024");
        feedBackOrderDetailDto.setQuantity(new BigDecimal(10));
        feedBackOrderDetailDto.setRealQuantity(new BigDecimal(200));
        feedBackOrderDetail.add(feedBackOrderDetailDto);
        feedBackOrderDto.setOrderCode("SO171103000012");
        feedBackOrderDto.setFeedBackOrderDetail(feedBackOrderDetail);
        ofcOrderStatusService.ofcWarehouseFeedBackFromWhc(feedBackOrderDto);

    }
}
