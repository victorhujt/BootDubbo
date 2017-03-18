package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcOrderNewstatus;
import com.xescm.ofc.edas.model.dto.whc.FeedBackInventoryDto;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcOrderNewstatusMapper;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.service.OfcOrderNewstatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderConstConstant.HASBEEN_CANCELED;
import static com.xescm.ofc.constant.OrderConstConstant.HASBEEN_COMPLETED;
import static com.xescm.ofc.constant.OrderConstConstant.ISEXCEPTION;

@Service
@Transactional
public class OfcOrderNewstatusServiceImpl extends BaseService<OfcOrderNewstatus> implements OfcOrderNewstatusService {
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcOrderNewstatusMapper ofcOrderNewstatusMapper;

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
    }

    @Override
    public int update(OfcOrderNewstatus orderNewstatus) {
        if(null!=orderNewstatus && !PubUtils.trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")){
            Map<String, String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode", orderNewstatus.getOrderCode());
            OfcOrderNewstatus orderNewstatu=ofcOrderNewstatusMapper.orderStatusSelectNew(mapperMap);
            if (!trimAndNullAsEmpty(orderNewstatu.getOrderLatestStatus()).equals(HASBEEN_CANCELED))
                if (!trimAndNullAsEmpty(orderNewstatu.getOrderLatestStatus()).equals(HASBEEN_COMPLETED)) {
                    return super.update(orderNewstatus);
                }
        }
        return 0;

    }
}
