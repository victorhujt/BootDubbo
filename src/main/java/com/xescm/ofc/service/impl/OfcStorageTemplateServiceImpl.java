package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.OfcStorageTemplate;
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
        if(null == ofcStorageTemplateList || ofcStorageTemplateList.size() < 1){
            logger.error("模板配置筛选结果为空!");
            throw new BusinessException("模板配置筛选结果为空!");
        }
        return ofcStorageTemplateList;
    }

    /**
     * 模板配置删除
     * @param temlpateName
     *
     */
    @Transactional
    @Override
    public void delTemplateByName(String temlpateName) {
        logger.info("模板配置删除service , ==> temlpateName:{}",temlpateName);
        if(PubUtils.isSEmptyOrNull(temlpateName)){
            logger.error("模板配置删除失败! 入参有误!");
            throw new BusinessException("模板配置删除失败! 入参有误!");
        }
        OfcStorageTemplate ofcStorageTemplate = new OfcStorageTemplate();
        ofcStorageTemplate.setTemplateName(temlpateName);
        ofcStorageTemplateMapper.delete(ofcStorageTemplate);
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
            int update = ofcStorageTemplateMapper.updateByTemplateCode(ofcStorageTemplate);
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
     * @param custCode
     * @param templateCode
     * @param activeSheetNum
     * @return
     */
    @Override
    public Wrapper<?> checkStorageTemplate(MultipartFile uploadFile, AuthResDto authResDto, String templateType, String custCode, String templateCode, Integer activeSheetNum) {
        //拿到所用模板的明细Map
        Map<String,OfcStorageTemplate> templateDetilMap = getTemplateReflect(templateCode);
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
            clazz = Class.forName("com.xescm.ofc.model.dto.ofc.OfcExcelBoradwise");
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
                OfcStorageTemplateDto ofcStorageTemplateDto = null;
                try {
                    ofcStorageTemplateDto = (OfcStorageTemplateDto) clazz.newInstance();
                } catch (Exception e) {
                    logger.error("校验明细类型Excel:{}", e);
                }
                //校验第一行,包括固定内容和收货人列表
                if(rowNum == 0){
                    checkExcelRequiedItem(commonRow,templateType,templateDetilMap);
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
                        System.out.println();
                    }
                }

                if(rowNum > 0){
                    ofcStorageTemplateDtoList.add(ofcStorageTemplateDto);
                }
            }

        }
        return null;
    }

    /**
     * 校验表格是否有所有的必填列
     * @param commonRow
     * @param templateType
     * @param templateDetilMap
     */
    private void checkExcelRequiedItem(Row commonRow, String templateType, Map<String, OfcStorageTemplate> templateDetilMap) {
        String[] inRquiredItem = {"custOrderCode","merchandiser","warehouseName","businessType","goodsCode","quantity"};
        String[] outRquiredItem = {"custOrderCode","merchandiser","warehouseName","businessType","goodsCode","quantity","consigneeName"};
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
        }
        String[] item = StringUtils.equals(templateType,"storageIn") ? inRquiredItem : outRquiredItem;
        for (String requiredItem : item) {
            if(!ofcStorageTemplateMap.containsKey(requiredItem)){
                OfcStorageTemplate ofcStorageTemplate = ofcStorageTemplateMap.get(requiredItem);
                logger.error("当前表格缺少列:{}",ofcStorageTemplate);
                throw new BusinessException("当前表格缺少列:" + ofcStorageTemplate.getReflectColName());
            }
        }
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
     * @return
     */
    private Map<String,OfcStorageTemplate> getTemplateReflect(String templateCode) {
        TemplateCondition templateCondition = new TemplateCondition();
        templateCondition.setTemplateCode(templateCode);
        List<OfcStorageTemplate> ofcStorageTemplateListForConvert = this.selectTemplateDetail(templateCondition);
        Map<String,OfcStorageTemplate> map = new HashMap<>();
        for (OfcStorageTemplate ofcStorageTemplate : ofcStorageTemplateListForConvert) {
            map.put(ofcStorageTemplate.getReflectColName(),ofcStorageTemplate);
        }
        return map;
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
