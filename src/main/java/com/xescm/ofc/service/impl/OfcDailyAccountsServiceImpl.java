package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcDailyAccount;
import com.xescm.ofc.domain.OrderCountResult;
import com.xescm.ofc.mapper.OfcDailyAccountMapper;
import com.xescm.ofc.model.dto.form.OrderCountForm;
import com.xescm.ofc.model.vo.ofc.OfcDailyAccountVo;
import com.xescm.ofc.service.OfcDailyAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by hujintao on 2017/3/29.
 */
@Service
@Transactional
public class OfcDailyAccountsServiceImpl  extends BaseService<OfcDailyAccount> implements OfcDailyAccountsService {
    private static final Logger logger = LoggerFactory.getLogger(OfcDailyAccountsServiceImpl.class);
    @Resource
    private OfcDailyAccountMapper ofcDailyAccountMapper;


    /**
     * 统计两小时内完成的订单
     * @param form
     * @return
     */
    @Override
    public List<OrderCountResult> countTwoHoursOrder(OrderCountForm form) {
        return ofcDailyAccountMapper.countTwoHoursOrder(form);
    }

    /**
     * 统计前一天的订单总数
     * @param form
     * @return
     */
    @Override

    public List<OrderCountResult> yesterdayOrderCount(OrderCountForm form) {
        return ofcDailyAccountMapper.yesterdayOrderCount(form);
    }


    @Override
    public List<OfcDailyAccountVo> queryDailyAccount(String date) {
        return ofcDailyAccountMapper.queryDailyAccount(date);
    }
}
