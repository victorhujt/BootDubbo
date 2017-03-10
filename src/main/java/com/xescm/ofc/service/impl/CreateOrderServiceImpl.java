package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.constant.GenCodePreffixConstant;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.OfcCreateOrderErrorLog;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.edas.model.dto.epc.CancelOrderDto;
import com.xescm.ofc.edas.model.dto.epc.QueryOrderStatusDto;
import com.xescm.ofc.edas.model.vo.epc.CannelOrderVo;
import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.model.dto.coo.CreateOrderEntity;
import com.xescm.ofc.model.dto.coo.CreateOrderResult;
import com.xescm.ofc.model.dto.coo.CreateOrderResultDto;
import com.xescm.ofc.model.dto.coo.MessageDto;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
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
    private OfcCreateOrderMapper ofcCreateOrderMapper;


    @Override
    public boolean CreateOrders(List<CreateOrderEntity> list) {
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
            List<CreateOrderEntity> createOrderEntityList = JacksonUtil.parseJsonWithFormat(data, new TypeReference<List<CreateOrderEntity>>() {
            });
            if (!CollectionUtils.isEmpty(createOrderEntityList)) {
                createOrderResultList = new ArrayList<>();
                ResultModel resultModel;
                boolean result = false;
                String reason = null;
                String custOrderCode = null;
                for (CreateOrderEntity createOrderEntity : createOrderEntityList) {
                    synchronized (this) {
                        try {
                            custOrderCode = createOrderEntity.getCustOrderCode();
                            String custCode = createOrderEntity.getCustCode();
                            OfcFundamentalInformation information = ofcFundamentalInformationService.queryOfcFundInfoByCustOrderCodeAndCustCode(custOrderCode, custCode);
                            if (information != null) {
                                String orderCode = information.getOrderCode();
                                OfcOrderStatus queryOrderStatus = ofcOrderStatusService.queryLastTimeOrderByOrderCode(orderCode);
                                //订单已存在,获取订单的最新状态,只有待审核的才能更新
                                if (queryOrderStatus != null && !StringUtils.equals(queryOrderStatus.getOrderStatus(), PENDING_AUDIT)) {
                                    logger.error("订单已经审核，跳过创单操作！custOrderCode:{},custCode:{}", custOrderCode, custCode);
                                    addCreateOrderEntityList(true, "订单已经审核，跳过创单操作", custOrderCode, orderCode, new ResultModel(ResultModel.ResultEnum.CODE_1001), createOrderResultList);
                                    return "";
                                }
                            }
                            String orderCode = codeGenUtils.getNewWaterCode(GenCodePreffixConstant.ORDER_PRE, 6);
                            resultModel = ofcCreateOrderService.ofcCreateOrder(createOrderEntity, orderCode);
                            if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                                addCreateOrderEntityList(result, resultModel.getDesc(), custOrderCode, orderCode, resultModel, createOrderResultList);
                                reason = resultModel == null ? "" : resultModel.getDesc();
                                logger.error("执行创单操作失败：custOrderCode,{},custCode:{},resson:{}", custOrderCode, custCode, reason);
                            } else {
                                result = true;
                                addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                                logger.info("校验数据成功，执行创单操作成功；custOrderCode,{},custCode:{},orderCode:{}", custOrderCode, custCode, orderCode);
                            }
                        } catch (Exception ex) {
                            logger.error("订单中心创建订单接口异常: {}, {}", ex, ex.getMessage());
                            addCreateOrderEntityList(false, reason, custOrderCode, null, new ResultModel(ResultModel.ResultEnum.CODE_9999), createOrderResultList);
                            saveErroeLog(createOrderEntity.getCustOrderCode(), createOrderEntity.getCustCode(), createOrderEntity.getOrderTime(), ex);
                        }
                    }
                }
            }
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
        if(PubUtils.trimAndNullAsEmpty(reason).equals("")) reason = resultModel.getDesc();
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

}
