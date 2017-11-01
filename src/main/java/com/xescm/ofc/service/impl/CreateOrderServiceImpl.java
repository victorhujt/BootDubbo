package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSON;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.epc.edas.dto.dachen.LockStockOrderDTO;
import com.xescm.ofc.constant.CreateOrderApiConstant;
import com.xescm.ofc.constant.GenCodePreffixConstant;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.OfcCreateOrderErrorLog;
import com.xescm.ofc.domain.OfcEnumeration;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.edas.model.dto.epc.CancelOrderDto;
import com.xescm.ofc.edas.model.dto.epc.QueryOrderStatusDto;
import com.xescm.ofc.edas.model.dto.ofc.OfcCreateOrderDTO;
import com.xescm.ofc.edas.model.vo.epc.CannelOrderVo;
import com.xescm.ofc.enums.ExceptionTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.model.dto.coo.CreateOrderResult;
import com.xescm.ofc.model.dto.coo.CreateOrderResultDto;
import com.xescm.ofc.model.dto.coo.MessageDto;
import com.xescm.ofc.mq.producer.CreateOrderApiProducer;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.uam.provider.DistributedLockEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.xescm.ofc.constant.BaseConstant.MQ_TAG_OrderToOfc;
import static com.xescm.ofc.constant.BaseConstant.REDIS_LOCK_PREFIX;
import static com.xescm.ofc.constant.OrderConstConstant.*;

/**
 * 订单api
 * Created by hiyond on 2016/11/15.
 */
@Service
public class CreateOrderServiceImpl implements CreateOrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private CodeGenUtils codeGenUtils;
    @Resource
    private OfcCreateOrderErrorLogService ofcCreateOrderErrorLogService;
    @Resource
    private OfcCreateOrderService ofcCreateOrderService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private CscCustomerEdasService cscCustomerEdasService;
    @Resource
    private OfcCreateOrderMapper ofcCreateOrderMapper;
    @Resource
    private DistributedLockEdasService distributedLockEdasService;
    @Resource
    private CreateOrderApiProducer createOrderApiProducer;
    @Resource
    private OfcEnumerationService ofcEnumerationService;

    @Override
    public boolean CreateOrders(List<OfcCreateOrderDTO> list) {
        return false;
    }

    /**
     * 创单api  custOrderCode与typeId是相同的
     *
     * @param data 传入的json格式的字符串
     * @throws Exception    异常
     */
    public String createOrder(String data) throws Exception {
        logger.info("订单中心创建订单接口开始");
        //组装接口的返回信息
        List<CreateOrderResult> createOrderResultList = null;
        try {
            List<OfcCreateOrderDTO> createOrderEntityList = JacksonUtil.parseJsonWithFormat(data, new TypeReference<List<OfcCreateOrderDTO>>() {
            });
            if (!CollectionUtils.isEmpty(createOrderEntityList)) {
                createOrderResultList = new ArrayList<>();
                ResultModel resultModel;
                boolean result = false;
                String reason = null;
                String custOrderCode = null;
                String key = null;
                for (OfcCreateOrderDTO createOrderEntity : createOrderEntityList) {
                    String platformType = createOrderEntity.getPlatformType();
                    AtomicBoolean lockStatus = new AtomicBoolean(false);
                    try {
                        custOrderCode = createOrderEntity.getCustOrderCode();
                        String custCode = createOrderEntity.getCustCode();
                        String orderCode = null;
                        // 对创建订单操作进行加锁，防止订单创建重复
                        // redis key : OFC:MQ:xeOrderToOfc:<客户编码>:<客户订单号>
                        key = REDIS_LOCK_PREFIX + MQ_TAG_OrderToOfc + ":" + custCode + ":" + custOrderCode;
                        // 验证锁是否存在
                        Wrapper<String> checkLock = distributedLockEdasService.checkLocksExist(key);
                        if (Wrapper.SUCCESS_CODE != checkLock.getCode()) {
                            // 加锁
                            Wrapper<Integer> lock = distributedLockEdasService.addLock(key, 5);
                            if (lock.getCode() == Wrapper.SUCCESS_CODE && lock.getResult().intValue() == 1) {
                                lockStatus.set(true);
                                OfcFundamentalInformation information = ofcFundamentalInformationService.queryOfcFundInfoByCustOrderCodeAndCustCode(custOrderCode, custCode);
                                if (information != null) {
                                    orderCode = information.getOrderCode();
                                    OfcOrderStatus queryOrderStatus = ofcOrderStatusService.queryLastTimeOrderByOrderCode(orderCode);
                                    //订单已存在,获取订单的最新状态,只有待审核的才能更新
                                    if (queryOrderStatus != null && !StringUtils.equals(queryOrderStatus.getOrderStatus(), PENDING_AUDIT)) {
                                        logger.error("订单已经审核，跳过创单操作！custOrderCode:{},custCode:{}", custOrderCode, custCode);
                                        if (CreateOrderApiConstant.XEBEST_CUST_CODE_TEST.equals(platformType)) {
                                            addCreateOrderEntityList(true, "订单已经创建并审核，跳过创单操作", custOrderCode, orderCode, new ResultModel(ResultModel.ResultEnum.CODE_1001), createOrderResultList);
                                        }
                                        return "";
                                    }
                                }
                                // 如果订单存在（待审核），则更新订单，单号不重新生成
                                orderCode = orderCode != null ? orderCode : codeGenUtils.getNewWaterCode(GenCodePreffixConstant.ORDER_PRE, 6);
                                //调用创建方法
                                resultModel = ofcCreateOrderService.ofcCreateOrder(createOrderEntity, orderCode);
                                if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                                    if (CreateOrderApiConstant.XEBEST_CUST_CODE_TEST.equals(platformType)) {
                                        addCreateOrderEntityList(result, resultModel.getDesc(), custOrderCode, orderCode, resultModel, createOrderResultList);
                                    }
                                    reason = resultModel == null ? "" : resultModel.getDesc();
                                    logger.error("执行创单操作失败：custOrderCode,{},custCode:{},resson:{}", custOrderCode, custCode, reason);
                                } else {
                                    result = true;
                                    if (CreateOrderApiConstant.XEBEST_CUST_CODE_TEST.equals(platformType)) {
                                        addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                                    }
                                    logger.info("校验数据成功，执行创单操作成功；custOrderCode,{},custCode:{},orderCode:{}", custOrderCode, custCode, orderCode);
                                }
                            } else {
                                throw new BusinessException(ExceptionTypeEnum.LOCK_FAIL.getCode(), ExceptionTypeEnum.LOCK_FAIL.getDesc());
                            }
                        } else {
                            throw new BusinessException(ExceptionTypeEnum.LOCK_EXIST.getCode(), ExceptionTypeEnum.LOCK_EXIST.getDesc());
                        }
                    } catch (BusinessException ex) {
                        logger.error("订单中心创建订单接口出错:{},{}", ex.getMessage(), ex);
                        throw ex;
                    } catch (Exception ex) {
                        logger.error("订单中心创建订单接口异常: {}, {}", ex, ex.getMessage());
                        if (CreateOrderApiConstant.XEBEST_CUST_CODE_TEST.equals(platformType)) {
                            addCreateOrderEntityList(false, reason, custOrderCode, null, new ResultModel(ResultModel.ResultEnum.CODE_9999), createOrderResultList);
                        }
                        saveErroeLog(createOrderEntity.getCustOrderCode(), createOrderEntity.getCustCode(), createOrderEntity.getOrderTime(), ex);
                    } finally {
                        // 释放锁
                        if (lockStatus.get()) {
                            distributedLockEdasService.clearLock(key);
                        }
                    }
                }
            }
        } catch (BusinessException ex) {
            logger.error("订单中心创建订单接口出错:{},{}", ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("订单中心创建订单接口出错:{},{}", ex.getMessage(), ex);
            throw new Exception(ex);
        } finally {
            //转换返回结果
            if (!CollectionUtils.isEmpty(createOrderResultList)) {
                String code = "200";
                StringBuilder reason = new StringBuilder();
                List<MessageDto> typeIdList = new ArrayList<>();
                for (int index = 0; index < createOrderResultList.size(); index++) {
                    CreateOrderResult orderResult = createOrderResultList.get(index);
                    if (!orderResult.getResult()) {
                        code = "500";
                    }
                    String typeIdAndReason = "typeId:" + orderResult.getTypeId() + "||reason:" + orderResult.getReason();
                    if (index != createOrderResultList.size() - 1) {
                        typeIdAndReason = typeIdAndReason + ",";
                    }
                    reason.append(typeIdAndReason);
                    MessageDto messageDto = new MessageDto();
                    messageDto.setTypeId(orderResult.getTypeId());
                    typeIdList.add(messageDto);
                }
                CreateOrderResultDto createOrderResultDto = new CreateOrderResultDto();
                createOrderResultDto.setCode(code);
                createOrderResultDto.setReason(reason.toString());
                createOrderResultDto.setMessage(typeIdList);

                //要反回的json格式的字符串
                String createOrderResultDtoJson = JacksonUtil.toJson(createOrderResultDto);
                return createOrderResultDtoJson;
            }
            logger.info("订单中心创建订单接口结束");
        }
        return null;
    }


    /**
     * 组装返回信息封装到 List<CreateOrderResult>
     *
     * @param result                结果
     * @param reason                原因
     * @param custOrderCode         客户订单编号
     * @param orderCode             orderCode
     * @param resultModel           返回的code
     * @param createOrderResultList 返回结果的集合
     */
    private void addCreateOrderEntityList(boolean result, String reason,
                                          String custOrderCode, String orderCode,
                                          ResultModel resultModel,
                                          List<CreateOrderResult> createOrderResultList) {
        if (PubUtils.trimAndNullAsEmpty(reason).equals("")) reason = resultModel.getDesc();
        CreateOrderResult createOrderResult = new CreateOrderResult(result, reason, custOrderCode, orderCode);
        createOrderResultList.add(createOrderResult);
    }


    /**
     * 取消订单
     * 客户订单编号以及货主编码 判断客户订单编号的订单是否存在
     * 不存在，返回结果代码为0,原因:发货单号不存在！
     * 若取消成功，返回结果代码为1;
     * 若取消失败，返回结果代码为0,并将执行失败的原因返回
     * 若订单状态为【待审核】、【已审核】、【执行中】则允许取消
     * 其他状态不允许取消
     * 状态已经已取消：返回订单已经取消
     * 状态已经已完成：订单已完成，无法取消
     * 状态是待审核，直接删除订单
     *
     * @param cancelOrderDto    取消实体
     * @return     CannelOrderVo
     */
    @Transactional
    @Override
    public Wrapper<CannelOrderVo> cancelOrderStateByOrderCode(CancelOrderDto cancelOrderDto) {
        CannelOrderVo cannelOrderVo = new CannelOrderVo();
        cannelOrderVo.setCustOrderCode(cancelOrderDto.getCustOrderCode());
        OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.queryOfcFundInfoByCustOrderCodeAndCustCode(cancelOrderDto.getCustOrderCode(), cancelOrderDto.getCustCode());
        if (ofcFundamentalInformation == null) {
            cannelOrderVo.setReason("发货单号不存在或已经取消");
            cannelOrderVo.setResultCode("0");
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ILLEGAL_ARGUMENT_MESSAGE, cannelOrderVo);
        }
        String custCode = ofcFundamentalInformation.getCustCode();
        String orderCode = ofcFundamentalInformation.getOrderCode();
        cannelOrderVo.setCustCode(custCode);
        cannelOrderVo.setResultCode("0");
        if (StringUtils.isBlank(orderCode)) {
            cannelOrderVo.setReason("发货单号不存在");
            cannelOrderVo.setResultCode("0");
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ILLEGAL_ARGUMENT_MESSAGE, cannelOrderVo);
        }
        OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.queryLastTimeOrderByOrderCode(orderCode);
        if (null == ofcOrderStatus) {
            cannelOrderVo.setReason("发货单号不存在");
            cannelOrderVo.setResultCode("0");
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ILLEGAL_ARGUMENT_MESSAGE, cannelOrderVo);
        }
        String orderState = ofcOrderStatus.getOrderStatus();
        if (StringUtils.equals(orderState, HASBEEN_COMPLETED)) {
            cannelOrderVo.setReason("已完成的订单");
            cannelOrderVo.setResultCode("0");
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ILLEGAL_ARGUMENT_MESSAGE, cannelOrderVo);
        } else if (StringUtils.equals(orderState, HASBEEN_CANCELED)) {
            cannelOrderVo.setReason("已取消的订单");
            cannelOrderVo.setResultCode("0");
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ILLEGAL_ARGUMENT_MESSAGE, cannelOrderVo);
        } else {
            AuthResDto authResDto = new AuthResDto();
            authResDto.setUserId(CREATE_ORDER_BYAPI);
            authResDto.setUserName(CREATE_ORDER_BYAPI);
            authResDto.setGroupRefName(CREATE_ORDER_BYAPI);
//            String result = ofcOrderManageService.orderCancel(orderCode, orderState, authResDto);
            String result = ofcOrderManageService.orderCancel(orderCode,  authResDto);
            if (StringUtils.equals("200", result)) {
                cannelOrderVo.setReason("操作成功");
                cannelOrderVo.setResultCode("1");
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, cannelOrderVo);
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, cannelOrderVo);
        }
    }

    /**
     * 保存错误信息
     *
     * @param cCustOrderCode
     * @param custCode
     * @param orderTime
     * @param ex
     */
    private void saveErroeLog(String cCustOrderCode, String custCode, String orderTime, Exception ex) {
        OfcCreateOrderErrorLog ofcCreateOrderErrorLog = new OfcCreateOrderErrorLog();
        ofcCreateOrderErrorLog.setCustOrderCode(cCustOrderCode);
        ofcCreateOrderErrorLog.setCustCode(custCode);
        ofcCreateOrderErrorLog.setOrderTime(DateUtils.String2Date(DateUtils.dateSubStringGetYMD(orderTime), DateUtils.DateFormatType.TYPE2));
        ofcCreateOrderErrorLog.setErrorLog(ex.toString());
        ofcCreateOrderErrorLogService.save(ofcCreateOrderErrorLog);
    }


    @Override
    public List<QueryOrderStatusDto> queryOrderStatusList(QueryOrderStatusDto queryOrderStatusDto) {
        return ofcCreateOrderMapper.queryOrderStatusList(queryOrderStatusDto);
    }

    /**
     * 根据任务表创建订单
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public ResultModel createOrderByTask(String data) throws Exception {
        logger.info("订单中心创建订单接口开始");
        ResultModel resultModel;
        try {
          //  CreateOrderEntity createOrderEntity = JacksonUtil.parseJsonWithFormat(data, CreateOrderEntity.class);
            OfcCreateOrderDTO createOrderEntity = JSON.parseObject(data, OfcCreateOrderDTO.class);
            String custOrderCode = createOrderEntity.getCustOrderCode();
            String platformType = createOrderEntity.getPlatformType();
            String custCode = createOrderEntity.getCustCode();
            String orderStatus = createOrderEntity.getOrderStatus();
            String orderCode = null;
            String key = null;
            String orderType = createOrderEntity.getOrderType();
            AtomicBoolean lockStatus = new AtomicBoolean(false);
            try {
                if (orderStatus != null && HASBEEN_CANCELED.equals(orderStatus)) {
                    logger.error("订单{}已经取消，跳过创单操作！", custOrderCode);
                    throw new BusinessException("订单" + custOrderCode + "已经取消，跳过创单操作！");
                }
                // 对创建订单操作进行加锁，防止订单创建重复
                // redis key : OFC:MQ:xeOrderToOfc:<客户编码>:<客户订单号>
                key = REDIS_LOCK_PREFIX + MQ_TAG_OrderToOfc + ":" + custCode + ":" + custOrderCode;
                // 验证锁是否存在
                Wrapper<String> checkLock = distributedLockEdasService.checkLocksExist(key);
                if (Wrapper.SUCCESS_CODE != checkLock.getCode()) {
                    // 加锁
                    Wrapper<Integer> lock = distributedLockEdasService.addLock(key, 80);
                    if (lock.getCode() == Wrapper.SUCCESS_CODE && lock.getResult().intValue() == 1) {
                        lockStatus.set(true);
                        OfcFundamentalInformation information = ofcFundamentalInformationService.queryOfcFundInfoByCustOrderCodeAndCustCode(custOrderCode, custCode);
                        if (information != null) {
                            orderCode = information.getOrderCode();
                            OfcOrderStatus queryOrderStatus = ofcOrderStatusService.queryLastTimeOrderByOrderCode(orderCode);
                            //订单已存在,获取订单的最新状态,只有待审核的才能更新
                            if (queryOrderStatus != null && !StringUtils.equals(queryOrderStatus.getOrderStatus(), PENDING_AUDIT)) {
                                logger.error("订单已经审核，跳过创单操作！custOrderCode:{},custCode:{}", custOrderCode, custCode);
                                if (CreateOrderApiConstant.XEBEST_CUST_CODE_TEST.equals(platformType) || CreateOrderApiConstant.DACHEN_CUST_CODE.equals(custCode)) {
                                    this.orderCreateResult(custCode, orderCode, custOrderCode, orderType,createOrderEntity.getBusinessType(), "订单已经审核，跳过创单操作", true);
                                }
                                return new ResultModel(ResultModel.ResultEnum.CODE_1001);
                            }
                        }
                        // 如果订单存在（待审核），则更新订单，单号不重新生成
                        orderCode = orderCode != null ? orderCode : codeGenUtils.getNewWaterCode(GenCodePreffixConstant.ORDER_PRE, 6);
                        //调用创建方法
                        resultModel = ofcCreateOrderService.ofcCreateOrder(createOrderEntity, orderCode);
                        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            logger.error("执行创单操作失败：custOrderCode,{},custCode:{},resson:{}", custOrderCode, custCode, resultModel.getDesc());
                            if (CreateOrderApiConstant.XEBEST_CUST_CODE_TEST.equals(platformType) || CreateOrderApiConstant.DACHEN_CUST_CODE.equals(custCode)) {
                                this.orderCreateResult(custCode, orderCode, custOrderCode,orderType, createOrderEntity.getBusinessType(), "订单创建失败:" + resultModel.getDesc(), false);
                            }
                        } else {
                            logger.info("校验数据成功，执行创单操作成功；custOrderCode,{},custCode:{},orderCode:{}", custOrderCode, custCode, orderCode);
                            if (CreateOrderApiConstant.XEBEST_CUST_CODE_TEST.equals(platformType) || CreateOrderApiConstant.DACHEN_CUST_CODE.equals(custCode)) {
                                this.orderCreateResult(custCode, orderCode, custOrderCode, orderType,createOrderEntity.getBusinessType(), "订单创建成功！", true);
                            }
                        }
                    } else {
                        logger.error("接口任务创建订单加锁失败：key={}", key);
                        throw new BusinessException(ExceptionTypeEnum.LOCK_FAIL.getCode(), ExceptionTypeEnum.LOCK_FAIL.getDesc());
                    }
                } else {
                    logger.error("接口任务创建订单锁存在或检查锁失败：key={}", key);
                    throw new BusinessException(ExceptionTypeEnum.LOCK_EXIST.getCode(), ExceptionTypeEnum.LOCK_EXIST.getDesc());
                }
            } catch (BusinessException ex) {
                logger.error("订单中心创建订单接口发生异常:{},{}", ex.getMessage(), ex);
                if (CreateOrderApiConstant.XEBEST_CUST_CODE_TEST.equals(platformType) || CreateOrderApiConstant.DACHEN_CUST_CODE.equals(custCode)) {
                    this.orderCreateResult(custCode, orderCode, custOrderCode, orderType,createOrderEntity.getBusinessType(), ResultModel.ResultEnum.CODE_9999.getDesc(), false);
                }
                throw ex;
            } catch (Exception ex) {
                logger.error("订单中心创建订单接口发生未知异常: {}, {}", ex, ex.getMessage());
                if (CreateOrderApiConstant.XEBEST_CUST_CODE_TEST.equals(platformType) || CreateOrderApiConstant.DACHEN_CUST_CODE.equals(custCode)) {
                    this.orderCreateResult(custCode, orderCode, custOrderCode, orderType,createOrderEntity.getBusinessType(), ResultModel.ResultEnum.CODE_9999.getDesc(), false);
                }
                throw ex;
            } finally {
                // 释放锁
                if (lockStatus.get()) {
                    distributedLockEdasService.clearLock(key);
                }
            }
        } catch (BusinessException ex) {
            logger.error("订单中心创建订单接口发生异常:{},{}", ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("订单中心创建订单接口发生未知异常:{},{}", ex.getMessage(), ex);
            throw ex;
        }
        return resultModel;
    }

    /**
     * 创单结果发送mq
     * @param orderCode
     * @param custOrderCode
     * @param result
     * @param isSuccess
     * @throws Exception
     */
    private void orderCreateResult(String custCode, String orderCode, String custOrderCode, String orderType,String businessType, String result, boolean isSuccess) throws Exception {
        String jsonMsg;
        String tag;
        // 大成
        String specialCust = "";
        OfcEnumeration ofcEnumeration = new OfcEnumeration();
        ofcEnumeration.setEnumSys("OFC");
        ofcEnumeration.setEnumType("SpecialCustEnum");
        ofcEnumeration.setEnumName("大成万达（天津）有限公司");
        List<OfcEnumeration> enumerations = ofcEnumerationService.queryOfcEnumerationList(ofcEnumeration);
        if (!CollectionUtils.isEmpty(enumerations)) {
            specialCust = enumerations.get(0).getEnumValue();
        }
        if (specialCust.equals(custCode)) {
            LockStockOrderDTO resultDto = new LockStockOrderDTO();
            resultDto.setOrderNo(custOrderCode);
            resultDto.setOrderType(orderType);
            resultDto.setBusinessType(businessType);
            resultDto.setPaasOrderNo(orderCode);
            resultDto.setState("L");    // 锁定
            jsonMsg = JacksonUtil.toJson(resultDto);
            tag = "DACHEN";
        } else { // 鲜易网
            CreateOrderResultDto resultDto = new CreateOrderResultDto();
            String code = isSuccess ? "200" : "500";
            String typeIdAndReason = "typeId:" + custOrderCode + "||reason:" + result;

            List<MessageDto> messageDtos = new ArrayList<>();
            MessageDto messageDto = new MessageDto();
            messageDto.setTypeId(custOrderCode);
            messageDtos.add(messageDto);

            resultDto.setCode(code);
            resultDto.setReason(typeIdAndReason);
            resultDto.setMessage(messageDtos);
            jsonMsg = JacksonUtil.toJson(resultDto);
            tag = "xeStatusBackTag";
        }
        // 发送创单结果
        createOrderApiProducer.sendCreateOrderResultMQ(jsonMsg, String.valueOf(jsonMsg.hashCode()), tag);
    }
}
