package com.xescm.ofc.web.restcontroller;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.ofc.constant.OrderConstant;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import com.xescm.ofc.service.OfcOrderDtoService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xescm.ofc.constant.OrderConstant.STOCK_IN_ORDER;

/**
 *
 * Created by lyh on 2016/10/12.
 * 客户中心订单追踪
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderFollowRest extends BaseController{
    @Resource
    private OfcOrderDtoService ofcOrderDtoService;
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @RequestMapping(value = "/orderFollowCon",method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> orderFollowCon( String code, String followTag) throws InvocationTargetException {
        logger.info("==>订单中心订单追踪条件筛选code code={}", code);
        logger.info("==>订单中心订单追踪条件标志位 followTag={}", followTag);
        Map<String, Object> map = new HashMap<>();
        try{
            code = PubUtils.trimAndNullAsEmpty(code);
            followTag = PubUtils.trimAndNullAsEmpty(followTag);
            OfcOrderDTO ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(code, followTag);
            List<OfcOrderStatus> ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen(code, followTag);//PubUtils.trimAndNullAsEmpty

            map.put("ofcOrderDTO",ofcOrderDTO);
            map.put("ofcOrderStatus",ofcOrderStatuses);
        }catch (BusinessException ex) {
            logger.error("订单中心订单追踪出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }catch (Exception ex){
            logger.error("订单中心订单追踪出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,Wrapper.SUCCESS_MESSAGE,map);
    }


    @RequestMapping(value = "/orderDetails/{orderCode}/{followTag}/{historyUrlTag}", method = RequestMethod.GET)
    public String orderDetails(@PathVariable("orderCode") String code,@PathVariable String followTag,@PathVariable String historyUrlTag, Map<String,Object> map) throws InvocationTargetException{
        logger.info("==>订单中心订单详情code code={}", code);
        logger.info("==>订单中心订单详情标志位 followTag={}", followTag);
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
        List<OfcOrderStatus> ofcOrderStatuses = new ArrayList<>();
        
        List<OfcGoodsDetailsInfo> goodsDetailsList = new ArrayList<>();
        CscSupplierInfoDto supportMessage = null;
        try{
            ofcOrderDTO = ofcOrderDtoService.orderDtoSelect(code, followTag);
            ofcOrderStatuses = ofcOrderStatusService.orderStatusScreen(code, followTag);
            goodsDetailsList=ofcGoodsDetailsInfoService.goodsDetailsScreenList(code,followTag);
            //仓配订单
            if(OrderConstant.WAREHOUSE_DIST_ORDER.equals(ofcOrderDTO.getOrderType())){
                String businessTypeHead = ofcOrderDTO.getBusinessType().substring(0,2);
                //如果是仓配订单而且业务类型是入库单,就去找供应商信息
                if(STOCK_IN_ORDER.equals(businessTypeHead)){
                    if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getSupportName()) || !PubUtils.isSEmptyOrNull(ofcOrderDTO.getSupportContactName()) ){
                        supportMessage = ofcOrderManageService.getSupportMessage(ofcOrderDTO.getSupportName(),ofcOrderDTO.getSupportContactName(),authResDtoByToken.getGroupRefCode(),authResDtoByToken);
                    }
                }
            }

        }catch (Exception ex){
            logger.error("订单中心订单追踪出现异常:{}", ex.getMessage(), ex);
        }
        map.put("ofcOrderDTO",ofcOrderDTO);
        map.put("orderStatusList",ofcOrderStatuses);
        map.put("goodsDetailsList",goodsDetailsList);
        map.put("supportMessage",supportMessage);
        if("orderManage".equals(historyUrlTag)){
            map.put("historyUrl",historyUrlTag);
        }else if("orderScreen".equals(historyUrlTag)){
            map.put("historyUrl",historyUrlTag);
        }

        return "order_detail";
    }



 }

