package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.csc.domain.CscContact;
import com.xescm.ofc.model.dto.csc.domain.CscContactCompany;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.vo.csc.CscContantAndCompanyVo;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.service.OfcOperationDistributingService;
import com.xescm.ofc.utils.JsonUtil;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.ofc.web.restcontroller.WebDto;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/11/30.
 */
@Service
public class OfcOperationDistributingServiceImpl implements OfcOperationDistributingService {

    @Autowired
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;

    @Override
    /**
     * 转换页面DTO为CSCDTO以便复用
     * @param ofcOrderDTO
     * @param purpose
     * @return
     */
    public CscContantAndCompanyDto switchOrderDtoToCscCAndCDto(OfcOrderDTO ofcOrderDTO,String purpose) {
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
    /**
     * 校验客户订单编号
     * @param jsonArray
     * @return
     * @throws Exception
     */
    @Override
    public Wrapper<?> validateCustOrderCode(JSONArray jsonArray) {
        String pageCustOrderCode = "";
        try {
            for(int i = 0; i < jsonArray.size(); i ++) {
                String json = jsonArray.get(i).toString();
                OfcOrderDTO ofcOrderDTO = (OfcOrderDTO) JsonUtil.json2Object(json, OfcOrderDTO.class);
                String custOrderCode = ofcOrderDTO.getCustOrderCode();
                if("" != custOrderCode){
                    if(!PubUtils.isSEmptyOrNull(custOrderCode) && pageCustOrderCode.equals(custOrderCode)){
                        return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方列表中第" + (i + 1) + "行,收货方名称为【" + ofcOrderDTO.getConsigneeName() + "】的客户订单编号重复！请检查！");
                    }
                    pageCustOrderCode = custOrderCode;
                    OfcFundamentalInformation ofcFundamentalInformation = new OfcFundamentalInformation();
                    ofcFundamentalInformation.setCustOrderCode(custOrderCode);
                    int checkCustOrderCodeResult = ofcFundamentalInformationService.checkCustOrderCode(ofcFundamentalInformation);
                    if (checkCustOrderCodeResult > 0) {
                        return WrapMapper.wrap(Wrapper.ERROR_CODE, "收货方列表中第" + (i + 1) + "行,收货方名称为【" + ofcOrderDTO.getConsigneeName() + "】的客户订单编号重复！请检查！");
                    }
                }

            }
        }catch (Exception ex){

        }

        return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
    }

    /**
     * 校验用户上传的Excel是否符合我们的格式
     * @param uploadFile
     * @param authResDto
     * @param custId
     * @param staticCell 固定列
     * @return
     */
    @Override
    public Wrapper<?> checkExcel(MultipartFile uploadFile, AuthResDto authResDto, String custId, Integer staticCell) {
        String filename = uploadFile.getOriginalFilename();
        String[] split = filename.split("\\.");
        String  suffix = split[1];
        boolean checkPass = true;
        HSSFWorkbook hssfWorkbook ;
        HSSFWorkbook newHssfWorkBook ;
        XSSFWorkbook xssfWorkbook ;
        XSSFWorkbook newXssfWorkBook ;
        Map<String,Object> goodsAndConsignee = new HashMap<>();
        try {
            if("xls".equals(suffix)){
                hssfWorkbook = new HSSFWorkbook(uploadFile.getInputStream());
                newHssfWorkBook = new HSSFWorkbook(uploadFile.getInputStream());
                HSSFCellStyle cellWarningStyle = newHssfWorkBook.createCellStyle();
                cellWarningStyle.setFillBackgroundColor(HSSFColor.RED.index);//设置单元格的颜色为红色
                cellWarningStyle.setFillForegroundColor(HSSFColor.RED.index);
                cellWarningStyle.setLeftBorderColor(HSSFColor.RED.index);
                cellWarningStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                int numberOfSheets = hssfWorkbook.getNumberOfSheets();
                //遍历sheet
                for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum ++){
                    HSSFSheet sheet = hssfWorkbook.getSheetAt(sheetNum);
                    //遍历row
                    for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++){
                        HSSFRow hssfRow = sheet.getRow(rowNum);
                        //遍历cell
                        for(int cellNum = 0; cellNum < hssfRow.getLastCellNum() + 1; cellNum ++){
                            HSSFCell hssfCell = hssfRow.getCell(cellNum);
                            //校验第一行,包括固定内容和收货人列表
                            if(rowNum == 0){
                                //校验模板第一行固定名称是否被改变
                                if(cellNum >= 0 && cellNum <= (staticCell -1)){
                                    //String[] cellName = {"货品编码","货品名称","规格","单位","单价"};

                                    //如果校验失败,就标记该单元格
                                    if(true){

                                        //如果校验成功,就
                                    }else{

                                    }

                                    //校验收货人名称是否在客户中心中维护了
                                }else if(cellNum > (staticCell -1)){

                                }
                            //校验从第二行开始的体
                            }else if(rowNum > 0){
                                //校验货品编码
                                if(cellNum == 0){


                                    //货品名称, 规格, 单位, 单价的数据不需校验,暂时只判断长度
                                }else if(cellNum > 0 && cellNum <= (staticCell -1)){

                                    //收货人的货品需求数量
                                }else if(cellNum > (staticCell -1)){

                                }
                            }
                        }

                    }




                    /*for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++){
                        if(rowNum == 0){//校验第一行
                            HSSFRow hssfRow = sheet.getRow(rowNum);
                            String[] cellName = {"货品编码","货品名称","规格","单位","单价"};
                            //遍历第一行
                            for(int cellNum = 0; cellNum < hssfRow.getLastCellNum() + 1; cellNum ++){
                                String firstRowCellValue = PubUtils.trimAndNullAsEmpty(hssfRow.getCell(cellNum).getStringCellValue());
                                if(cellNum < cellName.length ){
                                    if(!cellName[cellNum].equals(firstRowCellValue)){
                                        //模板固定内容
                                        checkPass = false;
                                        HSSFSheet newSheet = newHssfWorkBook.getSheetAt(sheetNum);//获取新文件的出错Sheet页
                                        HSSFRow newRow = newSheet.getRow(rowNum);
                                        HSSFCell newCell = newRow.getCell(cellNum);
                                        newCell.setCellStyle(cellWarningStyle);
                                        newCell.setCellValue(PubUtils.trimAndNullAsEmpty(hssfRow.getCell(cellNum).getStringCellValue() + "(建议:密码)"));
                                        System.out.println("sheet页第" + (sheetNum + 1) + "页,第1行,第" + (cellNum + 1) + "列的值不符合规范!请检查!");
                                    }
                                }else{
                                    //收货方名称校验
                                    String consigneeName = firstRowCellValue;
                                    CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                                    CscContactCompany cscContactCompany = new CscContactCompany();
                                    CscContact cscContact = new CscContact();
                                    cscContactCompany.setCustomerCode(custId);
                                    cscContactCompany.setContactCompanyName(consigneeName);
                                    cscContact.setPurpose("1");//用途为收货方
                                    cscContantAndCompanyDto.setCscContact(cscContact);
                                    cscContantAndCompanyDto.setCscContactCompany(cscContactCompany);
                                    Wrapper<List<CscContantAndCompanyVo>> queryCscCustomerResult = feignCscCustomerAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
                                    List<CscContantAndCompanyVo> result = queryCscCustomerResult.getResult();
                                    if(!PubUtils.isNull(result) && result.size() > 0){
                                        //如果能在客户中心查到
                                    }else{
                                        //收货人列表不在联系人档案中,则需要对当前格子进行报错处理
                                        checkPass = false;

                                    }
                                }
                            }
                        }else{
                            //接下来每一行
                            //第0列是货品编码,这个需要去客户中心进行校验
                            HSSFRow row = sheet.getRow(rowNum);
                            String goodsCode = row.getCell(0).getStringCellValue(); //货品编码
*//*
                            String url = row.getCell(1).getStringCellValue(); //url
                            String username = row.getCell(2).getStringCellValue();
                            String password = String.valueOf(row.getCell(3).getNumericCellValue());
                            Integer readCount = (int) row.getCell(4).getNumericCellValue();
                            list.add(new WebDto(name, url, username, password, readCount));
*//*
                        }
                    }*/
                    /*System.out.println("共有 " + list.size() + " 条数据：");
                    for(WebDto wd : list) {
                        System.out.println(wd);
                    }*/
                }
                String expFile = "D:/template/result.xls";
                OutputStream outputStream = new FileOutputStream(expFile);
                newHssfWorkBook.write(outputStream);
                outputStream.close();
                if(checkPass){
                    return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"校验成功!",hssfWorkbook);
                }else{
                    return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验失败!",newHssfWorkBook);
                }



















            }else if("xlsx".equals(suffix)){
                xssfWorkbook = new XSSFWorkbook(uploadFile.getInputStream());
                newXssfWorkBook = new XSSFWorkbook(uploadFile.getInputStream());
                XSSFCellStyle cellWarningStyle = newXssfWorkBook.createCellStyle();
                cellWarningStyle.setFillBackgroundColor(new XSSFColor(Color.red));//设置单元格的颜色为红色
                cellWarningStyle.setFillForegroundColor(new XSSFColor(Color.red));
                cellWarningStyle.setLeftBorderColor(new XSSFColor(Color.red));
                cellWarningStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
                int numberOfSheets = xssfWorkbook.getNumberOfSheets();
                for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum ++){
                    XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetNum);
                    List<WebDto> list = new ArrayList<>();
                    for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++){
                        if(rowNum == 0){//校验第一行是否符合我们的格式
                            XSSFRow xssfRow = sheet.getRow(rowNum);
                            String[] cellName = {"货品编码","货品名称","规格","单位","单价"};
                            //遍历第一行
                            for(int cellNum = 0; cellNum < xssfRow.getLastCellNum() + 1; cellNum ++){
                                if(cellNum < cellName.length ){
                                    if(!cellName[cellNum].equals(PubUtils.trimAndNullAsEmpty(xssfRow.getCell(cellNum).getStringCellValue()))){
                                        //模板固定内容
                                        checkPass = false;
                                        XSSFSheet newSheet = newXssfWorkBook.getSheetAt(sheetNum);//获取新文件的出错Sheet页
                                        XSSFRow newRow = newSheet.getRow(rowNum);
                                        XSSFCell newCell = newRow.getCell(cellNum);
                                        newCell.setCellStyle(cellWarningStyle);
                                        newCell.setCellValue(PubUtils.trimAndNullAsEmpty(xssfRow.getCell(cellNum).getStringCellValue() + "(建议:密码)"));
                                        System.out.println("sheet页第" + (sheetNum + 1) + "页,第1行,第" + (cellNum + 1) + "列的值不符合规范!请检查!");
                                    }
                                }else{
                                    //收货方名称
                                }
                            }
                        }else{
                            //接下来每一行
                            XSSFRow row = sheet.getRow(rowNum);
                            String name = row.getCell(0).getStringCellValue(); //名称
                            String url = row.getCell(1).getStringCellValue(); //url
                            String username = row.getCell(2).getStringCellValue();
                            String password = row.getCell(3).getStringCellValue();
                            Integer readCount = (int) row.getCell(4).getNumericCellValue();
                            list.add(new WebDto(name, url, username, password, readCount));
                        }
                    }
                    System.out.println("共有 " + list.size() + " 条数据：");
                    for(WebDto wd : list) {
                        System.out.println(wd);
                    }
                }
                String expFile = "D:/template/result.xlsx";
                OutputStream outputStream = new FileOutputStream(expFile);
                newXssfWorkBook.write(outputStream);
                outputStream.close();
                if(checkPass){
                    return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"校验成功!",xssfWorkbook);
                }else{
                    return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验失败!",newXssfWorkBook);
                }
            }else if("csv".equals(suffix)){

            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return WrapMapper.wrap(Wrapper.ERROR_CODE,"上传文档格式不正确!");
    }
}
