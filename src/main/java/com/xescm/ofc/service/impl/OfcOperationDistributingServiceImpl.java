package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.constant.ExcelCheckConstant;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.service.OfcExcelCheckService;
import com.xescm.ofc.service.OfcOperationDistributingService;
import com.xescm.ofc.service.OfcOrderPlaceService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.xescm.ofc.constant.ExcelCheckConstant.XLSX_EXCEL;
import static com.xescm.ofc.constant.ExcelCheckConstant.XLS_EXCEL;

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
    public CscContantAndCompanyDto switchOrderDtoToCscCAndCDto(OfcOrderDTO ofcOrderDTO, String purpose) {
        CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
        cscContantAndCompanyDto.setCscContactCompanyDto(new CscContactCompanyDto());
        cscContantAndCompanyDto.setCscContactDto(new CscContactDto());
        //发货方
        if(StringUtils.equals("2",purpose)){
            cscContantAndCompanyDto.getCscContactCompanyDto().setContactCompanyName(ofcOrderDTO.getConsignorName());
            cscContantAndCompanyDto.getCscContactCompanyDto().setType(ofcOrderDTO.getConsignorType());
            cscContantAndCompanyDto.getCscContactDto().setPurpose(purpose);
            cscContantAndCompanyDto.getCscContactDto().setContactName(ofcOrderDTO.getConsignorContactName());
            cscContantAndCompanyDto.getCscContactDto().setPhone(ofcOrderDTO.getConsignorContactPhone());
//            cscContantAndCompanyDto.getCscContact().setContactCompanyId(ofcOrderDTO.getConsignorCode());
            cscContantAndCompanyDto.getCscContactDto().setSerialNo(ofcOrderDTO.getConsignorContactCode());
            cscContantAndCompanyDto.getCscContactDto().setProvinceName(ofcOrderDTO.getDepartureProvince());
            cscContantAndCompanyDto.getCscContactDto().setCityName(ofcOrderDTO.getDepartureCity());
            cscContantAndCompanyDto.getCscContactDto().setAreaName(ofcOrderDTO.getDepartureDistrict());
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureTowns())){
                cscContantAndCompanyDto.getCscContactDto().setStreetName(ofcOrderDTO.getDepartureTowns());
            }
            if(PubUtils.isSEmptyOrNull(ofcOrderDTO.getDeparturePlaceCode())){
                throw new BusinessException("四级地址编码为空");
            }
            String[] departureCode = ofcOrderDTO.getDeparturePlaceCode().split(",");
            cscContantAndCompanyDto.getCscContactDto().setProvince(departureCode[0]);
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureCity()) && departureCode.length > 0){
                cscContantAndCompanyDto.getCscContactDto().setCity(departureCode[1]);
            }
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureDistrict()) && departureCode.length > 1){
                cscContantAndCompanyDto.getCscContactDto().setArea(departureCode[2]);
            }
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureTowns()) && departureCode.length > 2){
                cscContantAndCompanyDto.getCscContactDto().setStreet(departureCode[3]);
            }
            cscContantAndCompanyDto.getCscContactDto().setAddress(ofcOrderDTO.getDeparturePlace());
        }else if(StringUtils.equals("1",purpose)){
            cscContantAndCompanyDto.getCscContactCompanyDto().setContactCompanyName(ofcOrderDTO.getConsigneeName());
            cscContantAndCompanyDto.getCscContactCompanyDto().setType(ofcOrderDTO.getConsigneeType());
            cscContantAndCompanyDto.getCscContactDto().setPurpose(purpose);
            cscContantAndCompanyDto.getCscContactDto().setContactName(ofcOrderDTO.getConsigneeContactName());
            cscContantAndCompanyDto.getCscContactDto().setPhone(ofcOrderDTO.getConsigneeContactPhone());
//            cscContantAndCompanyDto.getCscContact().setContactCompanyId(ofcOrderDTO.getConsigneeCode());
            cscContantAndCompanyDto.getCscContactDto().setSerialNo(ofcOrderDTO.getConsigneeContactCode());
            cscContantAndCompanyDto.getCscContactDto().setProvinceName(ofcOrderDTO.getDestinationProvince());
            cscContantAndCompanyDto.getCscContactDto().setCityName(ofcOrderDTO.getDestinationCity());
            cscContantAndCompanyDto.getCscContactDto().setAreaName(ofcOrderDTO.getDestinationDistrict());
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDestinationTowns())){
                cscContantAndCompanyDto.getCscContactDto().setStreetName(ofcOrderDTO.getDestinationTowns());
            }
            if(PubUtils.isSEmptyOrNull(ofcOrderDTO.getDeparturePlaceCode())){
                throw new BusinessException("四级地址编码为空");
            }
            String[] departureCode = ofcOrderDTO.getDeparturePlaceCode().split(",");
            cscContantAndCompanyDto.getCscContactDto().setProvince(departureCode[0]);
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureCity()) && departureCode.length > 0){
                cscContantAndCompanyDto.getCscContactDto().setCity(departureCode[1]);
            }
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureDistrict()) && departureCode.length > 1){
                cscContantAndCompanyDto.getCscContactDto().setArea(departureCode[2]);
            }
            if(!PubUtils.isSEmptyOrNull(ofcOrderDTO.getDepartureTowns()) && departureCode.length > 2){
                cscContantAndCompanyDto.getCscContactDto().setStreet(departureCode[3]);
            }
            cscContantAndCompanyDto.getCscContactDto().setAddress(ofcOrderDTO.getDestination());
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
                ofcOrderDTO = JacksonUtil.parseJsonWithFormat(json, OfcOrderDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("校验客户订单编号转换DTO异常",e);
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
                ofcOrderDTO = JacksonUtil.parseJsonWithFormat(json, OfcOrderDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            validateOperationDistributingMsg(ofcOrderDTO);
            String orderGoodsListStr = null;
            try {
                orderGoodsListStr = JacksonUtil.toJson(ofcOrderDTO.getGoodsList());
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if(!PubUtils.isSEmptyOrNull(orderGoodsListStr)){
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            CscContantAndCompanyDto consignor = switchOrderDtoToCscCAndCDto(ofcOrderDTO,"2");//2为发货方
            CscContantAndCompanyDto consignee = switchOrderDtoToCscCAndCDto(ofcOrderDTO,"1");//1为收货方
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
            checkResult = acrossCheckExcel(uploadFile,suffix,sheetNum,customerCode,staticCell,authResDto);
//            checkResult = checkAcrossExcel(uploadFile,suffix,sheetNum,customerCode,staticCell,authResDto);

            //明细列表
        }else if(OrderConstConstant.MODEL_TYPE_BORADWISE.equals(modelType)){
            //根据modelMappingCode查询数据库,将用户的Excel表格跟我们的标准模板进行映射
            //这里就要将用户的模板上的字儿完全映射成我们的格式,然后我们在校验的时候就直接按照自己的标准模板来
            int staticCell = 5;
            checkResult = boradwiseCheckExcel(uploadFile,suffix,sheetNum,customerCode,staticCell,authResDto);
//            checkResult = checkBoradwiseExcel(uploadFile,suffix,sheetNum,customerCode,staticCell,authResDto);
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
    private Wrapper<?> acrossCheckExcel(MultipartFile uploadFile, String suffix, String sheetNumChosen,  String customerCode, Integer staticCell,AuthResDto authResDto) {
        if("xls".equals(suffix.toLowerCase())){
            return ofcExcelCheckService.checkXlsAcross(uploadFile,sheetNumChosen,customerCode,staticCell,authResDto);
        }else if("xlsx".equals(suffix.toLowerCase())){
            return ofcExcelCheckService.checkXlsxAcross(uploadFile,sheetNumChosen,customerCode,staticCell,authResDto);
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
    private Wrapper<?> boradwiseCheckExcel(MultipartFile uploadFile, String suffix, String sheetNumChosen, String customerCode, int staticCell,AuthResDto authResDto) {
        if("xls".equals(suffix.toLowerCase())){
            return ofcExcelCheckService.checkXlsBoradwise(uploadFile,sheetNumChosen,customerCode,staticCell,authResDto);
        }else if("xlsx".equals(suffix.toLowerCase())){
            return ofcExcelCheckService.checkXlsxBoradwise(uploadFile,sheetNumChosen,customerCode,staticCell,authResDto);
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE,"上传文档格式不正确!");
    }



    private Wrapper<?> checkAcrossExcel(MultipartFile uploadFile, String suffix, String sheetNumChosen, String customerCode, int staticCell,AuthResDto authResDto) {
        String suffixTolowerCase = suffix.toLowerCase();
        if(StringUtils.equals(suffixTolowerCase, XLS_EXCEL) || StringUtils.equals(suffixTolowerCase, XLSX_EXCEL)){
            return ofcExcelCheckService.checkAcrossExcel(uploadFile,sheetNumChosen,customerCode,staticCell,authResDto);
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE,"上传文档格式不正确!");
    }

    private Wrapper<?> checkBoradwiseExcel(MultipartFile uploadFile, String suffix, String sheetNumChosen, String customerCode, int staticCell,AuthResDto authResDto) {
        String suffixTolowerCase = suffix.toLowerCase();
        if(StringUtils.equals(suffixTolowerCase, XLS_EXCEL) || StringUtils.equals(suffixTolowerCase, XLSX_EXCEL)){
            return ofcExcelCheckService.checkBoradwiseExcel(uploadFile,sheetNumChosen,customerCode,staticCell,authResDto);
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE,"上传文档格式不正确!");
    }


}
