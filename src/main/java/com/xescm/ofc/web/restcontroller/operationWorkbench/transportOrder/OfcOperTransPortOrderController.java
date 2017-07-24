package com.xescm.ofc.web.restcontroller.operationWorkbench.transportOrder;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.enums.BusinessTypeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.service.OfcOrderPlaceService;
import com.xescm.ofc.web.controller.BaseController;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by hujintao on 2017/7/23.
 */
@Controller
@RequestMapping(value = "/page/ofc/transPort")
public class OfcOperTransPortOrderController extends BaseController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcOrderPlaceService ofcOrderPlaceService;


    /**
     * 订单中心下单
     * @param ofcOrderDTOStr        订单基本信息、收发货方信息
     * @param tag           标识下单、编辑、运输开单
     * @return      Wrapper
     */
    @RequestMapping("/orderPlaceCon/{tag}")
    @ResponseBody
    public Wrapper<?> orderPlace(@RequestBody OfcOrderDTO ofcOrderDTOStr, @PathVariable  String tag){
        logger.info("==>订单中心下单或编辑实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        logger.info("==>订单中心下单或编辑标志位 tag={}", tag);
        String resultMessage;
        try {
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            if (ofcOrderDTOStr == null) {
                throw new BusinessException("订单中心下单dto不能为空！");
            }
            if(null == ofcOrderDTOStr.getOrderTime()){
                throw new BusinessException("请选择订单日期");
            }
            if(CollectionUtils.isEmpty(ofcOrderDTOStr.getGoodsList())){
                throw new BusinessException("请至少添加一条货品！");
            };
            if(CollectionUtils.isEmpty(ofcOrderDTOStr.getGoodsList())){
                throw new BusinessException("请至少添加一条货品！");
            };
            if(ofcOrderDTOStr.getConsignor() == null){
                throw new BusinessException("发货人信息不允许为空！");
            }
            if(ofcOrderDTOStr.getConsignee() == null){
                throw new BusinessException("发货人信息不允许为空！");
            }
            //校验业务类型，如果是卡班，必须要有运输单号
            if(StringUtils.equals(ofcOrderDTOStr.getBusinessType(), BusinessTypeEnum.CABANNES.getCode())){
                if(StringUtils.isBlank(ofcOrderDTOStr.getTransCode())){
                    throw new BusinessException("业务类型是卡班，运输单号是必填项");
                }
            }
            if (null == ofcOrderDTOStr.getProvideTransport()){
                ofcOrderDTOStr.setProvideTransport(OrderConstConstant.WAREHOUSE_NO_TRANS);
            }
            if (null == ofcOrderDTOStr.getUrgent()){
                ofcOrderDTOStr.setUrgent(OrderConstConstant.DISTRIBUTION_ORDER_NOT_URGENT);
            }
            resultMessage = ofcOrderPlaceService.placeOrder(ofcOrderDTOStr,ofcOrderDTOStr.getGoodsList(),tag,authResDtoByToken,authResDtoByToken.getGroupRefCode()
                    ,ofcOrderDTOStr.getConsignor(),ofcOrderDTOStr.getConsignee(),ofcOrderDTOStr.getSupplier());
        } catch (BusinessException ex){
            logger.error("订单中心下单或编辑出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        } catch (Exception ex) {
            logger.error("订单中心下单或编辑出现未知异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);

        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,resultMessage);
    }


}
