package com.xescm.ofc.service.impl;


import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcMobileOrder;
import com.xescm.ofc.mapper.ofcMobileOrderMapper;
import com.xescm.ofc.service.OfcMobileOrderService;
import com.xescm.ofc.utils.CodeGenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hujintao on 2016/12/12.
 */
@Service
@Transactional
public class OfcMobileOrderServiceImpl extends BaseService<OfcMobileOrder>  implements OfcMobileOrderService {

    private static final Logger logger = LoggerFactory.getLogger(OfcOrderManageServiceImpl.class);
    @Autowired
    private ofcMobileOrderMapper ofcMobileOrderMapper;
    @Resource
    private CodeGenUtils codeGenUtils;
    @Override
    public OfcMobileOrder saveOfcMobileOrder(OfcMobileOrder ofcMobileOrder) {
        ofcMobileOrder.setMobileOrderCode(codeGenUtils.getNewWaterCode("SN",6));
        ofcMobileOrder.setBuniessType(OrderConstConstant.TRANSPORTORDER);
        save(ofcMobileOrder);
        return ofcMobileOrder;
    }

    @Override
    public List<OfcMobileOrder> queryOrderNotes(String mobileOrderStatus) {
        return ofcMobileOrderMapper.queryOrderNotes(mobileOrderStatus);
    }
}
