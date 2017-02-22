package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcOrderNewstatus;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.edas.model.dto.whc.FeedBackInventoryDto;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.service.OfcOrderNewstatusService;
import com.xescm.ofc.service.OfcOrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.xescm.ofc.constant.OrderConstConstant.ISEXCEPTION;

@Service
@Transactional
public class OfcOrderNewstatusServiceImpl extends BaseService<OfcOrderNewstatus> implements OfcOrderNewstatusService {
    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    /**
     * 仓储中心反馈仓储订单的异常原因
     * @param feedBackInventoryDto
     */
    @Override
    public void FeedBackInventory(FeedBackInventoryDto feedBackInventoryDto) {
        String orderCode=feedBackInventoryDto.getOrderCode();
        OfcFundamentalInformation ofcFundamentalInformation=ofcFundamentalInformationService.selectByKey(orderCode);
        if(ofcFundamentalInformation==null){
            throw new BusinessException("订单不存在");
        }
        OfcOrderStatus status=new OfcOrderStatus();
        status.setOrderStatus(ISEXCEPTION);//异常
        status.setNotes(feedBackInventoryDto.getReason());
        status.setOrderCode(orderCode);
        ofcOrderStatusService.save(status);
    }
}
