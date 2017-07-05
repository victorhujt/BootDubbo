package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OrderFollowOperResult;
import com.xescm.ofc.domain.OrderScreenResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.model.dto.ofc.OfcStorageDTO;
import com.xescm.ofc.model.vo.ofc.OfcGroupVo;
import com.xescm.uam.model.dto.group.UamGroupDto;

import java.util.List;
import java.util.Map;

/**
 * 运营-订单管理
 * Created by hiyond on 2016/11/24.
 */

public interface OfcOrderManageOperService {


    List<OrderScreenResult> queryOrderOper(OrderOperForm form);

    List<OrderSearchOperResult> queryOrderStorageDataOper(AuthResDto authResDto,OfcStorageDTO ofcStorageDTO);

    /**
     * 查询订单列表
     *
     * @param form
     * @return list
     */
    List<OrderSearchOperResult> queryOrderList(AuthResDto authResDto,OrderOperForm form);

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

    /**
     * 根据所选大区查询基地
     * @param uamGroupDto
     * @return
     */
    List<OfcGroupVo> getBaseListByCurArea(UamGroupDto uamGroupDto);

    /**
     * 根据所选基地反查大区
     * @param uamGroupDto
     * @return
     */
    OfcGroupVo queryAreaMsgByBase(UamGroupDto uamGroupDto);

    void checkUamGroupEdasResultNullOrError(Wrapper<?> allGroupByType);

    Map<String,List<OfcGroupVo>> loadGroupList();
}
