package com.xescm.ofc.web.restcontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsTypeAPIClient;
import com.xescm.ofc.model.dto.csc.CscGoodsApiDto;
import com.xescm.ofc.model.dto.csc.CscGoodsType;
import com.xescm.ofc.model.dto.csc.QueryCustomerNameAvgueDto;
import com.xescm.ofc.model.dto.csc.QueryCustomerNameDto;
import com.xescm.ofc.model.dto.rmc.RmcWarehouse;
import com.xescm.ofc.model.vo.csc.CscCustomerVo;
import com.xescm.ofc.model.vo.csc.CscGoodsApiVo;
import com.xescm.ofc.model.vo.csc.CscGoodsTypeVo;
import com.xescm.ofc.service.OfcOperationDistributingService;
import com.xescm.ofc.service.OfcOrderPlaceService;
import com.xescm.ofc.service.OfcWarehouseInformationService;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.utils.JacksonUtil;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private OfcOperationDistributingService ofcOperationDistributingService;
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
        logger.info("城配开单确认下单==> orderLists={}", orderLists);
        String resultMessage = null;
        try{
            if(PubUtils.isSEmptyOrNull(orderLists)){
                logger.error("城配开单批量下单入参为空");
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"您没有添加任何信息,请检查!");
            }
            JSONArray jsonArray = JSON.parseArray(orderLists);
            String batchNumber = codeGenUtils.getNewWaterCode("BN",4);//生成订单批次号,保证一批单子属于一个批次

            Wrapper<?> validateCustOrderCodeResult =  ofcOperationDistributingService.validateCustOrderCode(jsonArray);
            if(Wrapper.ERROR_CODE == validateCustOrderCodeResult.getCode()){
                return validateCustOrderCodeResult;
            }
            resultMessage = ofcOperationDistributingService.distributingOrderPlace(jsonArray,getAuthResDtoByToken(),batchNumber);
        } catch (BusinessException ex){
            logger.error("运营中心城配开单批量下单失败!{}",ex.getMessage(),ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,ex.getMessage());
        } catch (Exception ex){
            logger.error("运营中心城配开单批量下单失败!{}",ex.getMessage(),ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"运营中心城配开单批量下单失败!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,resultMessage);
    }

    /**
     * 根据选择的客户查询仓库
     * @param customerCode
     * @param model
     * @param response
     */
    @RequestMapping(value = "/queryWarehouseByCustId",method = RequestMethod.POST)
    @ResponseBody
    public void queryCustomerByName(String customerCode,Model model,HttpServletResponse response){
        logger.info("城配开单根据选择的客户查询仓库==> customerCode={}", customerCode);
        try{
            List<RmcWarehouse> rmcWarehouseByCustCode  = ofcWarehouseInformationService.getWarehouseListByCustCode(customerCode);
            response.getWriter().print(JSONUtils.objectToJson(rmcWarehouseByCustCode));
        }catch (Exception ex){
            logger.error("城配下单查询仓库列表失败!{}",ex.getMessage(),ex);
        }
    }

    /**
     * 根据选择的客户查询货品一级种类
     * @param customerCode
     * @param model
     * @param response
     */
    @RequestMapping(value = "/queryGoodsTypeByCustId",method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsTypeByCustId(String customerCode,Model model,HttpServletResponse response){
        Wrapper<List<CscGoodsTypeVo>> wrapper = null;
        try{
            CscGoodsType cscGoodsType = new CscGoodsType();
            wrapper = feignCscGoodsTypeAPIClient.queryCscGoodsTypeList(cscGoodsType);
            if(null != wrapper.getResult()){
                response.getWriter().print(JSONUtils.objectToJson(wrapper.getResult()));
            }
        }catch (Exception ex){
            logger.error("城配下单查询货品种类失败!异常信息为{},接口返回状态信息{}",ex.getMessage(),wrapper.getMessage(),ex);
        }
    }

    /**
     * 根据选择的客户和货品一级种类查询货品二级小类
     * @param customerCode
     * @param goodsType
     * @param model
     * @param response
     */
    @RequestMapping(value = "/queryGoodsSecTypeByCAndT",method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsSecTypeByCAndT(String customerCode, String goodsType,Model model,HttpServletResponse response){
        logger.info("城配开单根据选择的客户和货品一级种类查询货品二级小类==> goodsType={}", goodsType);
        Wrapper<List<CscGoodsTypeVo>> wrapper = null;
        try{
            CscGoodsType cscGoodsType = new CscGoodsType();
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
     * 2.0:前端还需再根据未修改的接口再把customerId改了!
     */
    @RequestMapping(value = "/queryGoodsListInDistrbuting", method = RequestMethod.POST)
    @ResponseBody
    public void queryGoodsListInDistrbuting(CscGoodsApiDto cscGoodsApiDto,HttpServletResponse response){
        logger.info("城配开单查询货品列表==> cscGoodsApiDto={}", cscGoodsApiDto);
        Wrapper<List<CscGoodsApiVo>> wrapper = null;
        try{
            wrapper = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
            if(null != wrapper.getResult()){
                response.getWriter().print(JSONUtils.objectToJson(wrapper.getResult()));
            }
        }catch (Exception ex){
            logger.error("城配下单查询货品列表失败!{}{}",ex.getMessage(),wrapper.getMessage(),ex);
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
        logger.info("城配开单根据客户名称查询客户==> queryCustomerName={}", queryCustomerName);
        logger.info("城配开单根据客户名称查询客户==> currPage={}", currPage);
        try{
            QueryCustomerNameDto queryCustomerNameDto = new QueryCustomerNameDto();
            if(!PubUtils.isSEmptyOrNull(queryCustomerName)){
                queryCustomerNameDto.setCustomerNames(new ArrayList<String>());
                queryCustomerNameDto.getCustomerNames().add(queryCustomerName);
            }
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
            logger.error("查询客户列表失败!异常信息为:{}{}",ex.getMessage(),ex);
        }

    }

     /**
     * Excel导入,展示Sheet页
     * @param paramHttpServletRequest
     */
    @RequestMapping(value = "/fileUploadAndCheck",method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> fileUploadAndCheck(HttpServletRequest paramHttpServletRequest){
        List<String> excelSheet = null;
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) paramHttpServletRequest;
            MultipartFile uploadFile = multipartHttpServletRequest.getFile("file");
            String fileName = multipartHttpServletRequest.getParameter("fileName");
            int potIndex = fileName.lastIndexOf(".") + 1;
            if(-1 == potIndex){
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"该文件没有扩展名!");
            }
            String suffix = fileName.substring(potIndex, fileName.length());
            excelSheet = ofcOperationDistributingService.getExcelSheet(uploadFile,suffix);
        }catch (BusinessException e) {
            e.printStackTrace();
            logger.error("城配开单Excel导入展示Sheet页出错:{}",e.getMessage(),e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        }catch (Exception e) {
            logger.error("城配开单Excel导入展示Sheet页出错:{}",e.getMessage(),e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"上传成功！",excelSheet);
    }

    /**
     * 根据用户选择的Sheet页进行校验并加载正确或错误信息
     * @param paramHttpServletRequest
     * @param response
     * @return
     */
    @RequestMapping(value = "/excelCheckBySheet",method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> excelCheckBySheet(HttpServletRequest paramHttpServletRequest, HttpServletResponse response){
        Wrapper<?> result = null;
        try {
            AuthResDto authResDto = getAuthResDtoByToken();
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) paramHttpServletRequest;
            MultipartFile uploadFile = multipartHttpServletRequest.getFile("file");
            String fileName = multipartHttpServletRequest.getParameter("fileName");
            int potIndex = fileName.lastIndexOf(".") + 1;
            if(-1 == potIndex){
                return WrapMapper.wrap(Wrapper.ERROR_CODE,"该文件没有扩展名!");
            }
            String suffix = fileName.substring(potIndex, fileName.length());
            String customerCode = multipartHttpServletRequest.getParameter("customerCode");
            String sheetNum = multipartHttpServletRequest.getParameter("sheetNum");
            //校验
            Wrapper<?> checkResult = ofcOperationDistributingService.checkExcel(uploadFile,suffix,sheetNum,authResDto,customerCode,5);
            //如果校验失败
            if(checkResult.getCode() == Wrapper.ERROR_CODE){
                List<String> xlsErrorMsg = (List<String>) checkResult.getResult();
                result = WrapMapper.wrap(Wrapper.ERROR_CODE,checkResult.getMessage(),xlsErrorMsg);
            }else if(checkResult.getCode() == Wrapper.SUCCESS_CODE){
                Map<String,JSONArray> resultMap = (Map<String, JSONArray>) checkResult.getResult();
                String resultJSON = JacksonUtil.toJsonWithFormat(resultMap);
                result =  WrapMapper.wrap(Wrapper.SUCCESS_CODE,checkResult.getMessage(),resultJSON);
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            logger.error("城配开单Excel导入校验出错:{}",e.getMessage(),e);
            result = com.xescm.ofc.wrap.WrapMapper.wrap(Wrapper.ERROR_CODE,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("城配开单Excel导入校验出错:{}",e.getMessage(),e);
            result = com.xescm.ofc.wrap.WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return result;
    }
    /**
     * 城配开单下载模板
     * @param response
     */
    @RequestMapping(value = "/downloadTemplate",method = RequestMethod.GET)
    @Deprecated
    public void downloadTemplate( HttpServletResponse response){
        try {
            File f = ResourceUtils.getFile("classpath:templates/xlsx/template_for_cp.xlsx");
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=template_for_cp.xlsx");
            response.addHeader("Content-Length", "" + f.length());
            response.setContentType("application/octet-stream;charset=UTF-8");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            int b;
            while((b = bis.read()) != -1) {
                outputStream.write(b);
            }
            bis.close();
            outputStream.close();
        } catch (Exception e){
            logger.error("城配开单下载模板出错{}",e.getMessage(),e);
        }

    }



}
