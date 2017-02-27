package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.enums.StorageImportInEnum;
import com.xescm.ofc.enums.StorageImportOutEnum;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcStorageTemplateMapper;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import com.xescm.ofc.model.dto.ofc.OfcStorageTemplateDto;
import com.xescm.ofc.service.OfcStorageTemplateService;
import com.xescm.ofc.utils.CodeGenUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.NoSuchFileException;
import java.util.*;

/**
 *
 * Created by lyh on 2017/2/18.
 */
@Service
public class OfcStorageTemplateServiceImpl extends BaseService<OfcStorageTemplate> implements OfcStorageTemplateService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcStorageTemplateMapper ofcStorageTemplateMapper;
    @Resource
    private CodeGenUtils codeGenUtils;




    /**
     * 模板配置保存
     * @param templateList
     * @param authResDto
     */
    @Override
    @Transactional
    public void saveTemplate(List<OfcStorageTemplate> templateList, AuthResDto authResDto) {
        if(null == templateList || templateList.size() < 1){
            logger.error("模板配置保存失败,入参为空");
            throw new BusinessException("模板配置保存失败,入参为空");
        }
        OfcStorageTemplate ofcStorageTemplateForCheck = templateList.get(0);
        //校验模板必填项
        checkTemplateRequiedItem(ofcStorageTemplateForCheck);
        //校验模板名称是否重复
        Integer repeat = ofcStorageTemplateMapper.checkTemplateNameUnique(ofcStorageTemplateForCheck.getTemplateName());
        if(null != repeat && repeat > 0) {
            logger.error("校验模板名称重复!");
            throw new BusinessException("校验模板名称重复!");
        }
        String templateCode = codeGenUtils.getNewWaterCode("TC", 6);
        String userId = authResDto.getUserId();
        String userName = authResDto.getUserName();
        Date now = new Date();
        for (OfcStorageTemplate ofcStorageTemplate : templateList) {
            if(null == ofcStorageTemplate){
                logger.error("模板配置保存失败");
                throw new BusinessException("模板配置保存失败");
            }
            ofcStorageTemplate.setTemplateCode(templateCode);
            ofcStorageTemplate.setCreator(userId);
            ofcStorageTemplate.setCreatorName(userName);
            ofcStorageTemplate.setCreatTime(now);
            ofcStorageTemplate.setOperator(userId);
            ofcStorageTemplate.setOperatorName(userName);
            ofcStorageTemplate.setOperTime(now);
            ofcStorageTemplateMapper.insert(ofcStorageTemplate);
        }
    }

    /**
     * 模板配置筛选
     * @param templateCondition
     * @return
     */
    @Override
    public List<OfcStorageTemplate> selectTemplateByCondition(TemplateCondition templateCondition) {
        if(null == templateCondition){
            logger.error("模板配置筛选条件为空!");
            throw new BusinessException("模板配置筛选条件为空!");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList = ofcStorageTemplateMapper.selectTemplateByCondition(templateCondition);
        OfcStorageTemplate ofcStorageTemplate = new OfcStorageTemplate();
        ofcStorageTemplate.setTemplateCode("standard");
        ofcStorageTemplate.setTemplateName("标准");
        ofcStorageTemplateList.add(0, ofcStorageTemplate);
        return ofcStorageTemplateList;
    }

    /**
     * 模板配置删除
     * @param temlpateCode
     *
     */
    @Transactional
    @Override
    public void delTemplateByCode(String temlpateCode) {
        logger.info("模板配置删除service , ==> temlpateCode:{}",temlpateCode);
        if(PubUtils.isSEmptyOrNull(temlpateCode)){
            logger.error("模板配置删除失败! 入参有误!");
            throw new BusinessException("模板配置删除失败! 入参有误!");
        }
        OfcStorageTemplate ofcStorageTemplate = new OfcStorageTemplate();
        ofcStorageTemplate.setTemplateCode(temlpateCode);
        int delete = ofcStorageTemplateMapper.delete(ofcStorageTemplate);
        if(delete == 0){
            logger.error("模板配置删除失败!");
            throw new BusinessException("模板配置删除失败");
        }
    }

    /**
     * 模板配置编辑
     * @param templateList
     * @param authResDto
     */
    @Transactional
    @Override
    public void templateEditConfirm(String templateList, AuthResDto authResDto) throws Exception {
        TypeReference<List<OfcStorageTemplate>> typeReference = new TypeReference<List<OfcStorageTemplate>>() {
        };
        List<OfcStorageTemplate> ofcStorageTemplates = JacksonUtil.parseJson(templateList, typeReference);
        String userId = authResDto.getUserId();
        String userName = authResDto.getUserName();
        Date now = new Date();
        for (OfcStorageTemplate ofcStorageTemplate : ofcStorageTemplates) {
            ofcStorageTemplate.setOperator(userId);
            ofcStorageTemplate.setOperatorName(userName);
            ofcStorageTemplate.setOperTime(now);
            ofcStorageTemplateMapper.updateByTemplateCode(ofcStorageTemplate);
        }
    }

    /**
     * 模板明细查询
     * @param templateCondition
     * @return
     */
    @Override
    public List<OfcStorageTemplate> selectTemplateDetail(TemplateCondition templateCondition) {
        if(null == templateCondition){
            logger.error("模板明细查询入参有误");
            throw new BusinessException("模板明细查询入参有误!");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList = ofcStorageTemplateMapper.selectTemplateDetail(templateCondition);
        if(ofcStorageTemplateList.size() < 1){
            logger.error("模板明细查询结果有误");
            throw new BusinessException("模板明细查询结果有误!");
        }
        return ofcStorageTemplateList;
    }



    /**
     * 校验模板必填项
     * @param ofcStorageTemplate
     */
    private void checkTemplateRequiedItem(OfcStorageTemplate ofcStorageTemplate) {
        if(PubUtils.isSEmptyOrNull(ofcStorageTemplate.getTemplateType())){
            logger.error("校验模板类型为空!");
            throw new BusinessException("校验模板类型为空!");
        }else if(PubUtils.isSEmptyOrNull(ofcStorageTemplate.getTemplateName())){
            logger.error("校验模板名称为空!");
            throw new BusinessException("校验模板名称为空!");
        }else if(PubUtils.isSEmptyOrNull(ofcStorageTemplate.getCustCode())){
            logger.error("校验客户编码为空!");
            throw new BusinessException("校验客户编码为空!");
        }else if(PubUtils.isSEmptyOrNull(ofcStorageTemplate.getCustName())){
            logger.error("校验客户名称为空!");
            throw new BusinessException("校验客户名称为空!");
        }
    }

    /**
     * 校验模板
     * @param uploadFile
     * @param authResDto
     * @param ofcStorageTemplate: templateType custCode templateCode
     * @param activeSheetNum
     * @return
     */
    @Override
    public Wrapper<?> checkStorageTemplate(MultipartFile uploadFile, AuthResDto authResDto,OfcStorageTemplate ofcStorageTemplate, Integer activeSheetNum) {
        //拿到所用模板的明细Map
        Map<String,OfcStorageTemplate> templateDetilMap = getTemplateReflect(ofcStorageTemplate.getTemplateCode(), ofcStorageTemplate.getTemplateType());
        //将模板映射成标准格式, 如果不是标准格式的就跳过不校验, 且不展示
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
        List<OfcStorageTemplateDto> ofcStorageTemplateDtoList = new ArrayList<>();
        boolean requiredField = true;
        try {
            clazz = Class.forName("com.xescm.ofc.model.dto.ofc.OfcStorageTemplateDto");
        } catch (ClassNotFoundException e) {
            logger.error("校验Excel,ClassNotFoundException:{}",e);
        }
        int numberOfSheets = workbook.getNumberOfSheets();

        //遍历sheet
        for (int sheetNum = 0; sheetNum < numberOfSheets; sheetNum ++) {
            if (sheetNum != activeSheetNum) {
                continue;
            }
            Sheet sheet = workbook.getSheetAt(sheetNum);
            //遍历row
            if (sheet.getLastRowNum() == 0) {
                throw new BusinessException("请先上传Excel导入数据，再加载后执行导入！");
            }
            //遍历行
            for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++) {
                Row commonRow = sheet.getRow(rowNum);
                OfcStorageTemplateDto ofcStorageTemplateDto;
                try {
                    ofcStorageTemplateDto = (OfcStorageTemplateDto) clazz.newInstance();
                } catch (Exception e) {
                    logger.error("校验明细类型Excel:{}", e);
                    throw new BusinessException("校验Excel错误");
                }
                //校验第一行,校验表格是否有所有的必填列列名, 并返回所有必填项在用户上传的Excel模板中的列号
                Map<Integer, String> requiedItemIndex = new HashMap<>();
                if(rowNum == 0){
                    requiedItemIndex = checkExcelRequiedItem(commonRow, ofcStorageTemplate.getTemplateType(), templateDetilMap);
                }
                if (null == commonRow) {
                    //标记当前行出错,并跳出当前循环
                    break;
                }
                //空行
                Cell cell = commonRow.getCell(0);
                if (null == cell || Cell.CELL_TYPE_BLANK == cell.getCellType()) {
                    continue;
                }

                //遍历列
                for(int cellNum = 0; cellNum < commonRow.getLastCellNum() + 1; cellNum ++) {
                    Cell commonCell = commonRow.getCell(cellNum);
                    //空列
//                    if (null == commonCell && cellNum > 11) {
                    //如果必填列为空则报错提示
                    if(null == commonCell && requiedItemIndex.containsKey(cellNum)){
                        String requiredColName = requiedItemIndex.get(cellNum);
                        logger.error("用户上传的Excel缺少必填项: {}, 位于第{}行, 第{}列", requiedItemIndex.get(cellNum), requiredColName, (rowNum + 1), (cellNum + 1));
                        xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!" + requiredColName + "必填!");
                        checkPass = false;
                        break;
                    }
                    //如果非必填列为空则跳过
                    if (null == commonCell) {
                        //标记当前列出错, 并跳过当前循环
                        break;
                    }
                    //校验第一行,包括固定内容和收货人列表
                    String cellValue = null;
                    if (Cell.CELL_TYPE_STRING == commonCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(commonCell.getStringCellValue());
                    } else if (Cell.CELL_TYPE_NUMERIC == commonCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(commonCell.getNumericCellValue()));
                    }
                    //至此, 已经能拿到每一列的值
                    if(rowNum == 0){//第一行, 将所有表格中固定的字段名称和位置固定
                        if(PubUtils.isSEmptyOrNull(cellValue)){
                            break;
                        }
                        String refCellValue = cellReflectToDomain(cellValue,templateDetilMap).getStandardColCode(); //标准表字段映射成对应实体的字段的值
                        if(PubUtils.isSEmptyOrNull(refCellValue)){
                            break;
                        }
                        modelNameStr.put(cellNum,refCellValue);
                    }else if(rowNum > 0) { // 表格的数据体
                        String cellNumCode = modelNameStr.get(cellNum);
                        //客户订单号
                        if(StringUtils.equals(StorageImportInEnum.CUST_ORDER_CODE.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //订单日期
                        }else if(StringUtils.equals(StorageImportInEnum.ORDER_TIME.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //开单员
                        }else if(StringUtils.equals(StorageImportInEnum.MERCHANDISER.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //仓库名称
                        }else if(StringUtils.equals(StorageImportInEnum.WAREHOUSE_NAME.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //业务类型
                        }else if(StringUtils.equals(StorageImportInEnum.BUSINESS_TYPE.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //备注
                        }else if(StringUtils.equals(StorageImportInEnum.NOTES.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //货品编码
                        }else if(StringUtils.equals(StorageImportInEnum.GOODS_CODE.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //货品名称
                        }else if(StringUtils.equals(StorageImportInEnum.GOODS_NAME.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //规格
                        }else if(StringUtils.equals(StorageImportInEnum.GOODS_SPEC.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //单位
                        }else if(StringUtils.equals(StorageImportInEnum.UNIT.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //单价
                        }else if(StringUtils.equals(StorageImportInEnum.UNIT_PRICE.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //入库数量 or  出库数量
                        }else if(StringUtils.equals(StorageImportInEnum.QUANTITY.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                cellValue = "0";
                            }
                            boolean matchesPot = cellValue.matches("\\d{1,6}\\.\\d{1,3}");
                            boolean matchesInt = cellValue.matches("\\d{1,6}");
                            //如果校验成功,就往结果集里堆
                            if(matchesPot || matchesInt){
                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field = null;
                                try {
                                    field = clazz.getDeclaredField(cellNumCode);
                                    field.setAccessible(true);
                                    field.set(ofcStorageTemplateDto,bigDecimal);
                                } catch (Exception e) {
                                    logger.error("");
                                    throw new BusinessException("");
                                }
                            }else{
                                checkPass = false;
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品数量格式不正确!");
                            }
                            //批次号
                        }else if(StringUtils.equals(StorageImportInEnum.PRODUCTION_BATCH.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //生产日期
                        }else if(StringUtils.equals(StorageImportInEnum.PRODUCTION_TIME.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //失效日期
                        }else if(StringUtils.equals(StorageImportInEnum.INVALID_TIME.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //供应商名称
                        }else if(StringUtils.equals(StorageImportInEnum.SUPPORT_NAME.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //预计入库时间
                        }else if(StringUtils.equals(StorageImportInEnum.ARRIVE_TIME.getStandardColCode(), cellNumCode)
                                ||StringUtils.equals(StorageImportOutEnum.SHIPMENT_TIME.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //是否提供运输服务
                        }else if(StringUtils.equals(StorageImportInEnum.PROVIDE_TRANSPORT.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //车牌号
                        }else if(StringUtils.equals(StorageImportInEnum.PLATE_NUMBER.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //司机姓名
                        }else if(StringUtils.equals(StorageImportInEnum.DRIVER_NAME.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //联系电话
                        }else if(StringUtils.equals(StorageImportInEnum.CONTACT_NUMBER.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                            //发货方名称
                        }else if(StringUtils.equals(StorageImportOutEnum.CONSIGNEE_NAME.getStandardColCode(), cellNumCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, cellNumCode);
                        }
                    }
                }

                if(rowNum > 0){
                    ofcStorageTemplateDtoList.add(ofcStorageTemplateDto);
                }
            }


        }
        //如果校验出错
        if(!checkPass){

            //校验成功!
        }else {

        }
        return null;
    }

    /**
     * 设置实体属性值
     * @param clazz
     * @param ofcStorageTemplateDto
     * @param cellValue
     * @param cellNumCode
     */
    private void setFiledValue(Class clazz, OfcStorageTemplateDto ofcStorageTemplateDto, Object cellValue, String cellNumCode) {
        try {
            Field field = clazz.getDeclaredField(cellNumCode);
            field.setAccessible(true);
            field.set(ofcStorageTemplateDto,cellValue);
        } catch (Exception e) {
            if(e instanceof NoSuchFieldException){
                logger.error("校验模板出错: NoSuchFieldException");
            }else if(e instanceof IllegalAccessException){
                logger.error("校验模板出错: IllegalAccessException");
            }else {
                logger.error("校验模板出错: {}", e);
            }
            throw new BusinessException("校验模板出错");
        }
    }

    /**
     * 校验表格是否有所有的必填列
     * @param commonRow
     * @param templateType
     * @param templateDetilMap
     * @return 必填列号, 及必填的映射列名
     */
    private Map<Integer, String> checkExcelRequiedItem(Row commonRow, String templateType, Map<String, OfcStorageTemplate> templateDetilMap) {
        String[] inRquiredItem = {"custOrderCode","merchandiser","warehouseName","businessType","goodsCode","quantity"};
        String[] outRquiredItem = {"custOrderCode","merchandiser","warehouseName","businessType","goodsCode","quantity","consigneeName"};
        Map<String, Integer> colNumMap = new HashMap<>();
        if(null == commonRow){
            logger.error("当前表格没有表头!请添加后重新上传!");
            throw new BusinessException("当前表格没有表头!请添加后重新上传!");
        }
        Map<String,OfcStorageTemplate> ofcStorageTemplateMap = new HashMap<>();
        for(int cellNum = 0; cellNum < commonRow.getLastCellNum() + 1; cellNum ++) {
            Cell commonCell = commonRow.getCell(cellNum);
            //空列
            if (null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                //标记当前列出错, 并跳过当前循环
                continue;
            }
            OfcStorageTemplate ofcStorageTemplate = cellReflectToDomain(commonCell.getStringCellValue(), templateDetilMap);
            ofcStorageTemplateMap.put(ofcStorageTemplate.getStandardColCode(), ofcStorageTemplate);
            colNumMap.put(ofcStorageTemplate.getStandardColCode(), cellNum);
        }
        String[] item = StringUtils.equals(templateType,"storageIn") ? inRquiredItem : outRquiredItem;
        Map<Integer, String> requiredColNum = new HashMap<>();
        for (String requiredItem : item) {
            if(!ofcStorageTemplateMap.containsKey(requiredItem)){
                OfcStorageTemplate ofcStorageTemplate = ofcStorageTemplateMap.get(requiredItem);
                logger.error("当前表格缺少列:{}",ofcStorageTemplate);
                throw new BusinessException("当前表格缺少列:" + ofcStorageTemplate.getReflectColName());
            }else {
                //将当前Excel模板必填列的列号记录
                requiredColNum.put(colNumMap.get(requiredItem), ofcStorageTemplateMap.get(requiredItem).getReflectColName());
            }
        }
        return requiredColNum;
    }

    /**
     * 校验仓储模板文件格式是否正确,并返回当前激活的Sheet页
     * @param file
     */
    @Override
    public Integer checkStorageTemplate(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        int potIndex = fileName.lastIndexOf(".") + 1;
        if(-1 == potIndex){
            throw new BusinessException("该文件没有扩展名!");
        }
        String suffix = fileName.substring(potIndex, fileName.length());
        if(!StringUtils.equals(suffix,"xls") && !StringUtils.equals(suffix,"xlsx")){
            throw new BusinessException("文件格式不正确!");
        }
        Workbook workbook;
        try {
            workbook = suffix.equals("xls")? new HSSFWorkbook(file.getInputStream()) : new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            logger.error("获取Sheet页内部异常:{}",e);
            throw new BusinessException("获取Sheet页内部异常");
        }
        return workbook.getActiveSheetIndex();
    }

    /**
     * 获取模板映射map
     * @param templateCode
     * @param templateType
     * @return
     */
    private Map<String,OfcStorageTemplate> getTemplateReflect(String templateCode, String templateType) {
        List<OfcStorageTemplate> ofcStorageTemplateListForConvert = new ArrayList<>();
        if(StringUtils.equals("standard",templateCode)){
            if(StringUtils.equals("storageIn",templateType)){
                List<StorageImportInEnum> storageImportInEnums = StorageImportInEnum.queryList();
                for (StorageImportInEnum storageImportInEnum : storageImportInEnums) {
                    insertStandardTemplate(ofcStorageTemplateListForConvert, storageImportInEnum.getStandardColCode(), storageImportInEnum.getStandardColName());
                }
            }else if(StringUtils.equals("storageOut",templateType)){
                List<StorageImportOutEnum> storageImportOutEnums = StorageImportOutEnum.queryList();
                for (StorageImportOutEnum storageImportOutEnum : storageImportOutEnums) {
                    insertStandardTemplate(ofcStorageTemplateListForConvert, storageImportOutEnum.getStandardColCode(), storageImportOutEnum.getStandardColName());
                }
            }
        }else {
            TemplateCondition templateCondition = new TemplateCondition();
            templateCondition.setTemplateCode(templateCode);
            ofcStorageTemplateListForConvert = this.selectTemplateDetail(templateCondition);
        }
        Map<String,OfcStorageTemplate> map = new HashMap<>();
        for (OfcStorageTemplate ofcStorageTemplate : ofcStorageTemplateListForConvert) {
            map.put(ofcStorageTemplate.getReflectColName(),ofcStorageTemplate);
        }
        return map;
    }

    /**
     * 制造标准模板
     * @param ofcStorageTemplateListForConvert
     * @param standardColCode
     * @param standardColName
     */
    private void insertStandardTemplate(List<OfcStorageTemplate> ofcStorageTemplateListForConvert, String standardColCode, String standardColName) {
        OfcStorageTemplate ofcStorageTemplate = new OfcStorageTemplate();
        ofcStorageTemplate.setStandardColCode(standardColCode);
        ofcStorageTemplate.setStandardColName(standardColName);
        ofcStorageTemplate.setReflectColName(standardColName);
        ofcStorageTemplateListForConvert.add(ofcStorageTemplate);
    }


    /**
     * 模板映射
     * @param cellValue
     * @param templateDetilMap
     * @return
     */
    private OfcStorageTemplate cellReflectToDomain(String cellValue, Map<String, OfcStorageTemplate> templateDetilMap) {
        OfcStorageTemplate ofcStorageTemplate = templateDetilMap.get(cellValue);
        if(null == ofcStorageTemplate){
            return null;
        }
        if(PubUtils.isSEmptyOrNull(ofcStorageTemplate.getStandardColCode())){
            logger.error("当前模板映射缺少标准列编码");
            throw new BusinessException("当前模板映射缺少标准列编码");
        }
        return ofcStorageTemplate;
    }


}
