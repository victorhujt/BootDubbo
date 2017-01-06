package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.ofc.domain.OrderFollowOperResult;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.uam.model.dto.group.UamGroupDto;
import org.springframework.stereotype.Service;
import com.xescm.ofc.model.dto.form.OrderOperForm;

import java.util.List;
import java.util.Map;

/**
 * 运营-订单管理
 * Created by hiyond on 2016/11/24.
 */

public interface OfcOrderManageOperService {

    List<OrderScreenResult> queryOrderOper(OrderOperForm form);

    /**
     * 查询订单列表
     *
     * @param form
     * @return list
     */
    List<OrderSearchOperResult> queryOrderList(OrderOperForm form);

    /**
     * 根据订单批次号查询订单列表
     *
     * @param orderBatchNumber
     * @return list
     */
    List<OrderSearchOperResult> queryOrderByOrderBatchNumber(String orderBatchNumber);

    List<OrderFollowOperResult> queryOrder(String code, String searchType);

    /**
     * 根据当前登录用户, 加载大区基地
     * @param authResDto
     * @return
     */
    Map<String,List<OfcGroupVo>> queryGroupList(AuthResDto authResDto);

    List<OfcGroupVo> getBaseListByCurArea(UamGroupDto uamGroupDto);
}
