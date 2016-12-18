package com.xescm.ofc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.feign.client.FeignCscContactAPIClient;
import com.xescm.ofc.feign.client.FeignCscGoodsAPIClient;
import com.xescm.ofc.model.dto.csc.*;
import com.xescm.ofc.model.dto.csc.domain.CscContact;
import com.xescm.ofc.model.dto.csc.domain.CscContactCompany;
import com.xescm.ofc.model.dto.ofc.OfcExcelBoradwise;
import com.xescm.ofc.model.vo.csc.CscGoodsApiVo;
import com.xescm.ofc.model.vo.ofc.OfcCheckExcelErrorVo;
import com.xescm.ofc.service.OfcExcelCheckService;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by lyh on 2016/12/16.
 */
@Service
public class OfcExcelCheckServiceImpl implements OfcExcelCheckService{

    @Autowired
    private FeignCscContactAPIClient feignCscContactAPIClient;
    @Autowired
    private FeignCscGoodsAPIClient feignCscGoodsAPIClient;

    /**
     * 校验XLS格式
     * 明细列表
     * @param uploadFile
     * @param sheetNumChosen
     * @param customerCode
     * @param staticCell
     * @return
     */
    @Override
    public Wrapper<?> checkXlsBoradwise(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell,AuthResDto authResDto) {

        HSSFWorkbook hssfWorkbook = null;
        Map<Integer,String> modelNameStr = new LinkedHashMap<>();
        Class clazz = null;
        List<OfcExcelBoradwise> ofcExcelBoradwiseList = new ArrayList<>();
        try {
            clazz = Class.forName("com.xescm.ofc.model.dto.ofc.OfcExcelBoradwise");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            hssfWorkbook = new HSSFWorkbook(uploadFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("校验Excel读取内部异常");
        }
        int numberOfSheets = hssfWorkbook.getNumberOfSheets();

        //遍历sheet
        for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum ++) {
            if (sheetNum != Integer.valueOf(sheetNumChosen)) {
                continue;
            }
            HSSFSheet sheet = hssfWorkbook.getSheetAt(sheetNum);

            //遍历row
            if(sheet.getLastRowNum() == 0){
                throw new BusinessException("请先上传Excel导入数据，再加载后执行导入！");
            }
            //遍历行
            for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++) {
                HSSFRow hssfRow = sheet.getRow(rowNum);
                String mapKey = "";
                JSONArray jsonArray = new JSONArray();
                OfcExcelBoradwise ofcExcelBoradwise = null;
                try {
                    ofcExcelBoradwise = (OfcExcelBoradwise) clazz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //空行
                HSSFCell cell = hssfRow.getCell(0);
                if(null == cell || HSSFCell.CELL_TYPE_BLANK  == cell.getCellType()){
                    continue;
                }
                if (null == hssfRow) {
                    //标记当前行出错,并跳出当前循环
                    break;
                }
                //遍历列
                for(int cellNum = 0; cellNum < hssfRow.getLastCellNum() + 1; cellNum ++) {
                    HSSFCell hssfCell = hssfRow.getCell(cellNum);
                    //空列
                    if (null == hssfCell) {
                        //标记当前列出错, 并跳过当前循环
                        break;
                    } else if (HSSFCell.CELL_TYPE_BLANK == hssfCell.getCellType()) {
                        continue;
                    }
                    //校验第一行,包括固定内容和收货人列表
                    String cellValue = null;
                    if (HSSFCell.CELL_TYPE_STRING == hssfCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(hssfCell.getStringCellValue());
                    } else if (HSSFCell.CELL_TYPE_NUMERIC == hssfCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(hssfCell.getNumericCellValue()));
                    } /*else if(HSSFCell.Cell){

                    }*/
                    //至此, 已经能拿到每一列的值
                    if(rowNum == 0){//第一行, 将所有表格中固定的字段名称和位置固定
                        String refCellValue = cellReflectToDomain(cellValue); //标准表字段映射成对应实体的字段的值
                        modelNameStr.put(cellNum,refCellValue);
                    }else if(rowNum > 0){ // 表格的数据体
                        String cellNumName = modelNameStr.get(cellNum); //拿到当前列对应的字段的值
                        try {
                            if("orderTime".equals(cellNumName)){
                                //对订单日期进行特殊处理
                            }else if("goodsAmount".equals(cellNumName)){//货品数量
                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,bigDecimal);
                            }else if("goodsUnitPirce".equals(cellNumName)){//货品单价
                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,bigDecimal);
                            }else if("consigneeContactPhone".equals(cellNumName)){//电话
                                DecimalFormat df = new DecimalFormat("0");
                                String format = df.format(Double.valueOf(cellValue));
                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,format);
                            }else{
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,cellValue);
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
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
        boolean checkPass = true;

        Map<String,JSONArray> resultMap = new LinkedHashMap<>();
        Map<String,CscContantAndCompanyResponseDto> getEEByCustOrderCode = new HashMap<>();
        Map<String,Boolean> orderByCustOrderCode = new HashMap<>();
        List<String> xlsErrorMsg = new ArrayList<>();
        List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = new ArrayList<>();
        List<CscGoodsImportDto> cscGoodsImportDtoList = new ArrayList<>();


        for(OfcExcelBoradwise ofcExcelBoradwise : ofcExcelBoradwiseList){
            String custOrderCode = ofcExcelBoradwise.getCustOrderCode();
            String mapKey = null;
            //如果该客户订单编号是第一次出现
            if(null == orderByCustOrderCode.get(custOrderCode) || orderByCustOrderCode.get(custOrderCode)){
                orderByCustOrderCode.put(custOrderCode,false);

                //取客户订单编号, 订单日期, 收货方名称, 联系人, 联系电话, 地址, 货品编码,货品名称,规格,单位,数量,单价, 往设定好的数据结构里放
                //去接口查该收货方名称是否在客户中心维护
                CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                CscContactCompany cscContactCompany = new CscContactCompany();
                CscContact cscContact = new CscContact();
                cscContantAndCompanyDto.setCustomerCode(customerCode);
                cscContactCompany.setContactCompanyName(ofcExcelBoradwise.getConsigneeName());
                cscContact.setPurpose("1");//用途为收货方
                cscContantAndCompanyDto.setCscContact(cscContact);
                cscContantAndCompanyDto.setCscContactCompany(cscContactCompany);
                Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = feignCscContactAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
                if(Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()){
                    throw new BusinessException(queryCscCustomerResult.getMessage());
                }
                List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = null;
                //如果存在
                if(null != result && result.size() > 0){
                    orderByCustOrderCode.put(custOrderCode,true);
                    //如果能在客户中心查到,就将该收货人名称记录下来,往consigneeNameList里放
                    cscContantAndCompanyResponseDto = result.get(0);
                    cscContantAndCompanyResponseDto.setCustOrderCode(custOrderCode);
                    getEEByCustOrderCode.put(ofcExcelBoradwise.getCustOrderCode(),cscContantAndCompanyResponseDto);
                    String jsonObjectKey = cscContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + cscContantAndCompanyResponseDto.getContactSerialNo();

                    //其中货品编码要判断一下是否在Map中存在,
                    if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                        //如果不存在则去接口判断该货品是否在客户中心中进行维护了
                        CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                        cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsApiDto.setCustomerCode(customerCode);
                        Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                        resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                        if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                            checkPass = false;
                            xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                            CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                            cscGoodsImportDto.setCustomerCode(customerCode);
                            cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                            cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                            cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                            cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                            cscGoodsImportDtoList.add(cscGoodsImportDto);
                        }
                        List<CscGoodsApiVo> cscGoodsApiVoResult = queryCscGoodsList.getResult();
                        if(null != cscGoodsApiVoResult && cscGoodsApiVoResult.size() > 0){
                            JSONArray jsonArray = new JSONArray();
                            //如果维护了就只取收货方和数量
                            CscGoodsApiVo cscGoodsApiVo = cscGoodsApiVoResult.get(0);
//                            mapKey = cscGoodsApiVo.getGoodsCode() + "@" + rowNum;
                            mapKey = cscGoodsApiVo.getGoodsCode();
                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put(jsonObjectKey,ofcExcelBoradwise.getGoodsAmount());
                            jsonArray.add(cscGoodsApiVo);
                            jsonArray.add(jsonObject);
                            jsonArray.add(cscContantAndCompanyResponseDto);

                            resultMap.put(mapKey,jsonArray);
                            //校验两个不同的客户订单编号对应了同一个收货方//判断JSONArray的第二个格子是否有该收货方了,如果有就提示出错!
                        }/*else{//如果货品既没在Map中也不在档案中
                            //如果没维护,就输出错误信息,并向cscGoodsImportDtoList中添加,checkPass=false
                            //如果校验失败,就标记该单元格
                            checkPass = false;
                            resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                            xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                            CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                            cscGoodsImportDto.setCustomerCode(customerCode);
                            cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                            cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                            cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                            cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                            cscGoodsImportDtoList.add(cscGoodsImportDto);
                        }*/
                    }else{
                        //如果在Map中已经存在,则存对应收货方和数量
                        JSONArray jsonArrayExistGoods = resultMap.get(ofcExcelBoradwise.getGoodsCode());
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
                    CscContantAndCompanyInportDto cscContantAndCompanyInportDto = new CscContantAndCompanyInportDto();
                    cscContantAndCompanyInportDto.setCustCode(customerCode);
                    cscContantAndCompanyInportDto.setContactCompanyName(ofcExcelBoradwise.getConsigneeName());
                    cscContantAndCompanyInportDto.setContactName(ofcExcelBoradwise.getConsigneeContactName());
                    cscContantAndCompanyInportDto.setPhone(ofcExcelBoradwise.getConsigneeContactPhone());
                    cscContantAndCompanyInportDto.setAddress(ofcExcelBoradwise.getConsigneeAddress());
                    cscContantAndCompanyInportDto.setUserId(authResDto.getUserId());
                    cscContantAndCompanyInportDto.setUserName(authResDto.getUserName());
                    cscContantAndCompanyInportDtoList.add(cscContantAndCompanyInportDto);
                    //即使当前收货方没有维护, 也要继续校验货品档案
                    if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                        resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                        CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                        cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsApiDto.setCustomerCode(customerCode);
                        Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                        if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                            checkPass = false;
                            xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                            CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                            cscGoodsImportDto.setCustomerCode(customerCode);
                            cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                            cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                            cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                            cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                            cscGoodsImportDtoList.add(cscGoodsImportDto);
                        }

                    }
                }
                //如果该客户订单编号不是第一次出现
            }else{
                //只取货品编码,货品名称,规格,单位,数量,单价,往设定好的数据结构里放
                if(!orderByCustOrderCode.get(custOrderCode)){//
                    if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                        resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                        CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                        cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsApiDto.setCustomerCode(customerCode);
                        Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                        if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                            checkPass = false;
                            xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                            CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                            cscGoodsImportDto.setCustomerCode(customerCode);
                            cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                            cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                            cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                            cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                            cscGoodsImportDtoList.add(cscGoodsImportDto);
                        }

                    }

                    continue;
                }
                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = getEEByCustOrderCode.get(ofcExcelBoradwise.getCustOrderCode());

                String jsonObjectKey = cscContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + cscContantAndCompanyResponseDto.getContactSerialNo();
                //其中货品编码要判断一下是否在Map中存在,
                if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                    //如果不存在则去接口判断该货品是否在客户中心中进行维护了
                    CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                    cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                    cscGoodsApiDto.setCustomerCode(customerCode);
                    Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                    if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                        checkPass = false;
                        xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                        CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                        cscGoodsImportDto.setCustomerCode(customerCode);
                        cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                        cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                        cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                        cscGoodsImportDtoList.add(cscGoodsImportDto);
                        continue;
                    }
                    List<CscGoodsApiVo> cscGoodsApiVoResult = queryCscGoodsList.getResult();
                    if(null != cscGoodsApiVoResult && cscGoodsApiVoResult.size() > 0){
                        //如果维护了就只取收货方和数量
                        JSONArray jsonArray = new JSONArray();
                        CscGoodsApiVo cscGoodsApiVo = cscGoodsApiVoResult.get(0);
//                            mapKey = cscGoodsApiVo.getGoodsCode() + "@" + rowNum;
                        mapKey = cscGoodsApiVo.getGoodsCode();
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put(jsonObjectKey,ofcExcelBoradwise.getGoodsAmount());
                        jsonArray.add(cscGoodsApiVo);
                        jsonArray.add(jsonObject);
                        jsonArray.add(cscContantAndCompanyResponseDto);

                        resultMap.put(mapKey,jsonArray);
                        //校验两个不同的客户订单编号对应了同一个收货方//判断JSONArray的第二个格子是否有该收货方了,如果有就提示出错!
                    }else{
                        //如果没维护,就输出错误信息,并向cscGoodsImportDtoList中添加,checkPass=false
                        //如果校验失败,就标记该单元格
                        checkPass = false;
                        xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                        CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                        cscGoodsImportDto.setCustomerCode(customerCode);
                        cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                        cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                        cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                        cscGoodsImportDtoList.add(cscGoodsImportDto);
                    }

                }else{
                    //如果在Map中已经存在,则报错!
                    JSONArray jsonArrayExistGoods = resultMap.get(ofcExcelBoradwise.getGoodsCode());
                    JSONObject jsonObjectExistGoods = (JSONObject) jsonArrayExistGoods.get(1);
                    if(null != jsonObjectExistGoods){
                        checkPass = false;
                        xlsErrorMsg.add("收货方名称【" + ofcExcelBoradwise.getConsigneeName() + "】匹配到两个客户订单编号!请检查!");
                    }else{
                        jsonObjectExistGoods.put(jsonObjectKey,ofcExcelBoradwise.getGoodsAmount());
                    }
                }
            }
        }

        if(checkPass){
            int goodsRowNum = 1;
            //将MapKey更改为适合前端模板的格式
            Map<String,JSONArray> afterResultMap = new LinkedHashMap<>();
            Boolean consigneeTag = false;
            int consigneeNum = 1;
            for(String mapKey : resultMap.keySet()){
                JSONArray jsonArray = resultMap.get(mapKey);

                for(String custOrderCode : getEEByCustOrderCode.keySet()){
                    //第一个3
                    if(1 == consigneeNum){
                        //所有的货品, 每个收货人都应该有, 如果没有就置为0, 而且要将3个一循环的数据堆齐
                        CscGoodsApiVo cscGoodsApiVo = (CscGoodsApiVo) jsonArray.get(0);
                        Double goodsAmout = cscGoodsApiVo.getGoodsAmount();
                        JSONObject jsonObject = (JSONObject) jsonArray.get(1);
                        for(String custOrderCodeIn : getEEByCustOrderCode.keySet()){
                            CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = getEEByCustOrderCode.get(custOrderCodeIn);
                            String consigneeAndGoodsKey = cscContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + cscContantAndCompanyResponseDto.getContactSerialNo();
                            if(null == jsonObject.get(consigneeAndGoodsKey)){
                                jsonObject.put(consigneeAndGoodsKey,new BigDecimal(0));
                            }else{
                                BigDecimal bigDecimal = (BigDecimal) jsonObject.get(consigneeAndGoodsKey);
                                goodsAmout = bigDecimal.doubleValue() + goodsAmout;
                            }
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
                afterResultMap.put(mapKey + "@" + goodsRowNum++,resultMap.get(mapKey));
            }


            return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"校验成功!",afterResultMap );
        }else{
            OfcCheckExcelErrorVo ofcCheckExcelErrorVo = new OfcCheckExcelErrorVo();
            ofcCheckExcelErrorVo.setXlsErrorMsg(xlsErrorMsg);
            ofcCheckExcelErrorVo.setCscContantAndCompanyInportDtoList(cscContantAndCompanyInportDtoList);
            ofcCheckExcelErrorVo.setCscGoodsImportDtoList(cscGoodsImportDtoList);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验失败!我们已为您显示校验结果,请改正后重新上传!",ofcCheckExcelErrorVo);
        }
    }


    /**
     * 校验XLSX格式
     * 明细列表
     * @param uploadFile
     * @param sheetNumChosen
     * @param customerCode
     * @param staticCell
     * @return
     */
    @Override
    public Wrapper<?> checkXlsxBoradwise(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell,AuthResDto authResDto) {
        XSSFWorkbook xssfWorkbook = null;
        Map<Integer,String> modelNameStr = new LinkedHashMap<>();
        Class clazz = null;
        List<OfcExcelBoradwise> ofcExcelBoradwiseList = new ArrayList<>();
        try {
            clazz = Class.forName("com.xescm.ofc.model.dto.ofc.OfcExcelBoradwise");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            xssfWorkbook = new XSSFWorkbook(uploadFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("校验Excel读取内部异常");
        }
        int numberOfSheets = xssfWorkbook.getNumberOfSheets();

        //遍历sheet
        for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum ++) {
            if (sheetNum != Integer.valueOf(sheetNumChosen)) {
                continue;
            }
            XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetNum);

            //遍历row
            if(sheet.getLastRowNum() == 0){
                throw new BusinessException("请先上传Excel导入数据，再加载后执行导入！");
            }
            //遍历行
            for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++) {
                XSSFRow xssfRow = sheet.getRow(rowNum);
                String mapKey = "";
                JSONArray jsonArray = new JSONArray();
                OfcExcelBoradwise ofcExcelBoradwise = null;
                try {
                    ofcExcelBoradwise = (OfcExcelBoradwise) clazz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //空行
                XSSFCell cell = xssfRow.getCell(0);
                if(null == cell || XSSFCell.CELL_TYPE_BLANK  == cell.getCellType()){
                    continue;
                }
                if (null == xssfRow) {
                    //标记当前行出错,并跳出当前循环
                    break;
                }
                //遍历列
                for(int cellNum = 0; cellNum < xssfRow.getLastCellNum() + 1; cellNum ++) {
                    XSSFCell xssfCell = xssfRow.getCell(cellNum);
                    //空列
                    if (null == xssfCell) {
                        //标记当前列出错, 并跳过当前循环
                        break;
                    } else if (XSSFCell.CELL_TYPE_BLANK == xssfCell.getCellType()) {
                        continue;
                    }
                    //校验第一行,包括固定内容和收货人列表
                    String cellValue = null;
                    if (XSSFCell.CELL_TYPE_STRING == xssfCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(xssfCell.getStringCellValue());
                    } else if (XSSFCell.CELL_TYPE_NUMERIC == xssfCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(xssfCell.getNumericCellValue()));
                    } /*else if(XSSFCell.Cell){

                    }*/
                    //至此, 已经能拿到每一列的值
                    if(rowNum == 0){//第一行, 将所有表格中固定的字段名称和位置固定
                        String refCellValue = cellReflectToDomain(cellValue); //标准表字段映射成对应实体的字段的值
                        modelNameStr.put(cellNum,refCellValue);
                    }else if(rowNum > 0){ // 表格的数据体
                        String cellNumName = modelNameStr.get(cellNum); //拿到当前列对应的字段的值
                        try {
                            if("orderTime".equals(cellNumName)){
                                //对订单日期进行特殊处理
                            }else if("goodsAmount".equals(cellNumName)){//货品数量
                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,bigDecimal);
                            }else if("goodsUnitPirce".equals(cellNumName)){//货品单价
                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,bigDecimal);
                            }else if("consigneeContactPhone".equals(cellNumName)){//电话
                                DecimalFormat df = new DecimalFormat("0");
                                String format = df.format(Double.valueOf(cellValue));
                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,format);
                            }else{
                                Field field = clazz.getDeclaredField(cellNumName);
                                field.setAccessible(true);
                                field.set(ofcExcelBoradwise,cellValue);
                            }
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
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
        boolean checkPass = true;

        Map<String,JSONArray> resultMap = new LinkedHashMap<>();
        Map<String,CscContantAndCompanyResponseDto> getEEByCustOrderCode = new HashMap<>();
        Map<String,Boolean> orderByCustOrderCode = new HashMap<>();
        List<String> xlsErrorMsg = new ArrayList<>();
        List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = new ArrayList<>();
        List<CscGoodsImportDto> cscGoodsImportDtoList = new ArrayList<>();


        for(OfcExcelBoradwise ofcExcelBoradwise : ofcExcelBoradwiseList){
            String custOrderCode = ofcExcelBoradwise.getCustOrderCode();
            String mapKey = null;
            //如果该客户订单编号是第一次出现
            if(null == orderByCustOrderCode.get(custOrderCode) || orderByCustOrderCode.get(custOrderCode)){
                orderByCustOrderCode.put(custOrderCode,false);

                //取客户订单编号, 订单日期, 收货方名称, 联系人, 联系电话, 地址, 货品编码,货品名称,规格,单位,数量,单价, 往设定好的数据结构里放
                //去接口查该收货方名称是否在客户中心维护
                CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                CscContactCompany cscContactCompany = new CscContactCompany();
                CscContact cscContact = new CscContact();
                cscContantAndCompanyDto.setCustomerCode(customerCode);
                cscContactCompany.setContactCompanyName(ofcExcelBoradwise.getConsigneeName());
                cscContact.setPurpose("1");//用途为收货方
                cscContantAndCompanyDto.setCscContact(cscContact);
                cscContantAndCompanyDto.setCscContactCompany(cscContactCompany);
                Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = feignCscContactAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
                if(Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()){
                    throw new BusinessException(queryCscCustomerResult.getMessage());
                }
                List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = null;
                //如果存在
                if(null != result && result.size() > 0){
                    orderByCustOrderCode.put(custOrderCode,true);
                    //如果能在客户中心查到,就将该收货人名称记录下来,往consigneeNameList里放
                    cscContantAndCompanyResponseDto = result.get(0);
                    cscContantAndCompanyResponseDto.setCustOrderCode(custOrderCode);
                    getEEByCustOrderCode.put(ofcExcelBoradwise.getCustOrderCode(),cscContantAndCompanyResponseDto);
                    String jsonObjectKey = cscContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + cscContantAndCompanyResponseDto.getContactSerialNo();

                    //其中货品编码要判断一下是否在Map中存在,
                    if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                        //如果不存在则去接口判断该货品是否在客户中心中进行维护了
                        CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                        cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsApiDto.setCustomerCode(customerCode);
                        Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                        resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                        if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                            checkPass = false;
                            xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                            CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                            cscGoodsImportDto.setCustomerCode(customerCode);
                            cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                            cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                            cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                            cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                            cscGoodsImportDtoList.add(cscGoodsImportDto);
                        }
                        List<CscGoodsApiVo> cscGoodsApiVoResult = queryCscGoodsList.getResult();
                        if(null != cscGoodsApiVoResult && cscGoodsApiVoResult.size() > 0){
                            JSONArray jsonArray = new JSONArray();
                            //如果维护了就只取收货方和数量
                            CscGoodsApiVo cscGoodsApiVo = cscGoodsApiVoResult.get(0);
//                            mapKey = cscGoodsApiVo.getGoodsCode() + "@" + rowNum;
                            mapKey = cscGoodsApiVo.getGoodsCode();
                            JSONObject jsonObject = new JSONObject();

                            jsonObject.put(jsonObjectKey,ofcExcelBoradwise.getGoodsAmount());
                            jsonArray.add(cscGoodsApiVo);
                            jsonArray.add(jsonObject);
                            jsonArray.add(cscContantAndCompanyResponseDto);

                            resultMap.put(mapKey,jsonArray);
                            //校验两个不同的客户订单编号对应了同一个收货方//判断JSONArray的第二个格子是否有该收货方了,如果有就提示出错!
                        }/*else{//如果货品既没在Map中也不在档案中
                            //如果没维护,就输出错误信息,并向cscGoodsImportDtoList中添加,checkPass=false
                            //如果校验失败,就标记该单元格
                            checkPass = false;
                            resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                            xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                            CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                            cscGoodsImportDto.setCustomerCode(customerCode);
                            cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                            cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                            cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                            cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                            cscGoodsImportDtoList.add(cscGoodsImportDto);
                        }*/
                    }else{
                        //如果在Map中已经存在,则存对应收货方和数量
                        JSONArray jsonArrayExistGoods = resultMap.get(ofcExcelBoradwise.getGoodsCode());
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
                    CscContantAndCompanyInportDto cscContantAndCompanyInportDto = new CscContantAndCompanyInportDto();
                    cscContantAndCompanyInportDto.setCustCode(customerCode);
                    cscContantAndCompanyInportDto.setContactCompanyName(ofcExcelBoradwise.getConsigneeName());
                    cscContantAndCompanyInportDto.setContactName(ofcExcelBoradwise.getConsigneeContactName());
                    cscContantAndCompanyInportDto.setPhone(ofcExcelBoradwise.getConsigneeContactPhone());
                    cscContantAndCompanyInportDto.setAddress(ofcExcelBoradwise.getConsigneeAddress());
                    cscContantAndCompanyInportDto.setUserId(authResDto.getUserId());
                    cscContantAndCompanyInportDto.setUserName(authResDto.getUserName());
                    cscContantAndCompanyInportDtoList.add(cscContantAndCompanyInportDto);
                    //即使当前收货方没有维护, 也要继续校验货品档案
                    if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                        resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                        CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                        cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsApiDto.setCustomerCode(customerCode);
                        Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                        if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                            checkPass = false;
                            xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                            CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                            cscGoodsImportDto.setCustomerCode(customerCode);
                            cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                            cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                            cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                            cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                            cscGoodsImportDtoList.add(cscGoodsImportDto);
                        }

                    }
                }
                //如果该客户订单编号不是第一次出现
            }else{
                //只取货品编码,货品名称,规格,单位,数量,单价,往设定好的数据结构里放
                if(!orderByCustOrderCode.get(custOrderCode)){//
                    if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                        resultMap.put(ofcExcelBoradwise.getGoodsCode(),null);
                        CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                        cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsApiDto.setCustomerCode(customerCode);
                        Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                        if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                            checkPass = false;
                            xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                            CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                            cscGoodsImportDto.setCustomerCode(customerCode);
                            cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                            cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                            cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                            cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                            cscGoodsImportDtoList.add(cscGoodsImportDto);
                        }

                    }

                    continue;
                }
                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = getEEByCustOrderCode.get(ofcExcelBoradwise.getCustOrderCode());

                String jsonObjectKey = cscContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + cscContantAndCompanyResponseDto.getContactSerialNo();
                //其中货品编码要判断一下是否在Map中存在,
                if(!resultMap.containsKey(ofcExcelBoradwise.getGoodsCode())){
                    //如果不存在则去接口判断该货品是否在客户中心中进行维护了
                    CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                    cscGoodsApiDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                    cscGoodsApiDto.setCustomerCode(customerCode);
                    Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                    if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                        checkPass = false;
                        xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                        CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                        cscGoodsImportDto.setCustomerCode(customerCode);
                        cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                        cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                        cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                        cscGoodsImportDtoList.add(cscGoodsImportDto);
                        continue;
                    }
                    List<CscGoodsApiVo> cscGoodsApiVoResult = queryCscGoodsList.getResult();
                    if(null != cscGoodsApiVoResult && cscGoodsApiVoResult.size() > 0){
                        //如果维护了就只取收货方和数量
                        JSONArray jsonArray = new JSONArray();
                        CscGoodsApiVo cscGoodsApiVo = cscGoodsApiVoResult.get(0);
//                            mapKey = cscGoodsApiVo.getGoodsCode() + "@" + rowNum;
                        mapKey = cscGoodsApiVo.getGoodsCode();
                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put(jsonObjectKey,ofcExcelBoradwise.getGoodsAmount());
                        jsonArray.add(cscGoodsApiVo);
                        jsonArray.add(jsonObject);
                        jsonArray.add(cscContantAndCompanyResponseDto);

                        resultMap.put(mapKey,jsonArray);
                        //校验两个不同的客户订单编号对应了同一个收货方//判断JSONArray的第二个格子是否有该收货方了,如果有就提示出错!
                    }else{
                        //如果没维护,就输出错误信息,并向cscGoodsImportDtoList中添加,checkPass=false
                        //如果校验失败,就标记该单元格
                        checkPass = false;
                        xlsErrorMsg.add("货品编码为【" + ofcExcelBoradwise.getGoodsCode() + "】在货品档案中不存在!请维护!");
                        CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                        cscGoodsImportDto.setCustomerCode(customerCode);
                        cscGoodsImportDto.setGoodsCode(ofcExcelBoradwise.getGoodsCode());
                        cscGoodsImportDto.setGoodsName(ofcExcelBoradwise.getGoodsName());
                        cscGoodsImportDto.setSpecification(ofcExcelBoradwise.getGoodsSpec());
                        cscGoodsImportDto.setUnit(ofcExcelBoradwise.getGoodsUnit());
                        cscGoodsImportDtoList.add(cscGoodsImportDto);
                    }

                }else{
                    //如果在Map中已经存在,则报错!
                    JSONArray jsonArrayExistGoods = resultMap.get(ofcExcelBoradwise.getGoodsCode());
                    JSONObject jsonObjectExistGoods = (JSONObject) jsonArrayExistGoods.get(1);
                    if(null != jsonObjectExistGoods){
                        checkPass = false;
                        xlsErrorMsg.add("收货方名称【" + ofcExcelBoradwise.getConsigneeName() + "】匹配到两个客户订单编号!请检查!");
                    }else{
                        jsonObjectExistGoods.put(jsonObjectKey,ofcExcelBoradwise.getGoodsAmount());
                    }
                }
            }
        }

        if(checkPass){
            int goodsRowNum = 1;
            //将MapKey更改为适合前端模板的格式
            Map<String,JSONArray> afterResultMap = new LinkedHashMap<>();
            Boolean consigneeTag = false;
            int consigneeNum = 1;
            for(String mapKey : resultMap.keySet()){
                JSONArray jsonArray = resultMap.get(mapKey);

                for(String custOrderCode : getEEByCustOrderCode.keySet()){
                    //第一个3
                    if(1 == consigneeNum){
                        //所有的货品, 每个收货人都应该有, 如果没有就置为0, 而且要将3个一循环的数据堆齐
                        CscGoodsApiVo cscGoodsApiVo = (CscGoodsApiVo) jsonArray.get(0);
                        Double goodsAmout = cscGoodsApiVo.getGoodsAmount();
                        JSONObject jsonObject = (JSONObject) jsonArray.get(1);
                        for(String custOrderCodeIn : getEEByCustOrderCode.keySet()){
                            CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = getEEByCustOrderCode.get(custOrderCodeIn);
                            String consigneeAndGoodsKey = cscContantAndCompanyResponseDto.getContactCompanySerialNo() + "@" + cscContantAndCompanyResponseDto.getContactSerialNo();
                            if(null == jsonObject.get(consigneeAndGoodsKey)){
                                jsonObject.put(consigneeAndGoodsKey,new BigDecimal(0));
                            }else{
                                BigDecimal bigDecimal = (BigDecimal) jsonObject.get(consigneeAndGoodsKey);
                                goodsAmout = bigDecimal.doubleValue() + goodsAmout;
                            }
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
                afterResultMap.put(mapKey + "@" + goodsRowNum++,resultMap.get(mapKey));
            }


            return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"校验成功!",afterResultMap );
        }else{
            OfcCheckExcelErrorVo ofcCheckExcelErrorVo = new OfcCheckExcelErrorVo();
            ofcCheckExcelErrorVo.setXlsErrorMsg(xlsErrorMsg);
            ofcCheckExcelErrorVo.setCscContantAndCompanyInportDtoList(cscContantAndCompanyInportDtoList);
            ofcCheckExcelErrorVo.setCscGoodsImportDtoList(cscGoodsImportDtoList);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验失败!我们已为您显示校验结果,请改正后重新上传!",ofcCheckExcelErrorVo);
        }
    }

    /**
     * 列名和实体的映射
     */
    private String cellReflectToDomain(String cellName){
        if(cellName.equals("客户订单号")){
            return "custOrderCode";
        }else if(cellName.equals("订单日期")){
            return "orderTime";
        }else if(cellName.equals("收货方名称")){
            return "consigneeName";
        }else if(cellName.equals("联系人")){
            return "consigneeContactName";
        }else if(cellName.equals("联系电话")){
            return "consigneeContactPhone";
        }else if(cellName.equals("地址")){
            return "consigneeAddress";
        }else if(cellName.equals("货品编码")){
            return "goodsCode";
        }else if(cellName.equals("货品名称")){
            return "goodsName";
        }else if(cellName.equals("规格")){
            return "goodsSpec";
        }else if(cellName.equals("单位")){
            return "goodsUnit";
        }else if(cellName.equals("数量")){
            return "goodsAmount";
        }else if(cellName.equals("单价")){
            return "goodsUnitPirce";
        }else{
            throw new BusinessException("字段错误");
        }

    }



    /**
     * 校验XLS格式
     * 交叉
     * @param uploadFile
     * @param sheetNumChosen
     * @param customerCode
     * @param staticCell
     * @return
     */
    @Override
    public Wrapper<?> checkXlsAcross(MultipartFile uploadFile,String sheetNumChosen, String customerCode, Integer staticCell,AuthResDto authResDto){
        boolean checkPass = true;
        Map<String,JSONArray> resultMap = null;
        List<CscContantAndCompanyResponseDto> consigneeNameList = null;
        List<String> consigneeNameListForCheck = null;
        List<CscGoodsApiVo> goodsApiVoList = null;
        List<String> goodsCodeListForCheck = null;
        List<String> xlsErrorMsg = null;
        HSSFWorkbook hssfWorkbook = null;
        List<CscGoodsImportDto> cscGoodsImportDtoList = new ArrayList<>();
        List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = new ArrayList<>();
        try {
            hssfWorkbook = new HSSFWorkbook(uploadFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("校验Excel读取内部异常");
        }
        int numberOfSheets = hssfWorkbook.getNumberOfSheets();

        //遍历sheet
        for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum ++){
            if(sheetNum != Integer.valueOf(sheetNumChosen)){
                continue;
            }
            HSSFSheet sheet = hssfWorkbook.getSheetAt(sheetNum);
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
            for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++){
                HSSFRow hssfRow = sheet.getRow(rowNum);
                String mapKey = "";
                JSONArray jsonArray = new JSONArray();
                //空行
                HSSFCell cell = hssfRow.getCell(0);
                if(null == hssfRow || HSSFCell.CELL_TYPE_BLANK  == cell.getCellType() ){
                    //标记当前行出错,并跳出当前循环
                    break;
                }
                //遍历cell
                for(int cellNum = 0; cellNum < hssfRow.getLastCellNum() + 1; cellNum ++){
                    HSSFCell hssfCell = hssfRow.getCell(cellNum);
                    //空列
                    if(null == hssfCell){
                        //标记当前列出错, 并跳过当前循环
                        break;
                    }else if(HSSFCell.CELL_TYPE_BLANK == hssfCell.getCellType()){
                        continue;
                    }
                    //校验第一行,包括固定内容和收货人列表
                    String cellValue = null;
                    if(HSSFCell.CELL_TYPE_STRING == hssfCell.getCellType()){
                        cellValue = PubUtils.trimAndNullAsEmpty(hssfCell.getStringCellValue());
                    }else if(HSSFCell.CELL_TYPE_NUMERIC == hssfCell.getCellType()){
                        cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(hssfCell.getNumericCellValue()));
                    }
                    if(rowNum == 0){
                        //第一行全为字符串
//                            String cellValue = PubUtils.trimAndNullAsEmpty(hssfCell.getStringCellValue());

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
                            CscContactCompany cscContactCompany = new CscContactCompany();
                            CscContact cscContact = new CscContact();
                            cscContantAndCompanyDto.setCustomerCode(customerCode);
                            cscContactCompany.setContactCompanyName(cellValue);
                            cscContact.setPurpose("1");//用途为收货方
                            cscContantAndCompanyDto.setCscContact(cscContact);
                            cscContantAndCompanyDto.setCscContactCompany(cscContactCompany);
                            Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = feignCscContactAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
                            if(Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()){
                                throw new BusinessException(queryCscCustomerResult.getMessage());
                            }
                            List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
                            if(null != result && result.size() > 0){
                                //如果能在客户中心查到,就将该收货人名称记录下来,往consigneeNameList里放
                                consigneeNameList.add(result.get(0));
                                consigneeNameListForCheck.add(result.get(0).getContactCompanyName());
                            }else{
                                //收货人列表不在联系人档案中,则需要对当前格子进行报错处理
                                checkPass = false;
                                consigneeNameList.add(new CscContantAndCompanyResponseDto());
                                consigneeNameListForCheck.add("");

                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该收货方名称在联系人档案中不存在!");
                                CscContantAndCompanyInportDto cscContantAndCompanyInportDto = new CscContantAndCompanyInportDto();
                                cscContantAndCompanyInportDto.setCustCode(customerCode);
                                cscContantAndCompanyInportDto.setContactCompanyName(cellValue);
                                cscContantAndCompanyInportDto.setUserId(authResDto.getUserId());
                                cscContantAndCompanyInportDto.setUserName(authResDto.getUserName());
                                cscContantAndCompanyInportDtoList.add(cscContantAndCompanyInportDto);
                            }
                        }
                        //校验从第二行开始的体
                    }else if(rowNum > 0){
                        //校验第一列即货品编码是否已经在货品档案中进行了维护

                        JSONObject jsonObject = new JSONObject();
                        if(cellNum == 0){
                            String goodsCode = cellValue;
                            if(PubUtils.isSEmptyOrNull(goodsCode)){
                                continue;
                            }
                            CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                            cscGoodsApiDto.setGoodsCode(goodsCode);
                            cscGoodsApiDto.setCustomerCode(customerCode);
                            Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                            if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                                checkPass = false;
                                goodsApiVoList.add(new CscGoodsApiVo());
                                goodsCodeListForCheck.add("");
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品在货品档案中不存在!");
                                CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                                cscGoodsImportDto.setCustomerCode(customerCode);
                                cscGoodsImportDto.setGoodsCode(goodsCode);
                                //只有货品编码

                                cscGoodsImportDtoList.add(cscGoodsImportDto);
                                break;
                            }
                            List<CscGoodsApiVo> result = queryCscGoodsList.getResult();
                            if(null != result && result.size() > 0){
                                //如果校验成功,就往结果集里堆
                                CscGoodsApiVo cscGoodsApiVo = result.get(0);
                                mapKey =cscGoodsApiVo.getGoodsCode() + "@" + rowNum;
                                goodsApiVoList.add(cscGoodsApiVo); //
                                goodsCodeListForCheck.add(cscGoodsApiVo.getGoodsName());
                            }else{
                                //如果校验失败,就标记该单元格
                                checkPass = false;
                                goodsApiVoList.add(new CscGoodsApiVo());
                                goodsCodeListForCheck.add("");
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品名称在货品档案中不存在!");

                            }
                            //货品名称, 规格, 单位, 单价的数据暂时不需校验
                        }else if(cellNum > 0 && cellNum <= (staticCell -1)){

                            //收货人的货品需求数量
                        }else if(cellNum > (staticCell -1)){
                            try{
                                //校验是否数字
                                Double goodsAmount = 0.0;
                                Double goodsAndConsigneeNum = hssfCell.getNumericCellValue();
                                //使用正则对数字进行校验
                                boolean matches = goodsAndConsigneeNum.toString().matches("\\d{1,6}\\.\\d{1,3}");
                                //如果校验成功,就往结果集里堆
                                if(matches){
                                    CscContantAndCompanyResponseDto cscContantAndCompanyVo = consigneeNameList.get(cellNum - staticCell);
                                    CscGoodsApiVo cscGoodsApiVo = goodsApiVoList.get(rowNum - 1);
                                    goodsAmount = cscGoodsApiVo.getGoodsAmount() + goodsAndConsigneeNum;
                                    cscGoodsApiVo.setGoodsAmount(goodsAmount);
                                    goodsApiVoList.remove(rowNum - 1);
                                    goodsApiVoList.add(rowNum-1,cscGoodsApiVo);
                                    String consigneeCode = cscContantAndCompanyVo.getContactCompanySerialNo();
                                    String consigneeContactCode = cscContantAndCompanyVo.getContactSerialNo();
                                    if(PubUtils.isSEmptyOrNull(consigneeCode) || PubUtils.isSEmptyOrNull(consigneeContactCode)){
                                        throw new BusinessException("收货方编码或收货方联系人编码为空!");
                                        //xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!收货方编码或收货方联系人编码为空!");
                                        //throw new BusinessException("收货方编码或收货方联系人编码为空!");
                                    }
                                    String consigneeMsg = consigneeCode + "@" + consigneeContactCode;
                                    jsonObject.put(consigneeMsg,goodsAndConsigneeNum);
                                    jsonArray.add(cscGoodsApiVo);
                                    jsonArray.add(jsonObject);
                                    jsonArray.add(cscContantAndCompanyVo);

                                    //如果数字格式不对,就标记该单元格
                                }else{
                                    checkPass = false;
                                    xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品数量格式不正确!");
                                }
                                //这里只抓不是数字的情况
                            }catch (IllegalStateException ex){
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品数量不是数字格式!");
                            }catch (Exception ex){//这里的Exception再放小点, 等报错的时候看看报的是什么异常
                                checkPass = false;
                                throw new BusinessException(ex.getMessage(), ex);
                            }
                        }
                    }
                }
                if(rowNum > 0){//避免第一行
                    resultMap.put(mapKey,jsonArray);//一条结果
                }
            }
        }
        Wrapper<List<String>> consigneeRepeatCheckResult = checkRepeat(consigneeNameListForCheck,Integer.valueOf(sheetNumChosen),"consignee");
        if(consigneeRepeatCheckResult.getCode() == Wrapper.ERROR_CODE){
            checkPass = false;
            xlsErrorMsg.addAll(consigneeRepeatCheckResult.getResult());
        }
        Wrapper<List<String>> goodsRepeatCheckResult = checkRepeat(goodsCodeListForCheck,Integer.valueOf(sheetNumChosen),"goods");
        if(goodsRepeatCheckResult.getCode() == Wrapper.ERROR_CODE){
            checkPass = false;
            xlsErrorMsg.addAll(goodsRepeatCheckResult.getResult());
        }
        if(checkPass){
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"校验成功!",resultMap);
        }else{
            OfcCheckExcelErrorVo ofcCheckExcelErrorVo = new OfcCheckExcelErrorVo();
            ofcCheckExcelErrorVo.setXlsErrorMsg(xlsErrorMsg);
            ofcCheckExcelErrorVo.setCscGoodsImportDtoList(cscGoodsImportDtoList);
            ofcCheckExcelErrorVo.setCscContantAndCompanyInportDtoList(cscContantAndCompanyInportDtoList);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验失败!我们已为您显示校验结果,请改正后重新上传!",ofcCheckExcelErrorVo);
        }

    }


    /**
     * 校验XLSX格式
     * 交叉
     * @param uploadFile
     * @param sheetNumChosen
     * @param custId
     * @param staticCell
     * @return
     */
    public Wrapper<?> checkXlsxAcross(MultipartFile uploadFile,String sheetNumChosen, String custId, Integer staticCell,AuthResDto authResDto){
        boolean checkPass = true;
        Map<String,JSONArray> resultMap = null;
        List<CscContantAndCompanyResponseDto> consigneeNameList = null;
        List<String> consigneeNameListForCheck = null;
        List<CscGoodsApiVo> goodsApiVoList = null;
        List<String> goodsCodeListForCheck = null;
        List<String> xlsErrorMsg = null;
        XSSFWorkbook xssfWorkbook = null;
        List<CscGoodsImportDto> cscGoodsImportDtoList = new ArrayList<>();
        List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = new ArrayList<>();
        try {
            xssfWorkbook = new XSSFWorkbook(uploadFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("校验Excel读取内部异常",e);
        }
        int numberOfSheets = xssfWorkbook.getNumberOfSheets();

        //遍历sheet
        for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum ++){
            if(sheetNum != Integer.valueOf(sheetNumChosen)){
                continue;
            }
            XSSFSheet sheet = xssfWorkbook.getSheetAt(sheetNum);
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
            for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++){
                XSSFRow xssfRow = sheet.getRow(rowNum);
                String mapKey = "";
                JSONArray jsonArray = new JSONArray();
                //空行
                XSSFCell cell = xssfRow.getCell(0);
                if(null == xssfRow || HSSFCell.CELL_TYPE_BLANK  == cell.getCellType() ){
                    //标记当前行出错,并跳出当前循环
                    break;
                }
                //遍历cell
                for(int cellNum = 0; cellNum < xssfRow.getLastCellNum() + 1; cellNum ++){
                    XSSFCell xssfCell = xssfRow.getCell(cellNum);
                    //空列
                    if(null == xssfCell){
                        //标记当前列出错, 并跳过当前循环
                        break;
                    }else if(HSSFCell.CELL_TYPE_BLANK == xssfCell.getCellType()){
                        continue;
                    }
                    //校验第一行,包括固定内容和收货人列表
                    String cellValue = null;
                    if(HSSFCell.CELL_TYPE_STRING == xssfCell.getCellType()){
                        cellValue = PubUtils.trimAndNullAsEmpty(xssfCell.getStringCellValue());
                    }else if(HSSFCell.CELL_TYPE_NUMERIC == xssfCell.getCellType()){
                        cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(xssfCell.getNumericCellValue()));
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
                            CscContactCompany cscContactCompany = new CscContactCompany();
                            CscContact cscContact = new CscContact();
                            cscContantAndCompanyDto.setCustomerCode(custId);
                            cscContactCompany.setContactCompanyName(cellValue);
                            cscContact.setPurpose("1");//用途为收货方
                            cscContantAndCompanyDto.setCscContact(cscContact);
                            cscContantAndCompanyDto.setCscContactCompany(cscContactCompany);
                            Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = feignCscContactAPIClient.queryCscReceivingInfoList(cscContantAndCompanyDto);
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
                                CscContantAndCompanyInportDto cscContantAndCompanyInportDto = new CscContantAndCompanyInportDto();
                                cscContantAndCompanyInportDto.setCustCode(custId);
                                cscContantAndCompanyInportDto.setContactCompanyName(cellValue);
                                cscContantAndCompanyInportDto.setUserId(authResDto.getUserId());
                                cscContantAndCompanyInportDto.setUserName(authResDto.getUserName());
                                cscContantAndCompanyInportDtoList.add(cscContantAndCompanyInportDto);
                            }
                        }
                        //校验从第二行开始的体
                    }else if(rowNum > 0){
                        //校验第一列即货品编码是否已经在货品档案中进行了维护

                        JSONObject jsonObject = new JSONObject();
                        if(cellNum == 0){
                            String goodsCode = cellValue;
                            if(PubUtils.isSEmptyOrNull(goodsCode)){
                                continue;
                            }
                            CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                            cscGoodsApiDto.setGoodsCode(goodsCode);
                            cscGoodsApiDto.setCustomerCode(custId);
                            Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = feignCscGoodsAPIClient.queryCscGoodsList(cscGoodsApiDto);
                            if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                                checkPass = false;
                                goodsApiVoList.add(new CscGoodsApiVo());
                                goodsCodeListForCheck.add("");
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品在货品档案中不存在!");
                                CscGoodsImportDto cscGoodsImportDto = new CscGoodsImportDto();
                                cscGoodsImportDto.setCustomerCode(custId);
                                cscGoodsImportDto.setGoodsCode(goodsCode);
                                //只有货品编码

                                cscGoodsImportDtoList.add(cscGoodsImportDto);
                                break;
                            }
                            List<CscGoodsApiVo> result = queryCscGoodsList.getResult();
                            if(null != result && result.size() > 0){
                                //如果校验成功,就往结果集里堆
                                CscGoodsApiVo cscGoodsApiVo = result.get(0);
                                mapKey =cscGoodsApiVo.getGoodsCode() + "@" + rowNum;
                                goodsApiVoList.add(cscGoodsApiVo); //
                                goodsCodeListForCheck.add(cscGoodsApiVo.getGoodsName());
                            }else{
                                //如果校验失败,就标记该单元格
                                checkPass = false;
                                goodsApiVoList.add(new CscGoodsApiVo());
                                goodsCodeListForCheck.add("");
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品名称在货品档案中不存在!");

                            }
                            //货品名称, 规格, 单位, 单价的数据暂时不需校验
                        }else if(cellNum > 0 && cellNum <= (staticCell -1)){

                            //收货人的货品需求数量
                        }else if(cellNum > (staticCell -1)){
                            try{
                                //校验是否数字
                                Double goodsAmount = 0.0;
                                Double goodsAndConsigneeNum = xssfCell.getNumericCellValue();
                                //使用正则对数字进行校验
                                boolean matches = goodsAndConsigneeNum.toString().matches("\\d{1,6}\\.\\d{1,3}");
                                //如果校验成功,就往结果集里堆
                                if(matches){
                                    CscContantAndCompanyResponseDto cscContantAndCompanyVo = consigneeNameList.get(cellNum - staticCell);
                                    CscGoodsApiVo cscGoodsApiVo = goodsApiVoList.get(rowNum - 1);
                                    goodsAmount = cscGoodsApiVo.getGoodsAmount() + goodsAndConsigneeNum;
                                    cscGoodsApiVo.setGoodsAmount(goodsAmount);
                                    goodsApiVoList.remove(rowNum - 1);
                                    goodsApiVoList.add(rowNum-1,cscGoodsApiVo);
                                    String consigneeCode = cscContantAndCompanyVo.getContactCompanySerialNo();
                                    String consigneeContactCode = cscContantAndCompanyVo.getContactSerialNo();
                                    if(PubUtils.isSEmptyOrNull(consigneeCode) || PubUtils.isSEmptyOrNull(consigneeContactCode)){
                                        throw new BusinessException("收货方编码或收货方联系人编码为空!");
//                                            checkPass = false;
//                                            xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!收货方编码或收货方联系人编码为空!");
//                                            continue;
                                    }
                                    String consigneeMsg = consigneeCode + "@" + consigneeContactCode;
                                    jsonObject.put(consigneeMsg,goodsAndConsigneeNum);
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
                                checkPass = false;
                                throw new BusinessException(ex.getMessage(), ex);

                            }
                        }
                    }
                }
                if(rowNum > 0){             //避免第一行
                    resultMap.put(mapKey,jsonArray);//一条结果
                }
            }

        }
        Wrapper<List<String>> consigneeRepeatCheckResult = checkRepeat(consigneeNameListForCheck,Integer.valueOf(sheetNumChosen),"consignee");
        if(consigneeRepeatCheckResult.getCode() == Wrapper.ERROR_CODE){
            checkPass = false;
            xlsErrorMsg.addAll(consigneeRepeatCheckResult.getResult());
        }
        Wrapper<List<String>> goodsRepeatCheckResult = checkRepeat(goodsCodeListForCheck,Integer.valueOf(sheetNumChosen),"goods");
        if(goodsRepeatCheckResult.getCode() == Wrapper.ERROR_CODE){
            checkPass = false;
            xlsErrorMsg.addAll(goodsRepeatCheckResult.getResult());
        }
        if(checkPass){
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"校验成功!",resultMap);
        }else{
            OfcCheckExcelErrorVo ofcCheckExcelErrorVo = new OfcCheckExcelErrorVo();
            ofcCheckExcelErrorVo.setXlsErrorMsg(xlsErrorMsg);
            ofcCheckExcelErrorVo.setCscGoodsImportDtoList(cscGoodsImportDtoList);
            ofcCheckExcelErrorVo.setCscContantAndCompanyInportDtoList(cscContantAndCompanyInportDtoList);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"校验失败!我们已为您显示校验结果,请改正后重新上传!",ofcCheckExcelErrorVo);
        }
    }

    /**
     * 校验收货方或货品出现重复
     * @param consigneeOrGoodsList
     * @param sheetNum
     * @param tag
     * @return
     */
    private Wrapper<List<String>> checkRepeat(List<String> consigneeOrGoodsList, int sheetNum, String tag){
        List<String> errorConsigneeOrGoodsList = new ArrayList<>();
        Map<String,String> consigneeOrGoodsRepeatMap = new HashMap<>();
        Integer consigneeOrGoodsNum = null;
        if("goods".equals(tag)){
            consigneeOrGoodsNum = 2;
        }else if("consignee".equals(tag)){
            consigneeOrGoodsNum = 5;
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
                if(repeatStr.split("\\,").length > 1){
                    errorConsigneeOrGoodsList.add("sheet页第" + sheetNum + "页,第1列,第" + repeatStr + "行的值不符合规范!原因:货品编码重复!");
                }
            }
        }else if("consignee".equals(tag)){
            for(String consigneeName : consigneeOrGoodsRepeatMap.keySet()){
                String repeatStr = consigneeOrGoodsRepeatMap.get(consigneeName);
                if(consigneeOrGoodsRepeatMap.get(consigneeName).split("\\,").length > 1){
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
}
