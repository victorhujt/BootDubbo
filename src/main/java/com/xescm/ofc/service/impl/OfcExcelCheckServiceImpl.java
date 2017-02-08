package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscContantAndCompanyInportDto;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.provider.CscContactEdasService;
import com.xescm.csc.provider.CscGoodsEdasService;
import com.xescm.ofc.constant.ExcelCheckConstant;
import com.xescm.ofc.enums.ExcelCheckEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.csc.OfcContantAndCompanyResponseDto;
import com.xescm.ofc.model.dto.csc.OfcGoodsApiVo;
import com.xescm.ofc.model.dto.csc.OfcGoodsImportDto;
import com.xescm.ofc.model.dto.ofc.OfcExcelBoradwise;
import com.xescm.ofc.model.vo.ofc.OfcCheckExcelErrorVo;
import com.xescm.ofc.service.OfcExcelCheckService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 城配开单Excel导入校验
 * Created by lyh on 2016/12/16.
 */
@Service
public class OfcExcelCheckServiceImpl implements OfcExcelCheckService{

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Resource
    private CscContactEdasService cscContactEdasService;
    @Resource
    private CscGoodsEdasService cscGoodsEdasService;


    /**
     * 校验交叉类型Excel
     * @param uploadFile 前台上传的文件
     * @param sheetNumChosen 前端选择的Sheet页页码
     * @param customerCode 客户编码
     * @param staticCell 从第几列开始是收货方
     * @param authResDto 当前登录用户
     * @return 根据不同结果返回不同泛型
     */
    @Override
    public Wrapper<?> checkAcrossExcel(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell, AuthResDto authResDto) {

        boolean checkPass = true;
        Map<String,JSONArray> resultMap = null;
        List<CscContantAndCompanyResponseDto> consigneeNameList;
        List<String> consigneeNameListForCheck = null;
        List<OfcGoodsApiVo> goodsApiVoList;
        List<String> goodsCodeListForCheck = null;
        List<String> xlsErrorMsg = null;
        List<OfcGoodsImportDto> cscGoodsImportDtoList = new ArrayList<>();
        List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = new ArrayList<>();
        InputStream inputStream;
        Workbook workbook;
        try {
            inputStream = uploadFile.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            logger.error("校验Excel读取内部异常,{}",e);
            throw new BusinessException("校验Excel读取内部异常");
        } catch (InvalidFormatException e) {
            logger.error("校验Excel读取内部异常{}",e);
            throw new BusinessException("校验Excel读取内部异常");
        }
        int numberOfSheets = workbook.getNumberOfSheets();

        //遍历sheet
        for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum ++){
            if(sheetNum != Integer.parseInt(sheetNumChosen)){
                continue;
            }
            Sheet sheet = workbook.getSheetAt(sheetNum);
            resultMap = new LinkedHashMap<>();
            consigneeNameList = new ArrayList<>();
            consigneeNameListForCheck = new ArrayList<>();
            goodsApiVoList = new ArrayList<>();
            goodsCodeListForCheck = new ArrayList<>();
            xlsErrorMsg = new ArrayList<>();
            //遍历row
            if(sheet.getLastRowNum() == 0){
                throw new BusinessException("请先上传Excel导入数据，再加载后执行导入！");
            }
            int realCellNum = 0;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++){
                Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = null;
                Row commonRow = sheet.getRow(rowNum);
                String mapKey = "";
                boolean hasGoods = false;
                JSONArray jsonArray = new JSONArray();

                if (null == commonRow) {
                    //标记当前行出错,并跳出当前循环
                    break;
                }

                //有效列共有多少
                if(0 == rowNum){
                    for(int cellNum = 0; cellNum < commonRow.getLastCellNum() + 1; cellNum ++) {
                        Cell commonCell = commonRow.getCell(cellNum);
                        if(null == commonCell || Cell.CELL_TYPE_BLANK  == commonCell.getCellType()){
                            break;
                        }else{
                            realCellNum ++;
                        }
                    }
                }

                //空行
                Cell cell = commonRow.getCell(0);
                if(null == cell || Cell.CELL_TYPE_BLANK  == cell.getCellType()){
                    continue;
                }

                //遍历cell
                OfcGoodsImportDto cscGoodsImportDto = new OfcGoodsImportDto();
                for(int cellNum = 0; cellNum < realCellNum; cellNum ++){
                    Cell commonCell = commonRow.getCell(cellNum);
                    //空列
                    if(null == commonCell){
                        //标记当前列出错, 并跳过当前循环
                        if(cellNum < realCellNum){
                            if(cellNum > (staticCell -1)){
                                commonCell = commonRow.createCell(rowNum,Cell.CELL_TYPE_BLANK);
                            }else{
                                continue;
                            }
                        }else{
                            break;
                        }
                    }else if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                        if(cellNum > realCellNum -1){
                            break;
                        }
                        if (rowNum != 1 || cellNum <= (staticCell -1)) {
                            continue;
                        }
                    }
                    //校验第一行,包括固定内容和收货人列表
                    String cellValue = null;
                    if(Cell.CELL_TYPE_STRING == commonCell.getCellType()){
                        cellValue = PubUtils.trimAndNullAsEmpty(commonCell.getStringCellValue());
                    }else if(Cell.CELL_TYPE_NUMERIC == commonCell.getCellType()){
                        cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(commonCell.getNumericCellValue()));
                        /*DecimalFormat df = new DecimalFormat("0");
                        cellValue = df.format(Double.valueOf(cellValue));*/
                    }
                    if(rowNum == 0){
                        //第一行全为字符串

                        //校验模板第一行前5列的固定名称是否被改变
                        if(cellNum >= 0 && cellNum <= (staticCell -1)){
                            String[] cellName = {"货品编码","货品名称","规格","单位","单价"};
                            //如果校验失败,就标记该单元格
                            if(!cellName[cellNum].equals(cellValue)){
                                //模板固定内容
                                checkPass = false;
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!建议:" + cellName[cellNum]);

                            }/*else{
                                        //如果校验成功,就不用变
                                    }*/
                            //校验5列之后的收货人名称是否在客户中心收发货档案中维护了
                        }else if(cellNum > (staticCell -1)){//   第一个收货人cellNum是5 > 5 - 1
                            if(PubUtils.isSEmptyOrNull(cellValue)){
                                consigneeNameList.add(new CscContantAndCompanyResponseDto());
                                consigneeNameListForCheck.add("");
                                continue;
                            }
                            //如果校验失败,就标记该单元格
                            CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                            CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
                            CscContactDto cscContactDto = new CscContactDto();
                            cscContantAndCompanyDto.setCustomerCode(customerCode);
                            cscContactCompanyDto.setContactCompanyName(cellValue);
                            cscContactDto.setPurpose("1");//用途为收货方
                            cscContantAndCompanyDto.setCscContactDto(cscContactDto);
                            cscContantAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
                            Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = cscContactEdasService.queryCscReceivingInfoList(cscContantAndCompanyDto);
                            if(Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()){
                                throw new BusinessException(queryCscCustomerResult.getMessage());
                            }
                            List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
                            if(null != result && result.size() > 0){
                                //如果能在客户中心查到,就将该收货人名称记录下来,往consigneeNameList里放
                                CscContantAndCompanyResponseDto cscContantAndCompanyVo = result.get(0);
                                consigneeNameList.add(cscContantAndCompanyVo);
                                consigneeNameListForCheck.add(cscContantAndCompanyVo.getContactCompanyName());
                            }else{
                                //收货人列表不在联系人档案中,则需要对当前格子进行报错处理
                                checkPass = false;
                                consigneeNameList.add(new CscContantAndCompanyResponseDto());
                                consigneeNameListForCheck.add("");
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该收货方名称在联系人档案中不存在!");
                                CscContantAndCompanyInportDto cscContantAndCompanyInportDto = addCscContantAndCompanyInportDto(ExcelCheckConstant.ACROSS,customerCode,null,authResDto,cellValue);
                                cscContantAndCompanyInportDtoList.add(cscContantAndCompanyInportDto);
                            }
                        }
                        //校验从第二行开始的体
                    }else if(rowNum > 0){
                        //校验第一列即货品编码是否已经在货品档案中进行了维护

                        JSONObject jsonObject = new JSONObject();
                        if(cellNum == 0){
                            if(Cell.CELL_TYPE_STRING == commonCell.getCellType()){
                                cellValue = PubUtils.trimAndNullAsEmpty(commonCell.getStringCellValue());
                            }else if(Cell.CELL_TYPE_NUMERIC == commonCell.getCellType()){
                                cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(commonCell.getNumericCellValue()));
                                DecimalFormat df = new DecimalFormat("0");
                                cellValue = df.format(Double.valueOf(cellValue));
                            }
                            String goodsCode = cellValue;
                            if(PubUtils.isSEmptyOrNull(goodsCode)){
                                continue;
                            }
                            CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                            cscGoodsApiDto.setGoodsCode(goodsCode);
                            cscGoodsApiDto.setCustomerCode(customerCode);
                            queryCscGoodsList = cscGoodsEdasService.queryCscGoodsList(cscGoodsApiDto);
                            if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                                checkPass = false;
                                goodsApiVoList.add(new OfcGoodsApiVo());
                                goodsCodeListForCheck.add("");
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品在货品档案中不存在!");

                                cscGoodsImportDto.setCustomerCode(customerCode);
                                cscGoodsImportDto.setGoodsCode(goodsCode);
                                //只有货品编码
                            }
                            List<CscGoodsApiVo> result = queryCscGoodsList.getResult();
                            if(null != result && result.size() > 0){
                                //如果校验成功,就往结果集里堆
                                hasGoods = true;
                                CscGoodsApiVo cscGoodsApiVo = result.get(0);
                                mapKey =cscGoodsApiVo.getGoodsCode() + "@" + rowNum;
                                OfcGoodsApiVo ofcGoodsApiVo = new OfcGoodsApiVo();
                                try {
                                    BeanUtils.copyProperties(ofcGoodsApiVo,cscGoodsApiVo);
                                    ofcGoodsApiVo.setGoodsAmount(Double.valueOf("0"));
                                } catch (Exception e) {
                                    logger.error("Excel导入异常,{}",e);
                                    throw new BusinessException("Excel导入异常");
                                }
                                goodsApiVoList.add(ofcGoodsApiVo); //
                                goodsCodeListForCheck.add(cscGoodsApiVo.getGoodsName());
                            }
                            //货品名称, 规格, 单位, 单价的数据暂时不需校验
                        }else if(cellNum > 0 && cellNum <= (staticCell -1)){
                            if(!hasGoods){
                                if(cellNum == 1){
                                    cscGoodsImportDto.setGoodsName(cellValue);
                                }else if(cellNum == 2){
                                    cscGoodsImportDto.setSpecification(cellValue);
                                }else if(cellNum == 3){
                                    cscGoodsImportDto.setUnit(cellValue);
                                }else if(cellNum == 4){
                                    cscGoodsImportDto.setUnitPrice(cellValue);
                                }
                            }


                            //收货人的货品需求数量
                        }else if(cellNum > (staticCell -1)){
                            if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                                break;
                            }
                            try{
                                //校验是否数字
                                Double goodsAmount;
                                Double goodsAndConsigneeNum;
                                if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                    goodsAndConsigneeNum = Double.valueOf("0");
                                }else{
                                    goodsAndConsigneeNum = commonCell.getNumericCellValue();
                                    if(null == goodsAndConsigneeNum){
                                        goodsAndConsigneeNum = Double.valueOf("0");
                                    }
                                }
                                //使用正则对数字进行校验
                                boolean matches = goodsAndConsigneeNum.toString().matches("\\d{1,6}\\.\\d{1,3}");
                                boolean matchesInt = goodsAndConsigneeNum.toString().matches("\\d{1,6}");
                                //如果校验成功,就往结果集里堆
                                if(matches || matchesInt){
                                    if((cellNum - staticCell) >= consigneeNameList.size()){
                                        break;
                                    }
                                    CscContantAndCompanyResponseDto cscContantAndCompanyVo = consigneeNameList.get(cellNum - staticCell);
                                    if(null == cscContantAndCompanyVo){
                                        continue;
                                    }
                                    OfcGoodsApiVo cscGoodsApiVo = goodsApiVoList.get(rowNum - 1);
                                    goodsAmount = cscGoodsApiVo.getGoodsAmount() + goodsAndConsigneeNum;
                                    cscGoodsApiVo.setGoodsAmount(goodsAmount);
                                    goodsApiVoList.remove(rowNum - 1);
                                    goodsApiVoList.add(rowNum-1,cscGoodsApiVo);
                                    String consigneeCode = cscContantAndCompanyVo.getContactCompanySerialNo();
                                    String consigneeContactCode = cscContantAndCompanyVo.getContactSerialNo();
                                    if(PubUtils.isSEmptyOrNull(consigneeCode) || PubUtils.isSEmptyOrNull(consigneeContactCode)){
//                                        throw new BusinessException("收货方编码或收货方联系人编码为空!");
                                        checkPass = false;
                                        //xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该收货方在档案中不存在或信息不完整!");
                                        break;
                                    }
                                    String consigneeMsg = consigneeCode + "@" + consigneeContactCode;
                                    jsonObject.put(consigneeMsg,goodsAndConsigneeNum);
                                    cscGoodsApiVo.setUnitPrice(cscGoodsImportDto.getUnitPrice());
                                    jsonArray.add(cscGoodsApiVo);
                                    jsonArray.add(jsonObject);
                                    jsonArray.add(cscContantAndCompanyVo);

                                    //如果数字格式不对,就标记该单元格
                                }else{
                                    checkPass = false;
                                    xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品数量格式不正确!最大999999.999!");
                                }
                                //这里只抓不是数字的情况
                            }catch (IllegalStateException ex){
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品数量不是数字格式!");
                            }catch (Exception ex){//这里的Exception再放小点, 等报错的时候看看报的是什么异常
                                logger.error("Excel导入异常,{}",ex);
                                throw new BusinessException("Excel导入异常");

                            }
                        }
                    }
                }

                if(rowNum > 0){             //避免第一行
                    if(!PubUtils.isSEmptyOrNull(cscGoodsImportDto.getGoodsCode())){
                        cscGoodsImportDtoList.add(cscGoodsImportDto);
                    }

                    resultMap.put(mapKey,jsonArray);//一条结果
                }
            }

        }
        Wrapper<List<String>> consigneeRepeatCheckResult = checkRepeat(consigneeNameListForCheck,Integer.parseInt(sheetNumChosen),"consignee");
        if(consigneeRepeatCheckResult.getCode() == Wrapper.ERROR_CODE){
            checkPass = false;
            xlsErrorMsg.addAll(consigneeRepeatCheckResult.getResult());
        }
        Wrapper<List<String>> goodsRepeatCheckResult = checkRepeat(goodsCodeListForCheck,Integer.parseInt(sheetNumChosen),"goods");
        if(goodsRepeatCheckResult.getCode() == Wrapper.ERROR_CODE){
            checkPass = false;
            xlsErrorMsg.addAll(goodsRepeatCheckResult.getResult());
        }
        if(checkPass){
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"校验成功!",resultMap);
        }else{
            OfcCheckExcelErrorVo ofcCheckExcelErrorVo = new OfcCheckExcelErrorVo();
            ofcCheckExcelErrorVo.setXlsErrorMsg(xlsErrorMsg);
            ofcCheckExcelErrorVo.setCscGoodsImportDtoList(removeGoodsRepeat(cscGoodsImportDtoList));
            ofcCheckExcelErrorVo.setCscContantAndCompanyInportDtoList(removeConsigneeRepeat(cscContantAndCompanyInportDtoList));
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验失败!我们已为您显示校验结果,请改正后重新上传!",ofcCheckExcelErrorVo);
        }

    }

    /**
     * 校验明细类型Excel
     * @param uploadFile 前台上传的文件
     * @param sheetNumChosen 前端选择的Sheet页页码
     * @param customerCode 客户编码
     * @param staticCell 从第几列开始是收货方
     * @param authResDto 当前登录用户
     * @return 根据不同结果返回不同泛型
     */
    @Override
    public Wrapper<?> checkBoradwiseExcel(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell, AuthResDto authResDto) {

        InputStream inputStream ;
        Workbook workbook;
        try {
            inputStream = uploadFile.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            logger.error("校验Excel读取内部异常{}",e);
            throw new BusinessException("校验Excel读取内部异常");
        }

        boolean checkPass = true;
        List<String> xlsErrorMsg = new ArrayList<>();
        Map<Integer,String> modelNameStr = new LinkedHashMap<>();
        Class clazz = null;
        List<OfcExcelBoradwise> ofcExcelBoradwiseList = new ArrayList<>();
        boolean requiredField = true;
        try {
            clazz = Class.forName("com.xescm.ofc.model.dto.ofc.OfcExcelBoradwise");
        } catch (ClassNotFoundException e) {
            logger.error("校验Excel,ClassNotFoundException:{}",e);
        }
        int numberOfSheets = workbook.getNumberOfSheets();

        //遍历sheet
        for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum ++) {
            if (sheetNum != Integer.parseInt(sheetNumChosen)) {
                continue;
            }
            Sheet sheet = workbook.getSheetAt(sheetNum);

            //遍历row
            if(sheet.getLastRowNum() == 0){
                throw new BusinessException("请先上传Excel导入数据，再加载后执行导入！");
            }
            //遍历行
            for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++) {
                Row commonRow = sheet.getRow(rowNum);
                OfcExcelBoradwise ofcExcelBoradwise = null;
                try {
                    ofcExcelBoradwise = (OfcExcelBoradwise) clazz.newInstance();
                }catch (Exception e) {
                    logger.error("校验明细类型Excel:{}",e);
                }
                if (null == commonRow) {
                    //标记当前行出错,并跳出当前循环
                    break;
                }
                //空行
                Cell cell = commonRow.getCell(0);
                if(null == cell || Cell.CELL_TYPE_BLANK  == cell.getCellType()){
                    continue;
                }

                //遍历列
                for(int cellNum = 0; cellNum < commonRow.getLastCellNum() + 1; cellNum ++) {
                    Cell commonCell = commonRow.getCell(cellNum);
                    //空列
                    if (null == commonCell  && cellNum > 11) {
                        //标记当前列出错, 并跳过当前循环
                        break;
                    }
                    //校验第一行,包括固定内容和收货人列表
                    String cellValue = null;
                    if (commonCell != null && Cell.CELL_TYPE_STRING == commonCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(commonCell.getStringCellValue());
                    } else if (commonCell != null && Cell.CELL_TYPE_NUMERIC == commonCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(commonCell.getNumericCellValue()));

                    }
                    //至此, 已经能拿到每一列的值
                    if(rowNum == 0){//第一行, 将所有表格中固定的字段名称和位置固定
                        if(PubUtils.isSEmptyOrNull(cellValue)){
                            break;
                        }
                        String refCellValue = cellReflectToDomain(cellValue); //标准表字段映射成对应实体的字段的值
                        modelNameStr.put(cellNum,refCellValue);
                    }else if(rowNum > 0){ // 表格的数据体
                        String cellNumName = modelNameStr.get(cellNum); //拿到当前列对应的字段的值
                        try {
                            if(ExcelCheckEnum.CONSIGNEE_NAME.getCode().equals(cellNumName)){
                                if(null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                    xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!收货方名称必填!");
                                    requiredField = false;
                                    continue;
                                }
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,cellValue);
                            }else if(ExcelCheckEnum.ORDER_TIME.getCode().equals(cellNumName)){
                                logger.info("对订单日期进行特殊处理");
                            }else if(ExcelCheckEnum.GOODS_AMOUNT.getCode().equals(cellNumName)){//货品数量
                                if(null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                    cellValue = "0";
                                }
                                boolean matchesPot = cellValue.matches("\\d{1,6}\\.\\d{1,3}");
                                boolean matchesInt = cellValue.matches("\\d{1,6}");
                                //如果校验成功,就往结果集里堆
                                if(matchesPot || matchesInt){
                                    BigDecimal bigDecimal = new BigDecimal(cellValue);
                                    Field field = clazz.getDeclaredField(cellNumName);
                                    field.setAccessible(true);
                                    field.set(ofcExcelBoradwise,bigDecimal);
                                }else{
                                    checkPass = false;
                                    xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品数量格式不正确!");
                                }
                            }else if(ExcelCheckEnum.GOODS_UNITPIRCE.getCode().equals(cellNumName)){//货品单价
                                if(null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                    cellValue = "0";
                                }


                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,bigDecimal);
                            }else if(ExcelCheckEnum.CONSIGNEECONTACT_PHONE.getCode().equals(cellNumName)){//电话
                                if(null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                    cellValue = "0";
                                }
                                String format = null;
                                String cellValuePhone;
                                if (commonCell != null && Cell.CELL_TYPE_STRING == commonCell.getCellType()) {
                                    cellValuePhone = PubUtils.trimAndNullAsEmpty(commonCell.getStringCellValue());
                                    format = cellValuePhone;
                                } else if (commonCell != null && Cell.CELL_TYPE_NUMERIC == commonCell.getCellType()) {
                                    cellValuePhone = PubUtils.trimAndNullAsEmpty(String.valueOf(commonCell.getNumericCellValue()));
                                    DecimalFormat df = new DecimalFormat("0");
                                    format = df.format(Double.valueOf(cellValuePhone));
                                }
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,format);
                            }else if(ExcelCheckEnum.CUST_ORDER_CODE.getCode().equals(cellNumName)){
                                if(null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                    xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!客户订单号必填!");
                                    requiredField = false;
                                    continue;
                                }
                                //客户订单号
                                cellValue=checkCommonCell(commonCell,cellValue);
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,cellValue);
                            }else if(ExcelCheckEnum.GOODS_CODE.getCode().equals(cellNumName)){
                                if(null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                    xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!货品编码必填!");
                                    requiredField = false;
                                    continue;
                                }
                                cellValue=checkCommonCell(commonCell,cellValue);
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,cellValue);
                            }else if(!PubUtils.isSEmptyOrNull(cellNumName)){
                                if(null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                    cellValue = "";
                                }
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,cellValue);
                            }
                        } catch (Exception e) {
                            logger.error("校验明细类型Excel, 遍历表格的数据体:{}",e);
                        }
                    }
                }
                if(rowNum > 0){
                    ofcExcelBoradwiseList.add(ofcExcelBoradwise);
                }
            }
        }
        //至此,表格中所有的数据都存在了ofcExcelBoradwiseList里,然后对ofcExcelBoradwiseList进行遍
        //无法再按照之前的方法进行
        //如果必填字段有缺失
        if(!requiredField){
            OfcCheckExcelErrorVo ofcCheckExcelErrorVo = new OfcCheckExcelErrorVo();
            ofcCheckExcelErrorVo.setXlsErrorMsg(xlsErrorMsg);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验失败!我们已为您显示校验结果,请改正后重新上传!",ofcCheckExcelErrorVo);
        }

        Map<String,JSONArray> resultMap = new LinkedHashMap<>();
        Map<String,OfcContantAndCompanyResponseDto> getEEByCustOrderCode = new HashMap<>();
        Map<String,Boolean> orderByCustOrderCode = new HashMap<>();
        List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = new ArrayList<>();
        List<OfcGoodsImportDto> cscGoodsImportDtoList = new ArrayList<>();


        for(OfcExcelBoradwise ofcExcelBoradwise : ofcExcelBoradwiseList){
            ofcExcelBoradwise.setGoodsCode(ofcExcelBoradwise.getGoodsCode() + "@" + ofcExcelBoradwise.getGoodsUnitPirce());
            String custOrderCode = ofcExcelBoradwise.getCustOrderCode();
            String mapKey;
            //如果该客户订单编号是第一次出现
            if(null == orderByCustOrderCode.get(custOrderCode)){//---
                orderByCustOrderCode.put(custOrderCode,false);

                //取客户订单编号, 订单日期, 收货方名称, 联系人, 联系电话, 地址, 货品编码,货品名称,规格,单位,数量,单价, 往设定好的数据结构里放
                //去接口查该收货方名称是否在客户中心维护
                CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
                CscContactDto cscContactDto = new CscContactDto();
                cscContantAndCompanyDto.setCustomerCode(customerCode);
                cscContactCompanyDto.setContactCompanyName(ofcExcelBoradwise.getConsigneeName());
                cscContactDto.setPurpose("1");//用途为收货方
                cscContantAndCompanyDto.setCscContactDto(cscContactDto);
                cscContantAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
                Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = cscContactEdasService.queryCscReceivingInfoList(cscContantAndCompanyDto);
                if(Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()){
                    throw new BusinessException(queryCscCustomerResult.getMessage());
                }
                List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto;
                //如果存在
                if(null != result && result.size() > 0){
//                    orderByCustOrderCode.put(custOrderCode,true);
                    //如果能在客户中心查到,就将该收货人名称记录下来,往consigneeNameList里放
                    OfcContantAndCompanyResponseDto ofcContantAndCompanyResponseDto = new OfcContantAndCompanyResponseDto();
                    cscContantAndCompanyResponseDto = result.get(0);
                    try {
                        BeanUtils.copyProperties(ofcContantAndCompanyResponseDto,cscContantAndCompanyResponseDto);
                    } catch (Exception e) {
                        logger.error("校验明细类型Excel异常,{}",e);
                        throw new BusinessException("校验明细类型Excel异常");
                    }
                    ofcContantAndCompanyResponseDto.setCustOrderCode(custOrderCode);
                    ofcContantAndCompanyResponseDto.setContactSerialNo(cscContantAndCompanyResponseDto.getContactSerialNo() + "@" + custOrderCode);//___
                    getEEByCustOrderCode.put(ofcExcelBoradwise.getCustOrderCode(),ofcContantAndCompanyResponseDto);
                    String jsonObjectKey = ofcContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + ofcContantAndCompanyResponseDto.getContactSerialNo();

                    //其中货品编码要判断一下是否在Map中存在,
                    if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                        //如果不存在则去接口判断该货品是否在客户中心中进行维护了
                        CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                        cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode().split("\\@")[0]);
                        cscGoodsApiDto.setCustomerCode(customerCode);
                        Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = cscGoodsEdasService.queryCscGoodsList(cscGoodsApiDto);
                        resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                        checkPass=checkGoodsCode(queryCscGoodsList,checkPass,xlsErrorMsg,ofcExcelBoradwise,customerCode,cscGoodsImportDtoList);
                        List<CscGoodsApiVo> cscGoodsApiVoResult = queryCscGoodsList.getResult();
                        if(null != cscGoodsApiVoResult && cscGoodsApiVoResult.size() > 0){
                            JSONArray jsonArray = new JSONArray();
                            //如果维护了就只取收货方和数量
                            CscGoodsApiVo cscGoodsApiVo = cscGoodsApiVoResult.get(0);
                            mapKey = cscGoodsApiVo.getGoodsCode() + "@" + ofcExcelBoradwise.getGoodsUnitPirce();
                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put(jsonObjectKey,ofcExcelBoradwise.getGoodsAmount());
                            cscGoodsApiVo.setUnitPrice(String.valueOf(ofcExcelBoradwise.getGoodsUnitPirce()));
                            cscGoodsApiVo.setGoodsCode(cscGoodsApiDto.getGoodsCode() + "@" + ofcExcelBoradwise.getGoodsUnitPirce());
                            OfcGoodsApiVo ofcGoodsApiVo = new OfcGoodsApiVo();
                            try {
                                BeanUtils.copyProperties(ofcGoodsApiVo,cscGoodsApiVo);
                                ofcGoodsApiVo.setGoodsAmount(Double.valueOf("0"));
                            } catch (Exception e) {
                                logger.error("校验明细类型Excel异常,{}",e);
                                throw new BusinessException("校验明细类型Excel异常");
                            }
                            jsonArray.add(ofcGoodsApiVo);
                            jsonArray.add(jsonObject);
                            jsonArray.add(ofcContantAndCompanyResponseDto);

                            resultMap.put(mapKey,jsonArray);
                            //校验两个不同的客户订单编号对应了同一个收货方//判断JSONArray的第二个格子是否有该收货方了,如果有就提示出错!
                        }
                    }else{
                        //如果在Map中已经存在,则存对应收货方和数量
                        JSONArray jsonArrayExistGoods = resultMap.get(ofcExcelBoradwise.getGoodsCode());
                        if(null == jsonArrayExistGoods){
//                            break;
                            continue;
                        }
                        JSONObject jsonObjectExistGoods = (JSONObject) jsonArrayExistGoods.get(1);
                        jsonObjectExistGoods.put(jsonObjectKey,ofcExcelBoradwise.getGoodsAmount());
                        jsonArrayExistGoods.remove(1);
                        jsonArrayExistGoods.add(1,jsonObjectExistGoods);
                        resultMap.put(ofcExcelBoradwise.getGoodsCode(),jsonArrayExistGoods);
                        //
                    }
                }else{
                    //收货人列表不在联系人档案中,则需要对当前格子进行报错处理
                    //如果不存在,就输出错误信息,并向cscContantAndCompanyInportDtoList中添加,checkPass=false
                    checkPass = false;
                    xlsErrorMsg.add("收货方名称【" + ofcExcelBoradwise.getConsigneeName() + "】在联系人档案中不存在!");
                    CscContantAndCompanyInportDto cscContantAndCompanyInportDto = addCscContantAndCompanyInportDto(ExcelCheckConstant.BROADWISE,customerCode,ofcExcelBoradwise,authResDto,null);
                    cscContantAndCompanyInportDtoList.add(cscContantAndCompanyInportDto);
                    //即使当前收货方没有维护, 也要继续校验货品档案
                    if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                        resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                        CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                        cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode().split("\\@")[0]);
                        cscGoodsApiDto.setCustomerCode(customerCode);
                        Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = cscGoodsEdasService.queryCscGoodsList(cscGoodsApiDto);
                        checkPass=checkGoodsCode(queryCscGoodsList,checkPass,xlsErrorMsg,ofcExcelBoradwise,customerCode,cscGoodsImportDtoList);
                    }
                }
                //如果该客户订单编号不是第一次出现
            }else{

                //其中货品编码要判断一下是否在Map中存在,
                if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                    //如果不存在则去接口判断该货品是否在客户中心中进行维护了
                    CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                    cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode().split("\\@")[0]);
                    cscGoodsApiDto.setCustomerCode(customerCode);
                    Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = cscGoodsEdasService.queryCscGoodsList(cscGoodsApiDto);
                    if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                        checkPass = false;
                        xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode().split("\\@")[0] + "】在货品档案中不存在!请维护!");
                        OfcGoodsImportDto cscGoodsImportDto = addCscGoodsImportDto(ExcelCheckConstant.BROADWISE,customerCode,ofcExcelBoradwise);
                        cscGoodsImportDtoList.add(cscGoodsImportDto);
                        continue;
                    }
                    List<CscGoodsApiVo> cscGoodsApiVoResult = queryCscGoodsList.getResult();
                    if(null != cscGoodsApiVoResult && cscGoodsApiVoResult.size() > 0){
                        //如果维护了就只取收货方和数量
                        JSONArray jsonArray = new JSONArray();
                        CscGoodsApiVo cscGoodsApiVo = cscGoodsApiVoResult.get(0);
//                            mapKey = cscGoodsApiVo.getGoodsCode() + "@" + rowNum;
                        mapKey = cscGoodsApiVo.getGoodsCode() + "@" + ofcExcelBoradwise.getGoodsUnitPirce();
                        JSONObject jsonObject = new JSONObject();
                        OfcContantAndCompanyResponseDto cscContantAndCompanyResponseDto = getEEByCustOrderCode.get(ofcExcelBoradwise.getCustOrderCode());
                        if(null == cscContantAndCompanyResponseDto){
                            continue;
                        }
                        String jsonObjectKey = cscContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + cscContantAndCompanyResponseDto.getContactSerialNo();
                        jsonObject.put(jsonObjectKey,ofcExcelBoradwise.getGoodsAmount());
                        cscGoodsApiVo.setUnitPrice(String.valueOf(ofcExcelBoradwise.getGoodsUnitPirce()));
                        cscGoodsApiVo.setGoodsCode(cscGoodsApiDto.getGoodsCode() + "@" + ofcExcelBoradwise.getGoodsUnitPirce());

                        OfcGoodsApiVo ofcGoodsApiVo = new OfcGoodsApiVo();
                        try {
                            BeanUtils.copyProperties(ofcGoodsApiVo,cscGoodsApiVo);
                            ofcGoodsApiVo.setGoodsAmount(Double.valueOf("0"));
                        } catch (Exception e) {
                            logger.error("校验明细类型Excel异常,{}",e);
                            throw new BusinessException("校验明细类型Excel异常");
                        }
                        jsonArray.add(ofcGoodsApiVo);
                        jsonArray.add(jsonObject);
                        jsonArray.add(cscContantAndCompanyResponseDto);

                        resultMap.put(mapKey,jsonArray);
                        //校验两个不同的客户订单编号对应了同一个收货方//判断JSONArray的第二个格子是否有该收货方了,如果有就提示出错!
                    }else{
                        //如果没维护,就输出错误信息,并向cscGoodsImportDtoList中添加,checkPass=false
                        //如果校验失败,就标记该单元格
                        checkPass = false;
                        xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode().split("\\@")[0] + "】在货品档案中不存在!请维护!");
                        OfcGoodsImportDto cscGoodsImportDto = addCscGoodsImportDto(ExcelCheckConstant.BROADWISE,customerCode,ofcExcelBoradwise);
                        cscGoodsImportDtoList.add(cscGoodsImportDto);
                    }

                }else{
                    //如果在Map中已经存在,则说明同一个客户订单编号下, 出现了两个一样的货品编码! 不是这样的,也可能说明两个客户订单编号下有两个不同的收货方有相同的货品需求//很明显的情况是有重复货品的那个客户订单重复出现两次!
                    JSONArray jsonArrayExistGoods = resultMap.get(ofcExcelBoradwise.getGoodsCode());
                    if(null != jsonArrayExistGoods && null != jsonArrayExistGoods.get(1)){
                        JSONObject jsonObjectExistGoods = (JSONObject) jsonArrayExistGoods.get(1);
                        //相同货品编码的当前订单编号下的做累加
                        BigDecimal laterGoodsAmount = ofcExcelBoradwise.getGoodsAmount();
                        OfcContantAndCompanyResponseDto cscContantAndCompanyResponseDto = getEEByCustOrderCode.get(ofcExcelBoradwise.getCustOrderCode());
                        if(null == cscContantAndCompanyResponseDto){
                            continue;
                        }
                        String jsonObjectKey = cscContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + cscContantAndCompanyResponseDto.getContactSerialNo();
                        BigDecimal goodsAmount = (BigDecimal) jsonObjectExistGoods.get(jsonObjectKey);
                        if(null == goodsAmount){
                            goodsAmount = new BigDecimal(0);
                        }
                        laterGoodsAmount = laterGoodsAmount.add(goodsAmount);
                        jsonObjectExistGoods.put(jsonObjectKey,laterGoodsAmount);

                    }

                }
            }
        }

        if(checkPass){
            int goodsRowNum = 1;
            //将MapKey更改为适合前端模板的格式
            Map<String,JSONArray> afterResultMap = new LinkedHashMap<>();
            int consigneeNum = 1;
            List<String> goodsCodeNoAmountList = new ArrayList<>();
            for(String mapKey : resultMap.keySet()){
                JSONArray jsonArray = resultMap.get(mapKey);

                for(String custOrderCode : getEEByCustOrderCode.keySet()){
                    //第一个3
                    if(1 == consigneeNum){
                        //所有的货品, 每个收货人都应该有, 如果没有就置为0, 而且要将3个一循环的数据堆齐
                        if(null == jsonArray.get(0)){
                            throw new BusinessException("货品校验时出错!");
                        }
                        OfcGoodsApiVo cscGoodsApiVo = (OfcGoodsApiVo) jsonArray.get(0);
                        Double goodsAmout = cscGoodsApiVo.getGoodsAmount();
                        JSONObject jsonObject = (JSONObject) jsonArray.get(1);
                        for(String custOrderCodeIn : getEEByCustOrderCode.keySet()){
                            OfcContantAndCompanyResponseDto cscContantAndCompanyResponseDto = getEEByCustOrderCode.get(custOrderCodeIn);
                            String consigneeAndGoodsKey = cscContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + cscContantAndCompanyResponseDto.getContactSerialNo();
                            if(null == jsonObject.get(consigneeAndGoodsKey)){
                                jsonObject.put(consigneeAndGoodsKey,new BigDecimal(0));
                            }else{
                                BigDecimal bigDecimal = (BigDecimal) jsonObject.get(consigneeAndGoodsKey);
                                goodsAmout = bigDecimal.add(new BigDecimal(goodsAmout.toString())).doubleValue();
                            }
                        }
                        if(goodsAmout == 0){
                            goodsCodeNoAmountList.add(cscGoodsApiVo.getGoodsCode());
                        }
                        cscGoodsApiVo.setGoodsAmount(goodsAmout);
                        jsonArray.remove(1);
                        jsonArray.add(1,jsonObject);
                        jsonArray.remove(2);
                        jsonArray.add(2,getEEByCustOrderCode.get(custOrderCode));
                        resultMap.put(mapKey,jsonArray);

                    }else{
                        jsonArray.add(null);//cscGoodsApiVo
                        jsonArray.add(null);//jsonObject
                        jsonArray.add(getEEByCustOrderCode.get(custOrderCode));
                        resultMap.put(mapKey,jsonArray);
                    }
                    consigneeNum ++;
                }
                consigneeNum = 1;
                OfcGoodsApiVo ofcGoodsApiVo = (OfcGoodsApiVo) jsonArray.get(0);
                if(ofcGoodsApiVo.getGoodsAmount() != 0){
                    afterResultMap.put(mapKey + "@" + goodsRowNum++,resultMap.get(mapKey));
                }
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"校验成功!",afterResultMap );
        }else{
            OfcCheckExcelErrorVo ofcCheckExcelErrorVo = new OfcCheckExcelErrorVo();
            ofcCheckExcelErrorVo.setXlsErrorMsg(xlsErrorMsg);
            ofcCheckExcelErrorVo.setCscContantAndCompanyInportDtoList(removeConsigneeRepeat(cscContantAndCompanyInportDtoList));
            ofcCheckExcelErrorVo.setCscGoodsImportDtoList(removeGoodsRepeat(cscGoodsImportDtoList));
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验失败!我们已为您显示校验结果,请改正后重新上传!",ofcCheckExcelErrorVo);
        }
    }

    /**
     * 校验收货方或货品出现重复
     * @param consigneeOrGoodsList 收货方或货品列表
     * @param sheetNum 当前Sheet页
     * @param tag 标记验重的是收货方还是货品:   goods 货品, consignee 收货方
     * @return 校验结果
     */
    private Wrapper<List<String>> checkRepeat(List<String> consigneeOrGoodsList, int sheetNum, String tag){
        List<String> errorConsigneeOrGoodsList = new ArrayList<>();
        Map<String,String> consigneeOrGoodsRepeatMap = new HashMap<>();
        Integer consigneeOrGoodsNum;
        if("goods".equals(tag)){
            consigneeOrGoodsNum = 2;
        }else if("consignee".equals(tag)){
            consigneeOrGoodsNum = 5;
        }else {
            logger.error("校验收货方或货品出现重复,tag位入参有误");
            throw new BusinessException("校验收货方或货品出现重复错误");
        }
        for(String consigneeOrGoodsName : consigneeOrGoodsList){
            if(PubUtils.isSEmptyOrNull(consigneeOrGoodsName)){
                consigneeOrGoodsNum ++;
                continue;
            }
            String repeatStr = consigneeOrGoodsRepeatMap.get(consigneeOrGoodsName);
            if(!PubUtils.isSEmptyOrNull(repeatStr)){
                repeatStr += "," + consigneeOrGoodsNum;
                consigneeOrGoodsRepeatMap.put(consigneeOrGoodsName, repeatStr);
            }else{
                consigneeOrGoodsRepeatMap.put(consigneeOrGoodsName, consigneeOrGoodsNum.toString());
            }
            consigneeOrGoodsNum ++;
        }

        if("goods".equals(tag)){
            for(String goodsCode : consigneeOrGoodsRepeatMap.keySet()){
                String repeatStr = consigneeOrGoodsRepeatMap.get(goodsCode);
                if(repeatStr.split(",").length > 1){
                    errorConsigneeOrGoodsList.add("sheet页第" + sheetNum + "页,第1列,第" + repeatStr + "行的值不符合规范!原因:货品编码重复!");
                }
            }
        }else if("consignee".equals(tag)){
            for(String consigneeName : consigneeOrGoodsRepeatMap.keySet()){
                String repeatStr = consigneeOrGoodsRepeatMap.get(consigneeName);
                if(consigneeOrGoodsRepeatMap.get(consigneeName).split(",").length > 1){
                    errorConsigneeOrGoodsList.add("sheet页第" + sheetNum + "页,第1行,第" + repeatStr + "列的值不符合规范!原因:收货人名称重复!");
                }
            }
        }
        if(errorConsigneeOrGoodsList.size() > 0){
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"Excel导入收货人或货品出现重复",errorConsigneeOrGoodsList);
        }else{
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE);
        }
    }

    /**
     * 向客户中心批量添加收货方时去重
     * @param cscContantAndCompanyInportDtoList 批量添加收货方List
     * @return  List
     */
    private List<CscContantAndCompanyInportDto> removeConsigneeRepeat(List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList){
        Set<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoSet = new HashSet<>();
        cscContantAndCompanyInportDtoSet.addAll(cscContantAndCompanyInportDtoList);
        cscContantAndCompanyInportDtoList = new ArrayList<>();
        for(CscContantAndCompanyInportDto cscContantAndCompanyInportDto : cscContantAndCompanyInportDtoSet){
            cscContantAndCompanyInportDtoList.add(cscContantAndCompanyInportDto);
        }
        return cscContantAndCompanyInportDtoList;
    }


    /**
     * 向客户中心批量添加货品时去重
     * @param cscGoodsImportDtoList 批量添加货品List
     * @return  List
     */
    private List<OfcGoodsImportDto> removeGoodsRepeat(List<OfcGoodsImportDto> cscGoodsImportDtoList){
        Set<OfcGoodsImportDto> cscGoodsImportDtoSet = new HashSet<>();
        cscGoodsImportDtoSet.addAll(cscGoodsImportDtoList);
        cscGoodsImportDtoList = new ArrayList<>();
        for(OfcGoodsImportDto cscGoodsImportDto : cscGoodsImportDtoSet){
            boolean noRepeat = true;
            for(OfcGoodsImportDto ofcGoodsImportDtoIn : cscGoodsImportDtoList){
                if(StringUtils.equals(cscGoodsImportDto.getGoodsCode(),ofcGoodsImportDtoIn.getGoodsCode())){
                    noRepeat = false;
                }
            }
            if(noRepeat){
                cscGoodsImportDtoList.add(cscGoodsImportDto);
            }
        }
        return cscGoodsImportDtoList;
    }


    /**
     * 批量添加收货方
     * @param tag 标记是交叉还是明细Excel
     * @param customerCode 客户编码
     * @param ofcExcelBoradwise 明细Excel实体
     * @param authResDto 当前登录用户
     * @param cellValue 单元格的内容
     * @return  List
     */

    private CscContantAndCompanyInportDto addCscContantAndCompanyInportDto(String tag,String customerCode,OfcExcelBoradwise ofcExcelBoradwise,AuthResDto authResDto, String cellValue){
        CscContantAndCompanyInportDto cscContantAndCompanyInportDto = new CscContantAndCompanyInportDto();
        if(StringUtils.equals(tag,ExcelCheckConstant.BROADWISE)){
            cscContantAndCompanyInportDto.setCustCode(customerCode);
            if(null != ofcExcelBoradwise){
                cscContantAndCompanyInportDto.setContactCompanyName(ofcExcelBoradwise.getConsigneeName());
                cscContantAndCompanyInportDto.setContactName(ofcExcelBoradwise.getConsigneeContactName());
                cscContantAndCompanyInportDto.setPhone(ofcExcelBoradwise.getConsigneeContactPhone());
                cscContantAndCompanyInportDto.setAddress(ofcExcelBoradwise.getConsigneeAddress());
            }
            if(null != authResDto){
                cscContantAndCompanyInportDto.setUserId(authResDto.getUserId());
                cscContantAndCompanyInportDto.setUserName(authResDto.getUserName());
            }
        }else if(StringUtils.equals(tag,ExcelCheckConstant.ACROSS)){
            cscContantAndCompanyInportDto.setCustCode(customerCode);
            cscContantAndCompanyInportDto.setContactCompanyName(cellValue);
            cscContantAndCompanyInportDto.setUserId(authResDto.getUserId());
            cscContantAndCompanyInportDto.setUserName(authResDto.getUserName());
        }
        return cscContantAndCompanyInportDto;
    }

    /**
     * 批量添加货品
     * @param tag 标记是交叉还是明细Excel
     * @param customerCode 客户编码
     * @param ofcExcelBoradwise 明细Excel实体
     * @return  List
     */
    private OfcGoodsImportDto addCscGoodsImportDto(String tag, String customerCode, OfcExcelBoradwise ofcExcelBoradwise){
        OfcGoodsImportDto cscGoodsImportDto = new OfcGoodsImportDto();
        if(StringUtils.equals(tag, ExcelCheckConstant.BROADWISE)){
            cscGoodsImportDto.setCustomerCode(customerCode);
            cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode().split("@")[0]);
            cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
            cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
            cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
        }else if(StringUtils.equals(tag,ExcelCheckConstant.ACROSS)){
            logger.info("Across");
        }
        return cscGoodsImportDto;
    }


    /**
     * 列名和实体的映射
     * @param cellName 明细Excel当前列名
     * @return  List
     */
    private String cellReflectToDomain(String cellName){
        if(StringUtils.equals(cellName, ExcelCheckEnum.CUST_ORDER_CODE.getDesc())){
            return ExcelCheckEnum.CUST_ORDER_CODE.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.ORDER_TIME.getDesc())){
            return ExcelCheckEnum.ORDER_TIME.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.CONSIGNEE_NAME.getDesc())){
            return ExcelCheckEnum.CONSIGNEE_NAME.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.CONSIGNEECONTACT_NAME.getDesc())){
            return ExcelCheckEnum.CONSIGNEECONTACT_NAME.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.CONSIGNEECONTACT_PHONE.getDesc())){
            return ExcelCheckEnum.CONSIGNEECONTACT_PHONE.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.CONSIGNEE_ADDRESS.getDesc())){
            return ExcelCheckEnum.CONSIGNEE_ADDRESS.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.GOODS_CODE.getDesc())){
            return ExcelCheckEnum.GOODS_CODE.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.GOODS_NAME.getDesc())){
            return ExcelCheckEnum.GOODS_NAME.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.GOODS_SPEC.getDesc())){
            return ExcelCheckEnum.GOODS_SPEC.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.GOODS_UNIT.getDesc())){
            return ExcelCheckEnum.GOODS_UNIT.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.GOODS_AMOUNT.getDesc())){
            return ExcelCheckEnum.GOODS_AMOUNT.getCode();
        }else if(StringUtils.equals(cellName,ExcelCheckEnum.GOODS_UNITPIRCE.getDesc())){
            return ExcelCheckEnum.GOODS_UNITPIRCE.getCode();
        }else {
            return "";
        }

    }

    /**
     * <p>Title:    .检查 </p>
     * <p>Description  CommonCell</p>
     *
     * @param    commonCell    cell
     * @param    cellValue     value
     * @author        杨东旭
     * @CreateDate    2017/2/4
     * @return      String
     */
    private String checkCommonCell(Cell commonCell,String cellValue){
        if (commonCell != null && Cell.CELL_TYPE_STRING == commonCell.getCellType()) {
            cellValue = PubUtils.trimAndNullAsEmpty(commonCell.getStringCellValue());
        } else if (commonCell != null && Cell.CELL_TYPE_NUMERIC == commonCell.getCellType()) {
            cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(commonCell.getNumericCellValue()));
            DecimalFormat df = new DecimalFormat("0");
            cellValue = df.format(Double.valueOf(cellValue));
        }
        return cellValue;
    }

    /**
     * <p>Title:    .检查 </p>
     * <p>Description  GoodsCode</p>
     *
     * @param     queryCscGoodsList
     * @param     checkPass
     * @param     xlsErrorMsg
     * @param     ofcExcelBoradwise
     * @param     customerCode
     * @param     cscGoodsImportDtoList
     * @author        杨东旭
     * @CreateDate    2017/2/4
     * @return    Boolean
     */
    private Boolean checkGoodsCode(Wrapper<List<CscGoodsApiVo>> queryCscGoodsList,boolean checkPass,
                                List<String> xlsErrorMsg,OfcExcelBoradwise ofcExcelBoradwise,
                                String customerCode,List<OfcGoodsImportDto> cscGoodsImportDtoList){
        if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
            checkPass = false;
            xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode().split("@")[0] + "】在货品档案中不存在!请维护!");
            OfcGoodsImportDto cscGoodsImportDto = addCscGoodsImportDto(ExcelCheckConstant.BROADWISE,customerCode,ofcExcelBoradwise);
            cscGoodsImportDtoList.add(cscGoodsImportDto);
        }
        return checkPass;
    }
}
