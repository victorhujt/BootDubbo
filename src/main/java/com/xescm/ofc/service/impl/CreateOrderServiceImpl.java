package com.xescm.ofc.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xescm.ofc.constant.ResultModel;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.coo.*;
import com.xescm.ofc.domain.dto.csc.*;
import com.xescm.ofc.domain.dto.csc.domain.CscContact;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsVo;
import com.xescm.ofc.domain.dto.csc.vo.CscStorevo;
import com.xescm.ofc.domain.dto.epc.CannelOrderVo;
import com.xescm.ofc.feign.client.*;
import com.xescm.ofc.mapper.OfcCreateOrderMapper;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.ofc.utils.JsonUtil;
import com.xescm.uam.domain.UamUser;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.xescm.ofc.enums.OrderConstEnum.*;

/**
 * 订单api
 * Created by hiyond on 2016/11/15.
 */
@Service
public class CreateOrderServiceImpl implements CreateOrderService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private OfcOrderStatusService ofcOrderStatusService;
    @Autowired
    private CodeGenUtils codeGenUtils;
    @Autowired
    private OfcCreateOrderErrorLogService ofcCreateOrderErrorLogService;
    @Autowired
    private OfcCreateOrderService ofcCreateOrderService;
    @Autowired
    private OfcOrderManageService ofcOrderManageService;


    @Override
    public boolean CreateOrders(List<CreateOrderEntity> list) {
        return false;
    }

    /**
     * 创单api  custOrderCode与typeId是相同的
     *
     * @param data 传入的json格式的字符串
     * @throws Exception
     */
    public String createOrder(String data) throws Exception {
        logger.info("订单中心创建订单接口开始");
        //组装接口的返回信息
        List<CreateOrderResult> createOrderResultList = null;
        try {
            List<CreateOrderEntity> createOrderEntityList = (List<CreateOrderEntity>) JsonUtil.json2List(data, new TypeReference<List<CreateOrderEntity>>() {
            });
            if (!CollectionUtils.isEmpty(createOrderEntityList)) {

                //createOrderResultList = new ArrayList<CreateOrderResult>();
                createOrderResultList = new ArrayList<>();
                ResultModel resultModel;
                //返回结果
                boolean result = false;
                //原因
                String reason = null;
                //客户订单编号
                String custOrderCode = null;
                //订单编号
                for (CreateOrderEntity createOrderEntity : createOrderEntityList) {
                    try {
                        custOrderCode = createOrderEntity.getCustOrderCode();
                        String orderCode = codeGenUtils.getNewWaterCode("SO", 6);
                        resultModel = ofcCreateOrderService.ofcCreateOrder(createOrderEntity, orderCode);
                        if (!StringUtils.equals(resultModel.getCode(), ResultModel.ResultEnum.CODE_0000.getCode())) {
                            addCreateOrderEntityList(result, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                        } else {
                            result = true;
                            addCreateOrderEntityList(true, reason, custOrderCode, orderCode, resultModel, createOrderResultList);
                        }
                        logger.info("校验数据成功，执行创单操作成功；orderCode:{}", orderCode);
                    } catch (Exception ex) {
                        addCreateOrderEntityList(false, reason, custOrderCode, null, new ResultModel(ResultModel.ResultEnum.CODE_9999), createOrderResultList);
                        saveErroeLog(createOrderEntity.getCustOrderCode(), createOrderEntity.getCustCode(), createOrderEntity.getOrderTime(), ex);
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
                StringBuffer reason = new StringBuffer();
                List<MessageDto> typeIdList = new ArrayList<>();
                for (int index = 0; index < createOrderResultList.size(); index++) {
                    CreateOrderResult orderResult = createOrderResultList.get(index);
                    if (orderResult.getResult() == false) {
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
                String createOrderResultDtoJson = JsonUtil.object2Json(createOrderResultDto);
                return createOrderResultDtoJson;
            }
            logger.debug("订单中心创建订单接口结束");
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
        reason = resultModel.getDesc();
        CreateOrderResult createOrderResult = new CreateOrderResult(result, reason, custOrderCode, orderCode);
        createOrderResultList.add(createOrderResult);
    }


    @Transactional
    @Override
    public Wrapper<CannelOrderVo> cancelOrderStateByOrderCode(String custOrderCode, String orderCode, String custCode) {
        CannelOrderVo cannelOrderVo = new CannelOrderVo();
        cannelOrderVo.setCustOrderCode(custOrderCode);
        cannelOrderVo.setCustCode(custCode);
        cannelOrderVo.setResultCode("0");
        if (StringUtils.isBlank(orderCode)) {
            cannelOrderVo.setReason("发货单号不存在");
            cannelOrderVo.setResultCode("0");
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, cannelOrderVo);
        }
        OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.queryOrderStateByOrderCode(orderCode);
        String orderState = ofcOrderStatus.getOrderStatus();
        if (StringUtils.equals(orderState, HASBEENCOMPLETED)) {
            cannelOrderVo.setReason("已完成的订单");
            cannelOrderVo.setResultCode("0");
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, cannelOrderVo);
        } else if (StringUtils.equals(orderState, HASBEENCANCELED)) {
            cannelOrderVo.setReason("已取消的订单");
            cannelOrderVo.setResultCode("0");
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, cannelOrderVo);
        } else {
            AuthResDto authResDto = new AuthResDto();
            UamUser uamUser = new UamUser();
            uamUser.setUserName(CREATE_ORDER_BYAPI);
            authResDto.setUamUser(uamUser);
            String result = ofcOrderManageService.orderCancel(orderCode, orderState, authResDto);
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
    public void saveErroeLog(String cCustOrderCode, String custCode, String orderTime, Exception ex) {
        OfcCreateOrderErrorLog ofcCreateOrderErrorLog = new OfcCreateOrderErrorLog();
        ofcCreateOrderErrorLog.setCustOrderCode(cCustOrderCode);
        ofcCreateOrderErrorLog.setCustCode(custCode);
        ofcCreateOrderErrorLog.setOrderTime(DateUtils.String2Date(DateUtils.dateSubStringGetYMD(orderTime), DateUtils.DateFormatType.TYPE2));
        ofcCreateOrderErrorLog.setErrorLog(ex.toString());
        ofcCreateOrderErrorLogService.save(ofcCreateOrderErrorLog);
    }

}
