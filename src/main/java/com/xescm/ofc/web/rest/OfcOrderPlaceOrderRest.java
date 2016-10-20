package com.xescm.ofc.web.rest;

import com.xescm.ofc.domain.*;
import com.xescm.ofc.domain.dto.CscGoods;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscSupplierAPIClient;
import com.xescm.ofc.feign.client.FeignCscWarehouseAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private FeignCscSupplierAPIClient feignCscSupplierAPIClient;
    @Autowired
    private FeignCscWarehouseAPIClient feignCscWarehouseAPIClient;
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

}
