package com.xescm.ofc.service.impl;

import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.packing.GoodsPackingDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.edas.model.dto.ofc.OfcOrderStatusDTO;
import com.xescm.ofc.edas.model.dto.ofc.OfcRealTimeTraceDTO;
import com.xescm.ofc.edas.model.dto.ofc.OfcTraceOrderDTO;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDetailDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderDto;
import com.xescm.ofc.edas.model.dto.whc.FeedBackOrderStatusDto;
import com.xescm.ofc.enums.OrderStatusEnum;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcOrderNewstatusMapper;
import com.xescm.ofc.mapper.OfcOrderStatusMapper;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.tfc.edas.model.dto.ofc.req.OfcRealTimeTraceReqDTO;
import com.xescm.tfc.edas.service.TfcQueryGpsInfoEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.CreateOrderApiConstant.DACHEN_CUST_CODE;
import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.STOCK_IN_ORDER;
import static com.xescm.ofc.constant.OrderConstant.STOCK_OUT_ORDER;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;

/**
 * 订单状态
 * @author lyh
 * @date 2016-10-10.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OfcOrderStatusServiceImpl extends BaseService<OfcOrderStatus> implements OfcOrderStatusService {
    @Resource
    private OfcOrderStatusMapper ofcOrderStatusMapper;
    @Resource
    private OfcOrderNewstatusService ofcOrderNewstatusService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OrderFollowOperService orderFollowOperService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private TfcQueryGpsInfoEdasService tfcQueryGpsInfoEdasService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcOrderNewstatusMapper ofcOrderNewstatusMapper;

    @Resource
    private StringRedisTemplate rt;

    @Override
    public int deleteByOrderCode(Object key) {
        return ofcOrderStatusMapper.deleteByOrderCode(key);
    }

    @Override
    public List<OfcOrderStatus> orderStatusScreen(String code, String followTag) {
        if (!"".equals(trimAndNullAsEmpty(code))) {
            String orderCode = null;
            String custOrderCode = null;
            String transCode = null;
            switch (followTag) {
                case "orderCode":
                    orderCode = code;
                    break;
                case "custOrderCode":
                    custOrderCode = code;
                    break;
                case "transCode":
                    transCode = code;
                    break;
            }
            Map<String, String> mapperMap = new HashMap<>(1024);
            mapperMap.put("orderCode", orderCode);
            mapperMap.put("custOrderCode", custOrderCode);
            mapperMap.put("transCode", transCode);
            return ofcOrderStatusMapper.orderStatusScreen(mapperMap);
        } else {
            throw new BusinessException("订单状态查询有误");
        }
    }

    @Override
    public OfcOrderStatus orderStatusSelect(String code, String followTag) {
        if (!"".equals(trimAndNullAsEmpty(code))) {
            String orderCode = null;
            String custOrderCode = null;
            String transCode = null;
            switch (followTag) {
                case "orderCode":
                    orderCode = code;
                    break;
                case "custOrderCode":
                    custOrderCode = code;
                    break;
                case "transCode":
                    transCode = code;
                    break;
            }
            Map<String, String> mapperMap = new HashMap<>(1024);
            mapperMap.put("orderCode", orderCode);
            mapperMap.put("custOrderCode", custOrderCode);
            mapperMap.put("transCode", transCode);
            OfcOrderNewstatus orderNewstatus = ofcOrderNewstatusMapper.orderStatusSelectNew(mapperMap);
            OfcOrderStatus ofcOrderStatus = ofcOrderStatusMapper.orderStatusSelect(mapperMap);
            if (orderNewstatus == null || "".equals(trimAndNullAsEmpty(orderNewstatus.getOrderCode()))
                    || "".equals(trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()))) {
                OfcOrderNewstatus orderNewstatu = new OfcOrderNewstatus();
                orderNewstatu.setOrderCode(ofcOrderStatus.getOrderCode());
                orderNewstatu.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
                orderNewstatu.setStatusUpdateTime(new Date());
                orderNewstatu.setStatusCreateTime(new Date());
                ofcOrderNewstatusService.save(orderNewstatus);
            } else {
                ofcOrderStatus.setOrderCode(orderNewstatus.getOrderCode());
                ofcOrderStatus.setOrderStatus(orderNewstatus.getOrderLatestStatus());
            }
            return ofcOrderStatus;
        } else {
            throw new BusinessException("订单状态查询有误");
        }

    }

    @Override
    public OfcOrderStatus queryOrderStateByOrderCode(String orderCode) {
        return ofcOrderStatusMapper.queryOrderStateByOrderCode(orderCode);
    }

    @Override
    public void cancelOrderStateByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus = new OfcOrderNewstatus();
        orderNewstatus.setOrderCode(orderCode);
        orderNewstatus.setOrderLatestStatus(OrderStatusEnum.BEEN_CANCELED.getCode());
        orderNewstatus.setStatusUpdateTime(new Date());
        ofcOrderNewstatusService.update(orderNewstatus);
        ofcOrderStatusMapper.cancelOrderStateByOrderCode(orderCode);
    }

    @Override
    public OfcOrderStatus queryLastUpdateOrderByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus = ofcOrderNewstatusService.selectByKey(orderCode);
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        if (orderNewstatus != null && !"".equals(trimAndNullAsEmpty(orderNewstatus.getOrderCode()))
                && !"".equals(trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()))) {
            ofcOrderStatus.setOrderCode(orderNewstatus.getOrderCode());
            ofcOrderStatus.setOrderStatus(orderNewstatus.getOrderLatestStatus());
        } else {
            ofcOrderStatus = ofcOrderStatusMapper.queryLastUpdateOrderByOrderCode(orderCode);
            if (null == ofcOrderStatus) {
                logger.error("查不到该订单的状态, 订单号: {}", orderCode);
                return new OfcOrderStatus();
            }
            OfcOrderNewstatus orderNewstatu = new OfcOrderNewstatus();
            orderNewstatu.setOrderCode(ofcOrderStatus.getOrderCode());
            orderNewstatu.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
            orderNewstatu.setStatusUpdateTime(new Date());
            orderNewstatu.setStatusCreateTime(new Date());
        }
        return ofcOrderStatus;
    }

    /**
     *
     * @param orderCode 订单号
     * @return 订单状态
     */
    @Override
    public OfcOrderStatus queryLastTimeOrderByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus = ofcOrderNewstatusService.selectByKey(orderCode);
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        if (orderNewstatus != null && !"".equals(trimAndNullAsEmpty(orderNewstatus.getOrderCode()))
                && !"".equals(trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()))) {
            ofcOrderStatus.setOrderCode(orderNewstatus.getOrderCode());
            ofcOrderStatus.setOrderStatus(orderNewstatus.getOrderLatestStatus());
        } else {
            ofcOrderStatus = ofcOrderStatusMapper.queryLastTimeOrderByOrderCode(orderCode);
            OfcOrderNewstatus orderNewstatu = new OfcOrderNewstatus();
            orderNewstatu.setOrderCode(ofcOrderStatus.getOrderCode());
            orderNewstatu.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
            orderNewstatu.setStatusUpdateTime(new Date());
            orderNewstatu.setStatusCreateTime(new Date());
        }
        return ofcOrderStatus;
    }

    @Override
    public int save(OfcOrderStatus ofcOrderStatus) {
        if (ofcOrderStatus != null && !"".equals(trimAndNullAsEmpty(ofcOrderStatus.getOrderCode()))) {
            if (!"".equals(trimAndNullAsEmpty(ofcOrderStatus.getOrderStatus()))) {
                OfcOrderNewstatus orderNewstatus = ofcOrderNewstatusService.selectByKey(ofcOrderStatus.getOrderCode());
                String tag = "noStatus";
                if (orderNewstatus != null) {
                    tag =  "haveStatus";
                } else {
                    orderNewstatus=new OfcOrderNewstatus();
                }
                String orderLatestStatus = trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus());
                if (!(OrderStatusEnum.BEEN_CANCELED.getCode().equals(orderLatestStatus) ||
                        OrderStatusEnum.BEEN_COMPLETED.getCode().equals(orderLatestStatus))) {
                    updateOrderNewStatus(ofcOrderStatus, tag);
                }
                ofcOrderStatus.setId(UUID.randomUUID().toString().replace("-", ""));
                ofcOrderStatus.setCreationTime(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
                return super.save(ofcOrderStatus);
            } else {
                throw new BusinessException("订单状态为空，保存订单状态失败");
            }
        }
        return 0;
    }

    @Override
    public void feedBackStatusFromWhc(FeedBackOrderStatusDto feedBackOrderStatusDto) {
        try {
            String orderCode = feedBackOrderStatusDto.getOrderCode();
            String type = "";
            String traceStatus = feedBackOrderStatusDto.getStatus();
            Date traceTime = feedBackOrderStatusDto.getTraceTime();
            if (StringUtils.isEmpty(orderCode)) {
                throw new BusinessException("订单号不可以为空");
            }
            if (StringUtils.isEmpty(feedBackOrderStatusDto.getStatus())) {
                throw new BusinessException("跟踪状态不能为空");
            }
            OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
            if (ofcFundamentalInformation == null) {
                throw new BusinessException("订单不存在");
            }
            OfcOrderStatus orderStatus = orderStatusSelect(orderCode,"orderCode");
            OfcOrderStatus status = new OfcOrderStatus();
            if (orderStatus != null) {
                if (HASBEEN_COMPLETED.equals(orderStatus.getOrderStatus())) {
                    throw new BusinessException("订单已经完成");
                }
                if (HASBEEN_CANCELED.equals(orderStatus.getOrderStatus())) {
                    throw new BusinessException("订单已经取消");
                }
            }
            String businessTypePrifx = trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0,2);
            if (STOCK_IN_ORDER.equals(businessTypePrifx)) {
                type = OFC_WHC_IN_TYPE;
            }
            else if (STOCK_OUT_ORDER.equals(businessTypePrifx)) {
                type = OFC_WHC_OUT_TYPE;
            }
            String statusDesc = translateStatusToDesc(traceStatus,type);
            if (!orderStatus.getStatusDesc().contains(statusDesc)) {
                status.setLastedOperTime(new Date());
                status.setStatusDesc(statusDesc);
                status.setOrderCode(orderCode);
                status.setOrderStatus(orderStatus.getOrderStatus());
                status.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                        + " " + statusDesc);
                status.setOrderCode(orderCode);
                save(status);
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @Override
    public void ofcWarehouseFeedBackFromWhc(FeedBackOrderDto feedBackOrderDto) {
        try {
            String orderCode = feedBackOrderDto.getOrderCode();
            List<FeedBackOrderDetailDto> details = feedBackOrderDto.getFeedBackOrderDetail();
            if (StringUtils.isEmpty(orderCode)) {
                throw new BusinessException("订单号不可以为空");
            }
            if (CollectionUtils.isEmpty(details)) {
                throw new BusinessException("货品详情不能为空");
            }
            OfcWarehouseInformation ofcWarehouseInformation = new OfcWarehouseInformation();
            ofcWarehouseInformation.setOrderCode(orderCode);
            ofcWarehouseInformation = ofcWarehouseInformationService.selectOne(ofcWarehouseInformation);
            OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
            if (ofcFundamentalInformation == null) {
                throw new BusinessException("订单不存在");
            }
            OfcOrderStatus orderStatus = orderStatusSelect(orderCode,"orderCode");
            OfcOrderStatus status = new OfcOrderStatus();
            if (orderStatus != null) {
                if (OrderStatusEnum.BEEN_COMPLETED.getCode().equals(orderStatus.getOrderStatus())) {
                    throw new BusinessException("订单已经完成");
                }
                if (OrderStatusEnum.BEEN_CANCELED.getCode().equals(orderStatus.getOrderStatus())) {
                    throw new BusinessException("订单已经取消");
                }
            }
            String str = "";
            String businessTypePrifx = trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0,2);

            if (STOCK_IN_ORDER.equals(businessTypePrifx)) {
                status.setOrderStatus(OrderStatusEnum.INPUT_COMPLETED.getCode());
                status.setStatusDesc(OrderStatusEnum.INPUT_COMPLETED.getDesc());
                status.setTraceStatus(OrderStatusEnum.INPUT_COMPLETED.getCode());
                status.setTrace(OrderStatusEnum.INPUT_COMPLETED.getDesc());
                str = "入库单";
            } else if (STOCK_OUT_ORDER.equals(businessTypePrifx)) {
                str = "出库单";
                status.setOrderStatus(OrderStatusEnum.OUTPUT_COMPLETED.getCode());
                status.setStatusDesc(OrderStatusEnum.OUTPUT_COMPLETED.getDesc());
                status.setTraceStatus(OrderStatusEnum.OUTPUT_COMPLETED.getCode());
                status.setTrace(OrderStatusEnum.OUTPUT_COMPLETED.getDesc());
            }
            status.setLastedOperTime(new Date());
            status.setOrderCode(orderCode);
            save(status);

            if (ofcWarehouseInformation != null) {
                if (WEARHOUSE_WITH_TRANS.equals(ofcWarehouseInformation.getProvideTransport())) {
                    if (rt.hasKey(ofcFundamentalInformation.getOrderCode())) {
                        logger.info("仓储订单运输先完成,订单号为{}", ofcFundamentalInformation.getOrderCode());
                        status.setOrderStatus(OrderStatusEnum.BEEN_COMPLETED.getCode());
                        status.setStatusDesc(OrderStatusEnum.BEEN_COMPLETED.getDesc());
                        status.setTraceStatus(OrderStatusEnum.BEEN_COMPLETED.getCode());
                        status.setTrace(OrderStatusEnum.BEEN_COMPLETED.getDesc());
                        //更新订单完成时间
                        ofcFundamentalInformation.setFinishedTime(new Date());
                        rt.delete(ofcFundamentalInformation.getOrderCode());
                    } else {
                        logger.info("===>仓储订单仓储先完成,订单号为{}", ofcFundamentalInformation.getOrderCode());
                        rt.opsForValue().set(ofcFundamentalInformation.getOrderCode(),"");
                    }
                } else {
                    status.setOrderStatus(OrderStatusEnum.BEEN_COMPLETED.getCode());
                    status.setStatusDesc(OrderStatusEnum.BEEN_COMPLETED.getDesc());
                    status.setTraceStatus(OrderStatusEnum.BEEN_COMPLETED.getCode());
                    status.setTrace(OrderStatusEnum.BEEN_COMPLETED.getDesc());
                    //更新订单完成时间
                    ofcFundamentalInformation.setFinishedTime(new Date());
                }
            }
            //转换为原包装的数量
            conversionUnitQuantity(details, ofcWarehouseInformation, ofcFundamentalInformation);
            //更新实际的数量
            for (FeedBackOrderDetailDto detail :details) {
                OfcGoodsDetailsInfo good = new OfcGoodsDetailsInfo();
                good.setGoodsCode(detail.getGoodsCode());
                good.setOrderCode(orderCode);
                good.setRealQuantity(detail.getRealQuantity());
                ofcGoodsDetailsInfoService.updateByOrderCode(good);
            }

            status.setLastedOperTime(new Date());
            status.setOrderCode(orderCode);
            status.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1)
                    + " " + "订单号为" + orderCode + str + "已完成");
            status.setOrderCode(orderCode);
            save(status);
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void conversionUnitQuantity(List<FeedBackOrderDetailDto> detailDtos, OfcWarehouseInformation ofcWarehouseInformation, OfcFundamentalInformation ofcFundamentalInformation) {
        for (FeedBackOrderDetailDto goodsInfo :detailDtos) {
            if (WAREHOUSE_DIST_ORDER.equals(ofcFundamentalInformation.getOrderType())) {
                CscGoodsApiDto cscGoods = new CscGoodsApiDto();
                String goodsCode = goodsInfo.getGoodsCode();
                String unit = goodsInfo.getUnit();
                String warehouseCode = ofcWarehouseInformation.getWarehouseCode();
                String custCode = ofcFundamentalInformation.getCustCode();
                //天津自动化仓 用天津仓包装校验
                if ("000001".equals(warehouseCode)) {
                    cscGoods.setWarehouseCode("ck0024");
                } else {
                    cscGoods.setWarehouseCode(warehouseCode);
                }
                cscGoods.setFromSys("WMS");
                cscGoods.setGoodsCode(goodsCode);
                cscGoods.setCustomerCode(custCode);
                cscGoods.setPNum(1);
                cscGoods.setPSize(10);
                try{
                    logger.info("匹配包装的参数为:{}", JacksonUtil.toJson(cscGoods));
                }catch (Exception e){
                    e.printStackTrace();
                }
                Wrapper<PageInfo<CscGoodsApiVo>> goodsRest = ofcGoodsDetailsInfoService.validateGoodsByCode(cscGoods);
                try{
                    logger.info("匹配包装的响应结果为:{}",JacksonUtil.toJson(goodsRest));
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (goodsRest != null && Wrapper.SUCCESS_CODE == goodsRest.getCode() && goodsRest.getResult() != null &&
                        PubUtils.isNotNullAndBiggerSize(goodsRest.getResult().getList(), 0)) {
                    CscGoodsApiVo cscGoodsApiVo = goodsRest.getResult().getList().get(0);
                    List<GoodsPackingDto>  packages = cscGoodsApiVo.getGoodsPackingDtoList();
                    if (!CollectionUtils.isEmpty(packages)) {
                        for (GoodsPackingDto packingDto : packages) {
                            if (StringUtils.equals(unit,packingDto.getLevelDescription())) {
                                logger.info("orderCode is {}",ofcFundamentalInformation.getOrderCode());
                                logger.info("unit is {}",unit);
                                logger.info("packingDto.getLevelDescription() is {}",packingDto.getLevelDescription());
                                BigDecimal pquantity;
                                BigDecimal realQuantity = goodsInfo.getRealQuantity();
                                BigDecimal ls = packingDto.getLevelSpecification();
                                if (!(ls == null || ls.compareTo(new BigDecimal(0)) == 0)) {
                                    logger.info("订单号为:{}的货品编码为:{}转化为原包装的数量",ofcFundamentalInformation.getOrderCode(),goodsCode);
                                    logger.info("主单位的数量为:{}转化率为:{}",realQuantity.doubleValue(),ls.doubleValue());
                                    //大成客户特殊处理
                                    if (DACHEN_CUST_CODE.equals(custCode)) {
                                        pquantity = realQuantity.multiply(ls).setScale(2,BigDecimal.ROUND_HALF_DOWN);
                                    } else {
                                        //保留三位小数
                                        pquantity = realQuantity.divide(ls,3,BigDecimal.ROUND_HALF_DOWN);
                                    }
                                    goodsInfo.setRealQuantity(pquantity);
                                    logger.info("主单位的数量转化为原包装的数量为:{}",realQuantity.doubleValue());
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     *
     * @param orderCode 订单号
     * @return 状态跟踪
     */
    @Override
    public OfcTraceOrderDTO queryOrderByCode(String orderCode) throws Exception {
        logger.info("查单的订单号为:{}",orderCode);
        /*发运时间*/
        String departureTime = "";
        /*签收时间*/
        String signTime = "";
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        CheckUtils.checkArgument(ofcFundamentalInformation == null, ResultCodeEnum.RESULTISNULL);
        if (ofcFundamentalInformation.getOrderType().equals(WAREHOUSE_DIST_ORDER)) {
          OfcWarehouseInformation ofcWarehouseInformation = new OfcWarehouseInformation();
          ofcWarehouseInformation.setOrderCode(orderCode);
          List<OfcWarehouseInformation> ofcWarehouseInformations = ofcWarehouseInformationService.select(ofcWarehouseInformation);
          if (!CollectionUtils.isEmpty(ofcWarehouseInformations) && ofcWarehouseInformations.size() == 1) {
              CheckUtils.checkArgument(!ofcWarehouseInformations.get(0).getProvideTransport().equals(WEARHOUSE_WITH_TRANS), ResultCodeEnum.ISNOTSUPPORT);
          }
        }
        OfcTraceOrderDTO ofcTraceOrderDTO = new OfcTraceOrderDTO();
        List<OfcOrderStatusDTO> orderStatusDTOs = new ArrayList<>();
        //获取订单的跟踪状态
        List<OfcOrderStatus> ofcOrderStatuses = orderFollowOperService.queryOrderStatus(orderCode, "orderCode");
        CheckUtils.checkArgument(CollectionUtils.isEmpty(ofcOrderStatuses), ResultCodeEnum.RESULTISNULL);
        for (OfcOrderStatus status : ofcOrderStatuses) {
            if (PubUtils.isSEmptyOrNull(status.getTrace())) {
                continue;
            }
            //发运时间
            if ("30".equals(status.getTraceStatus())) {
                departureTime = DateUtils.Date2String(status.getLastedOperTime(), DateUtils.DateFormatType.TYPE1);
            }
            //签收时间
            if ("50".equals(status.getTraceStatus())) {
                signTime = DateUtils.Date2String(status.getLastedOperTime(), DateUtils.DateFormatType.TYPE1);
            }
            OfcOrderStatusDTO dto = new OfcOrderStatusDTO();
            dto.setLastOperTime(status.getLastedOperTime());
            dto.setOperator(PubUtils.isSEmptyOrNull(status.getOperator())?"":status.getOperator());
            dto.setTrace(status.getTrace());
            dto.setStatus(status.getTraceStatus());
            dto.setNotes(status.getNotes());
            orderStatusDTOs.add(dto);
        }
        ofcTraceOrderDTO.setOfcOrderStatusDTOs(orderStatusDTOs);
        //实时跟踪的状态
        OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.selectByKey(orderCode);
        if (ofcDistributionBasicInfo != null) {
            String plateNumber = ofcDistributionBasicInfo.getPlateNumber();
            OfcRealTimeTraceReqDTO reqDTO = new OfcRealTimeTraceReqDTO();
            if (!(PubUtils.isSEmptyOrNull(plateNumber) && PubUtils.isSEmptyOrNull(departureTime))) {
                reqDTO.setPlateNumber(plateNumber);
                reqDTO.setStartTime(departureTime);
                reqDTO.setEndTime(signTime);
                long beginTime = System.currentTimeMillis();
                logger.info("调用获取实时位置的接口参数为:{}", JacksonUtil.toJson(reqDTO));
                Wrapper<List<OfcRealTimeTraceDTO>> ofcRealTimeTraceDTOResult = tfcQueryGpsInfoEdasService.queryGpsforOfc(reqDTO);
                long endTime = System.currentTimeMillis();
                logger.info("调用获取实时位置的接口接口耗时:{}ms",(endTime - beginTime));
                if (ofcRealTimeTraceDTOResult.getCode() == Wrapper.SUCCESS_CODE) {
                    if (!CollectionUtils.isEmpty(ofcRealTimeTraceDTOResult.getResult())) {
                        logger.info("调用获取实时位置的结果集大小为:{}",ofcRealTimeTraceDTOResult.getResult().size());
                        ofcTraceOrderDTO.setOfcRealTimeTraceDTOs(ofcRealTimeTraceDTOResult.getResult());
                    }
                }
            }
        }
        return ofcTraceOrderDTO;
    }

    private void updateOrderNewStatus(OfcOrderStatus ofcOrderStatus, String tag) {
        OfcOrderNewstatus orderNewstatus = new OfcOrderNewstatus();
        orderNewstatus.setOrderCode(ofcOrderStatus.getOrderCode());
        orderNewstatus.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
        if ("haveStatus".equals(tag)) {
            orderNewstatus.setStatusUpdateTime(new Date());
            ofcOrderNewstatusService.update(orderNewstatus);
        } else if ("noStatus".equals(tag)) {
            orderNewstatus.setStatusUpdateTime(new Date());
            orderNewstatus.setStatusCreateTime(new Date());
            ofcOrderNewstatusService.save(orderNewstatus);
        }
    }

    private String translateStatusToDesc(String statusCode,String businessType) {
        String statusDesc = "";
        if (statusCode.equals(TRACE_STATUS_1)) {
            if (OFC_WHC_IN_TYPE.equals(businessType)) {
                statusDesc="入库单已创建";
            } else if (OFC_WHC_OUT_TYPE.equals(businessType)) {
                statusDesc="出库单已创建";
            }
        } else if (statusCode.equals(TRACE_STATUS_2)) {
            if (OFC_WHC_IN_TYPE.equals(businessType)) {
                statusDesc="部分收货";
            }
        } else if (statusCode.equals(TRACE_STATUS_3)) {
            if (OFC_WHC_IN_TYPE.equals(businessType)) {
                statusDesc="完全收货";
            } else if (OFC_WHC_OUT_TYPE.equals(businessType)) {
                statusDesc="出库分配完成";
            }
        } else if (statusCode.equals(TRACE_STATUS_4)) {
            if (OFC_WHC_IN_TYPE.equals(businessType)) {

            } else if (OFC_WHC_OUT_TYPE.equals(businessType)) {
                statusDesc="拣货完成";
            }
        } else if (statusCode.equals(TRACE_STATUS_5)) {
            if (OFC_WHC_IN_TYPE.equals(businessType)) {

            } else if (OFC_WHC_OUT_TYPE.equals(businessType)) {
                statusDesc="二次拣货完成";
            }
        } else if (statusCode.equals(TRACE_STATUS_6)) {
            if (OFC_WHC_IN_TYPE.equals(businessType)) {

            } else if (OFC_WHC_OUT_TYPE.equals(businessType)) {
                statusDesc="装车完成";
            }
        } else if (statusCode.equals(TRACE_STATUS_7)) {
            if (OFC_WHC_IN_TYPE.equals(businessType)) {

            } else if (OFC_WHC_OUT_TYPE.equals(businessType)) {
                statusDesc = "出库单已发运";
            }
        } else if (statusCode.equals(TRACE_STATUS_8)) {
            if (OFC_WHC_IN_TYPE.equals(businessType)) {
                statusDesc = "入库单取消";
            } else if (OFC_WHC_OUT_TYPE.equals(businessType)) {
                statusDesc = "出库单取消";
            }
        } else if (statusCode.equals(TRACE_STATUS_9)) {
            if (OFC_WHC_IN_TYPE.equals(businessType)) {
                statusDesc = "入库完毕";
            } else if (OFC_WHC_OUT_TYPE.equals(businessType)) {
                statusDesc = "出库完毕";
            }
        }
        return statusDesc;
    }
}
