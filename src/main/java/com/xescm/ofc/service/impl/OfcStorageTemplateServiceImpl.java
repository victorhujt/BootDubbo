package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.provider.CscContactEdasService;
import com.xescm.csc.provider.CscGoodsEdasService;
import com.xescm.csc.provider.CscSupplierEdasService;
import com.xescm.ofc.domain.*;
import com.xescm.ofc.enums.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcStorageTemplateMapper;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcStorageTemplateDto;
import com.xescm.ofc.service.*;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.rmc.edas.domain.qo.RmcWareHouseQO;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.type.TypeReference;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

import static com.xescm.ofc.constant.GenCodePreffixConstant.BATCH_PRE;
import static com.xescm.ofc.constant.GenCodePreffixConstant.STO_TEMP_PRE;
import static com.xescm.ofc.constant.OrderConstant.WAREHOUSE_DIST_ORDER;

/**
 *
 * Created by lyh on 2017/2/18.
 */
@Service
public class OfcStorageTemplateServiceImpl extends BaseService<OfcStorageTemplate> implements OfcStorageTemplateService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcFundamentalInformationService ofcFundamentalInformationService;
    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private OfcOrderStatusService ofcOrderStatusService;
    @Resource
    private RmcWarehouseEdasService rmcWarehouseEdasService;
    @Resource
    private CscContactEdasService cscContactEdasService;
    @Resource
    private CscGoodsEdasService cscGoodsEdasService;
    @Resource
    private OfcWarehouseInformationService ofcWarehouseInformationService;
    @Resource
    private OfcDistributionBasicInfoService ofcDistributionBasicInfoService;
    @Resource
    private OfcOrderManageService ofcOrderManageService;
    @Resource
    private CscSupplierEdasService cscSupplierEdasService;
    @Resource
    private OfcStorageTemplateMapper ofcStorageTemplateMapper;
    @Resource
    private CodeGenUtils codeGenUtils;




    /**
     * 模板配置保存
     * @param templateList 模板配置列表
     * @param authResDto 登录用户
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
        String templateCode = codeGenUtils.getNewWaterCode(STO_TEMP_PRE, 6);
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
     * 模板配置筛选// 模板导入用
     * @param templateCondition 筛选条件
     * @return 筛选结果
     */
    @Override
    public List<OfcStorageTemplate> selectTemplate(TemplateCondition templateCondition) {
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
     * 模板配置筛选
     * @param templateCondition 筛选条件
     * @return 筛选结果
     */
    @Override
    public List<OfcStorageTemplate> selectTemplateByCondition(TemplateCondition templateCondition) {
        if(null == templateCondition){
            logger.error("模板配置筛选条件为空!");
            throw new BusinessException("模板配置筛选条件为空!");
        }
        return ofcStorageTemplateMapper.selectTemplateByCondition(templateCondition);
    }



    /**
     * 模板配置删除
     * @param temlpateCode 模板编码
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
//        int delete = ofcStorageTemplateMapper.delete(ofcStorageTemplate);
        int delete = ofcStorageTemplateMapper.deleteByTemplateCode(temlpateCode);
        if(delete == 0){
            logger.error("模板配置删除失败!");
            throw new BusinessException("模板配置删除失败");
        }
    }

    /**
     * 模板配置编辑
     * @param templateList 模板配置列表
     * @param authResDto 登录用户
     * @param lastTemplateType 修改之前的模板类型
     */
    @Transactional
    @Override
    public void templateEditConfirm(String templateList, AuthResDto authResDto, String lastTemplateType) throws Exception {
        TypeReference<List<OfcStorageTemplate>> typeReference = new TypeReference<List<OfcStorageTemplate>>() {
        };
        List<OfcStorageTemplate> ofcStorageTemplates = JacksonUtil.parseJson(templateList, typeReference);
        this.checkTemplateListRequired(ofcStorageTemplates);
        String userId = authResDto.getUserId();
        String userName = authResDto.getUserName();
        Date now = new Date();
        OfcStorageTemplate ofcStorageTemplateForFix = ofcStorageTemplates.get(0);
        String currTemplateType = ofcStorageTemplateForFix.getTemplateType();
        OfcStorageTemplate ofcStorageTemplate = new OfcStorageTemplate();
        ofcStorageTemplate.setIndexNum(22);

        Integer changeNum = StringUtils.equals(currTemplateType, "storageIn") ? 21 : 22;
        Integer updateNum = changeNum;
        int num = 0;
        for (OfcStorageTemplate ofcStorageTemp : ofcStorageTemplates) {
            num ++ ;
            ofcStorageTemp.setOperator(userId);
            ofcStorageTemp.setOperatorName(userName);
            ofcStorageTemp.setOperTime(now);
            int i = 0;
            if(num != changeNum){
                i = ofcStorageTemplateMapper.updateByTemplateCode(ofcStorageTemp);
            }else {

                if(StringUtils.equals(currTemplateType, "storageIn") && StringUtils.equals(lastTemplateType, "storageOut")){
                    //21
                    ofcStorageTemplate.setTemplateCode(ofcStorageTemplateForFix.getTemplateCode());
                    //删掉模板中第22条
                    int delete = ofcStorageTemplateMapper.delete(ofcStorageTemplate);
                    if(delete == 0){
                        logger.error("模板配置编辑更新失败, 删掉模板中第22条失败");
                        throw new BusinessException("模板配置编辑更新失败");
                    }
                }else if(StringUtils.equals(currTemplateType, "storageOut") && StringUtils.equals(lastTemplateType, "storageIn")){
                    //22
                    //新增模板第22条
                    ModelMapper modelMapper = new ModelMapper();
                    modelMapper.map(ofcStorageTemplate, ofcStorageTemplateForFix);
                    modelMapper.map(ofcStorageTemp, ofcStorageTemplate);
                    ofcStorageTemp.setId(null);
                    ofcStorageTemp.setCreatTime(now);
                    ofcStorageTemp.setCreator(userId);
                    ofcStorageTemp.setCreatorName(userName);
                    i = ofcStorageTemplateMapper.insert(ofcStorageTemp);
                }else {
                    i = ofcStorageTemplateMapper.updateByTemplateCode(ofcStorageTemp);
                }
            }
            updateNum -= i;
        }
        if(updateNum.compareTo(changeNum) == 0){
            logger.error("模板配置编辑更新失败! updateNum:{}", updateNum);
            throw new BusinessException("模板配置编辑更新失败!");
        }

    }

    /**
     * 模板明细查询
     * @param templateCondition 筛选条件
     * @return 筛选结果
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
     * @param ofcStorageTemplate 模板
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
     * @param uploadFile 上传的文件
     * @param authResDto 登录的用户
     * @param ofcStorageTemplate: templateType custCode templateCode
     * @param activeSheetNum 用户打开时活跃的Sheet页
     * @return 校验结果
     */
    @Override
    public Wrapper<?> checkStorageTemplate(MultipartFile uploadFile, AuthResDto authResDto,OfcStorageTemplate ofcStorageTemplate, Integer activeSheetNum) {

        //根据模板编码和类型拿到用户保存的配置模板的映射 key是用户表头列名
        //这里是用户进行模板配置了的, 下面还有(在第一行校验表头列名的时候, 如果用户的列名能与标准列名对应上, 那么依然进行可以映射)
        List<Object> templateReflect = getTemplateReflect(ofcStorageTemplate.getTemplateCode(), ofcStorageTemplate.getTemplateType());
        //这里拿到的模板配置是不带同名校验的
        Map<String,OfcStorageTemplate> templateDetilMap = (Map<String, OfcStorageTemplate>) templateReflect.get(0);//key是用户表头列名  //有映射列名的
        Map<String,OfcStorageTemplate> forDefaultButNotRequired = (Map<String, OfcStorageTemplate>) templateReflect.get(1);//key是用户表头列标准编码 // 没有映射列名的
        Map<String,OfcStorageTemplate> forDefaultButNotRequiredName = (Map<String, OfcStorageTemplate>) templateReflect.get(2);//key是用户表头列标准名称 // 没有映射列名的

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
        Map<Integer,OfcStorageTemplate> modelNameStr = new LinkedHashMap<>();
        Class clazz = null;
        List<OfcStorageTemplateDto> ofcStorageTemplateDtoList = new ArrayList<>();
        Map<String, CscGoodsApiVo> goodsCheck = new HashMap<>();
        Map<String, CscContantAndCompanyResponseDto> consigneeCheck = new HashMap<>();
        Map<String, CscSupplierInfoDto> supplierCheck = new HashMap<>();
        //去RMC查到所有仓库
        Map<String, RmcWarehouseRespDto> allWarehouseByRmc =  this.getAllWarehouseByCustCode(ofcStorageTemplate.getCustCode());
        try {
            clazz = Class.forName("com.xescm.ofc.model.dto.ofc.OfcStorageTemplateDto");
        } catch (ClassNotFoundException e) {
            logger.error("校验Excel,ClassNotFoundException:{}",e);
        }
        int numberOfSheets = workbook.getNumberOfSheets();
        //必填列列号
        Map<Integer, String> requiedItemIndex = new HashMap<>();
        //有用列
        List<String> usefulCol = new ArrayList<>();

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
                    throw new BusinessException("校验Excel失败! 内部错误!");
                }
                //校验第一行,校验表格是否有所有的必填列列名,
                //并返回所有必填项在用户上传的Excel模板中的列号,及必填列号对应的映射模板列名称

                if(rowNum == 0){
                    try {
                        //加入同名校验,相同名字的进行自动映射
                        sameNameAutoReflect(commonRow, templateDetilMap, forDefaultButNotRequiredName);
                        requiedItemIndex = checkExcelRequiedItem(commonRow, ofcStorageTemplate.getTemplateType(), templateDetilMap);
                    } catch (BusinessException e) {
                        logger.error("表头必填列校验错误!, e:{}", e.getMessage());
                        xlsErrorMsg.add(e.getMessage());
                        return WrapMapper.wrap(Wrapper.ERROR_CODE, "当前Excel校验出错", xlsErrorMsg);
                    }
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
                    logger.info("开始校验第{}行, 第{}列, 当前单元格的值为:{}", rowNum + 1, cellNum + 1, ToStringBuilder.reflectionToString(commonCell));
                    //空列
//                    if (null == commonCell && cellNum > 11) {
                    //如果必填列为空则报错提示
                    if(null == commonCell && requiedItemIndex.containsKey(cellNum)){
                        String requiredColName = requiedItemIndex.get(cellNum);
                        logger.error("用户上传的Excel缺少必填项: {},{}, 位于第{}行, 第{}列", requiedItemIndex.get(cellNum), requiredColName, (rowNum + 1), (cellNum + 1));
//                        xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!" + requiredColName + "必填!");
                        xlsErrorMsg.add("【" + requiredColName + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                        checkPass = false;
                        break;
                    }
                    //如果非必填列为空则跳过
                     if (null == commonCell) {
                        //标记当前列出错, 并跳过当前循环
                        continue;
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
                        OfcStorageTemplate ofcStorageTemplateForReflect = cellReflectToDomain(cellValue,templateDetilMap); //标准表字段映射成对应实体的字段的值
                        if(null == ofcStorageTemplateForReflect || PubUtils.isSEmptyOrNull(ofcStorageTemplateForReflect.getStandardColCode())){
                            continue;
                        }
                        modelNameStr.put(cellNum + 1,ofcStorageTemplateForReflect);
                        String usefulColName = cellValue;
                        String usefulColCode = ofcStorageTemplateForReflect.getStandardColCode();
                        usefulCol.add(usefulColName + "@" + usefulColCode);
                    }else if(rowNum > 0) { // 表格的数据体
                        OfcStorageTemplate ofcStorageTemplateForCheck = modelNameStr.get(cellNum + 1);
                        if(null == ofcStorageTemplateForCheck){
                            //当前列没在模板映射里配置, 所以直接跳过
                            logger.info("当前列没在模板映射里配置, 所以直接跳过");
                            continue;
                        }
                        String standardColCode = ofcStorageTemplateForCheck.getStandardColCode();
                        String colDefaultVal = ofcStorageTemplateForCheck.getColDefaultVal();
                        if(PubUtils.isSEmptyOrNull(standardColCode)){
                            logger.info("当前列在模板映射配置没有standardColCode, 所以直接跳过");
                            continue;
                        }
                        //客户订单号//必填列名
                        if(StringUtils.equals(StorageImportInEnum.CUST_ORDER_CODE.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有客户订单号", rowNum + 1, cellNum + 1);
//                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "缺少必填字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //订单日期
                        }else if(StringUtils.equals(StorageImportInEnum.ORDER_TIME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.info("订单日期 ==> ", cellValue);
                                cellValue = DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1);
                                logger.info("订单日期 ==> ", cellValue);

                            }else {
                                //如果Excel中用户手写了订单日期, 就用用户写的日期, 并对该单元格的值进行校验
                                String[] split = cellValue.split(" ");

                                //正则还需要修正
                                if(split.length > 1 ? !(split[0].matches(PubUtils.REGX_YEARDATE) && split[1].matches(PubUtils.REGX_TIME)) : !cellValue.matches(PubUtils.REGX_YEARDATE)){
                                    logger.error("当前行:{},列:{} 校验失败,不是日期类型", rowNum + 1, cellNum + 1);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ cellValue + ",不是日期类型");
                                    checkPass = false;
                                    continue;
                                }
                            }
                            logger.info("订单日期 ==> ", cellValue);
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //开单员//必填列名
                        }else if(StringUtils.equals(StorageImportInEnum.MERCHANDISER.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                if(!PubUtils.isSEmptyOrNull(colDefaultVal)){
                                    cellValue = colDefaultVal;
                                }else {
                                    cellValue = authResDto.getUserName();
                                }
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //仓库名称//必填列名
                        }else if(StringUtils.equals(StorageImportInEnum.WAREHOUSE_NAME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                if(!PubUtils.isSEmptyOrNull(colDefaultVal)){
                                    cellValue = colDefaultVal;
                                }else {
                                    logger.error("当前行:{},列:{} 没有仓库名称", rowNum + 1, cellNum + 1);
                                    xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                    checkPass = false;
                                    continue;
                                }
                            }
                            //如果有仓库名称, 校验该仓库名称是否存在!
                            if(!allWarehouseByRmc.containsKey(cellValue)){
                                logger.error("当前行:{},列:{}仓库名称:{}, 调用接口未能找到该仓库", rowNum + 1, cellNum + 1, cellValue);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "仓库名称【" + cellValue + "】无效! 该客户下没有该仓库!");
                                checkPass = false;
                                continue;
                            }
                            RmcWarehouseRespDto rmcWarehouseRespDto = allWarehouseByRmc.get(cellValue);
                            ofcStorageTemplateDto.setWarehouseCode(rmcWarehouseRespDto.getWarehouseCode());
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //业务类型//必填列名
                        }else if(StringUtils.equals(StorageImportInEnum.BUSINESS_TYPE.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                if(!PubUtils.isSEmptyOrNull(colDefaultVal)){
                                    cellValue = colDefaultVal;
                                }else {
                                    logger.error("当前行:{},列:{} 没有业务类型", rowNum + 1, cellNum + 1);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + ",列:" + (cellNum + 1) + " 缺少必填字段:" + ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                    checkPass = false;
                                    continue;
                                }
                            }else {
                                //如果当前单元格不空, 需要校验是否是ofc可识别的订单类型
                                Wrapper result = checkBusinessType(cellValue, ofcStorageTemplate.getTemplateType());
                                if(Wrapper.ERROR_CODE == result.getCode()){
                                    logger.error("当前行:{},列:{} 业务类型:{}不可识别", rowNum + 1, cellNum + 1, cellValue);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + ",列:" + (cellNum + 1) + " 业务类型【" + cellValue + "】无效!");
                                    checkPass = false;
                                    continue;
                                }
                                cellValue = (String) result.getResult();
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //备注
                        }else if(StringUtils.equals(StorageImportInEnum.NOTES.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有备注", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "缺少必填字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //货品编码//必填列名
                        }else if(StringUtils.equals(StorageImportInEnum.GOODS_CODE.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有货品编码", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                checkPass = false;
                                continue;
                            }
                            //货品编码不空,判断是否已经校验过
                            //如果没校验过就调用CSC接口进行校验
                            if(!goodsCheck.containsKey(cellValue)){
                                CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                                cscGoodsApiDto.setGoodsCode(cellValue);
                                cscGoodsApiDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                                Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = cscGoodsEdasService.queryCscGoodsList(cscGoodsApiDto);
                                if(Wrapper.ERROR_CODE == queryCscGoodsList.getCode()){
                                    logger.error("当前行:{},列:{} 货品编码校验失败, 请维护", rowNum + 1, cellNum + 1);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "【" + ofcStorageTemplateForCheck.getReflectColName() + "】为：【" + cellValue +"】校验出错！");
                                    checkPass = false;
                                    continue;
                                }
                                List<CscGoodsApiVo> cscGoodsApiVoResult = queryCscGoodsList.getResult();
                                //没有校验通过
                                if(null == cscGoodsApiVoResult || cscGoodsApiVoResult.size() == 0){
                                    logger.error("当前行:{},列:{} 货品编码校验失败, 请维护", rowNum + 1, cellNum + 1);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "货品编码校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "货品编码【" + cellValue + "】货品档案信息不存在！");
                                    checkPass = false;
                                    continue;
                                    //校验通过
                                }else {
                                    logger.info("当前货品编码:{},校验通过", cellValue);
                                    goodsCheck.put(cellValue, cscGoodsApiVoResult.get(0));
                                    ofcStorageTemplateDto.setCscGoodsApiVo(cscGoodsApiVoResult.get(0));
                                }
                            }else {
                                logger.info("当前货品编码:{},不用校验", cellValue);
                                //不用校验, 直接堆
                                CscGoodsApiVo cscGoodsApiVo = goodsCheck.get(cellValue);
                                ofcStorageTemplateDto.setCscGoodsApiVo(cscGoodsApiVo);
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //货品名称
                        }else if(StringUtils.equals(StorageImportInEnum.GOODS_NAME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有货品名称", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //规格
                        }else if(StringUtils.equals(StorageImportInEnum.GOODS_SPEC.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有规格", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //单位
                        }else if(StringUtils.equals(StorageImportInEnum.UNIT.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有单位", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //单价
                        }else if(StringUtils.equals(StorageImportInEnum.UNIT_PRICE.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有单价", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            boolean matchesPot = cellValue.matches("\\d{1,6}\\.\\d{1,3}");
                            boolean matchesInt = cellValue.matches("\\d{1,6}");
                            if(!matchesPot && !matchesInt){
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "单价【" + cellValue + "】格式错误！");
                                checkPass = false;
                                continue;
                            }
                            BigDecimal bigDecimal = new BigDecimal(cellValue);
                            Field field;
                            try {
                                field = clazz.getDeclaredField(standardColCode);
                                field.setAccessible(true);
                                field.set(ofcStorageTemplateDto,bigDecimal);
                            } catch (Exception e) {
                                logger.error("当前行:{},列:{} 单价校验失败", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                throw new BusinessException("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                            }
                            //入库数量 or  出库数量//必填列名
                        }else if(StringUtils.equals(StorageImportInEnum.QUANTITY.getStandardColCode(), standardColCode)){

                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.info("【{}】列第{}行数据为空，请检查文件！", ofcStorageTemplateForCheck.getReflectColName(), (rowNum + 1));
                                cellValue = "0";
                            }
                            boolean matchesPot = cellValue.matches("\\d{1,6}\\.\\d{1,3}");
                            boolean matchesInt = cellValue.matches("\\d{1,6}");
                            //如果校验成功,就往结果集里堆
                            if(matchesPot || matchesInt){
                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field;
                                try {
                                    field = clazz.getDeclaredField(standardColCode);
                                    field.setAccessible(true);
                                    field.set(ofcStorageTemplateDto,bigDecimal);
                                } catch (Exception e) {
                                    logger.error("当前行:{},列:{} 入库数量 or  出库数量校验失败", rowNum + 1, cellNum + 1);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    throw new BusinessException("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                }
                            }else{
                                checkPass = false;
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值不符合规范!该货品数量格式不正确!");
                            }
                            //批次号
                        }else if(StringUtils.equals(StorageImportInEnum.PRODUCTION_BATCH.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有批次号", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //生产日期
                        }else if(StringUtils.equals(StorageImportInEnum.PRODUCTION_TIME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有生产日期", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            //校验是不是日期类型
                            //yyyy-MM-dd || yyyy-MM-dd hh:mm:ss
                            String[] split = cellValue.split(" ");
                            if(split.length > 1 ? !(split[0].matches(DateUtils.DATE) && split[1].matches(PubUtils.REGX_TIME)) : !cellValue.matches(DateUtils.DATE)){
                                logger.error("当前行:{},列:{} 校验失败,不是日期类型", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ cellValue + ",不是日期类型");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //失效日期
                        }else if(StringUtils.equals(StorageImportInEnum.INVALID_TIME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有失效日期", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            //校验是不是日期类型
                            String[] split = cellValue.split(" ");
                            if(split.length > 1 ? !(split[0].matches(PubUtils.REGX_YEARDATE) && split[1].matches(PubUtils.REGX_TIME)) : !cellValue.matches(PubUtils.REGX_YEARDATE)){
                                logger.error("当前行:{},列:{} 校验失败,不是日期类型", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ cellValue + ",不是日期类型");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //供应商名称
                        }else if(StringUtils.equals(StorageImportInEnum.SUPPORT_NAME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有供应商名称", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            //对供应商名称进行校验// supplierCheck

                            //供应商名称不空,判断是否已经校验过
                            //如果没校验过就调用CSC接口进行校验
                            if(!supplierCheck.containsKey(cellValue)){
                                CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
                                cscSupplierInfoDto.setSupplierName(cellValue);
                                cscSupplierInfoDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                                Wrapper<List<CscSupplierInfoDto>> listWrapper = cscSupplierEdasService.querySupplierByAttribute(cscSupplierInfoDto);
                                if(Wrapper.ERROR_CODE == listWrapper.getCode()){
                                    logger.error("当前行:{},列:{} 供应商名称校验失败, 请维护", rowNum + 1, cellNum + 1);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + ofcStorageTemplateForCheck.getReflectColName() + "为: " + cellValue +"校验出错!");
                                    checkPass = false;
                                    continue;
                                }
                                List<CscSupplierInfoDto> result = listWrapper.getResult();
                                //没有校验通过
                                if(null == result || result.size() == 0){
                                    logger.error("当前行:{},列:{} 供应商名称校验失败, 请维护", rowNum + 1, cellNum + 1);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "供应商名称校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "供应商名称【" + cellValue + "】无效！");
                                    checkPass = false;
                                    continue;
                                    //校验通过
                                }else {
                                    logger.info("当前供应商名称:{},校验通过", cellValue);
                                    supplierCheck.put(cellValue, result.get(0));
                                    ofcStorageTemplateDto.setCscSupplierInfoDto(result.get(0));
                                }
                            }else {
                                logger.info("当前供应商名称:{},已经校验过, 不用校验", cellValue);
                                //不用校验, 直接堆
                                CscSupplierInfoDto cscSupplierInfoDto = supplierCheck.get(cellValue);
                                ofcStorageTemplateDto.setCscSupplierInfoDto(cscSupplierInfoDto);
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //预计入(出)库时间
                        }else if(StringUtils.equals(StorageImportInEnum.ARRIVE_TIME.getStandardColCode(), standardColCode)
                                ||StringUtils.equals(StorageImportOutEnum.SHIPMENT_TIME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有预计入库时间", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            //校验是不是日期类型
                            if(!PubUtils.isDateTimeFormat(cellValue)
                                    && !PubUtils.isTimeFormat(cellValue) && !PubUtils.isYearDateFormat(cellValue)){
                                logger.error("当前行:{},列:{} 校验失败,不是日期类型", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ cellValue + ",不是日期类型");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //是否提供运输服务
                        }else if(StringUtils.equals(StorageImportInEnum.PROVIDE_TRANSPORT.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                if(!PubUtils.isSEmptyOrNull(colDefaultVal)){
                                    cellValue = StringUtils.equals(ofcStorageTemplate.getTemplateType(), "storageIn") ? "0" : colDefaultVal.equals("是") ? "1" : "0";
                                }else {
                                    logger.error("当前行:{},列:{} 没有是否提供运输服务, 默认为0", rowNum + 1, cellNum + 1);
                                    cellValue = "0";
                                }
                            }
                            //只接受:是/否
                            if(!StringUtils.equals("是", cellValue) && !StringUtils.equals("否", cellValue)){
                                logger.error("当前行:{},列:{} 校验失败,是否提供运输服务字段只接受:是/否, 用户表中数据为:{}", rowNum + 1, cellNum + 1, cellValue);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "是否提供运输校验失败:"+ cellValue + ",只接受:是/否");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue.equals("是") ? "1" : "0", standardColCode);
                            //车牌号
                        }else if(StringUtils.equals(StorageImportInEnum.PLATE_NUMBER.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有车牌号", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //司机姓名
                        }else if(StringUtils.equals(StorageImportInEnum.DRIVER_NAME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有司机姓名", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //联系电话
                        }else if(StringUtils.equals(StorageImportInEnum.CONTACT_NUMBER.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有联系电话", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            //校验该电话格式是否正确

                            boolean matchesPot = PubUtils.isMobileNumber(cellValue);
                            if(!matchesPot){
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "联系电话【" + cellValue + "】格式错误！");
                                checkPass = false;
                                continue;
                            }
                            BigDecimal bigDecimal = new BigDecimal(cellValue);
                            Field field;
                            try {
                                field = clazz.getDeclaredField(standardColCode);
                                field.setAccessible(true);
                                field.set(ofcStorageTemplateDto,bigDecimal);
                            } catch (Exception e) {
                                logger.error("当前行:{},列:{} 联系电话校验失败", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                throw new BusinessException("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                            }

//                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //发货方名称//必填列名
                        }else if(StringUtils.equals(StorageImportOutEnum.CONSIGNEE_NAME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有发货方名称", rowNum + 1, cellNum);
                                xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                checkPass = false;
                                continue;
                            }
                            //对发货方名称进行校验
                            //去接口查该收货方名称是否在客户中心维护
                            if(!consigneeCheck.containsKey(cellValue)){
                                CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                                CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
                                CscContactDto cscContactDto = new CscContactDto();
                                cscContantAndCompanyDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                                cscContactCompanyDto.setContactCompanyName(cellValue);
                                cscContactDto.setPurpose("1");//用途为收货方
                                cscContantAndCompanyDto.setCscContactDto(cscContactDto);
                                cscContantAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
                                Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = cscContactEdasService.queryCscReceivingInfoList(cscContantAndCompanyDto);
                                if(Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()){
                                    throw new BusinessException(queryCscCustomerResult.getMessage());
                                }
                                List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
                                //即在客户中心没有找到该收货方
                                if(null == result || result.size() == 0){
                                    logger.error("当前行:{},列:{} 发货方名称校验失败, 请维护", rowNum + 1, cellNum);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "发货方名称校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "发货方名称【" + cellValue + "】无效！");
                                    checkPass = false;
                                    continue;
                                    //找到该收货方了
                                }else {
                                    CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = result.get(0);
                                    ofcStorageTemplateDto.setCscConsigneeDto(cscContantAndCompanyResponseDto);
                                    consigneeCheck.put(cellValue, cscContantAndCompanyResponseDto);
                                }
                            }else {
                                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = consigneeCheck.get(cellValue);
                                ofcStorageTemplateDto.setCscConsigneeDto(cscContantAndCompanyResponseDto);
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                        }
                    }
                }
                if(rowNum > 0 && checkPass){
                    //补齐
                    ofcStorageTemplateDto.getOfcOrderDTO().setCustCode(ofcStorageTemplate.getCustCode());
                    ofcStorageTemplateDto.getOfcOrderDTO().setCustName(ofcStorageTemplate.getCustName());
                    //若文件中不存在此列，则所有订单默认为当前日期.
                    Date now = new Date();
                    if(PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getOrderTime())){
                        ofcStorageTemplateDto.getOfcOrderDTO().setOrderTime(now);
                        ofcStorageTemplateDto.setOrderTime(DateUtils.Date2String(now, DateUtils.DateFormatType.TYPE1));
                    }
                    //若文件中不存在此列，则所有订单默认为当前操作员名称。
                    if(PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getMerchandiser())){
                        ofcStorageTemplateDto.setMerchandiser(authResDto.getUserName());
                    }
                    //若文件中不存在此列，则仓库名称列报错
                    if(PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getWarehouseName())){
//                        logger.error("当前行:{},仓库名称校验失败,仓库名称是空的, 请维护", rowNum + 1);
//                        xlsErrorMsg.add("行:" + (rowNum + 1) + "仓库名称为空！");
//                        checkPass = false;
                        OfcStorageTemplate forWarehouseName = forDefaultButNotRequired.get("warehouseName");
                        if(PubUtils.isSEmptyOrNull(forWarehouseName.getColDefaultVal())){
                            logger.error("当前行:{},仓库名称校验失败,模板配置中仓库名称列默认值是空的, 请维护", rowNum + 1);
                            xlsErrorMsg.add("行:" + (rowNum + 1) + "模板配置中仓库名称默认值为空！");
                            checkPass = false;
                            continue;
                        }
                        if(allWarehouseByRmc.containsKey(forWarehouseName.getColDefaultVal())){
                            ofcStorageTemplateDto.setWarehouseName(forWarehouseName.getColDefaultVal());
                        }else {
                            logger.error("当前行:{},仓库名称校验失败,模板配置中仓库名称默认值{}校验失败, 请维护", rowNum + 1, forWarehouseName.getColDefaultVal());
                            xlsErrorMsg.add("行" + (rowNum + 1) + "模板配置中仓库名称默认值【" + forWarehouseName.getColDefaultVal() + "】校验失败！该客户下没有该仓库!");
                            checkPass = false;
                            continue;
                        }
                    }
                    //若文件中不存在此列，则业务类型列报错
                    if(PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getBusinessType())){
                        OfcStorageTemplate forBusinessType = forDefaultButNotRequired.get("businessType");
                        if(PubUtils.isSEmptyOrNull(forBusinessType.getColDefaultVal())){
                            logger.error("当前行:{},模板配置中业务类型默认值校验失败,业务类型是空的, 请维护", rowNum + 1);
                            xlsErrorMsg.add("行:" + (rowNum + 1) + "模板配置中业务类型默认值为空！");
                            checkPass = false;
                            continue;
                        }
                        Wrapper wrapper = checkBusinessType(forBusinessType.getColDefaultVal(), ofcStorageTemplate.getTemplateType());
                        if(wrapper.getCode() == 200){
                            ofcStorageTemplateDto.setBusinessType((String) wrapper.getResult());
                        }else {
                            logger.error("当前行:{},业务类型校验失败,模板配置中业务类型默认值校验失败, 请维护", rowNum + 1);
                            xlsErrorMsg.add("行:" + (rowNum + 1) + "模板配置中业务类型默认值【" + forBusinessType.getColDefaultVal() + "】校验失败！");
                            checkPass = false;
                            continue;
                        }
                    }
                    //如果Excel中没有是否提供运输这一列, 则默认设置为用户默认的, 如果没有默认的就置为否(即0)
                    if(PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getProvideTransport())){
                        //拿着provideTransport找到默认值
                        OfcStorageTemplate forDefault = forDefaultButNotRequired.get("provideTransport");
                        String provideTrans = null;
                        if(null != forDefault){
                            provideTrans = forDefault.getColDefaultVal();
                        }
                        ofcStorageTemplateDto.setProvideTransport(PubUtils.isSEmptyOrNull(provideTrans) ? "0" : StringUtils.equals(provideTrans, "是") ? "1" : "0");
                    }

                    ofcStorageTemplateDtoList.add(ofcStorageTemplateDto);
                }
            }
        }

        //基本校验通过后检查客户订单编号是否重复
        Set<String> custOrderCodeSet = new HashSet<>();
        for (OfcStorageTemplateDto ofcStorageTemplateDto : ofcStorageTemplateDtoList) {
            custOrderCodeSet.add(ofcStorageTemplateDto.getCustOrderCode());
        }
        for (String custOrderCode : custOrderCodeSet) {
            int repeat = ofcFundamentalInformationService.checkCustOrderCodeRepeat(custOrderCode);
            if(repeat > 0) {
                logger.error("客户订单号【{}】已经在系统中存在，无法重复导入，请检查处理!", custOrderCode);
                xlsErrorMsg.add("客户订单号【" + custOrderCode + "】已经在系统中存在，无法重复导入，请检查处理!");
                checkPass = false;
            }
        }

        //如果校验出错
        if(!checkPass){
            logger.error("当前Excel校验出错!");
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "当前Excel校验出错", xlsErrorMsg);
        }
        logger.info("当前Excel校验成功!");
        //校验成功!
        //ofcStorageTemplateDtoList 用来下单用的
        //然后堆一个
        List<Object> succeedResult = new ArrayList<>();
        succeedResult.add(usefulCol);
        succeedResult.add(ofcStorageTemplateDtoList);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, succeedResult);
    }

    /**
     * 只要用户的Excel中的列名是标准列名, 也进行自动映射
     * @param commonRow Excel第一列
     * @param templateDetilMap 用户模板
     * @param forDefaultButNotRequiredName 表中没有
     */
    private void sameNameAutoReflect(Row commonRow, Map<String, OfcStorageTemplate> templateDetilMap
            , Map<String, OfcStorageTemplate> forDefaultButNotRequiredName) {
        //先以用户模板配置的为准
        for(int cellNum = 0; cellNum < commonRow.getLastCellNum() + 1; cellNum ++) {
            Cell commonCell = commonRow.getCell(cellNum);
            //空列
            if (null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                //标记当前列出错, 并跳过当前循环
                continue;
            }
            String commonCellValue = commonCell.getStringCellValue();// 当前列 列名
            //先以用户模板配置的为准
            if(!templateDetilMap.containsKey(commonCellValue) && forDefaultButNotRequiredName.containsKey(commonCellValue)){
                OfcStorageTemplate ofcStorageTemplate = forDefaultButNotRequiredName.get(commonCellValue);
                templateDetilMap.put(commonCellValue, ofcStorageTemplate);
            }
        }
    }

    /**
     * 校验订单业务类型
     * @param cellValue  单元格的值
     * @param templateType 模板类型
     * @return 校验结果
     */
    private Wrapper checkBusinessType(String cellValue, String templateType) {
        Boolean check = true;
        //入库单
        if(StringUtils.equals(templateType, "storageIn")){
            List<String> codeList = BusinessTypeStorageInEnum.getCodeList();
            List<String> descList = BusinessTypeStorageInEnum.getDescList();
            if(!codeList.contains(cellValue) && !descList.contains(cellValue)){
                check = false;
            }else if(descList.contains(cellValue)){
                cellValue = BusinessTypeStorageInEnum.getBusinessCodeByTypeDesc(cellValue);
            }
            //出库单
        }else if(StringUtils.equals(templateType, "storageOut")){
            List<String> codeList = BusinessTypeStorageOutEnum.getCodeList();
            List<String> descList = BusinessTypeStorageOutEnum.getDescList();
            if(!codeList.contains(cellValue) && !descList.contains(cellValue)){
                check = false;
            }else if(descList.contains(cellValue)){
                cellValue = BusinessTypeStorageOutEnum.getBusinessCodeByTypeDesc(cellValue);
            }
        }
        if(!check){
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, cellValue);
    }


    /**
     * @return key : 仓库名称, value : 仓库信息
     * @param custCode 客户编码
     */
    private Map<String,RmcWarehouseRespDto> getAllWarehouseByCustCode(String custCode) {
        if(PubUtils.isSEmptyOrNull(custCode)){
            logger.error("仓储批量导单抓取当前客户下的仓库失败, 入参为空!");
            throw new BusinessException("仓储批量导单抓取当前客户下的仓库失败");
        }
        List<RmcWarehouseRespDto> rmcWarehouseByCustCode  = ofcWarehouseInformationService.getWarehouseListByCustCode(custCode);
        Map<String, RmcWarehouseRespDto> map = new HashMap<>();
        for (RmcWarehouseRespDto rmcWarehouseRespDto : rmcWarehouseByCustCode) {
            map.put(rmcWarehouseRespDto.getWarehouseName(), rmcWarehouseRespDto);
        }
        return map;
    }


    /**
     * 通过RMC接口返回所有的仓库信息
     * @return 所有仓库信息
     */
    public List<RmcWarehouseRespDto> allWarehouseByRmc(){
        Wrapper<List<RmcWarehouseRespDto>> listWrapper = rmcWarehouseEdasService.queryWarehouseList(new RmcWareHouseQO());
        if(listWrapper.getCode() == Wrapper.ERROR_CODE || CollectionUtils.isEmpty(listWrapper.getResult())) {
            logger.error("从资源中心加载所有仓库列表失败, 接口返回的错误信息为: {}", listWrapper.getMessage());
            throw new BusinessException("从资源中心加载所有仓库列表失败!");
        }
        return listWrapper.getResult();
    }




    /**
     * 设置实体属性值
     * @param clazz 类
     * @param ofcStorageTemplateDto 模板实体
     * @param cellValue 单元格值
     * @param cellNumCode 当前列号对应的实体中的字段名称
     */
    private void setFiledValue(Class clazz, OfcStorageTemplateDto ofcStorageTemplateDto, Object cellValue, String cellNumCode) {
        try {
            if(null == cellValue){
                return;
            }
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
     * 校验表格第一行列名是否有所有的必填列, 并将表格中的必填列的列号返回
     * 2017年3月9日 增加逻辑: 如果用户的列名能与标准列名对应上, 那么依然进行可以映射
     * @param commonRow 通用行
     * @param templateType 模板类型
     * @param templateDetilMap 列名的模板映射信息
     * @return key必填列号, value对应的映射列名
     */
    private Map<Integer, String> checkExcelRequiedItem(Row commonRow, String templateType, Map<String, OfcStorageTemplate> templateDetilMap) {
        List<String> inRquiredItems = InRquiredItem.getstandardCodeList();
        List<String> outRquiredItems = OutRquiredItem.getstandardCodeList();
        List<String> item = StringUtils.equals(templateType,"storageIn") ? inRquiredItems : outRquiredItems;
        List<String> check = new ArrayList<>();
        check.addAll(item);
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
            String commonCellValue = commonCell.getStringCellValue();// 当前列 列名
            //根据当前列列名找到映射数据
            OfcStorageTemplate ofcStorageTemplate = cellReflectToDomain(commonCellValue, templateDetilMap);
            if(null == ofcStorageTemplate){
                // 当前模板中的列名, 没有找到在初始化的Map的映射表中找到映射
                logger.info("当前模板中的列名:{}, 没有找到在初始化的Map的映射表中找到映射, 说明此列不在存储的模板中", commonCellValue);
                continue;
            }
            String standardColCode = ofcStorageTemplate.getStandardColCode();
            //如果能找到
            int index = check.indexOf(standardColCode);
            if(index != -1){
                check.remove(index);
            }
            ofcStorageTemplateMap.put(standardColCode, ofcStorageTemplate);//key : 标准列code, value: 对应映射实体
            colNumMap.put(standardColCode, cellNum); //key : 标准列code, value: 必填列号
        }

        if (check.size() >0) {
            logger.error("必填列有缺失! 缺失的字段List:{}", check.toArray());
            StringBuilder sb = new StringBuilder("【");
            for (String s : check) {
                if(!PubUtils.isSEmptyOrNull(s)){
                    sb.append(OutRquiredItem.getAnyByStandardCode(s).getStandardColName());
                    sb.append(" ");
                }
            }
            sb.append("】");
            if(check.size() == item.size()){
                logger.error("必填列有缺失!缺失的字段为:{}。建议：是否未匹配模板？", sb.toString());
                throw new BusinessException("必填列有缺失!缺失的字段为:" + sb.toString()+ "。建议：是否未匹配模板？");
            }else {
                logger.error("必填列有缺失!缺失的字段为:{}", sb.toString());
                throw new BusinessException("必填列有缺失!缺失的字段为:" + sb.toString());
            }

        }
        Map<Integer, String> requiredColNum = new HashMap<>();
        //遍历必填列编码
        for (String requiredItem : item) {
            if(!ofcStorageTemplateMap.containsKey(requiredItem)){
                if(StringUtils.equals("storageIn", templateType)){
                    InRquiredItem anyByStandardCode = InRquiredItem.getAnyByStandardCode(requiredItem);
                    logger.error("没有在初始化的Map的映射表中找到必填映射列:{},{}", requiredItem, anyByStandardCode.getStandardColName());
                    throw new BusinessException("没有在初始化映射表中找到必填映射列:" + anyByStandardCode.getStandardColName());
                }else if(StringUtils.equals("storageOut", templateType)){
                    OutRquiredItem anyByStandardCode = OutRquiredItem.getAnyByStandardCode(requiredItem);
                    logger.error("没有在初始化的Map的映射表中找到必填映射列:{},{}", requiredItem, anyByStandardCode.getStandardColName());
                    throw new BusinessException("【" + anyByStandardCode.getStandardColName() + "】列名不存在，请检查文件");
                }
            }else {
                //将当前Excel模板必填列的列号记录
                requiredColNum.put(colNumMap.get(requiredItem), ofcStorageTemplateMap.get(requiredItem).getReflectColName());
            }
        }

        return requiredColNum;
    }

    /**
     * 校验仓储模板文件格式是否正确,并返回当前激活的Sheet页
     * @param file 用户上传的文件
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
     * 获取用户保存的所选模板映射map
     * @param templateCode 模板编码
     * @param templateType 模板类型
     * @return List<Object> 0: key:映射列名  value:映射列名对应的保存的模板映射  1: key:映射列编码  value:映射列名对应的保存的模板映射
     */
    private List<Object> getTemplateReflect(String templateCode, String templateType) {
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
        Map<String, OfcStorageTemplate> map = new HashMap<>();
        Map<String, OfcStorageTemplate> mapForDefaultButNotRequire = new HashMap<>();
        Map<String, OfcStorageTemplate> mapForDefaultButNotRequireName = new HashMap<>();
        for (OfcStorageTemplate ofcStorageTemplate : ofcStorageTemplateListForConvert) {
            String reflectColName = ofcStorageTemplate.getReflectColName();
            if(!PubUtils.isSEmptyOrNull(reflectColName)){
                map.put(reflectColName, ofcStorageTemplate);
            }
            mapForDefaultButNotRequire.put(ofcStorageTemplate.getStandardColCode(), ofcStorageTemplate);
            mapForDefaultButNotRequireName.put(ofcStorageTemplate.getStandardColName(), ofcStorageTemplate);
        }
        List<Object> result = new ArrayList<>();
        result.add(map);
        result.add(mapForDefaultButNotRequire);
        result.add(mapForDefaultButNotRequireName);
        return result;
    }

    /**
     * 制造标准模板
     * @param ofcStorageTemplateListForConvert 模板映射List
     * @param standardColCode 标准模板列编码
     * @param standardColName 标准模板列名称
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
     * @param cellValue 单元格的值
     * @param templateDetilMap 用户模板
     * @return 映射到的实体
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

    /**
     * 仓储开单批量导单确认下单
     * @param orderList 订单List
     * @param authResDto 登录用户
     * @return 下单结果
     */
    @Override
    @Transactional
    public Wrapper orderConfirm(String orderList, AuthResDto authResDto) throws Exception{
        logger.info("orderList ==> {}", orderList);
        logger.info("authResDto ==> {}", authResDto);
        List<String> orderBatchNumberList = new ArrayList<>();
        if(PubUtils.isSEmptyOrNull(orderList) || null == authResDto){
            logger.error("仓储开单批量导单确认下单失败, orderConfirm入参有误");
            throw new BusinessException("仓储开单批量导单确认下单失败!");
        }
        TypeReference<List<OfcStorageTemplateDto>> typeReference = new TypeReference<List<OfcStorageTemplateDto>>() {
        };
        List<OfcStorageTemplateDto> ofcStorageTemplateDtoList = JacksonUtil.parseJsonWithFormat(orderList, typeReference);
        if(CollectionUtils.isEmpty(ofcStorageTemplateDtoList)){
            throw new BusinessException("仓储开单批量导单确认下单失败! 订单列表为空!");
        }
        Map<String, List<OfcStorageTemplateDto>> orderMap = new HashMap<>();
        for (OfcStorageTemplateDto ofcStorageTemplateDto : ofcStorageTemplateDtoList) {
            if(null == ofcStorageTemplateDto){
                logger.info("仓储开单批量导单确认下单, 订单信息为空! ");
                continue;
            }
            String custOrderCode = ofcStorageTemplateDto.getCustOrderCode();
            List<OfcStorageTemplateDto> orderListByCustOrderCode = orderMap.get(custOrderCode);
            if(!orderMap.containsKey(custOrderCode) && orderListByCustOrderCode == null){
                logger.info("初始化");
                orderListByCustOrderCode = new ArrayList<>();
                orderMap.put(custOrderCode, orderListByCustOrderCode);
            }
            orderListByCustOrderCode.add(ofcStorageTemplateDto);
            orderMap.put(custOrderCode, orderListByCustOrderCode);
        }

        for (String orderMapKey : orderMap.keySet()) {

            List<OfcStorageTemplateDto> order = orderMap.get(orderMapKey);
            OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
            OfcStorageTemplateDto forOrderMsg = order.get(0);
            logger.info("forOrderMsg------, {}", ToStringBuilder.reflectionToString(forOrderMsg));
            BeanUtils.copyProperties(forOrderMsg.getOfcOrderDTO(), ofcOrderDTO);
            BeanUtils.copyProperties(forOrderMsg, ofcOrderDTO, "orderTime");
            ofcOrderDTO.setOrderTime(DateUtils.String2Date(forOrderMsg.getOrderTime(), DateUtils.DateFormatType.TYPE1));
            if(!PubUtils.isSEmptyOrNull(forOrderMsg.getProvideTransport())){
                ofcOrderDTO.setProvideTransport(Integer.valueOf(forOrderMsg.getProvideTransport()));
            }
            logger.info("ofcOrderDTO------, {}", ToStringBuilder.reflectionToString(ofcOrderDTO));
            //在这里将订单信息补充完整
            String orderBatchNumber = codeGenUtils.getNewWaterCode(BATCH_PRE,4);
            orderBatchNumberList.add(orderBatchNumber);
            ofcOrderDTO.setOrderBatchNumber(orderBatchNumber);
            ofcOrderDTO.setOrderType(WAREHOUSE_DIST_ORDER);
            if(ofcOrderDTO.getProvideTransport() == null){
                ofcOrderDTO.setProvideTransport(0);
            }
//            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = new ArrayList<>();
            Map<String, OfcGoodsDetailsInfo> ofcGoodsDetailsInfoMap = new HashMap<>();
            MathContext mathContext = new MathContext(3);
            for (OfcStorageTemplateDto ofcStorageTemplateDto : order) {
                OfcGoodsDetailsInfo ofcGoodsDetailsInfo = convertCscGoods(ofcStorageTemplateDto);
                String goodsCode = ofcGoodsDetailsInfo.getGoodsCode();
                if(ofcGoodsDetailsInfoMap.containsKey(goodsCode)){
                    OfcGoodsDetailsInfo info = ofcGoodsDetailsInfoMap.get(goodsCode);
                    if(null == info.getQuantity() || null == ofcGoodsDetailsInfo.getQuantity()){
                        logger.error("货品数量出错!");
                        throw new BusinessException("货品数量出错!");
                    }
                    info.setQuantity(info.getQuantity().add(ofcGoodsDetailsInfo.getQuantity(), mathContext));
                    ofcGoodsDetailsInfoMap.put(goodsCode, info);
                }else {
                    ofcGoodsDetailsInfoMap.put(goodsCode, ofcGoodsDetailsInfo);
                }
            }
            List<OfcGoodsDetailsInfo> detailsInfos = new ArrayList<>(ofcGoodsDetailsInfoMap.values());
            ofcGoodsDetailsInfoMap.values();
            CscContantAndCompanyDto cscContantAndCompanyDto = convertCscConsignee(forOrderMsg.getCscConsigneeDto());
            convertConsigneeToDis(forOrderMsg.getCscConsigneeDto(), ofcOrderDTO);
            convertSupplierToWare(forOrderMsg.getCscSupplierInfoDto(), ofcOrderDTO);
            Wrapper save = ofcOrderManageService.saveStorageOrder(ofcOrderDTO, detailsInfos, "batchSave"
                    , null, cscContantAndCompanyDto, new CscSupplierInfoDto(), authResDto);
            if(save.getCode() == Wrapper.ERROR_CODE){
                logger.error("仓储开单批量导单确认下单失败, 错误信息:{}", save.getMessage());
                return save;
            }
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, orderBatchNumberList);
    }

    private void convertSupplierToWare(CscSupplierInfoDto cscSupplierInfoDto, OfcOrderDTO ofcOrderDTO) {
        if(null == cscSupplierInfoDto || PubUtils.isSEmptyOrNull(cscSupplierInfoDto.getSupplierCode())){
            return;
        }
        logger.info("==========> cscSupplierInfoDto:{} ", ToStringBuilder.reflectionToString(cscSupplierInfoDto));
        ofcOrderDTO.setSupportCode(cscSupplierInfoDto.getSupplierCode());
        ofcOrderDTO.setSupportName(cscSupplierInfoDto.getSupplierName());
        ofcOrderDTO.setSupportContactCode(cscSupplierInfoDto.getContactCode());
        ofcOrderDTO.setSupportContactName(cscSupplierInfoDto.getContactName());
    }

    private void convertConsigneeToDis(CscContantAndCompanyResponseDto cscConsigneeDto, OfcOrderDTO ofcOrderDTO) {
        if(null == cscConsigneeDto || PubUtils.isSEmptyOrNull(cscConsigneeDto.getContactCompanySerialNo())){
            return;
        }
        logger.info("==========> cscConsigneeDto:{} ", ToStringBuilder.reflectionToString(cscConsigneeDto));
        ofcOrderDTO.setConsigneeCode(cscConsigneeDto.getContactCompanySerialNo());
        ofcOrderDTO.setConsigneeContactCode(cscConsigneeDto.getContactSerialNo());
        ofcOrderDTO.setConsigneeName(cscConsigneeDto.getContactCompanyName());
        ofcOrderDTO.setConsigneeContactName(cscConsigneeDto.getContactName());
        ofcOrderDTO.setConsigneeType(cscConsigneeDto.getType());
        ofcOrderDTO.setConsigneeContactPhone(cscConsigneeDto.getPhone());
        ofcOrderDTO.setDestinationProvince(cscConsigneeDto.getProvinceName());
        ofcOrderDTO.setDestinationCity(cscConsigneeDto.getCityName());
        ofcOrderDTO.setDestinationDistrict(cscConsigneeDto.getAreaName());
        ofcOrderDTO.setDestinationTowns(cscConsigneeDto.getStreetName());
        ofcOrderDTO.setDestination(cscConsigneeDto.getDetailAddress());
        StringBuilder sb = new StringBuilder(cscConsigneeDto.getProvince());
        if(!PubUtils.isSEmptyOrNull(cscConsigneeDto.getCity())){
            sb.append(cscConsigneeDto.getCity());
            if(!PubUtils.isSEmptyOrNull(cscConsigneeDto.getArea())) {
                sb.append(cscConsigneeDto.getArea());
                if(!PubUtils.isSEmptyOrNull(cscConsigneeDto.getStreet())) {
                    sb.append(cscConsigneeDto.getStreet());
                }
            }
        }
        ofcOrderDTO.setDestinationCode(sb.toString());
    }

    /**
     * 转换客户中心DTO发货方
     * @param cscConsigneeDto 发货方
     * @return 转换后的发货方
     * @throws Exception 异常
     */
    private CscContantAndCompanyDto convertCscConsignee(CscContantAndCompanyResponseDto cscConsigneeDto) throws Exception{
        logger.info("转换客户中心DTO发货方 cscConsigneeDto:{}", cscConsigneeDto);
        CscContantAndCompanyDto cscContactAndCompanyDto = new CscContantAndCompanyDto();
        CscContactDto cscContactDto = new CscContactDto();
        CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
        BeanUtils.copyProperties(cscConsigneeDto, cscContactDto);
        BeanUtils.copyProperties(cscConsigneeDto, cscContactCompanyDto);
        cscContactAndCompanyDto.setCscContactDto(cscContactDto);
        cscContactAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
        logger.info("转换客户中心DTO发货方 cscContactAndCompanyDto:{}", cscContactAndCompanyDto);
        return cscContactAndCompanyDto;
    }

    /**
     * 转换客户中心货品
     * @param ofcStorageTemplateDto 订单DTO
     * @return 转换后的货品
     */
    private OfcGoodsDetailsInfo convertCscGoods(OfcStorageTemplateDto ofcStorageTemplateDto) throws Exception{
        logger.info("转换客户中心货品: ofcStorageTemplateDto.getCscGoodsApiVo():{}", ofcStorageTemplateDto.getCscGoodsApiVo());
        CscGoodsApiVo cscGoodsApiVo = ofcStorageTemplateDto.getCscGoodsApiVo();
        OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
        BeanUtils.copyProperties(cscGoodsApiVo, ofcGoodsDetailsInfo);
        ofcGoodsDetailsInfo.setId(null);
        ofcGoodsDetailsInfo.setGoodsSpec(cscGoodsApiVo.getSpecification());
        ofcGoodsDetailsInfo.setQuantity(ofcStorageTemplateDto.getQuantity());
        logger.info("转换客户中心货品 ofcGoodsDetailsInfo:{}", ofcGoodsDetailsInfo);
        return ofcGoodsDetailsInfo;
    }

    /**
     * 仓储开单批量导单审核
     * @param result 审核的结果
     * @param authResDto 当前登录用户
     * @return 批量审核的结果
     */
    @Override
    public Wrapper storageTemplateAudit(Object result, AuthResDto authResDto) {
        logger.info("仓储开单批量导单审核开始=========>result:{}", result);
        logger.info("仓储开单批量导单审核开始=========>authResDto:{}", authResDto);
        if(null == result || null == authResDto){
            logger.error("仓储开单批量导单审核失败, 入参有误");
            throw new BusinessException("仓储开单批量导单审核失败!");
        }
        List<String> orderBatchNumber = (List<String>) result;
        List<OfcFundamentalInformation> ofcFundamentalInformationList = new ArrayList<>();
        for (String batchNum : orderBatchNumber) {
            ofcFundamentalInformationList.addAll(ofcFundamentalInformationService.queryFundamentalByBatchNumber(batchNum));
        }
        logger.info("============>仓储开单批量导单需要审核的订单:{}", ofcFundamentalInformationList);
        for (OfcFundamentalInformation ofcFundamentalInformation : ofcFundamentalInformationList) {
            String orderCode = ofcFundamentalInformation.getOrderCode();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
            OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
            OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.queryByOrderCode(orderCode);
            OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.queryOrderStateByOrderCode(orderCode);
            String review;
            try {
                review = ofcOrderManageService.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetailsInfoList, ofcDistributionBasicInfo, ofcWarehouseInformation
                        , new OfcFinanceInformation(), ofcOrderStatus.getOrderStatus(), "review", authResDto);
            } catch (Exception e) {
                logger.error("仓储开单批量导单审核, 当前订单审核失败, 直接跳过该订单, 订单号: {}", orderCode);
                continue;
            }
            logger.info("仓储开单批量导单审核, 订单号:{}审核结果:{}", orderCode, review);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }

    /**
     * 后端校验模板必填
     * @param ofcStorageTemplates 用户上传的模板
     */
    @Override
    public void checkTemplateListRequired(List<OfcStorageTemplate> ofcStorageTemplates) {
        logger.info("校验模板必填 ofcStorageTemplates:{}", ofcStorageTemplates);
        if(CollectionUtils.isEmpty(ofcStorageTemplates)){
            logger.error("校验模板必填失败!");
            throw new BusinessException("校验模板必填失败!");
        }
        String templateType = ofcStorageTemplates.get(0).getTemplateType();
        boolean storageIn = StringUtils.equals(templateType, "storageIn");
        int standardNum = storageIn ? StorageImportInEnum.queryList().size() : StorageImportOutEnum.queryList().size();
        if(standardNum != ofcStorageTemplates.size()){
            logger.error("校验模板必填失败! 模板数量错误! ");
            throw new BusinessException("校验模板必填失败! 模板数量错误! ");
        }
        for (OfcStorageTemplate ofcStorageTemplate : ofcStorageTemplates) {
            int indexNum = ofcStorageTemplate.getIndexNum();
            String reflectColName = ofcStorageTemplate.getReflectColName();
            String colDefaultVal = ofcStorageTemplate.getColDefaultVal();
            if(indexNum == 1 && PubUtils.isSEmptyOrNull(reflectColName)){
                throw new BusinessException(StorageImportOutEnum.CUST_ORDER_CODE.getStandardColName() + "的模板列名不能为空!");
            }else if(indexNum == 3 && (PubUtils.isSEmptyOrNull(reflectColName) && PubUtils.isSEmptyOrNull(colDefaultVal))){
                throw new BusinessException(StorageImportOutEnum.MERCHANDISER.getStandardColName() + "的模板列名和默认值必填一个");
            }else if(indexNum == 4 && (PubUtils.isSEmptyOrNull(reflectColName) && PubUtils.isSEmptyOrNull(colDefaultVal))){
                throw new BusinessException(StorageImportOutEnum.WAREHOUSE_NAME.getStandardColName() + "的模板列名和默认值必填一个");
            }else if(indexNum == 5 && (PubUtils.isSEmptyOrNull(reflectColName) && PubUtils.isSEmptyOrNull(colDefaultVal))){
                throw new BusinessException(StorageImportOutEnum.BUSINESS_TYPE.getStandardColName() + "的模板列名和默认值必填一个");
            }else if(indexNum == 7 && PubUtils.isSEmptyOrNull(reflectColName)){
                throw new BusinessException(StorageImportOutEnum.GOODS_CODE.getStandardColName() + "的模板列名不能为空!");
            }else if(indexNum == 12 && PubUtils.isSEmptyOrNull(reflectColName)){
                throw new BusinessException(storageIn ? StorageImportInEnum.QUANTITY.getStandardColName()
                        : StorageImportOutEnum.QUANTITY.getStandardColName()  + "的模板列名不能为空!");
            }else if(indexNum == 22 && PubUtils.isSEmptyOrNull(reflectColName)){
                throw new BusinessException(StorageImportOutEnum.CONSIGNEE_NAME  + "的模板列名不能为空!");
            }
        }
        logger.info("校验模板必填成功!");
    }

}
