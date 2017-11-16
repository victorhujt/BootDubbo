package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcOrderNewstatus;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.edas.model.dto.whc.FeedBackInventoryDto;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.service.OfcOrderNewstatusService;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderConstConstant.*;

@Service
@Transactional
public class OfcOrderNewstatusServiceImpl extends BaseService<OfcOrderNewstatus> implements OfcOrderNewstatusService {
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
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
        ofcFundamentalInformation.setIsException(ISEXCEPTION);
        ofcFundamentalInformation.setExceptionReason(feedBackInventoryDto.getReason());
        ofcFundamentalInformation.setOrderCode(orderCode);
        ofcFundamentalInformationService.update(ofcFundamentalInformation);

        //将订单状态置为待审核
        OfcOrderStatus ofcOrderStatus=new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        ofcOrderStatus.setOrderStatus(PENDING_AUDIT);
        ofcOrderStatus.setStatusDesc("待审核");
        ofcOrderStatus.setLastedOperTime(new Date());
        ofcOrderStatus.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1) + " " + feedBackInventoryDto.getReason());
        ofcOrderStatusService.save(ofcOrderStatus);
    }

    @Override
    public int update(OfcOrderNewstatus orderNewstatus) {
        if(null != orderNewstatus && !"".equals(PubUtils.trimAndNullAsEmpty(orderNewstatus.getOrderCode()))){
            OfcOrderNewstatus orderNewstatu = selectByKey(orderNewstatus.getOrderCode());
            String status = trimAndNullAsEmpty(orderNewstatu.getOrderLatestStatus());
             if (!(HASBEEN_CANCELED.equals(status) || HASBEEN_COMPLETED.equals(status))) {
                 return super.update(orderNewstatus);
             }
        }
        return 0;

    }
}
