package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcCustOrderNewstatus;
import com.xescm.ofc.domain.OfcCustOrderStatus;
import com.xescm.ofc.domain.OfcOrderNewstatus;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcCustOrderNewstatusMapper;
import com.xescm.ofc.service.OfcCustOrderNewstatusService;
import com.xescm.ofc.service.OfcCustOrderStatusService;
import com.xescm.ofc.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderConstConstant.HASBEEN_CANCELED;
import static com.xescm.ofc.constant.OrderConstConstant.HASBEEN_COMPLETED;

/**
 *
 * Created by lyh on 2017/9/14.
 */
@Service
public class OfcCustOrderStatusServiceImpl extends BaseService<OfcCustOrderStatus> implements OfcCustOrderStatusService{


    @Resource
    private OfcCustOrderNewstatusService ofcCustOrderNewstatusService;
    @Resource
    private OfcCustOrderNewstatusMapper custOrderNewstatusMapper;

    @Override
    public Integer saveOrderStatus(OfcCustOrderStatus ofcOrderStatus) {
        if (ofcOrderStatus != null && !trimAndNullAsEmpty(ofcOrderStatus.getOrderCode()).equals("")) {
            if (!trimAndNullAsEmpty(ofcOrderStatus.getOrderStatus()).equals("")) {
                OfcOrderNewstatus orderNewstatus = ofcCustOrderNewstatusService.selectByKey(ofcOrderStatus.getOrderCode());
                String tag = "noStatus";
                if (orderNewstatus != null) {
                    tag =  "haveStatus";
                } else {
                    orderNewstatus=new OfcOrderNewstatus();
                }
                if (!trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals(HASBEEN_CANCELED))
                    if (!trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals(HASBEEN_COMPLETED)) {
                        this.updateOrderNewStatus(ofcOrderStatus, tag);
                    }
                ofcOrderStatus.setId(UUID.randomUUID().toString().replace("-", ""));
                ofcOrderStatus.setCreationTime(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
                return super.save(ofcOrderStatus);
            } else {
                throw new BusinessException("订单状态为空，保存订单状态失败");
            }
        }
        return 0;
    }

    private void updateOrderNewStatus(OfcOrderStatus ofcOrderStatus, String tag) {
        OfcCustOrderNewstatus orderNewstatus = new OfcCustOrderNewstatus();
        orderNewstatus.setOrderCode(ofcOrderStatus.getOrderCode());
        orderNewstatus.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
        if (tag.equals("haveStatus")) {
            orderNewstatus.setStatusUpdateTime(new Date());
            custOrderNewstatusMapper.updateByPrimaryKey(orderNewstatus);
        } else if (tag.equals("noStatus")) {
            orderNewstatus.setStatusUpdateTime(new Date());
            orderNewstatus.setStatusCreateTime(new Date());
            custOrderNewstatusMapper.insert(orderNewstatus);
        }
    }
}
