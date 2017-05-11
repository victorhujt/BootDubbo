package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcDailyAccount;
import com.xescm.ofc.domain.OrderCountResult;
import com.xescm.ofc.model.dto.form.OrderCountForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Repository
public interface OfcDailyAccountMapper extends Mapper<OfcDailyAccount> {

    /**
     * 统计前一天两小时内完成的订单
     * @param form
     * @return
     */
    List<OrderCountResult> countTwoHoursOrder(@Param("form") OrderCountForm form);

    /**
     * 统计前一天的订单数量
     * @param form
     * @return
     */
    List<OrderCountResult> yesterdayOrderCount(@Param("form") OrderCountForm form);

    List<OfcDailyAccount> queryDailyAccount(@Param("dailydate") String dailydate);

}