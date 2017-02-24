package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.model.vo.ofc.OfcBatchOrderVo;
import com.xescm.ofc.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfcFundamentalInformationMapper extends MyMapper<OfcFundamentalInformation> {
    String getOrderCodeByCustOrderCode(Object key);

    int checkCustOrderCode(OfcFundamentalInformation ofcFundamentalInformation);

    String getOrderCodeByCustOrderCodeAndCustCode(@Param("custOrderCode") String custOrderCode, @Param("custCode") String custCode);

    List<OfcFundamentalInformation> queryOrder(@Param("code") String code, @Param("searchType") String searchType);

    OfcBatchOrderVo queryByBatchNumber(@Param("orderBatchNumber") String orderBatchNumber);

    List<OfcFundamentalInformation> queryOrderByOrderBatchNumber(@Param("orderBatchNumber") String orderBatchNumber);

    OfcFundamentalInformation queryDataByCustOrderCode(@Param("custOrderCode") String custOrderCode);

    OfcFundamentalInformation getLastMerchandiser(@Param("operatorName") String operatorName);

    /**
     * 根据客户订单编号与货主编码查询不是已经取消的订单
     * @param ofcFundamentalInformation
     * @return
     */
    OfcFundamentalInformation queryOfcFundInfoByCustOrderCodeAndCustCode(OfcFundamentalInformation ofcFundamentalInformation);
}