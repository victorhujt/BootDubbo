package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.contantAndCompany.CscContactCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.ofc.constant.OrderConstConstant;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.enums.ResultCodeEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcOrderInfoDTO;
import com.xescm.ofc.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.xescm.ofc.constant.ExcelCheckConstant.XLSX_EXCEL;
import static com.xescm.ofc.constant.ExcelCheckConstant.XLS_EXCEL;
import static com.xescm.ofc.constant.OrderConstConstant.PENDING_AUDIT;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.ORDER_TAG_OPER_DISTRI;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.REVIEW;

/**
 * 城配开单
 * Created by lyh on 2016/11/30.
 */
@Service
public class OfcOperationDistributingServiceImpl implements OfcOperationDistributingService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcOrderPlaceService ofcOrderPlaceService;
    @Resource
    private OfcExcelCheckService ofcExcelCheckService;
    @Resource
    private OfcFundamentalInformationService ofcFundamentalInfoService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;

    /**
     * 转换页面DTO为CSCCUSTOMERDTO以便复用原有下单逻辑
     * @param ofcOrderDTO 订单实体
     * @param purpose 联系人用途: 2 发货方, 1 收货方
     * @return      CscContantAndCompanyDto
     */
    @Override
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
                logger.error("转换页面DTO为CSCCUSTOMERDTO以便复用原有下单逻辑,四级地址编码为空");
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
                logger.error("转换页面DTO为CSCCUSTOMERDTO以便复用原有下单逻辑,四级地址编码为空");
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
     * @param jsonArray 订单json数组
     * @return  Wrapper
     */
    @Override
    public Wrapper<?> validateCustOrderCode(JSONArray jsonArray) {
        List<String> custOrderCodeList = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i ++) {
            String json = jsonArray.get(i).toString();
            OfcOrderDTO ofcOrderDTO;
            try {
                ofcOrderDTO = JacksonUtil.parseJsonWithFormat(json, OfcOrderDTO.class);
            } catch (Exception e) {
                logger.error("校验客户订单编号转换DTO异常{}",e);
                throw new BusinessException("校验客户订单编号转换DTO异常");
            }
            String custOrderCode = ofcOrderDTO.getCustOrderCode();
            if(!PubUtils.isSEmptyOrNull(custOrderCode)){
                if(custOrderCodeList.contains(custOrderCode)){
                    logger.error("收货方列表中第" + (i + 1) + "行,收货方名称为【" + ofcOrderDTO.getConsigneeName() + "】的客户订单编号重复！");
                    return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方列表中第" + (i + 1) + "行,收货方名称为【" + ofcOrderDTO.getConsigneeName() + "】的客户订单编号重复！请检查！");
                }
                custOrderCodeList.add(custOrderCode);
            }
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }


    /**
     * 获取用户上传的Excel的sheet页
     * @param uploadFile 前台上传的文件
     * @param suffix Excel后缀 xls,xlsx
     * @return      List
     */
    @Override
    public List<String> getExcelSheet(MultipartFile uploadFile, String suffix) {
        List<String> sheetMsgList = new ArrayList<>();
        if("xls".equals(suffix)){
            HSSFWorkbook hssfWorkbook;
            try {
                hssfWorkbook = new HSSFWorkbook(uploadFile.getInputStream());
            } catch (IOException e) {
                logger.error("获取Sheet页内部异常,{}",e);
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
            XSSFWorkbook xssfWorkbook;
            try {
                xssfWorkbook = new XSSFWorkbook(uploadFile.getInputStream());
            } catch (IOException e) {
                logger.error("获取Sheet页内部异常,{}",e);
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
     * @param ofcOrderDTO 订单实体
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
     * @param jsonArray 订单json数组
     * @return      String
     */
    @Override
    @Transactional
    public String distributingOrderPlace(JSONArray jsonArray,AuthResDto authResDtoByToken,String batchNumber) {
        String resultMessage = null;
        // 检查客户订单号是否重复
        this.checkCustOrderCode(jsonArray);
        boolean sendMQ = true;
        List<OfcOrderInfoDTO> result = new ArrayList<>();
        // 导入
        for (Object aJsonArray : jsonArray) {
            OfcOrderInfoDTO ofcOrderInfoDTO;
            String json = aJsonArray.toString();
            OfcOrderDTO ofcOrderDTO = null;
            try {
                ofcOrderDTO = JacksonUtil.parseJsonWithFormat(json, OfcOrderDTO.class);
            } catch (Exception e) {
                logger.error("城配开单批量下单循环入口, JSON转换异常, {}", e);
            }
            //2017年4月7日 追加逻辑: 开单员即登录人
            ofcOrderDTO.setMerchandiser(authResDtoByToken.getUserName());
            this.validateOperationDistributingMsg(ofcOrderDTO);
            String orderGoodsListStr = null;
            try {
                orderGoodsListStr = JacksonUtil.toJson(ofcOrderDTO != null ? ofcOrderDTO.getGoodsList() : new OfcOrderDTO());
            } catch (Exception e) {
                logger.error("城配开单批量下单循环入口, JSON转换异常, {}", e);
            }
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = new ArrayList<>();
            if (!PubUtils.isSEmptyOrNull(orderGoodsListStr)) {
                ofcGoodsDetailsInfos = JSONObject.parseArray(orderGoodsListStr, OfcGoodsDetailsInfo.class);
            }
            CscContantAndCompanyDto consignor = switchOrderDtoToCscCAndCDto(ofcOrderDTO, "2");//2为发货方
            CscContantAndCompanyDto consignee = switchOrderDtoToCscCAndCDto(ofcOrderDTO, "1");//1为收货方
            if (ofcOrderDTO != null) {
                ofcOrderDTO.setOrderBatchNumber(batchNumber);
            }
            try {
                ofcOrderInfoDTO = ofcOrderPlaceService.distributionPlaceOrder(ofcOrderDTO, ofcGoodsDetailsInfos, ORDER_TAG_OPER_DISTRI, authResDtoByToken, ofcOrderDTO != null ? ofcOrderDTO.getCustCode() : new OfcOrderDTO().toString()
                    , consignor, consignee);
                result.add(ofcOrderInfoDTO);
            } catch (Exception ex) {
                sendMQ = false;
                logger.error("城配导单导入订单发生异常：异常详情 => {}", ex);
                break;
            }
            if (ResultCodeEnum.ERROROPER.getMsg().equals(resultMessage)) {
                return resultMessage;
            }
        }
        // 发送订单信息到Tfc、Ac、Whc
        if (sendMQ && PubUtils.isNotNullAndBiggerSize(result, 0)) {
            for (OfcOrderInfoDTO ofcOrderInfoDTO : result) {
                distributionSendMQ(ofcOrderInfoDTO, authResDtoByToken);
            }
        }
        return resultMessage;
    }

    /**
     * 城配导单发送到下方系统
     * @param ofcOrderInfoDTO
     * @param authResDtoByToken
     */
    private void distributionSendMQ(OfcOrderInfoDTO ofcOrderInfoDTO, AuthResDto authResDtoByToken) {
        try {
            OfcFundamentalInformation ofcFundamentalInformation = ofcOrderInfoDTO.getOfcFundamentalInformation();
            OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcOrderInfoDTO.getOfcDistributionBasicInfo();
            OfcFinanceInformation ofcFinanceInformation = ofcOrderInfoDTO.getOfcFinanceInformation();
            OfcWarehouseInformation ofcWarehouseInformation = ofcOrderInfoDTO.getOfcWarehouseInformation();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = ofcOrderInfoDTO.getGoodsDetailsInfoList();
            if (!PubUtils.isSEmptyOrNull(ofcFundamentalInformation.getOrderBatchNumber())) {
                //进行自动审核
                String code = ofcOrderManageService.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetailsInfos, ofcDistributionBasicInfo,
                    ofcWarehouseInformation, ofcFinanceInformation, PENDING_AUDIT, REVIEW, authResDtoByToken);
                if (StringUtils.equals(String.valueOf(Wrapper.ERROR_CODE),code)) {
                    throw new BusinessException("自动审核操作失败!");
                }
            }
            //城配开单订单推结算中心
            ofcOrderManageService.pushOrderToAc(ofcFundamentalInformation,ofcFinanceInformation,ofcDistributionBasicInfo,ofcGoodsDetailsInfos, ofcWarehouseInformation);
        } catch (Exception e) {
            
        }
    }

    // 检查客户订单号重复
    private void checkCustOrderCode(JSONArray jsonArray) {
        for (Object aJsonArray : jsonArray) {
            String json = aJsonArray.toString();
            OfcOrderDTO ofcOrderDTO = null;
            try {
                ofcOrderDTO = JacksonUtil.parseJsonWithFormat(json, OfcOrderDTO.class);
            } catch (Exception e) {
                logger.error("城配开单批量下单循环入口, JSON转换异常, {}", e);
            }
            // 检查客户订单号是否重复
            String custCode = ofcOrderDTO.getCustCode();
            String custOrderCode = ofcOrderDTO.getCustOrderCode();
            if (!PubUtils.isNull(custCode) && !PubUtils.isNull(custOrderCode)) {
                OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
                ofcFundamentalInformation.setCustCode(custCode);
                ofcFundamentalInformation.setCustOrderCode(custOrderCode);
                int count = ofcFundamentalInfoService.checkCustOrderCode(ofcFundamentalInformation);
                if (count >= 1) {
                    throw new BusinessException("客户订单编号" + ofcFundamentalInformation.getCustOrderCode() + "已经存在!您不能重复下单!");
                }
            }
        }
    }

    /**
     * Excel校验入口
     * 在这里区分模板类型
     * @param uploadFile 前台上传的文件
     * @param suffix Excel后缀: xls, xlsx
     * @param sheetNum 前端选择的Sheet页页码
     * @param authResDto 当前登录用户
     * @param customerCode 客户编码
     * @param modelType Excel模板类型: 交叉, 明细
     * @param modelMappingCode 保留
     * @return  Wrapper
     */
    @Override
    public Wrapper<?> checkExcel(MultipartFile uploadFile, String suffix, String sheetNum
            , AuthResDto authResDto, String customerCode,  String modelType, String modelMappingCode) {
        Wrapper<?> checkResult = null;
        int staticCell = 5;
        //交叉
        if(OrderConstConstant.MODEL_TYPE_ACROSS.equals(modelType)){
            //根据modelMappingCode查询数据库,将用户的Excel表格跟我们的标准模板进行映射
            //这里就要将用户的模板上的字儿完全映射成我们的格式,然后我们在校验的时候就直接按照自己的标准模板来
            checkResult = checkAcrossExcel(uploadFile,suffix,sheetNum,customerCode,staticCell,authResDto);

            //明细列表
        }else if(OrderConstConstant.MODEL_TYPE_BORADWISE.equals(modelType)){
            //根据modelMappingCode查询数据库,将用户的Excel表格跟我们的标准模板进行映射
            //这里就要将用户的模板上的字儿完全映射成我们的格式,然后我们在校验的时候就直接按照自己的标准模板来
            checkResult = checkBoradwiseExcel(uploadFile,suffix,sheetNum,customerCode,staticCell,authResDto);
        }
        return checkResult;
    }

    /**
     * 校验不同类型Excel模板:Across
     * @param uploadFile 前台上传的文件
     * @param suffix Excel后缀: xls, xlsx
     * @param sheetNumChosen 前端选择的Sheet页页码
     * @param customerCode 客户编码
     * @param staticCell 从第几列开始是收货方
     * @param authResDto 当前登录用户
     * @return  Wrapper
     */
    private Wrapper<?> checkAcrossExcel(MultipartFile uploadFile, String suffix, String sheetNumChosen, String customerCode, int staticCell,AuthResDto authResDto) {
        String suffixTolowerCase = suffix.toLowerCase();
        if(StringUtils.equals(suffixTolowerCase, XLS_EXCEL) || StringUtils.equals(suffixTolowerCase, XLSX_EXCEL)){
            return ofcExcelCheckService.checkAcrossExcel(uploadFile,sheetNumChosen,customerCode,staticCell,authResDto);
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE,"上传文档格式不正确!");
    }

    /**
     * 校验不同类型Excel模板:Boradwise
     * @param uploadFile 前台上传的文件
     * @param suffix Excel后缀: xls, xlsx
     * @param sheetNumChosen 前端选择的Sheet页页码
     * @param customerCode 客户编码
     * @param staticCell 从第几列开始是收货方
     * @param authResDto 当前登录用户
     * @return  Wrapper
     */
    private Wrapper<?> checkBoradwiseExcel(MultipartFile uploadFile, String suffix, String sheetNumChosen, String customerCode, int staticCell,AuthResDto authResDto) {
        String suffixTolowerCase = suffix.toLowerCase();
        if(StringUtils.equals(suffixTolowerCase, XLS_EXCEL) || StringUtils.equals(suffixTolowerCase, XLSX_EXCEL)){
            return ofcExcelCheckService.checkBoradwiseExcel(uploadFile,sheetNumChosen,customerCode,staticCell,authResDto);
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE,"上传文档格式不正确!");
    }


}
