package com.xescm.ofc.service.impl;

import com.xescm.ofc.domain.OfcDailyAccount;
import com.xescm.ofc.domain.OrderCountResult;
import com.xescm.ofc.mapper.OfcDailyAccountMapper;
import com.xescm.ofc.model.dto.form.OrderCountForm;
import com.xescm.ofc.service.OfcDailyAccountsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
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
    public List<OfcDailyAccount> queryDailyAccount(String date) {
        List<OfcDailyAccount> OfcDailyAccountVoList =  ofcDailyAccountMapper.queryDailyAccount(date);
        if(!CollectionUtils.isEmpty(OfcDailyAccountVoList)){
            //按 应收确认日清 + 应付确认日清 - 事后补录订单 排序
            Collections.sort(OfcDailyAccountVoList,new Comparator<OfcDailyAccount>(){
                public int compare(OfcDailyAccount o1, OfcDailyAccount o2) {
                    if(o1.getTotal().doubleValue()<o2.getTotal().doubleValue()){
                        return 1;
                    }else if(o1.getTotal().doubleValue()>o2.getTotal().doubleValue()){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
        }
        return OfcDailyAccountVoList;
    }
}
