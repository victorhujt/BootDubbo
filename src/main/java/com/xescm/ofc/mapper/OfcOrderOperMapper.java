package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OrderFollowOperResult;
import com.xescm.ofc.domain.OrderSearchOperResult;
import com.xescm.ofc.domain.OrderCountResult;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderDetailInfoDto;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderInfoDto;
import com.xescm.ofc.model.dto.form.OrderOperForm;
import com.xescm.ofc.model.dto.form.OrderStorageOperForm;
import com.xescm.ofc.model.dto.form.OrderCountForm;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfcOrderOperMapper {

    /**
     *查询订单列表
     * @param form
     * @return list
     */
    List<OrderSearchOperResult> queryOrderList(@Param("form") OrderOperForm form);

    List<OrderSearchOperResult> queryStorageOrderList(@Param("form") OrderStorageOperForm form);

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
}
