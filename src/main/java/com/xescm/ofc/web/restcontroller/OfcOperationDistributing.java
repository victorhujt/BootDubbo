package com.xescm.ofc.web.restcontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcOrderDTO;
import com.xescm.ofc.domain.dto.csc.*;
import com.xescm.ofc.domain.dto.csc.vo.CscCustomerVo;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsTypeVo;
import com.xescm.ofc.domain.dto.rmc.RmcWarehouse;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsTypeAPIClient;
import com.xescm.ofc.service.OfcOrderPlaceService;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.utils.JacksonUtil;
import com.xescm.ofc.utils.JsonUtil;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
/*
*
 * Created by lyh on 2016/11/19.
 */

@RequestMapping(value = "/ofc/distributing",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOperationDistributing extends BaseController{
    @Autowired
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Autowired
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private FeignCscGoodsTypeAPIClient feignCscGoodsTypeAPIClient; //000


    @RequestMapping(value = "/placeOrdersListCon",method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> placeOrdersListCon(String orderLists, Model model){
        logger.info("==> orderLists={}", orderLists);
        String resultMessage = null;
        try{
            if(PubUtils.isSEmptyOrNull(orderLists)){
                logger.error("城配开单批量下单入参为空");
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"您没有添加任何信息,请检查!");
            }
            JSONArray jsonArray = JSON.parseArray(orderLists);
            for(int i = 0; i < jsonArray.size(); i ++){
                String json = jsonArray.get(i).toString();
                OfcOrderDTO ofcOrderDTO = (OfcOrderDTO) JsonUtil.json2Object(json, OfcOrderDTO.class);
                String orderGoodsListStr = JsonUtil.list2Json(ofcOrderDTO.getGoodsList());
                //orderGoodsListStr = orderGoodsListStr.replace("~`","");
                AuthResDto authResDtoByToken = getAuthResDtoByToken();
                QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
                queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
                Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
                String custId = (String) wrapper.getResult();

                List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
                if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                    ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
                }
                resultMessage =  ofcOrderPlaceService.placeOrder(ofcOrderDTO,ofcGoodsDetailsInfos,"place",authResDtoByToken,custId
                        ,new CscContantAndCompanyDto(),new CscContantAndCompanyDto(),new CscSupplierInfoDto());
            }
        }catch (Exception ex){
            logger.error("运营中心城配开单批量下单失败!",ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,resultMessage);


    }

    //根据选择的客户查询仓库
    @RequestMapping(value = "/queryWarehouseByCustId",method = RequestMethod.POST)
    @ResponseBody
    public void queryCustomerByName(String custId,Model model,HttpServletResponse response){
        logger.info("==> custId={}", custId);
        try{
            List<RmcWarehouse> rmcWarehouseByCustCode  = ofcWarehouseInformationService.getWarehouseListByCustCode(custId);
            response.getWriter().print(JSONUtils.objectToJson(rmcWarehouseByCustCode));
        }catch (Exception ex){
            logger.error("城配下单查询仓库列表失败!",ex.getMessage());
        }
    }
    //根据选择的客户查询货品种类和货品小类
    @RequestMapping(value = "/queryGoodsTypeByCustId",method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsTypeByCustId(String custId,Model model,HttpServletResponse response){
        logger.info("==> custId={}", custId);
        try{
            //List<RmcWarehouse> rmcWarehouseByCustCode  = ofcWarehouseInformationService.getWarehouseListByCustCode(custId);
            CscGoodsType cscGoodsType = new CscGoodsType();
            ///0000



            Wrapper<List<CscGoodsTypeVo>> wrapper = feignCscGoodsTypeAPIClient.queryCscGoodsTypeList(cscGoodsType);
            response.getWriter().print(JSONUtils.objectToJson(wrapper));
        }catch (Exception ex){
            logger.error("城配下单查询仓库列表失败!",ex.getMessage());
        }
    }


    @RequestMapping(value = "/queryCustomerByName",method = RequestMethod.POST)
    @ResponseBody
    public void queryCustomerByName(String queryCustomerName, String currPage, HttpServletResponse response){
        logger.info("==> queryCustomerName={}", queryCustomerName);
        logger.info("==> currPage={}", currPage);
        try{
            if(PubUtils.isSEmptyOrNull(queryCustomerName)){
                logger.error("查询客户列表参数为空!");
            }
            QueryCustomerNameDto queryCustomerNameDto = new QueryCustomerNameDto();
            if(!PubUtils.isSEmptyOrNull(queryCustomerName)){
                queryCustomerNameDto.setCustomerNames(new ArrayList<String>());
                queryCustomerNameDto.getCustomerNames().add(queryCustomerName);
            }
            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerByName(queryCustomerNameDto);
            if(wrapper.getCode() == Wrapper.ERROR_CODE){
                logger.error("查询客户列表失败,查询结果有误!");
            }
            List<CscCustomerVo> cscCustomerVoList = (List<CscCustomerVo>) wrapper.getResult();
            response.getWriter().print(JSONUtils.objectToJson(cscCustomerVoList));
        }catch (Exception ex){
            logger.error("查询客户列表失败!",ex.getMessage());
        }

    }


}
