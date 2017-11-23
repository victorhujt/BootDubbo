package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OrderScreenCondition;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by lyh on 2016/10/10.
 */

@Repository
public interface OfcOrderScreenMapper extends MyMapper<OrderScreenResult> {
    /**
     * 订单查询结果来自于
     * 订单基本信息表的: 订单编号, 客户订单编号, 订单日期, 订单类型, 业务类型
     * 订单状态表的: 订单状态
     * 配送基本信息表的: 收货方名称
     * 仓配信息表的: 仓库名称
     * 订单基本信息表的:备注
     * 订单基本信息表的: 店铺名称
     */
    /**
     * 订单筛选
     * @return
     */
    List<OrderScreenResult> orderScreen(OrderScreenCondition orderScreenCondition);

    /**
     * 查询订单--运营
     * @param form
     * @return List<OrderScreenResult>
     */
    List<OrderScreenResult> queryOrderOper(@Param(value = "form") OrderOperForm form);


    List<String> searchOverallOrder(@Param(value = "code") String code);

    List<OrderSearchOperResult> queryOrderListForCust(@Param("form")OrderOperForm form, @Param("userId") String userId, @Param("withUser") Boolean withUser);

    List<OrderSearchOperResult> queryOrderListForCustUnion(@Param("form")OrderOperForm form, @Param("userId") String userId, @Param("withUser") Boolean withUser, @Param("userGroupCode") String userGroupCode);

    List<OrderSearchOperResult> queryOrderListForCustPrecise(@Param("form")OrderOperForm form, @Param("userId") String userId, @Param("withUser") Boolean withUser);
}
