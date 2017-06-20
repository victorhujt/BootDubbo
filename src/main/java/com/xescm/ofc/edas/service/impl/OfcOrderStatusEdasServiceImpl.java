package com.xescm.ofc.edas.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.edas.model.dto.epc.QueryOrderStatusDto;
import com.xescm.ofc.edas.model.dto.ofc.OfcTraceOrderDTO;
import com.xescm.ofc.edas.model.dto.whc.FeedBackInventoryDto;
import com.xescm.ofc.edas.service.OfcOrderStatusEdasService;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CheckUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by wangsongtao on 2016/12/27.
 */
@Service
public class OfcOrderStatusEdasServiceImpl implements OfcOrderStatusEdasService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Resource
    private CreateOrderService createOrderService;

    @Resource
    private OfcOrderNewstatusService OfcOrderNewstatusService;
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private OfcOrderScreenService ofcOrderScreenService;

    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;

    @Override
    public Wrapper<List<QueryOrderStatusDto>> queryOrderStatus(QueryOrderStatusDto queryOrderStatusDto) {
        logger.info("取消订单接口参数：queryOrderStatusDto：{}", ToStringBuilder.reflectionToString(queryOrderStatusDto));
        try {
            if (queryOrderStatusDto == null) {
                throw new IllegalArgumentException("查询QueryOrderStatusDto参数不能为空");
            }
            if (StringUtils.isBlank(queryOrderStatusDto.getCustCode())) {
                throw new IllegalArgumentException("客户订单编号不能为空");
            }
            List<QueryOrderStatusDto> list = createOrderService.queryOrderStatusList(queryOrderStatusDto);
            Wrapper<List<QueryOrderStatusDto>> wrapper = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, list);
            return wrapper;
        } catch (IllegalArgumentException ex) {



            logger.error("取消订单接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (BusinessException ex) {
            logger.error("取消订单接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("取消订单接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     * 仓储中心反馈异常
     * @param feedBackInventoryDto
     * @return
     */
    @Override
    public Wrapper<?> feedBackInventory(FeedBackInventoryDto feedBackInventoryDto) {
        try{
            if(feedBackInventoryDto==null){
                throw new IllegalArgumentException("反馈库存异常Dto不能为空");
            }
            if(StringUtils.isEmpty(feedBackInventoryDto.getOrderCode())){
                throw new IllegalArgumentException("订单号不能为空");
            }
            if(StringUtils.isEmpty(feedBackInventoryDto.getReason())){
                throw new IllegalArgumentException("异常原因不能为空");
            }
            OfcOrderNewstatusService.FeedBackInventory(feedBackInventoryDto);

        }catch (Exception e){
            WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage() );
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }

    /**
     *
     * @param code 运单单号或者订单号或者客户订单号
     * @return 订单号的集合
     */
    @Override
    public Wrapper queryOrderByCode(String code) {
        boolean isExistMore = false;
        List<String> orderCodeList = new ArrayList<>();
        try {
            CheckUtils.checkArgument(PubUtils.isSEmptyOrNull(code), ResultCodeEnum.PARAMERROR);
            logger.info("订单查询 ==> code : {}", code);
            //查询结果是订单号集合
            List<String> result = ofcOrderScreenService.searchOverallOrder(code);
            CheckUtils.checkArgument(CollectionUtils.isEmpty(result), ResultCodeEnum.RESULTISNULL);
            logger.info("订单查询的结果集为: {}", JacksonUtil.toJson(result));
            if(result.size() > 1){
                isExistMore = true;
                for (String orderCode:result) {
                    OfcFundamentalInformation ofcFundamentalInformation = ofcFundamentalInformationService.selectByKey(orderCode);
                    if(ofcFundamentalInformation != null){
                        String custName = ofcFundamentalInformation.getCustName();
                        orderCodeList.add(custName+"_"+orderCode);
                    }
                }
            }
            if(isExistMore){
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, orderCodeList);
            }else{
                return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
            }
        } catch (BusinessException ex) {
            logger.error("订单查询出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(ResultCodeEnum.getErrorCode(ex.getCode()), ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单查询出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param orderCode 订单号
     * @return 订单号的状态跟踪
     */
    @Override
    public Wrapper<OfcTraceOrderDTO> traceByOrderCode(String orderCode) {
            OfcTraceOrderDTO ofcTraceOrderDTO;
        try {
            CheckUtils.checkArgument(PubUtils.isSEmptyOrNull(orderCode), ResultCodeEnum.PARAMERROR);
            ofcTraceOrderDTO = ofcOrderStatusService.queryOrderByCode(orderCode);
            CheckUtils.checkArgument(CollectionUtils.isEmpty(ofcTraceOrderDTO.getOfcOrderStatusDTOs()), ResultCodeEnum.RESULTISNULL);
        }catch (BusinessException e){
            return WrapMapper.wrap(ResultCodeEnum.getErrorCode(e.getCode()), e.getMessage());
        }catch (Exception e){
            logger.error("订单跟踪查询出现异常:{}", e.getMessage(), e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ofcTraceOrderDTO);
    }
}
