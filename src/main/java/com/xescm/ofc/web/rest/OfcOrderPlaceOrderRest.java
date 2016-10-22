package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.service.*;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

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

    /**
     * 下单
     * @param ofcOrderDTO
     * @param response
     * @return
     */
    @RequestMapping("/orderPlaceCon")
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
                return "order_manage";
            }
            return "order_place";
        } catch (Exception e) {
            e.printStackTrace();
            return "order_place";
        }
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
    public void supplierSelectByCscApi(Model model, CscSupplierInfoDto cscSupplierInfoDto, HttpServletResponse response){
        //调用外部接口,最低传CustomerCode
        Wrapper<List<CscSupplierInfoDto>> cscSupplierList = feignCscSupplierAPIClient.querySupplierByAttribute(cscSupplierInfoDto);
        try {
            response.getWriter().print(JSONUtils.objectToJson(cscSupplierList.getResult()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
