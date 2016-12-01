package com.xescm.ofc.web.controller;

import com.xescm.ofc.domain.OfcMerchandiser;
import com.xescm.ofc.model.dto.csc.QueryCustomerIdDto;
import com.xescm.ofc.model.dto.csc.QueryStoreDto;
import com.xescm.ofc.model.vo.csc.CscStorevo;
import com.xescm.ofc.model.dto.rmc.RmcWarehouse;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscStoreAPIClient;
import com.xescm.ofc.service.OfcMerchandiserService;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import com.xescm.uam.domain.dto.AuthResDto;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private OfcMerchandiserService ofcMerchandiserService;

    public String getCustId() {
        AuthResDto authResDtoByToken = getAuthResDtoByToken();
        QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
        queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
        Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
        String custId = (String) wrapper.getResult();
        return custId;
    }

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
            logger.error("订单中心从API获取仓库信息出现异常:{}", ex.getMessage(), ex);
            ex.printStackTrace();
            //rmcWarehouseByCustCode = new ArrayList<RmcWarehouse>();
            rmcWarehouseByCustCode = new ArrayList<>();
        }catch (Exception ex){
            logger.error("订单中心下单出现异常:{}", ex.getMessage(), ex);
            ex.printStackTrace();
            //rmcWarehouseByCustCode = new ArrayList<RmcWarehouse>();
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
        List<OfcMerchandiser> merchandiserList = ofcMerchandiserService.selectAll();
        map.put("merchandiserList",merchandiserList);
        map.put("currentTime",new Date());
        setDefaultModel(model);
        return "operation_distributing";
    }

    /**
     * 城配开单Excel导入
     * @param model
     * @param historyUrl
     * @param map
     * @return
     */
    @RequestMapping(value = "/ofc/operationDistributingExcel/{historyUrl}/{custId}")
    public String operationDistributingExcel(Model model, @PathVariable String historyUrl,@PathVariable String custId , Map<String,Object> map){
        logger.info("城配开单Excel导入==> historyUrl={}", historyUrl);
        logger.info("城配开单Excel导入==> custId={}", custId);
        if("operation_distributing".equals(historyUrl)){
            historyUrl = "/ofc/operationDistributing";
        }
        setDefaultModel(model);
        map.put("historyUrl",historyUrl);
        map.put("custId",custId);
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
        try{
            List<OfcMerchandiser> merchandiserList = ofcMerchandiserService.selectAll();
            map.put("merchandiserList",merchandiserList);
            map.put("currentTime",new Date());
            setDefaultModel(model);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ModelAndView("order_tranload");
    }

    /**
     * 跳转运营中心→订单跟踪
     * @return
     */
    @RequestMapping(value = "/orderFollowOpera")
    public String orderFollowOpera() {
        return "order_follow_opera";
    }

    /**
     * 跳转到运营→订单管理 orderManageOpera
     *
     * @return modelAndView
     */
    @RequestMapping(value = "ofc/orderManageOpera", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView orderManageOpera(Model model) {
        ModelAndView modelAndView = new ModelAndView("order_manage_opera");
        setDefaultModel(model);
        return modelAndView;
    }


}
