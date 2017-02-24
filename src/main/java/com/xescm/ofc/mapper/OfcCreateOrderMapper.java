package com.xescm.ofc.mapper;

import com.xescm.ofc.edas.model.dto.epc.QueryOrderStatusDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * Created by hiyond on 2016/11/15.
 */
@Repository
public interface OfcCreateOrderMapper {

    /**
     * 根据客户订单号与货主编码查询是否存在订单
     * @param custOrderCode 客户订单号
     * @param custCode 货主编码
     * @return queryCountByOrderStatus
     */
    int queryCountByOrderStatus(@Param(value = "custOrderCode") String custOrderCode, @Param(value = "custCode") String custCode);

    /**
     * 对接平台定时获取订单中心的鲜易网七天内待发货状态中的订单
     * 参数：客户编码 订单日期开始日期 订单日期结束日期
     * 仅需要订单状态为【待审核】、【已审核】、【任务中】 三种状态的订单列表
     * 返回：List
     * @param queryOrderStatusDto
     * @return
     */
    List<QueryOrderStatusDto> queryOrderStatusList(QueryOrderStatusDto queryOrderStatusDto);

}
