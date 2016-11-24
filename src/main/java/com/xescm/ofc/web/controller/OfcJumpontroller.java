package com.xescm.ofc.web.controller;

import com.xescm.ofc.domain.dto.csc.CscGoods;
import com.xescm.ofc.domain.dto.csc.QueryCustomerIdDto;
import com.xescm.ofc.domain.dto.csc.QueryStoreDto;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsVo;
import com.xescm.ofc.domain.dto.csc.vo.CscStorevo;
import com.xescm.ofc.domain.dto.rmc.RmcWarehouse;
import com.xescm.ofc.enums.OrderStatusEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscStoreAPIClient;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.PubUtils;
import com.xescm.uam.utils.wrap.Wrapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/10/18.
 */
@Controller
@RequestMapping(produces = {"application/json;charset=UTF-8"})
public class OfcJumpontroller extends BaseController{

    @Autowired
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private FeignCscStoreAPIClient feignCscStoreAPIClient;
    @Autowired
    private FeignCscGoodsAPIClient feignCscGoodsAPIClient;

    @RequestMapping(value="/ofc/orderPlace")
    public ModelAndView index(Model model,Map<String,Object> map , HttpServletRequest request, HttpServletResponse response){
        List<RmcWarehouse> rmcWarehouseByCustCode = null;
        List<CscStorevo> cscStoreListResult = null;
        setDefaultModel(model);
        try{

            String custId = getCustId();
            QueryStoreDto queryStoreDto = new QueryStoreDto();
            queryStoreDto.setCustomerId(custId);
            Wrapper<List<CscStorevo>> storeByCustomerId = feignCscStoreAPIClient.getStoreByCustomerId(queryStoreDto);
            cscStoreListResult = storeByCustomerId.getResult();
            rmcWarehouseByCustCode = ofcWarehouseInformationService.getWarehouseListByCustCode(custId);

        }catch (BusinessException ex){
            logger.error("订单中心从API获取仓库信息出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            rmcWarehouseByCustCode = new ArrayList<>();
        }catch (Exception ex){
            logger.error("订单中心下单出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            rmcWarehouseByCustCode = new ArrayList<>();
        }
        map.put("rmcWarehouseByCustCode",rmcWarehouseByCustCode);
        map.put("cscStoreByCustId",cscStoreListResult);
        return new ModelAndView("order_place");

    }
     @RequestMapping(value = "/ofc/orderManage")
    public String orderManage(Model model){
        return "order_manage";
    }

    @RequestMapping(value = "/ofc/orderScreen")
    public String orderScreen(Model model){
        return "order_screen";
    }

    @ApiOperation(value="追踪订单", notes="根据code和followTag来追踪订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "订单编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "followTag", value = "追踪标记", required = true, dataType = "String")
    })

    @RequestMapping(value="/ofc/orderFollow",method = RequestMethod.GET)
    public String orderFollow(Model model, String code, String followTag, Map<String,Object> map){
        logger.debug("==>订单中心订单追踪条件筛选code code={}", code);
        logger.debug("==>订单中心订单追踪条件标志位 followTag={}", followTag);
        return "order_follow";
    }

    /**
     * 进入主页
     * @param model
     * @return
     */
    @RequestMapping(value = "/index")
    public String toIndex(Model model){

        return "index";
    }

    @RequestMapping(value = "/ofc/planAllocation")
    public String planAllocation(Model model){
        return "plan_allocation";
    }

    /**
     * 城配开单
     * @param model
     * @return
     */
    @RequestMapping(value = "/ofc/operationDistributing")
    public String operationDistributing(Model model,Map<String,Object> map){

        map.put("currentTime",new Date());
        return "operation_distributing";
    }

    /**
     * 城配开单Excel导入
     * @param model
     * @param historyUrl
     * @param map
     * @return
     */
    @RequestMapping(value = "/ofc/operationDistributingExcel/{historyUrl}")
    public String operationDistributingExcel(Model model, @PathVariable String historyUrl, Map<String,Object> map){
        if("operation_distributing".equals(historyUrl)){
            historyUrl = "/ofc/operationDistributing";
        }
        map.put("historyUrl",historyUrl);
        return "operation_distributing_excel";
    }

    /**
     * 运输开单
     * @param model
     * @param map
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/ofc/tranLoad")
    public ModelAndView tranLoad(Model model,Map<String,Object> map , HttpServletRequest request, HttpServletResponse response){
        List<RmcWarehouse> rmcWarehouseByCustCode = null;
        List<CscGoodsVo> cscGoodsVoList = null;
        setDefaultModel(model);
        try{
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
            queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
            String custId = (String) wrapper.getResult();
            /*CscGoods cscGoods=new CscGoods();
            cscGoods.setCustomerId(custId);
            cscGoods.setGoodsCode(PubUtils.trimAndNullAsEmpty(cscGoods.getGoodsCode()));
            cscGoods.setGoodsName(PubUtils.trimAndNullAsEmpty(cscGoods.getGoodsName()));
            Wrapper<List<CscGoodsVo>> cscGoodsLists = feignCscGoodsAPIClient.queryCscGoodsList(cscGoods);
            cscGoodsVoList = cscGoodsLists.getResult();*/
        }catch (BusinessException ex){
            logger.error("订单中心从API获取仓库信息出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            rmcWarehouseByCustCode = new ArrayList<>();
        }catch (Exception ex){
            logger.error("订单中心下单出现异常:{},{}", ex.getMessage(), ex);
            ex.printStackTrace();
            rmcWarehouseByCustCode = new ArrayList<>();
        }
        //map.put("cscGoodsVoList",cscGoodsVoList);
        return new ModelAndView("order_tranload");

    }

    /**
     * 运营→订单管理 orderManageOpera
     * @return modelAndView
     */
    @RequestMapping("ofc/orderManageOpera")
    public ModelAndView orderManageOpera() {
        ModelAndView modelAndView = new ModelAndView("order_manage_opera");
        modelAndView.addObject("orderStatus",OrderStatusEnum.queryList());
        return modelAndView;
    }

    public String getCustId() {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
        queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
        Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
        String custId = (String) wrapper.getResult();
        return custId;
    }



}
