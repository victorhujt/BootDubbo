package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscContactAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyResponseDto;
import com.xescm.ofc.model.dto.csc.CscGoodsApiDto;
import com.xescm.ofc.model.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.model.dto.csc.domain.CscContact;
import com.xescm.ofc.model.dto.csc.domain.CscContactCompany;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.csc.CscGoodsApiVo;
import com.xescm.ofc.service.OfcExcelCheckService;
import com.xescm.ofc.service.OfcOperationDistributingService;
import com.xescm.ofc.service.OfcOrderPlaceService;
import com.xescm.ofc.utils.JsonUtil;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Created by lyh on 2016/11/30.
 */
@Service
public class OfcOperationDistributingServiceImpl implements OfcOperationDistributingService {

    @Autowired
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Autowired
    private OfcExcelCheckService ofcExcelCheckService;


    @Override
    /**
     * 转换页面DTO为CSCCUSTOMERDTO以便复用原有下单逻辑
     * @param ofcOrderDTO
     * @param purpose
     * @return
     */
    public CscContantAndCompanyDto switchOrderDtoToCscCAndCDto(OfcOrderDTO ofcOrderDTO,String purpose) {
        CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
        cscContantAndCompanyDto.setCscContactCompany(new CscContactCompany());
        cscContantAndCompanyDto.setCscContact(new CscContact());
        //发货方
        if(StringUtils.equals("2",purpose)){
            cscContantAndCompanyDto.getCscContactCompany().setContactCompanyName(ofcOrderDTO.getConsignorName());
            cscContantAndCompanyDto.getCscContactCompany().setType(ofcOrderDTO.getConsignorType());
            cscContantAndCompanyDto.getCscContact().setPurpose(purpose);
            cscContantAndCompanyDto.getCscContact().setContactName(ofcOrderDTO.getConsignorContactName());
            cscContantAndCompanyDto.getCscContact().setPhone(ofcOrderDTO.getConsignorContactPhone());
//            cscContantAndCompanyDto.getCscContact().setContactCompanyId(ofcOrderDTO.getConsignorCode());
            cscContantAndCompanyDto.getCscContact().setContactCode(ofcOrderDTO.getConsignorContactCode());
            cscContantAndCompanyDto.getCscContact().setProvinceName(ofcOrderDTO.getDepartureProvince());
            cscContantAndCompanyDto.getCscContact().setCityName(ofcOrderDTO.getDepartureCity());
            cscContantAndCompanyDto.getCscContact().setAreaName(ofcOrderDTO.getDepartureDistrict());
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureTowns())){
                cscContantAndCompanyDto.getCscContact().setStreetName(ofcOrderDTO.getDepartureTowns());
            }
            if(PubUtils.isSEmptyOrNull(ofcOrderDTO.getDeparturePlaceCode())){
                throw new BusinessException("四级地址编码为空");
            }
            String[] departureCode = ofcOrderDTO.getDeparturePlaceCode().split(",");
            cscContantAndCompanyDto.getCscContact().setProvince(departureCode[0]);
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureCity()) && departureCode.length > 0){
                cscContantAndCompanyDto.getCscContact().setCity(departureCode[1]);
            }
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureDistrict()) && departureCode.length > 1){
                cscContantAndCompanyDto.getCscContact().setArea(departureCode[2]);
            }
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureTowns()) && departureCode.length > 2){
                cscContantAndCompanyDto.getCscContact().setStreet(departureCode[3]);
            }
            cscContantAndCompanyDto.getCscContact().setAddress(ofcOrderDTO.getDeparturePlace());
        }else if(StringUtils.equals("1",purpose)){
            cscContantAndCompanyDto.getCscContactCompany().setContactCompanyName(ofcOrderDTO.getConsigneeName());
            cscContantAndCompanyDto.getCscContactCompany().setType(ofcOrderDTO.getConsigneeType());
            cscContantAndCompanyDto.getCscContact().setPurpose(purpose);
            cscContantAndCompanyDto.getCscContact().setContactName(ofcOrderDTO.getConsigneeContactName());
            cscContantAndCompanyDto.getCscContact().setPhone(ofcOrderDTO.getConsigneeContactPhone());
//            cscContantAndCompanyDto.getCscContact().setContactCompanyId(ofcOrderDTO.getConsigneeCode());
            cscContantAndCompanyDto.getCscContact().setContactCode(ofcOrderDTO.getConsigneeContactCode());
            cscContantAndCompanyDto.getCscContact().setProvinceName(ofcOrderDTO.getDestinationProvince());
            cscContantAndCompanyDto.getCscContact().setCityName(ofcOrderDTO.getDestinationCity());
            cscContantAndCompanyDto.getCscContact().setAreaName(ofcOrderDTO.getDestinationDistrict());
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDestinationTowns())){
                cscContantAndCompanyDto.getCscContact().setStreetName(ofcOrderDTO.getDestinationTowns());
            }
            if(PubUtils.isSEmptyOrNull(ofcOrderDTO.getDeparturePlaceCode())){
                throw new BusinessException("四级地址编码为空");
            }
            String[] departureCode = ofcOrderDTO.getDeparturePlaceCode().split(",");
            cscContantAndCompanyDto.getCscContact().setProvince(departureCode[0]);
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureCity()) && departureCode.length > 0){
                cscContantAndCompanyDto.getCscContact().setCity(departureCode[1]);
            }
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureDistrict()) && departureCode.length > 1){
                cscContantAndCompanyDto.getCscContact().setArea(departureCode[2]);
            }
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureTowns()) && departureCode.length > 2){
                cscContantAndCompanyDto.getCscContact().setStreet(departureCode[3]);
            }
            cscContantAndCompanyDto.getCscContact().setAddress(ofcOrderDTO.getDestination());
        }


        return cscContantAndCompanyDto;
    }
    /**
     * 校验客户订单编号
     * @param jsonArray
     * @return
     * @throws Exception
     */
    @Override
    public Wrapper<?> validateCustOrderCode(JSONArray jsonArray) {
        List<String> custOrderCodeList = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i ++) {
            String json = jsonArray.get(i).toString();
            OfcOrderDTO ofcOrderDTO = null;
            try {
                ofcOrderDTO = (OfcOrderDTO) JsonUtil.json2Object(json, OfcOrderDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("校验客户订单编号转换异常",e);
            }
            String custOrderCode = ofcOrderDTO.getCustOrderCode();
            if(!PubUtils.isSEmptyOrNull(custOrderCode)){
                if(custOrderCodeList.contains(custOrderCode)){
                    return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方列表中第" + (i + 1) + "行,收货方名称为【" + ofcOrderDTO.getConsigneeName() + "】的客户订单编号重复！请检查！");
                }
                custOrderCodeList.add(custOrderCode);
            }
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }


    /**
     * 获取用户上传的Excel的sheet页
     * @param uploadFile
     * @param suffix
     * @return
     */
    @Override
    public List<String> getExcelSheet(MultipartFile uploadFile, String suffix) {
        List<String> sheetMsgList = new ArrayList<>();
        if("xls".equals(suffix)){
            HSSFWorkbook hssfWorkbook = null;
            try {
                hssfWorkbook = new HSSFWorkbook(uploadFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("获取Sheet页内部异常",e);
            }
            int activeSheetIndex = hssfWorkbook.getActiveSheetIndex();
            int numberOfSheets = hssfWorkbook.getNumberOfSheets();
            for(int sheetNum = 0; sheetNum < numberOfSheets;  sheetNum ++){
                if(sheetNum == activeSheetIndex){
                    sheetMsgList.add(hssfWorkbook.getSheetName(sheetNum) + "@active");
                }else{
                    sheetMsgList.add(hssfWorkbook.getSheetName(sheetNum) + "@");
                }
            }
        }else if("xlsx".equals(suffix)){
            XSSFWorkbook xssfWorkbook = null;
            try {
                xssfWorkbook = new XSSFWorkbook(uploadFile.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("获取Sheet页内部异常",e);
            }
            int activeSheetIndex = xssfWorkbook.getActiveSheetIndex();
            int numberOfSheets = xssfWorkbook.getNumberOfSheets();
            for(int sheetNum = 0; sheetNum < numberOfSheets;  sheetNum ++){
                if(sheetNum == activeSheetIndex){
                    sheetMsgList.add(xssfWorkbook.getSheetName(sheetNum) + "@active");
                }else{
                    sheetMsgList.add(xssfWorkbook.getSheetName(sheetNum) + "@");
                }
            }
        }else{
            throw new BusinessException("您上传的文档格式不正确!");
        }
        return sheetMsgList;
    }


    /**
     * 城配开单必填项后端校验
     */
    @Override
    public void validateOperationDistributingMsg(OfcOrderDTO ofcOrderDTO) {
        if(PubUtils.isSEmptyOrNull(ofcOrderDTO.getMerchandiser())){
            throw new BusinessException("开单员未填写!");
        }
        if(PubUtils.isSEmptyOrNull(ofcOrderDTO.getCustName())){
            throw new BusinessException("客户名称未填写!");
        }
        if(null == ofcOrderDTO.getGoodsList()){
            throw new BusinessException("货品信息有误!");
        }
        if(ofcOrderDTO.getGoodsList().size() < 1){
            throw new BusinessException("货品列表为空!");
        }

    }

    /**
     * 城配开单批量下单循环入口
     * @param jsonArray
     * @return
     */
    @Override
    @Transactional
    public String distributingOrderPlace(JSONArray jsonArray,AuthResDto authResDtoByToken,String batchNumber) {
        String resultMessage = null;
        for(int i = 0; i < jsonArray.size(); i ++){
            String json = jsonArray.get(i).toString();
            OfcOrderDTO ofcOrderDTO = null;
            try {
                ofcOrderDTO = (OfcOrderDTO) JsonUtil.json2Object(json, OfcOrderDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            validateOperationDistributingMsg(ofcOrderDTO);
            String orderGoodsListStr = null;
            try {
                orderGoodsListStr = JsonUtil.list2Json(ofcOrderDTO.getGoodsList());
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            CscContantAndCompanyDto consignor = switchOrderDtoToCscCAndCDto(ofcOrderDTO,"2");
            CscContantAndCompanyDto consignee = switchOrderDtoToCscCAndCDto(ofcOrderDTO,"1");
            ofcOrderDTO.setOrderBatchNumber(batchNumber);
            resultMessage =  ofcOrderPlaceService.placeOrder(ofcOrderDTO,ofcGoodsDetailsInfos,"distributionPlace",authResDtoByToken,ofcOrderDTO.getCustCode()
                    ,consignor,consignee,new CscSupplierInfoDto());
            if(ResultCodeEnum.ERROROPER.getName().equals(resultMessage)){
                return resultMessage;
            }
        }
        return resultMessage;
    }

    /**
     * Excel校验入口
     * 在这里区分模板类型
     * @param uploadFile
     * @param suffix
     * @param sheetNum
     * @param authResDto
     * @param customerCode
     * @param modelType
     * @param modelMappingCode
     * @return
     */
    @Override
    public Wrapper<?> checkExcel(MultipartFile uploadFile, String suffix, String sheetNum, AuthResDto authResDto, String customerCode,  String modelType, String modelMappingCode) {
        Wrapper<?> checkResult = null;
        //交叉
        if(OrderConstConstant.MODEL_TYPE_ACROSS.equals(modelType)){
            //根据modelMappingCode查询数据库,将用户的Excel表格跟我们的标准模板进行映射
            //这里就要将用户的模板上的字儿完全映射成我们的格式,然后我们在校验的时候就直接按照自己的标准模板来
            int staticCell = 5;
            checkResult = acrossCheckExcel(uploadFile,suffix,sheetNum,customerCode,staticCell);

            //明细列表
        }else if(OrderConstConstant.MODEL_TYPE_BORADWISE.equals(modelType)){
            //根据modelMappingCode查询数据库,将用户的Excel表格跟我们的标准模板进行映射
            //这里就要将用户的模板上的字儿完全映射成我们的格式,然后我们在校验的时候就直接按照自己的标准模板来
            int staticCell = 5;
            checkResult = boradwiseCheckExcel(uploadFile,suffix,sheetNum,customerCode,staticCell);
        }
        return checkResult;
    }

    /**
     * 校验用户上传的Excel是否符合我们的格式
     * 交叉
     * @param uploadFile
     * @param suffix
     * @param customerCode
     * @param staticCell 固定列
     * @return
     * */
    private Wrapper<?> acrossCheckExcel(MultipartFile uploadFile, String suffix, String sheetNumChosen,  String customerCode, Integer staticCell) {
        if("xls".equals(suffix)){
            return ofcExcelCheckService.checkXlsAcross(uploadFile,sheetNumChosen,customerCode,staticCell);
        }else if("xlsx".equals(suffix)){
            return ofcExcelCheckService.checkXlsxAcross(uploadFile,sheetNumChosen,customerCode,staticCell);
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE,"上传文档格式不正确!");
    }

    /**
     * 校验用户上传的Excel是否符合我们的格式
     * 明细列表
     * @param uploadFile
     * @param suffix
     * @param sheetNumChosen
     * @param customerCode
     * @param staticCell
     * @return
     */
    private Wrapper<?> boradwiseCheckExcel(MultipartFile uploadFile, String suffix, String sheetNumChosen, String customerCode, int staticCell) {
        if("xls".equals(suffix)){
            return ofcExcelCheckService.checkXlsBoradwise(uploadFile,sheetNumChosen,customerCode,staticCell);
        }else if("xlsx".equals(suffix)){
            return ofcExcelCheckService.checkXlsxBoradwise(uploadFile,sheetNumChosen,customerCode,staticCell);
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE,"上传文档格式不正确!");
    }


}
