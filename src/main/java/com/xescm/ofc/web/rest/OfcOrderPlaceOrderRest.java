package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.CscContantAndCompanyDto;
import com.xescm.ofc.domain.dto.CscGoods;
import com.xescm.ofc.domain.dto.CscSupplierInfoDto;
import com.xescm.ofc.enums.OrderConstEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscSupplierAPIClient;
import com.xescm.ofc.feign.client.FeignCscWarehouseAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.ofc.wrap.WrapMapper;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.xescm.ofc.utils.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/8.
 */
@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOrderPlaceOrderRest extends BaseController{

    @Autowired
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Autowired
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Autowired
    private FeignCscGoodsAPIClient feignCscGoodsAPIClient;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private FeignCscSupplierAPIClient feignCscSupplierAPIClient;

    /**
     * 编辑
     * @param
     * @param response
     * @return
     */
    @RequestMapping("/orderEdit/{tag}/{ofcOrderDTOJson}")
    public String orderEdit(Model model, @PathVariable String ofcOrderDTOJson, @PathVariable String tag, HttpServletResponse response){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTO={}", ofcOrderDTOJson);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        if(StringUtils.isBlank(ofcOrderDTOJson)){
            logger.debug(ofcOrderDTOJson);
            ofcOrderDTOJson = JSONUtils.objectToJson(new OfcOrderDTO());
        }
        OfcOrderDTO ofcOrderDTO = JSONUtils.jsonToPojo(ofcOrderDTOJson, OfcOrderDTO.class);
        if (null == ofcOrderDTO.getProvideTransport()){
            ofcOrderDTO.setProvideTransport(OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS);
        }
        if (null == ofcOrderDTO.getUrgent()){
            ofcOrderDTO.setUrgent(OrderConstEnum.DISTRIBUTIONORDERNOTURGENT);
        }
        try {
            String result = ofcOrderPlaceService.placeOrder(ofcOrderDTO,tag);
            if(tag.equals("manage")){
                return "order_manage";
            }
            return "order_place";
        } catch (Exception e) {
            return "order_place";
        }
    }

    @RequestMapping("/orderPlaceCon")
    @ResponseBody
    public Wrapper<?> orderPlace(Model model, String ofcOrderDTOStr, String tag, HttpServletResponse response){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTOStr={}", ofcOrderDTOStr);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        String resultMessage = null;
        try {
            if(StringUtils.isBlank(ofcOrderDTOStr)){
                ofcOrderDTOStr = JSONUtils.objectToJson(new OfcOrderDTO());
            }
            OfcOrderDTO ofcOrderDTO = JSONUtils.jsonToPojo(ofcOrderDTOStr, OfcOrderDTO.class);

            if (null == ofcOrderDTO.getProvideTransport()){
                ofcOrderDTO.setProvideTransport(OrderConstEnum.WAREHOUSEORDERNOTPROVIDETRANS);
            }
            if (null == ofcOrderDTO.getUrgent()){
                ofcOrderDTO.setUrgent(OrderConstEnum.DISTRIBUTIONORDERNOTURGENT);
            }
            resultMessage = ofcOrderPlaceService.placeOrder(ofcOrderDTO,tag);

       }catch (BusinessException ex){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        catch (Exception ex) {
            logger.error("订单中心下单或编辑出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,resultMessage);
    }


    /**
     * 货品筛选
     * @param ofcGoodsDetailsInfo
     * @return
     */
    @RequestMapping("/goodsScans")
    public String placeOrder(Model model,OfcGoodsDetailsInfo ofcGoodsDetailsInfo){
        logger.debug("==>订单中心下单货品筛选实体 ofcGoodsDetailsInfo={}", ofcGoodsDetailsInfo);
        ofcGoodsDetailsInfo.setGoodsCode("1");
        ofcGoodsDetailsInfo.setGoodsCode("1");
        ofcGoodsDetailsInfoService.select(ofcGoodsDetailsInfo);
        return "order_place";
    }

    /**
     * 货品筛选(调用客户中心API)
     */
    @ApiOperation(value="下单货品筛选", notes="根据查询条件筛选货品")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "cscGoods", value = "货品筛选条件", required = true, dataType = "CscGoods"),
    })
    @RequestMapping(value = "/goodsSelect",method = RequestMethod.POST)
    public void goodsSelectByCscApi(Model model, CscGoods cscGoods, HttpServletResponse response){
/*        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        //调用外部接口,最低传CustomerCode
        cscGoods.setCustomerCode(authResDtoByToken.getGroupId());*/

        Wrapper<List<CscGoods>> cscGoodsLists = feignCscGoodsAPIClient.queryCscGoodsList(cscGoods);
        try {
            response.getWriter().print(JSONUtils.objectToJson(cscGoodsLists.getResult()));
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 收货方发货方筛选(调用客户中心API)
     */
    @ApiOperation(value="下单收发货方筛选", notes="根据查询条件筛选收发货方")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/contactSelect",method = RequestMethod.POST)
    public void contactSelectByCscApi(Model model, CscContantAndCompanyDto cscContantAndCompanyDto, HttpServletResponse response){
        //调用外部接口,最低传CustomerCode和purpose
        Wrapper<List<CscContantAndCompanyDto>> cscReceivingInfoList = feignCscCustomerAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
        try {
            response.getWriter().print(JSONUtils.objectToJson(cscReceivingInfoList.getResult()));
        } catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    /**
     * 供应商筛选(调用客户中心API)
     */
    @ApiOperation(value="下单供应商筛选", notes="根据查询条件筛选供应商")
    @ApiImplicitParams({
    })
    @RequestMapping(value = "/supplierSelect",method = RequestMethod.POST)
    public void supplierSelectByCscApi(Model model, CscSupplierInfoDto cscSupplierInfoDto, HttpServletResponse response) throws InvocationTargetException{
        //调用外部接口,最低传CustomerCode
        Wrapper<List<CscSupplierInfoDto>> cscSupplierList = feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
        try {
            response.getWriter().print(JSONUtils.objectToJson(cscSupplierList.getResult()));
        }catch (IOException e) {
            throw new BusinessException(e.getMessage());
        }
    }


}
