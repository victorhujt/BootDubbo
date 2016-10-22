package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.CscContantAndCompanyDto;
import com.xescm.ofc.domain.dto.CscGoods;
import com.xescm.ofc.domain.dto.CscSupplierInfoDto;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscSupplierAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by lyh on 2016/10/8.
 */
@RequestMapping(value = "/ofc")
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
    FeignCscSupplierAPIClient feignCscSupplierAPIClient;
    /**
     * 下单
     * @param ofcOrderDTO
     * @param response
     * @return
     */
    @RequestMapping("/orderPlaceCon/tag/{ofcOrderDTO}")
    public String orderPlace(Model model, OfcOrderDTO ofcOrderDTO, String tag, HttpServletResponse response){
        logger.debug("==>订单中心下单或编辑实体 ofcOrderDTO={}", ofcOrderDTO);
        logger.debug("==>订单中心下单或编辑标志位 tag={}", tag);
        System.out.println(ofcOrderDTO);
        if (ofcOrderDTO.getProvideTransport()==null){
            ofcOrderDTO.setProvideTransport(0);
        }
        if (ofcOrderDTO.getUrgent()==null){
            ofcOrderDTO.setUrgent(0);
        }
        try {
            String result = ofcOrderPlaceService.placeOrder(ofcOrderDTO,tag);
            if(tag.equals("manage")){
                return "/order_manage";
            }
            return "order_place";
        } catch (Exception e) {
            e.printStackTrace();
            return "order_place";
        }
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
    })
    @RequestMapping(value = "/goodsSelect",method = RequestMethod.POST)
    public void goodsSelectByCscApi(Model model, CscGoods cscGoods, HttpServletResponse response){
        //调用外部接口,最低传CustomerCode
        cscGoods.setCustomerCode("customCode1476932806900");
        Wrapper<List<CscGoods>> cscGoodsLists = feignCscGoodsAPIClient.queryCscGoodsList(cscGoods);
        try {
            response.getWriter().print(JSONUtils.objectToJson(cscGoodsLists.getResult()));
        } catch (IOException e) {
            e.printStackTrace();
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
        //调用外部接口,最低传CustomerCode
        Wrapper<List<CscContantAndCompanyDto>> cscReceivingInfoList = feignCscCustomerAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
        try {
            response.getWriter().print(JSONUtils.objectToJson(cscReceivingInfoList.getResult()));
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

}
