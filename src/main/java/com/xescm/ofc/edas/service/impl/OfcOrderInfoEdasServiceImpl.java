package com.xescm.ofc.edas.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.edas.model.dto.dpc.resp.OfcOrderGoodsTempDto;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderDetailInfoDto;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderDetailTypeDto;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderInfoDto;
import com.xescm.ofc.edas.service.OfcOrderInfoEdasService;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcGoodsDetailsInfoMapper;
import com.xescm.ofc.mapper.OfcOrderOperMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title:      OfcOrderInfoEdasServiceImpl. </p>
 * <p>Description 订单信息edas接口 </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author nothing
 * @CreateDate 2017/3/2 13:31
 */
@Service
public class OfcOrderInfoEdasServiceImpl implements OfcOrderInfoEdasService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcOrderOperMapper ofcOrderOperMapper;
    @Resource
    private OfcGoodsDetailsInfoMapper ofcGoodsDetailsInfoMapper;

    /**
     * <p>Title:      getOrderInfoByOrderCode. </p>
     * <p>Description 根据订单号批量查询订单信息.</p>
     *
     * @param orderCodes 订单号
     * @return 订单信息
     * @Author nothing
     * @CreateDate 2017/3/2 13:33
     */
    @Override
    public Wrapper<List<OfcOrderInfoDto>> getOrderInfoByOrderCode(List<String> orderCodes) {
        long start = System.currentTimeMillis();
        Wrapper<List<OfcOrderInfoDto>> result = null;
        try {
            if (PubUtils.isNotNullAndSmallerSize(orderCodes, 101)) {
                List<OfcOrderInfoDto> orderList = ofcOrderOperMapper.getOrderInfoByOrderCode(orderCodes);
                for (OfcOrderInfoDto orderInfo : orderList) {
                    String orderCode = orderInfo.getOrderCode();
                    if (!PubUtils.isOEmptyOrNull(orderCode)) {
                        List<OfcOrderDetailInfoDto> detail = ofcOrderOperMapper.getOrderDetailInfoByQrderCode(orderCode);
                        orderInfo.setOrderDetails(detail);
                    }
                }
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, orderList);
            } else if (PubUtils.isNull(orderCodes)) {
                result = WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, "订单号参数不能为空！");
            } else if (PubUtils.isNotNullAndBiggerSize(orderCodes, 100)) {
                result = WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, "每次最多查询100单！");
            }
        } catch (Exception ex) {
            LOGGER.error("查询订单发生异常: ", ex);
            result = WrapMapper.error();
        }
        LOGGER.info("======>【getOrderInfoByOrderCode】查询耗时: {} 毫秒", (System.currentTimeMillis() - start));
        return result;
    }

    /**
     * <p>Title:      getOrderGoodsTypeByOrderCode. </p>
     * <p>Description 根据订单编号查询订单货品分类名称</p>
     *
     * @param codeList 订单号集合
     * @author nothing
     * @date 2017/10/23 13:59
     * @return
     */
    @Override
    public Wrapper<List<OfcOrderDetailTypeDto>> getOrderGoodsTypeByOrderCode(List<String> codeList) {
        long start = System.currentTimeMillis();
        Wrapper<List<OfcOrderDetailTypeDto>> result;
        try {
            List<OfcGoodsDetailsInfo> res = ofcGoodsDetailsInfoMapper.getOrderGoodsTypeByOrderCode(codeList);
            Map<String, List<String>> orderList = new HashMap<>();
            for (OfcGoodsDetailsInfo goodsDetailsInfo : res) {
                String orderCode = goodsDetailsInfo.getOrderCode();
                String goodsType = goodsDetailsInfo.getGoodsType();
                if (orderList.containsKey(orderCode)) {
                    orderList.get(orderCode).add(goodsType);
                } else {
                    List<String> goodsTypes = new ArrayList<>();
                    goodsTypes.add(goodsType);
                    orderList.put(orderCode, goodsTypes);
                }
            }
            // 按订单组织实体
            List<OfcOrderDetailTypeDto> list = new ArrayList<>();
            for (String orderCode : orderList.keySet()) {
                OfcOrderDetailTypeDto dto = new OfcOrderDetailTypeDto();
                dto.setOrderCode(orderCode);
                dto.setGoodsTypes(orderList.get(orderCode));
                list.add(dto);
            }
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, list);
        } catch (BusinessException ex) {
            LOGGER.error("根据订单号查询货品分类发生异常：异常详情 => {}", ex.getMessage());
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("根据订单号查询货品分类发生未知异常：异常详情 => {}", ex.getMessage());
            result = WrapMapper.error();
        }
        LOGGER.info("======>【getOrderDetailTypeByOrderCode】查询耗时: {} 毫秒", (System.currentTimeMillis() - start));
        return result;
    }

    @Override
    public Wrapper<List<OfcOrderGoodsTempDto>> getOrderGoodsTempByOrderCode(List<String> list) {
        return null;
    }
}
