package com.xescm.ofc.web.restcontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcOrderDTO;
import com.xescm.ofc.domain.dto.csc.*;
import com.xescm.ofc.domain.dto.csc.domain.CscContact;
import com.xescm.ofc.domain.dto.csc.domain.CscContactCompany;
import com.xescm.ofc.domain.dto.csc.vo.CscCustomerVo;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsApiVo;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsTypeVo;
import com.xescm.ofc.domain.dto.rmc.RmcWarehouse;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsTypeAPIClient;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.*;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private OfcOrderManageService ofcOrderManageService;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;
    @Autowired
    private FeignCscGoodsTypeAPIClient feignCscGoodsTypeAPIClient;
    @Autowired
    private FeignCscGoodsAPIClient feignCscGoodsAPIClient;
    @Resource
    private CodeGenUtils codeGenUtils;


    /**
     * 城配开单确认下单
     * @param orderLists
     * @param model
     * @return
     */
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
            String batchNumber = codeGenUtils.getNewWaterCode("BN",4);//生成订单批次号,保证一批单子属于一个批次
            Wrapper<?> validateCustOrderCodeResult =  validateCustOrderCode(jsonArray);
            if(Wrapper.ERROR_CODE == validateCustOrderCodeResult.getCode()){
                return validateCustOrderCodeResult;
            }
            for(int i = 0; i < jsonArray.size(); i ++){
                String json = jsonArray.get(i).toString();
                OfcOrderDTO ofcOrderDTO = (OfcOrderDTO) JsonUtil.json2Object(json, OfcOrderDTO.class);

                String orderGoodsListStr = JsonUtil.list2Json(ofcOrderDTO.getGoodsList());
                //orderGoodsListStr = orderGoodsListStr.replace("~`","");
                AuthResDto authResDtoByToken = getAuthResDtoByToken();
                QueryCustomerIdDto queryCustomerIdDto = new QueryCustomerIdDto();
                //queryCustomerIdDto.setGroupId(authResDtoByToken.getGroupId());
                //Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerIdByGroupId(queryCustomerIdDto);
                //String custId = (String) wrapper.getResult();

               // List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<OfcGoodsDetailsInfo>();
                List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
                if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){ // 如果货品不空才去添加
                    ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
                }
                CscContantAndCompanyDto consignor = switchOrderDtoToCscCAndCDto(ofcOrderDTO,"2");
                CscContantAndCompanyDto consignee = switchOrderDtoToCscCAndCDto(ofcOrderDTO,"1");

                ofcOrderDTO.setOrderBatchNumber(batchNumber);

                resultMessage =  ofcOrderPlaceService.placeOrder(ofcOrderDTO,ofcGoodsDetailsInfos,"place",authResDtoByToken,ofcOrderDTO.getCustCode()
                        ,consignor,consignee,new CscSupplierInfoDto());

            }
        }catch (Exception ex){
            logger.error("运营中心城配开单批量下单失败!{}",ex.getMessage(),ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,resultMessage);
    }

    /**
     * 校验客户订单编号
     * @param jsonArray
     * @return
     * @throws Exception
     */
    private Wrapper<?> validateCustOrderCode(JSONArray jsonArray) throws Exception {
        String pageCustOrderCode = "";
        for(int i = 0; i < jsonArray.size(); i ++) {
            String json = jsonArray.get(i).toString();
            OfcOrderDTO ofcOrderDTO = (OfcOrderDTO) JsonUtil.json2Object(json, OfcOrderDTO.class);
            String custOrderCode = ofcOrderDTO.getCustOrderCode();
            if("" != custOrderCode){
                System.out.println("pageCustOrderCode"+pageCustOrderCode);
                System.out.println("custOrderCode"+custOrderCode);
                if(!PubUtils.isSEmptyOrNull(custOrderCode) && pageCustOrderCode.equals(custOrderCode)){
                    logger.error("城配下单批量下单,客户订单编号重复");
                    return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方列表中第" + (i + 1) + "行,收货方名称为【" + ofcOrderDTO.getConsigneeName() + "】的客户订单编号重复！请检查！");
                }
                pageCustOrderCode = custOrderCode;
                OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
                ofcFundamentalInformation.setCustOrderCode(custOrderCode);
                int checkCustOrderCodeResult = ofcFundamentalInformationService.checkCustOrderCode(ofcFundamentalInformation);
                if (checkCustOrderCodeResult > 0) {
                    logger.error("城配下单批量下单,客户订单编号重复");
                    return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方列表中第" + (i + 1) + "行,收货方名称为【" + ofcOrderDTO.getConsigneeName() + "】的客户订单编号重复！请检查！");
                }
            }

        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }

    /**
     * 根据选择的客户查询仓库
     * @param custId
     * @param model
     * @param response
     */
    @RequestMapping(value = "/queryWarehouseByCustId",method = RequestMethod.POST)
    @ResponseBody
    public void queryCustomerByName(String custId,Model model,HttpServletResponse response){
        logger.info("==> custId={}", custId);
        try{
            List<RmcWarehouse> rmcWarehouseByCustCode  = ofcWarehouseInformationService.getWarehouseListByCustCode(custId);
            response.getWriter().print(JSONUtils.objectToJson(rmcWarehouseByCustCode));
        }catch (Exception ex){
            logger.error("城配下单查询仓库列表失败!{}",ex.getMessage(),ex);
        }
    }

    /**
     * 根据选择的客户查询货品一级种类
     * @param custId
     * @param model
     * @param response
     */
    @RequestMapping(value = "/queryGoodsTypeByCustId",method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsTypeByCustId(String custId,Model model,HttpServletResponse response){
        logger.info("==> custId={}", custId);
        Wrapper<List<CscGoodsTypeVo>> wrapper = null;
        try{
            //List<RmcWarehouse> rmcWarehouseByCustCode  = ofcWarehouseInformationService.getWarehouseListByCustCode(custId);
            CscGoodsType cscGoodsType = new CscGoodsType();
            cscGoodsType.setCustomerId(custId);
            wrapper = feignCscGoodsTypeAPIClient.queryCscGoodsTypeList(cscGoodsType);
            if(null != wrapper.getResult()){
                response.getWriter().print(JSONUtils.objectToJson(wrapper.getResult()));
            }
        }catch (Exception ex){
            logger.error("城配下单查询货品种类失败!异常信息为{},接口返回状态信息{}",ex.getMessage(),wrapper.getMessage(),ex);
        }
    }

    /**
     * 根据选择的客户和货品一级种类查询货品小类
     * @param custId
     * @param goodsType
     * @param model
     * @param response
     */
    @RequestMapping(value = "/queryGoodsSecTypeByCAndT",method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsSecTypeByCAndT(String custId, String goodsType,Model model,HttpServletResponse response){
        logger.info("==> custId={}", custId);
        logger.info("==> goodsType={}", goodsType);
        Wrapper<List<CscGoodsTypeVo>> wrapper = null;
        try{
            //List<RmcWarehouse> rmcWarehouseByCustCode  = ofcWarehouseInformationService.getWarehouseListByCustCode(custId);
            CscGoodsType cscGoodsType = new CscGoodsType();
            cscGoodsType.setCustomerId(custId);
            cscGoodsType.setPid(goodsType);
            wrapper = feignCscGoodsTypeAPIClient.queryCscGoodsTypeList(cscGoodsType);
            if(null != wrapper.getResult()){
                response.getWriter().print(JSONUtils.objectToJson(wrapper.getResult()));
            }
        }catch (Exception ex){
            logger.error("城配下单查询货品小类失败!异常信息为{},接口返回状态信息{}",ex.getMessage(),wrapper.getMessage(),ex);
        }
    }

    /**
     * 查询货品列表
     * @param cscGoodsApiDto
     * @param response
     */
    @RequestMapping(value = "/queryGoodsListInDistrbuting", method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsListInDistrbuting(CscGoodsApiDto cscGoodsApiDto,HttpServletResponse response){
        logger.info("==> cscGoodsApiDto={}", cscGoodsApiDto);
        Wrapper<List<CscGoodsApiVo>> wrapper = null;
        try{
            wrapper = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
            if(null != wrapper.getResult()){
                response.getWriter().print(JSONUtils.objectToJson(wrapper.getResult()));
            }
        }catch (Exception ex){
            logger.error("城配下单查询货品列表失败!{}",ex.getMessage(),wrapper.getMessage());
        }
    }


    /**
     * 根据客户名称查询客户
     * @param queryCustomerName
     * @param currPage
     * @param response
     */
    @RequestMapping(value = "/queryCustomerByName",method = RequestMethod.POST)
    @ResponseBody
    public void queryCustomerByName(String queryCustomerName, String currPage, HttpServletResponse response){
        logger.info("==> queryCustomerName={}", queryCustomerName);
        logger.info("==> currPage={}", currPage);
        try{
            QueryCustomerNameDto queryCustomerNameDto = new QueryCustomerNameDto();
            if(!PubUtils.isSEmptyOrNull(queryCustomerName)){
                queryCustomerNameDto.setCustomerNames(new ArrayList<String>());
                queryCustomerNameDto.getCustomerNames().add(queryCustomerName);
            }
//            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerByName(queryCustomerNameDto);
            QueryCustomerNameAvgueDto queryCustomerNameAvgueDto = new QueryCustomerNameAvgueDto();
            queryCustomerNameAvgueDto.setCustomerName(queryCustomerName);
            Wrapper<?> wrapper = feignCscCustomerAPIClient.QueryCustomerByNameAvgue(queryCustomerNameAvgueDto);
            if(wrapper.getCode() == Wrapper.ERROR_CODE){
                logger.error("查询客户列表失败,查询结果有误!");
            }
            List<CscCustomerVo> cscCustomerVoList = (List<CscCustomerVo>) wrapper.getResult();
            if(null == cscCustomerVoList){
                response.getWriter().print(JSONUtils.objectToJson(new ArrayList<CscCustomerVo>()));
            }else{
                response.getWriter().print(JSONUtils.objectToJson(cscCustomerVoList));
            }
        }catch (Exception ex){
            logger.error("查询客户列表失败!异常信息为:{}",ex.getMessage(),ex);
        }

    }

    /**
     * 转换页面DTO为CSCDTO以便复用
     * @param ofcOrderDTO
     * @param purpose
     * @return
     */
    private CscContantAndCompanyDto switchOrderDtoToCscCAndCDto(OfcOrderDTO ofcOrderDTO,String purpose) {
        CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
        cscContantAndCompanyDto.setCscContactCompany(new CscContactCompany());
        cscContantAndCompanyDto.setCscContact(new CscContact());
        if(StringUtils.equals("2",purpose)){
            cscContantAndCompanyDto.getCscContactCompany().setContactCompanyName(ofcOrderDTO.getConsignorName());
            cscContantAndCompanyDto.getCscContactCompany().setType(ofcOrderDTO.getConsignorType());
            cscContantAndCompanyDto.getCscContact().setPurpose(purpose);
            cscContantAndCompanyDto.getCscContact().setContactName(ofcOrderDTO.getConsignorContactName());
            cscContantAndCompanyDto.getCscContact().setPhone(ofcOrderDTO.getConsignorContactPhone());
            cscContantAndCompanyDto.getCscContact().setContactCompanyId(ofcOrderDTO.getConsignorCode());
            cscContantAndCompanyDto.getCscContact().setContactCode(ofcOrderDTO.getConsignorContactCode());
            cscContantAndCompanyDto.getCscContact().setProvinceName(ofcOrderDTO.getDepartureProvince());
            cscContantAndCompanyDto.getCscContact().setCityName(ofcOrderDTO.getDepartureCity());
            cscContantAndCompanyDto.getCscContact().setAreaName(ofcOrderDTO.getDepartureDistrict());
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureTowns())){
                cscContantAndCompanyDto.getCscContact().setStreetName(ofcOrderDTO.getDepartureTowns());
            }
            String[] departureCode = ofcOrderDTO.getDeparturePlaceCode().split(",");
            cscContantAndCompanyDto.getCscContact().setProvince(departureCode[0]);
            cscContantAndCompanyDto.getCscContact().setCity(departureCode[1]);
            cscContantAndCompanyDto.getCscContact().setArea(departureCode[2]);
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureTowns())){
                cscContantAndCompanyDto.getCscContact().setStreet(departureCode[3]);
            }
            cscContantAndCompanyDto.getCscContact().setAddress(ofcOrderDTO.getDeparturePlace());
        }else if(StringUtils.equals("1",purpose)){
            cscContantAndCompanyDto.getCscContactCompany().setContactCompanyName(ofcOrderDTO.getConsigneeName());
            cscContantAndCompanyDto.getCscContactCompany().setType(ofcOrderDTO.getConsigneeType());
            cscContantAndCompanyDto.getCscContact().setPurpose(purpose);
            cscContantAndCompanyDto.getCscContact().setContactName(ofcOrderDTO.getConsigneeContactName());
            cscContantAndCompanyDto.getCscContact().setPhone(ofcOrderDTO.getConsigneeContactPhone());
            cscContantAndCompanyDto.getCscContact().setContactCompanyId(ofcOrderDTO.getConsigneeCode());
            cscContantAndCompanyDto.getCscContact().setContactCode(ofcOrderDTO.getConsigneeContactCode());
            cscContantAndCompanyDto.getCscContact().setProvinceName(ofcOrderDTO.getDestinationProvince());
            cscContantAndCompanyDto.getCscContact().setCityName(ofcOrderDTO.getDestinationCity());
            cscContantAndCompanyDto.getCscContact().setAreaName(ofcOrderDTO.getDestinationDistrict());
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDestinationTowns())){
                cscContantAndCompanyDto.getCscContact().setStreetName(ofcOrderDTO.getDestinationTowns());
            }
            String[] destinationCode = ofcOrderDTO.getDestinationCode().split(",");
            cscContantAndCompanyDto.getCscContact().setProvince(destinationCode[0]);
            cscContantAndCompanyDto.getCscContact().setCity(destinationCode[1]);
            cscContantAndCompanyDto.getCscContact().setArea(destinationCode[2]);
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureTowns())){
                cscContantAndCompanyDto.getCscContact().setStreet(destinationCode[3]);
            }
            cscContantAndCompanyDto.getCscContact().setAddress(ofcOrderDTO.getDestination());
        }


        return cscContantAndCompanyDto;
    }
}
