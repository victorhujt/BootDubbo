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
import java.util.concurrent.TimeUnit;

import static com.xescm.core.utils.PubUtils.trimAndNullAsEmpty;
import static com.xescm.ofc.constant.OrderConstConstant.*;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;

/**
 * 订单状态
 * Created by lyh on 2016/10/10.
 */
@Service
@Transactional
public class OfcOrderStatusServiceImpl extends BaseService<OfcOrderStatus> implements OfcOrderStatusService {
    @Resource
    private OfcOrderStatusMapper ofcOrderStatusMapper;
    @Resource
    private OfcOrderNewstatusMapper ofcOrderNewstatusMapper;
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
    private OfcRuntimePropertyService ofcRuntimePropertyService;

    @Resource
    private StringRedisTemplate rt;

    @Override
    public int deleteByOrderCode(Object key) {
        return ofcOrderStatusMapper.deleteByOrderCode(key);
    }

    @Override
    public List<OfcOrderStatus> orderStatusScreen(String code, String followTag) {
        if (!trimAndNullAsEmpty(code).equals("")) {
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
            // Map<String,String> mapperMap = new HashMap<String,String>();
            Map<String, String> mapperMap = new HashMap<>();
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
            Map<String, String> mapperMap = new HashMap<>();
            mapperMap.put("orderCode", orderCode);
            mapperMap.put("custOrderCode", custOrderCode);
            mapperMap.put("transCode", transCode);
            OfcOrderNewstatus orderNewstatus = ofcOrderNewstatusMapper.orderStatusSelectNew(mapperMap);
            OfcOrderStatus ofcOrderStatus = ofcOrderStatusMapper.orderStatusSelect(mapperMap);
            if (orderNewstatus == null || trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")
                    || trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals("")) {
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
        orderNewstatus.setOrderLatestStatus(HASBEEN_CANCELED);
        orderNewstatus.setStatusUpdateTime(new Date());
        ofcOrderNewstatusService.update(orderNewstatus);
        ofcOrderStatusMapper.cancelOrderStateByOrderCode(orderCode);
    }

    @Override
    public OfcOrderStatus queryLastUpdateOrderByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus = ofcOrderNewstatusService.selectByKey(orderCode);
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        if (orderNewstatus != null && !trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")
                && !trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals("")) {
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
    @Override
    public OfcOrderStatus queryLastTimeOrderByOrderCode(String orderCode) {
        OfcOrderNewstatus orderNewstatus = ofcOrderNewstatusService.selectByKey(orderCode);
        OfcOrderStatus ofcOrderStatus = new OfcOrderStatus();
        if (orderNewstatus != null && !trimAndNullAsEmpty(orderNewstatus.getOrderCode()).equals("")
                && !trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals("")) {
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
            String orderCode = ofcOrderStatus.getOrderCode();
            if (!trimAndNullAsEmpty(ofcOrderStatus.getOrderStatus()).equals("")) {
                OfcOrderNewstatus orderNewstatus = ofcOrderNewstatusService.selectByKey(orderCode);
                String tag = "noStatus";
                if (orderNewstatus != null) {
                    tag =  "haveStatus";
                } else {
                    orderNewstatus=new OfcOrderNewstatus();
                }
                if (!trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals(HASBEEN_CANCELED)){
                    if (!trimAndNullAsEmpty(orderNewstatus.getOrderLatestStatus()).equals(HASBEEN_COMPLETED)) {
                        logger.info("订单更新的订单号为:{},的状态为{}:",orderCode,ofcOrderStatus.getOrderStatus());
                        updateOrderNewStatus(ofcOrderStatus, tag);
                    }
                }
                ofcOrderStatus.setId(UUID.randomUUID().toString().replace("-", ""));
                ofcOrderStatus.setCreationTime(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1));
                logger.info("订单保存日志订单号为:{},状态{}:",orderCode,ofcOrderStatus.getOrderStatus());
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
            Date traceTime = feedBackOrderStatusDto.getTraceTime();
            String orderTraceStatus = feedBackOrderStatusDto.getStatus();
            if (StringUtils.isEmpty(orderCode)) {
                throw new BusinessException("订单号不可以为空");
            }
            if (StringUtils.isEmpty(orderTraceStatus)) {
                throw new BusinessException("跟踪状态不能为空");
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
                if (HASBEEN_COMPLETED.equals(orderStatus.getOrderStatus())) {
                    throw new BusinessException("订单已经完成");
                }
                if (HASBEEN_CANCELED.equals(orderStatus.getOrderStatus())) {
                    throw new BusinessException("订单已经取消");
                }
            }
            String businessType = trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType());
            if (orderTraceStatus.equals(OrderStatusEnum.BEEN_COMPLETED.getCode())) {
                if ("62".equals(businessType.substring(0,2))) {
                    status.setStatusDesc(OrderStatusEnum.INPUT_COMPLETED.getDesc());
                    status.setOrderStatus(OrderStatusEnum.INPUT_COMPLETED.getCode());
                }
                else if ("61".equals(businessType.substring(0,2))) {
                    status.setOrderStatus(OrderStatusEnum.OUTPUT_COMPLETED.getCode());
                    status.setStatusDesc(OrderStatusEnum.OUTPUT_COMPLETED.getDesc());
                    status.setTraceStatus("20");
                    status.setTrace("出库");
                }
            }
            status =  getOfcOrderStatus(ofcFundamentalInformation, status, ofcWarehouseInformation);
            status.setLastedOperTime(new Date());
            status.setOrderCode(orderCode);
            status.setOperator("");
            traceTime = traceTime == null ? new Date():traceTime;
            status.setNotes(DateUtils.Date2String(traceTime, DateUtils.DateFormatType.TYPE1)
                    + " " + status.getStatusDesc());
            status.setOrderCode(orderCode);
            save(status);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }


    @Override
    public void ofcWarehouseFeedBackFromWhc(FeedBackOrderDto feedBackOrderDto) {
        try {
            String orderCode = feedBackOrderDto.getOrderCode();
            List<FeedBackOrderDetailDto> detailDtos = feedBackOrderDto.getFeedBackOrderDetail();
            if (StringUtils.isEmpty(orderCode)) {
                throw new BusinessException("订单号不可以为空");
            }
            if (CollectionUtils.isEmpty(detailDtos)) {
                throw new BusinessException("货品详情不能为空");
            }

            for (FeedBackOrderDetailDto dto:detailDtos) {
                Long paasLineNo = dto.getPaasLineNo();
                if (paasLineNo == 0L || paasLineNo < 0L) {
                    throw new BusinessException("货品的行号不能小于或等于0");
                }
            }
            OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
            if (ofcFundamentalInformation == null) {
                throw new BusinessException("订单不存在");
            }
            OfcOrderStatus orderStatus = orderStatusSelect(orderCode,"orderCode");
            OfcOrderStatus status = new OfcOrderStatus();
            if (orderStatus != null) {
//                if (OrderStatusEnum.BEEN_COMPLETED.getCode().equals(orderStatus.getOrderStatus())) {
//                    throw new BusinessException("订单已经完成");
//                }
                if (OrderStatusEnum.BEEN_CANCELED.getCode().equals(orderStatus.getOrderStatus())) {
                    throw new BusinessException("订单已经取消");
                }
            }
//            if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("62")) {
//                status.setOrderStatus(OrderStatusEnum.INPUT_COMPLETED.getCode());
//                status.setStatusDesc(OrderStatusEnum.INPUT_COMPLETED.getDesc());
//            } else if (trimAndNullAsEmpty(ofcFundamentalInformation.getBusinessType()).substring(0, 2).equals("61")) {
//                status.setOrderStatus(OrderStatusEnum.OUTPUT_COMPLETED.getCode());
//                status.setStatusDesc(OrderStatusEnum.OUTPUT_COMPLETED.getDesc());
//                status.setTraceStatus("20");
//                status.setTrace("出库");
//            }
            OfcWarehouseInformation ofcWarehouseInformation = new OfcWarehouseInformation();
            ofcWarehouseInformation.setOrderCode(orderCode);
            ofcWarehouseInformation = ofcWarehouseInformationService.selectOne(ofcWarehouseInformation);
           // status =  getOfcOrderStatus(ofcFundamentalInformation, status, ofcWarehouseInformation);
            //转换为原包装的数量
             conversionUnitQuantity(detailDtos, ofcWarehouseInformation, ofcFundamentalInformation);
            //更新实际的数量
            for (FeedBackOrderDetailDto detail :detailDtos) {
                OfcGoodsDetailsInfo good = new OfcGoodsDetailsInfo();
                good.setOrderCode(orderCode);
                good.setPaasLineNo(detail.getPaasLineNo());
                good.setRealQuantity(detail.getRealQuantity());
                ofcGoodsDetailsInfoService.updateByOrderCode(good);
            }

//            status.setLastedOperTime(new Date());
//            status.setStatusDesc("订单号为" + orderCode + "已完成");
//            status.setOrderCode(orderCode);
//            status.setOperator("");
//            status.setNotes(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1)
//                    + " " + "订单号为" + orderCode + "已完成");
//            status.setOrderCode(orderCode);
         //   save(status);
            ofcFundamentalInformation.setFinishedTime(new Date());
            ofcFundamentalInformationService.update(ofcFundamentalInformation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OfcOrderStatus getOfcOrderStatus(OfcFundamentalInformation ofcFundamentalInformation, OfcOrderStatus status, OfcWarehouseInformation ofcWarehouseInformation) {
        String orderCode = ofcFundamentalInformation.getOrderCode();
        if (ofcWarehouseInformation != null) {
            if (WEARHOUSE_WITH_TRANS.equals(ofcWarehouseInformation.getProvideTransport())) {
                if (rt.hasKey(orderCode)) {
                    logger.info("仓储订单运输先完成,订单号为{}", orderCode);
                    status.setOrderStatus(OrderStatusEnum.BEEN_COMPLETED.getCode());
                    //更新订单完成时间
                    ofcFundamentalInformation.setFinishedTime(new Date());
                    rt.delete(orderCode);
                } else {
                    logger.info("===>仓储订单仓储先完成,订单号为{}", orderCode);
                    rt.opsForValue().set(orderCode,orderCode);
                }
            } else {
                status.setOrderStatus(OrderStatusEnum.BEEN_COMPLETED.getCode());
                status.setStatusDesc(OrderStatusEnum.BEEN_COMPLETED.getDesc());
                //更新订单完成时间
//                ofcFundamentalInformation.setFinishedTime(new Date());
            }
        }
        return status;
    }


    private void conversionUnitQuantity(List<FeedBackOrderDetailDto> detailDtos, OfcWarehouseInformation ofcWarehouseInformation, OfcFundamentalInformation ofcFundamentalInformation) {
        for (FeedBackOrderDetailDto goodsInfo :detailDtos) {
            if (WAREHOUSE_DIST_ORDER.equals(ofcFundamentalInformation.getOrderType())) {
                CscGoodsApiDto cscGoods = new CscGoodsApiDto();
                String goodsCode = goodsInfo.getGoodsCode();
                String unit = goodsInfo.getUnit();
                String warehouseCode = ofcWarehouseInformation.getWarehouseCode();
                String custCode = ofcFundamentalInformation.getCustCode();
                String orderCode = ofcFundamentalInformation.getOrderCode();
                String key =  custCode+":"+warehouseCode+":" + goodsCode;
                String packageKey = key +":"+unit;
                /**缓存中有包装先从缓存中取**/
                if (isSwitchCache()) {
                    if (rt.hasKey(packageKey)) {
                        String  v = rt.opsForValue().get(packageKey);
                        BigDecimal ls = new BigDecimal(v);
                        BigDecimal pquantity;
                        BigDecimal realQuantity = goodsInfo.getRealQuantity();
                        pquantity = accountRealQuantity(custCode, realQuantity, ls);
                        goodsInfo.setRealQuantity(pquantity);
                        continue;
                    }
                } else {
                    rt.delete(packageKey);
                }

                List<GoodsPackingDto>  packages;
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
                logger.info("匹配包装的参数为:{}",cscGoods);
                Wrapper<PageInfo<CscGoodsApiVo>> goodsRest = ofcGoodsDetailsInfoService.validateGoodsByCode(cscGoods);
                logger.info("匹配包装的响应结果为:{}",goodsRest);
                if (goodsRest != null && Wrapper.SUCCESS_CODE == goodsRest.getCode() && goodsRest.getResult() != null &&
                        PubUtils.isNotNullAndBiggerSize(goodsRest.getResult().getList(), 0)) {
                    CscGoodsApiVo cscGoodsApiVo = goodsRest.getResult().getList().get(0);
                    packages = cscGoodsApiVo.getGoodsPackingDtoList();
                    if (!CollectionUtils.isEmpty(packages)) {
                        for (GoodsPackingDto packingDto : packages) {
                                String levelDescription = packingDto.getLevelDescription();
                            if (StringUtils.equals(unit,levelDescription)) {
                                logger.info("orderCode is {}",orderCode);
                                logger.info("unit is {}",unit);
                                logger.info("packingDto.getLevelDescription() is {}",levelDescription);
                                BigDecimal pquantity;
                                BigDecimal realQuantity = goodsInfo.getRealQuantity();
                                BigDecimal ls = packingDto.getLevelSpecification();
                                if (!(ls == null || ls.compareTo(new BigDecimal(0)) == 0)) {
                                    logger.info("订单号为:{}的货品编码为:{}转化为原包装的数量",orderCode,goodsCode);
                                    logger.info("主单位的数量为:{}转化率为:{}",realQuantity.doubleValue(),ls.doubleValue());
                                    pquantity = accountRealQuantity(custCode, realQuantity, ls);
                                    goodsInfo.setRealQuantity(pquantity);
                                    if (isSwitchCache()) {
                                        key = key +":" +levelDescription;
                                        rt.opsForValue().set(key,ls.toString());
                                        /**缓存1天**/
                                        rt.expire(key,1, TimeUnit.DAYS);
                                    }

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

    private BigDecimal accountRealQuantity(String custCode, BigDecimal realQuantity, BigDecimal ls) {
        BigDecimal pquantity;
        //大成客户特殊处理
        if ("100259".equals(custCode)) {
            pquantity = realQuantity.multiply(ls).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        } else {
            //保留三位小数
            pquantity = realQuantity.divide(ls,3,BigDecimal.ROUND_HALF_DOWN);
        }
        return pquantity;
    }

    /**
     *
     * @param orderCode 订单号
     * @return 状态跟踪
     */
    @Override
    public OfcTraceOrderDTO queryOrderByCode(String orderCode) throws Exception {
        logger.info("查单的订单号为:{}",orderCode);
        String departureTime = "";//发运时间
        String signTime = "";//签收时间
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
        CheckUtils.checkArgument(ofcFundamentalInformation == null, ResultCodeEnum.RESULTISNULL);
        if (ofcFundamentalInformation.getOrderType().equals(WAREHOUSE_DIST_ORDER)) {

          OfcWarehouseInformation ofcWarehouseInformation = new OfcWarehouseInformation();
          ofcWarehouseInformation.setOrderCode(orderCode);
          List<OfcWarehouseInformation> ofcWarehouseInformations = ofcWarehouseInformationService.select(ofcWarehouseInformation);
          if (!CollectionUtils.isEmpty(ofcWarehouseInformations) && ofcWarehouseInformations.size() == 1) {
              CheckUtils.checkArgument(ofcWarehouseInformations.get(0).getProvideTransport()!=WEARHOUSE_WITH_TRANS, ResultCodeEnum.ISNOTSUPPORT);
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

    public void updateOrderNewStatus(OfcOrderStatus ofcOrderStatus, String tag) {
        OfcOrderNewstatus orderNewstatus = new OfcOrderNewstatus();
        orderNewstatus.setOrderCode(ofcOrderStatus.getOrderCode());
        orderNewstatus.setOrderLatestStatus(ofcOrderStatus.getOrderStatus());
        logger.info("订单更新状态订单号为{},tag为{}:",ofcOrderStatus.getOrderCode(),tag);
        if (tag.equals("haveStatus")) {
            orderNewstatus.setStatusUpdateTime(new Date());
            ofcOrderNewstatusService.update(orderNewstatus);
        } else if (tag.equals("noStatus")) {
            orderNewstatus.setStatusUpdateTime(new Date());
            orderNewstatus.setStatusCreateTime(new Date());
            ofcOrderNewstatusService.save(orderNewstatus);
        }
    }


    private boolean  isSwitchCache() {
        boolean isCache = false;
        //是否开启包装缓存的开关 on 开启
        OfcRuntimeProperty  ofcRuntimeProperty=  ofcRuntimePropertyService.findByName("good_package_cache_switch");
        if (ofcRuntimeProperty != null) {
            if ("on".equals(ofcRuntimeProperty.getValue())) {
                isCache = true;
            }
        }
        return isCache;
    }
}
