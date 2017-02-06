package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcPlanScreenCondition;
import com.xescm.ofc.domain.OfcPlanScreenResult;
import com.xescm.ofc.utils.MyMapper;

import java.util.List;

/**
 *
 * Created by lyh on 2016/10/10.
 */
public interface OfcPlanScreenMapper extends MyMapper<OfcPlanScreenResult> {
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
    List<OfcPlanScreenResult> planScreen(OfcPlanScreenCondition ofcPlanScreenCondition);
}
