package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OrderFollowOperResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderDetailInfoDto;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderInfoDto;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.model.dto.form.OrderStorageOperForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfcOrderOperMapper {



    List<OrderSearchOperResult> queryStorageOrderList(@Param("form") OrderStorageOperForm form, @Param("userId") String userId, @Param("withUser") Boolean withUser);

    /**
     * 根据订单批次号查询订单列表
     * @param orderBatchNumber
     * @return list
     */
    List<OrderSearchOperResult> queryOrderByOrderBatchNumber(@Param("orderBatchNumber") String orderBatchNumber);

    List<OrderFollowOperResult> queryOrder(@Param("code") String code, @Param("searchType") String searchType);

    /**
     * <p>Title:      getOrderInfoByOrderCode. </p>
     * <p>Description 根据订单号批量查询订单信息.</p>
     *
     * @param orderCodes 订单号
     * @return 订单信息
     * @Author nothing
     * @CreateDate 2017-03-02 14:43:00
     */
    List<OfcOrderInfoDto> getOrderInfoByOrderCode(@Param("list") List<String> orderCodes);

    /**
     * 根据订单号查询订单商品明细
     *
     * @param orderCode 订单号
     * @return 订单商品明细
     */
    List<OfcOrderDetailInfoDto> getOrderDetailInfoByQrderCode(@Param("orderCode") String orderCode);


    /**
     *查询订单列表
     * @param form
     * @param userId
     * @return list
     */
    List<OrderSearchOperResult> queryOrderList(@Param("form")OrderOperForm form, @Param("userId") String userId, @Param("withUser") Boolean withUser);

    List<OrderSearchOperResult> queryStorageOrderListUnion(@Param("form")OrderOperForm form, @Param("userId") String userId, @Param("withUser") Boolean withUser);

    List<OrderSearchOperResult> queryOrderListUnion(@Param("form")OrderOperForm form, @Param("userId") String userId, @Param("withUser") Boolean withUser);

}
