package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcDailyAccount;
import com.xescm.ofc.domain.OrderCountResult;
import com.xescm.ofc.model.dto.form.OrderCountForm;

import java.util.List;

/**
 * Created by hujintao on 2017/3/29.
 */
public interface OfcDailyAccountsService extends IService<OfcDailyAccount>{

    List<OrderCountResult> countTwoHoursOrder(OrderCountForm form);

    List<OrderCountResult> yesterdayOrderCount(OrderCountForm form);

    List<OfcDailyAccount> queryDailyAccount(String date);




}
