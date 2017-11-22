package com.xescm.ofc.service.impl;

import com.xescm.core.utils.PublicUtil;
import com.xescm.ofc.domain.OfcCustOrderNewstatus;
import com.xescm.ofc.domain.OfcCustOrderStatus;
import com.xescm.ofc.domain.OfcOrderNewstatus;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcCustOrderNewstatusMapper;
import com.xescm.ofc.mapper.OfcCustOrderStatusMapper;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import com.xescm.ofc.service.OfcCustOrderNewstatusService;
import com.xescm.ofc.service.OfcCustOrderStatusService;
import com.xescm.ofc.utils.BeanConvertor;
import com.xescm.ofc.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @Resource
    private OfcOrderStatusMapper ofcOrderStatusMapper;
    @Resource
    private OfcCustOrderStatusMapper ofcCustOrderStatusMapper;

    @Override
    public Integer saveOrderStatus(OfcCustOrderStatus ofcOrderStatus) {
        if (ofcOrderStatus != null && !trimAndNullAsEmpty(ofcOrderStatus.getOrderCode()).equals("")) {
            if (!trimAndNullAsEmpty(ofcOrderStatus.getOrderStatus()).equals("")) {
                OfcOrderNewstatus orderNewstatus = ofcCustOrderNewstatusService.selectByKey(ofcOrderStatus.getOrderCode());
                String tag = "noStatus";
                if (orderNewstatus != null) {
                    tag =  "haveStatus";
                } else {
                    orderNewstatus = new OfcOrderNewstatus();
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

    @Override
    @SuppressWarnings("unchecked")
    public List<OfcCustOrderStatus> queryByOrderCode(String orderCode) {
        logger.info("queryByOrderCode ==> orderCode{}", orderCode);
        if (PublicUtil.isEmpty(orderCode)) {
            logger.error("查询List<OfcCustOrderStatus>失败, 入参为空");
            throw new BusinessException("查询订单状态列表失败!");
        }
        List<OfcCustOrderStatus> result = new ArrayList<>();
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        ofcOrderStatus.setOrderCode(orderCode);
        List<OfcOrderStatus> orderStatuses = ofcOrderStatusMapper.queryOrderStatus(orderCode, "orderCode");
        if (CollectionUtils.isNotEmpty(orderStatuses)) {
            return BeanConvertor.listConvertor(orderStatuses, result, OfcCustOrderStatus.class);
        }
        return ofcCustOrderStatusMapper.queryByOrderCode(orderCode);
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
