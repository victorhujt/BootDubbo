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
import com.xescm.csc.model.dto.packing.GoodsPackingDto;
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
import com.xescm.whc.edas.dto.ResponseMsg;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.type.TypeReference;
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
import java.text.DecimalFormat;
import java.util.*;

import static com.xescm.ofc.constant.GenCodePreffixConstant.STO_TEMP_PRE;
import static com.xescm.ofc.constant.OrderConstConstant.STR_YES;
import static com.xescm.ofc.constant.OrderPlaceTagConstant.REVIEW;
import static com.xescm.ofc.constant.StorageTemplateConstant.*;

/**
 *
 * Created by lyh on 2017/2/18.
 */
@Service
public class OfcStorageTemplateServiceImpl extends BaseService<OfcStorageTemplate> implements OfcStorageTemplateService {

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
        if (null == templateList || templateList.size() < 1) {
            logger.error("模板配置保存失败,入参为空");
            throw new BusinessException("模板配置保存失败,入参为空");
        }
        this.checkTemplateListRequired(templateList);
        OfcStorageTemplate ofcStorageTemplateForCheck = templateList.get(0);
        //校验模板必填项
        checkTemplateRequiedItem(ofcStorageTemplateForCheck);
        //校验模板名称是否重复
        Integer repeat = ofcStorageTemplateMapper.checkTemplateNameUnique(ofcStorageTemplateForCheck.getTemplateName());
        if (null != repeat && repeat > 0) {
            logger.error("校验模板名称重复!");
            throw new BusinessException("校验模板名称重复!");
        }
        String templateCode = codeGenUtils.getNewWaterCode(STO_TEMP_PRE, 6);
        String userId = authResDto.getUserId();
        String userName = authResDto.getUserName();
        Date now = new Date();
        for (OfcStorageTemplate ofcStorageTemplate : templateList) {
            if (null == ofcStorageTemplate) {
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
        if (null == templateCondition) {
            logger.error("模板配置筛选条件为空!");
            throw new BusinessException("模板配置筛选条件为空!");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList = ofcStorageTemplateMapper.selectTemplateByCondition(templateCondition);
        OfcStorageTemplate ofcStorageTemplate = new OfcStorageTemplate();
        ofcStorageTemplate.setTemplateCode(STANDARD);
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
        if (null == templateCondition) {
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
        if (PubUtils.isSEmptyOrNull(temlpateCode)) {
            logger.error("模板配置删除失败! 入参有误!");
            throw new BusinessException("模板配置删除失败! 入参有误!");
        }
        OfcStorageTemplate ofcStorageTemplate = new OfcStorageTemplate();
        ofcStorageTemplate.setTemplateCode(temlpateCode);
//        int delete = ofcStorageTemplateMapper.delete(ofcStorageTemplate);
        int delete = ofcStorageTemplateMapper.deleteByTemplateCode(temlpateCode);
        if (delete == 0) {
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
        logger.info("模板配置编辑 ==> templateList:{}", templateList);
        logger.info("模板配置编辑 ==> authResDto:{}", authResDto);
        logger.info("模板配置编辑 ==> lastTemplateType:{}", lastTemplateType);
        TypeReference<List<OfcStorageTemplate>> typeReference = new TypeReference<List<OfcStorageTemplate>>() {
        };
        List<OfcStorageTemplate> ofcStorageTemplates = JacksonUtil.parseJson(templateList, typeReference);
        //校验模板必填项
        this.checkTemplateListRequired(ofcStorageTemplates);
        String userId = authResDto.getUserId();
        String userName = authResDto.getUserName();
        Date now = new Date();
        OfcStorageTemplate ofcStorageTemplateForFix = ofcStorageTemplates.get(0);
        String currTemplateType = ofcStorageTemplateForFix.getTemplateType();
        boolean templateTypeChange = !StringUtils.equals(currTemplateType, lastTemplateType);
        //模板类型改变
        if (templateTypeChange) {
            int delete = ofcStorageTemplateMapper.deleteByTemplateCode(ofcStorageTemplateForFix.getTemplateCode());
            if (delete == 0) {
                logger.error("模板配置删除失败!");
                throw new BusinessException("模板配置删除失败");
            }
        }
        int changeNum = 0;
        for (OfcStorageTemplate ofcStorageTemp : ofcStorageTemplates) {
            ofcStorageTemp.setOperator(userId);
            ofcStorageTemp.setOperatorName(userName);
            ofcStorageTemp.setOperTime(now);
            if (templateTypeChange) {
                ofcStorageTemp.setCreator(userId);
                ofcStorageTemp.setCreatorName(userName);
                ofcStorageTemp.setCreatTime(now);
                int insert = ofcStorageTemplateMapper.insert(ofcStorageTemp);
                changeNum += insert;
            } else {
                int update = ofcStorageTemplateMapper.updateByTemplateCode(ofcStorageTemp);
                changeNum += update;
            }
        }
        if (changeNum != this.getTemplateByType(currTemplateType)) {
            logger.error("模板配置编辑失败");
            throw new BusinessException("模板配置编辑失败");
        }
        logger.info("模板配置编辑成功! changeNum:{}", changeNum);
    }

    private int getTemplateByType(String currTemplateType) {
        if (StringUtils.equals(currTemplateType, STORAGE_IN)) {
            return StorageImportInEnum.queryList().size();
        } else if (StringUtils.equals(currTemplateType, STORAGE_OUT)) {
            return StorageImportOutEnum.queryList().size();
        }
        return Integer.MAX_VALUE;
    }

    /**
     * 模板明细查询
     * @param templateCondition 筛选条件
     * @return 筛选结果
     */
    @Override
    public List<OfcStorageTemplate> selectTemplateDetail(TemplateCondition templateCondition) {
        if (null == templateCondition) {
            logger.error("模板明细查询入参有误");
            throw new BusinessException("模板明细查询入参有误!");
        }
        List<OfcStorageTemplate> ofcStorageTemplateList = ofcStorageTemplateMapper.selectTemplateDetail(templateCondition);
        if (ofcStorageTemplateList.size() < 1) {
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
        if (PubUtils.isSEmptyOrNull(ofcStorageTemplate.getTemplateType())) {
            logger.error("校验模板类型为空!");
            throw new BusinessException("校验模板类型为空!");
        } else if (PubUtils.isSEmptyOrNull(ofcStorageTemplate.getTemplateName())) {
            logger.error("校验模板名称为空!");
            throw new BusinessException("校验模板名称为空!");
        } else if (PubUtils.isSEmptyOrNull(ofcStorageTemplate.getCustCode())) {
            logger.error("校验客户编码为空!");
            throw new BusinessException("校验客户编码为空!");
        } else if (PubUtils.isSEmptyOrNull(ofcStorageTemplate.getCustName())) {
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
    public Wrapper<?> checkStorageTemplate(MultipartFile uploadFile, AuthResDto authResDto, OfcStorageTemplate ofcStorageTemplate, Integer activeSheetNum) {
        //根据模板编码和类型拿到用户保存的配置模板的映射 key是用户表头列名
        //这里是用户进行模板配置了的, 下面还有(在第一行校验表头列名的时候, 如果用户的列名能与标准列名对应上, 那么依然进行可以映射)
        List<Object> templateReflect = this.getTemplateReflect(ofcStorageTemplate.getTemplateCode(), ofcStorageTemplate.getTemplateType());
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
            logger.error("校验仓配Excel读取内部异常{}",e);
            throw new BusinessException("校验仓配Excel读取内部异常");
        }

        boolean checkPass = true;
        List<String> xlsErrorMsg = new ArrayList<>();
        Map<Integer,OfcStorageTemplate> modelNameStr = new LinkedHashMap<>();
        Class clazz = null;
        List<OfcStorageTemplateDto> ofcStorageTemplateDtoList = new ArrayList<>();
        Map<String, CscGoodsApiVo> goodsCheck = new HashMap<>();
        Map<String, CscContantAndCompanyResponseDto> consigneeCheck = new HashMap<>();
        Map<String, CscContantAndCompanyResponseDto> consignorCheck = new HashMap<>();
        Map<String, CscContantAndCompanyResponseDto> storeCodeCheck = new HashMap<>();
        Map<String, CscContantAndCompanyResponseDto> consigneeContactCodeCheck = new HashMap<>();
        Map<String, CscSupplierInfoDto> supplierCheck = new HashMap<>();
        Map<String, CscSupplierInfoDto> supplierCodeCheck = new HashMap<>();
        //去RMC查到所有仓库
        Map<String, RmcWarehouseRespDto> allWarehouseByRmc =  this.getAllWarehouseByCustCode(ofcStorageTemplate.getCustCode());
        try {
            clazz = Class.forName("com.xescm.ofc.model.dto.ofc.OfcStorageTemplateDto");
        } catch (ClassNotFoundException e) {
            logger.error("校验Excel,ClassNotFoundException:{}",e);
            throw new BusinessException("校验Excel,ClassNotFoundException");
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

                if (rowNum == 0) {
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
                    //空列
//                    if (null == commonCell && cellNum > 11) {
                    //如果必填列为空则报错提示
                    if (null == commonCell && requiedItemIndex.containsKey(cellNum)) {
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
                        if (DateUtil.isCellDateFormatted(commonCell)) {
                            cellValue = PubUtils.trimAndNullAsEmpty(DateUtils.Date2String(commonCell.getDateCellValue(), DateUtils.DateFormatType.TYPE2));
                        }else {
                            cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(commonCell.getNumericCellValue()));
                        }
                    }

                    //再去空格
                    cellValue = PubUtils.trim(cellValue);

                    //至此, 已经能拿到每一列的值
                    if (rowNum == 0) {//第一行, 将所有表格中固定的字段名称和位置固定
                        if (PubUtils.isSEmptyOrNull(cellValue)) {
                            break;
                        }
                        OfcStorageTemplate ofcStorageTemplateForReflect = cellReflectToDomain(cellValue,templateDetilMap); //标准表字段映射成对应实体的字段的值
                        if (null == ofcStorageTemplateForReflect || PubUtils.isSEmptyOrNull(ofcStorageTemplateForReflect.getStandardColCode())) {
                            continue;
                        }
                        modelNameStr.put(cellNum + 1,ofcStorageTemplateForReflect);
                        String usefulColName = cellValue;
                        String usefulColCode = ofcStorageTemplateForReflect.getStandardColCode();
                        usefulCol.add(usefulColName + "@" + usefulColCode);
                    } else if (rowNum > 0) { // 表格的数据体
                        OfcStorageTemplate ofcStorageTemplateForCheck = modelNameStr.get(cellNum + 1);
                        if (null == ofcStorageTemplateForCheck) {
                            //当前列没在模板映射里配置, 所以直接跳过
                            logger.info("当前列没在模板映射里配置, 所以直接跳过");
                            continue;
                        }
                        String standardColCode = ofcStorageTemplateForCheck.getStandardColCode();
                        String colDefaultVal = ofcStorageTemplateForCheck.getColDefaultVal();
                        if (PubUtils.isSEmptyOrNull(standardColCode)) {
                            logger.info("当前列在模板映射配置没有standardColCode, 所以直接跳过");
                            continue;
                        }
                        //客户订单号//必填列名
                        if (StringUtils.equals(StorageImportInEnum.CUST_ORDER_CODE.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有客户订单号", rowNum + 1, cellNum + 1);
//                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "缺少必填字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                checkPass = false;
                                continue;
                            }
                            //对数字过长的单元格的值进行处理
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //订单日期
                        } else if (StringUtils.equals(StorageImportInEnum.ORDER_TIME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.info("订单日期 ==> ", cellValue);
                                cellValue = DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1);
                                logger.info("订单日期 ==> ", cellValue);

                            }else {
                                //如果Excel中用户手写了订单日期, 就用用户写的日期, 并对该单元格的值进行校验
                                String[] split = cellValue.split(" ");

                                //正则还需要修正
                                if (split.length > 1 ? !(split[0].matches(REGEX_YYYYMMDD) && split[1].matches(PubUtils.REGX_TIME)) : !cellValue.matches(REGEX_YYYYMMDD)) {
                                    logger.error("当前行:{},列:{} 校验失败,不是日期类型", rowNum + 1, cellNum + 1);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ cellValue + ",不是日期类型");
                                    checkPass = false;
                                    continue;
                                }
                            }
                            logger.info("订单日期 ==> ", cellValue);
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //开单员//必填列名
                        } else if (StringUtils.equals(StorageImportInEnum.MERCHANDISER.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                if (!PubUtils.isSEmptyOrNull(colDefaultVal)) {
                                    cellValue = colDefaultVal;
                                }else {
                                    cellValue = authResDto.getUserName();
                                }
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //仓库名称//必填列名
                        } else if (StringUtils.equals(StorageImportInEnum.WAREHOUSE_NAME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                if (!PubUtils.isSEmptyOrNull(colDefaultVal)) {
                                    cellValue = colDefaultVal;
                                }else {
                                    logger.error("当前行:{},列:{} 没有仓库名称", rowNum + 1, cellNum + 1);
                                    xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                    checkPass = false;
                                    continue;
                                }
                            }
                            //如果有仓库名称, 校验该仓库名称是否存在!
                            if (!allWarehouseByRmc.containsKey(cellValue)) {
                                logger.error("当前行:{},列:{}仓库名称:{}, 调用接口未能找到该仓库", rowNum + 1, cellNum + 1, cellValue);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "仓库名称【" + cellValue + "】无效! 该客户下没有该仓库!");
                                checkPass = false;
                                continue;
                            }
                            RmcWarehouseRespDto rmcWarehouseRespDto = allWarehouseByRmc.get(cellValue);
                            ofcStorageTemplateDto.setWarehouseCode(rmcWarehouseRespDto.getWarehouseCode());
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //业务类型//必填列名
                        } else if (StringUtils.equals(StorageImportInEnum.BUSINESS_TYPE.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                if (!PubUtils.isSEmptyOrNull(colDefaultVal)) {
                                    Wrapper wrapper = checkBusinessType(colDefaultVal, ofcStorageTemplate.getTemplateType());
                                    if (wrapper.getCode() == 200) {
                                        cellValue = (String) wrapper.getResult();
                                    }else {
                                        logger.error("当前行:{},业务类型校验失败,模板配置中业务类型默认值校验失败, 请维护", rowNum + 1);
                                        xlsErrorMsg.add("行:" + (rowNum + 1) + "模板配置中业务类型默认值【" + colDefaultVal + "】校验失败！");
                                        checkPass = false;
                                        continue;
                                    }
                                }else {
                                    logger.error("当前行:{},列:{} 没有业务类型", rowNum + 1, cellNum + 1);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + ",列:" + (cellNum + 1) + " 缺少必填字段:" + ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                    checkPass = false;
                                    continue;
                                }
                            }else {
                                //如果当前单元格不空, 需要校验是否是ofc可识别的订单类型
                                logger.info("-----------cellValue{}", cellValue);
                                logger.info("-----------cellValue.split().length :{}", cellValue.split("\\.").length);
                                Wrapper result = checkBusinessType(cellValue.split("\\.").length > 1 ? cellValue.split("\\.")[0] : cellValue, ofcStorageTemplate.getTemplateType());
                                if (Wrapper.ERROR_CODE == result.getCode()) {
                                    logger.error("当前行:{},列:{} 业务类型:{}不可识别", rowNum + 1, cellNum + 1, cellValue);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + ",列:" + (cellNum + 1) + result.getMessage());
                                    checkPass = false;
                                    continue;
                                }
                                cellValue = (String) result.getResult();
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //备注
                        } else if (StringUtils.equals(StorageImportInEnum.NOTES.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有备注", rowNum + 1, cellNum + 1);
//                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "缺少必填字段:"+ ofcStorageTemplateForCheck.getReflectColName());
//                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //货品编码//必填列名
                        } else if (StringUtils.equals(StorageImportInEnum.GOODS_CODE.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有货品编码", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                checkPass = false;
                                continue;
                            }
                            //对数字过长的单元格的值进行处理
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            //货品编码不空,判断是否已经校验过
                            //如果没校验过就调用CSC接口进行校验
                            //再去空格
                            cellValue = PubUtils.trim(cellValue);

                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //货品名称
                        } else if (StringUtils.equals(StorageImportInEnum.GOODS_NAME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有货品名称", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //规格
                        } else if (StringUtils.equals(StorageImportInEnum.GOODS_SPEC.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有规格", rowNum + 1, cellNum + 1);
                                continue;
                            }

                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //单位
                        } else if (StringUtils.equals(StorageImportInEnum.UNIT.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有单位", rowNum + 1, cellNum);
                                continue;
                            }

                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //单价
                        } else if (StringUtils.equals(StorageImportInEnum.UNIT_PRICE.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有单价", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            boolean matchesPot = cellValue.matches(SIX_POT_THREE);
                            boolean matchesInt = cellValue.matches(INTEGER_SIX);
                            if (!matchesPot && !matchesInt) {
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
                                logger.error("当前行:{},列:{} 单价校验失败, 异常信息: {}", rowNum + 1, cellNum + 1, e);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getStandardColName());
                                throw new BusinessException("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getStandardColName());
                            }
                            //入库数量 or  出库数量//必填列名
                        } else if (StringUtils.equals(StorageImportInEnum.QUANTITY.getStandardColCode(), standardColCode)) {
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.info("【{}】列第{}行数据为空，请检查文件！", ofcStorageTemplateForCheck.getReflectColName(), (rowNum + 1));
                                cellValue = "0";
                            }
                            boolean matchesPot = cellValue.matches(SIX_POT_THREE);
                            boolean matchesInt = cellValue.matches(INTEGER_SIX);
                            //如果校验成功,就往结果集里堆
                            if (matchesPot || matchesInt) {
                                BigDecimal bigDecimal = new BigDecimal(cellValue);
                                Field field;
                                try {
                                    field = clazz.getDeclaredField(standardColCode);
                                    field.setAccessible(true);
                                    field.set(ofcStorageTemplateDto,bigDecimal);
                                } catch (Exception e) {
                                    logger.error("当前行:{},列:{} 入库数量 or  出库数量校验失败 异常信息: {}", rowNum + 1, cellNum + 1, e);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getStandardColName());
                                    throw new BusinessException("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ ofcStorageTemplateForCheck.getStandardColName());
                                }
                            } else {
                                checkPass = false;
                                xlsErrorMsg.add("sheet页第" + (sheetNum + 1) + "页,第" + (rowNum + 1) + "行,第" + (cellNum + 1) + "列的值【" + cellValue +"】不符合规范!该货品数量格式不正确! 最大支持3位小数!");
                            }
                            //批次号
                        } else if (StringUtils.equals(StorageImportInEnum.PRODUCTION_BATCH.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有批次号", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            cellValue = PubUtils.trim(cellValue);
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //生产日期
                        } else if (StringUtils.equals(StorageImportInEnum.PRODUCTION_TIME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有生产日期", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            //校验是不是日期类型
                            //yyyy-MM-dd || yyyy-MM-dd hh:mm:ss
                            if (checkNullOrEmpty(cellValue)) {
                                logger.error("当前行:{},列:{} 没有生产日期", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            String[] split = cellValue.split(" ");

                            //正则还需要修正
                            if (split.length > 1 ? !(split[0].matches(REGEX_YYYYMMDD) && split[1].matches(PubUtils.REGX_TIME)) : !cellValue.matches(REGEX_YYYYMMDD)) {
                                logger.error("当前行:{},列:{} 校验失败,不是日期类型", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ cellValue + ",不是日期类型");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //失效日期
                        } else if (StringUtils.equals(StorageImportInEnum.INVALID_TIME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有失效日期", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            if (checkNullOrEmpty(cellValue)) {
                                logger.error("当前行:{},列:{} 没有失效日期", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            //校验是不是日期类型
                            String[] split = cellValue.split(" ");

                            //正则还需要修正
                            if (split.length > 1 ? !(split[0].matches(REGEX_YYYYMMDD) && split[1].matches(PubUtils.REGX_TIME)) : !cellValue.matches(REGEX_YYYYMMDD)) {
                                logger.error("当前行:{},列:{} 校验失败,不是日期类型", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ cellValue + ",不是日期类型");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //供应商名称
                        } else if (StringUtils.equals(StorageImportInEnum.SUPPORT_NAME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有供应商名称", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            if (!PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getCscSupplierInfoDto().getSupplierCode())) {
                                logger.info("当前供应商名称不需进行校验, 已经校验过供应商编码!");
                                setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                                continue;
                            }
                            //对供应商名称进行校验// supplierCheck

                            //供应商名称不空,判断是否已经校验过
                            //如果没校验过就调用CSC接口进行校验
                            //再去空格
                            cellValue = PubUtils.trim(cellValue);

                            if (!supplierCheck.containsKey(cellValue)) {
                                CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
                                cscSupplierInfoDto.setSupplierName(cellValue);
                                cscSupplierInfoDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                                Wrapper<List<CscSupplierInfoDto>> listWrapper = cscSupplierEdasService.querySupplierByAttribute(cscSupplierInfoDto);

                                if (Wrapper.ERROR_CODE == listWrapper.getCode()) {
                                    logger.error("当前行:{},列:{} 供应商名称校验失败, 请维护, 错误信息:{}", rowNum + 1, cellNum + 1, listWrapper.getMessage());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + ofcStorageTemplateForCheck.getStandardColName() + "为: " + cellValue +"校验出错!");
                                    checkPass = false;
                                    continue;
                                }
                                List<CscSupplierInfoDto> result = listWrapper.getResult();
                                //没有校验通过
                                if (null == result || result.size() == 0) {
                                    logger.error("当前行:{},列:{} 供应商名称校验失败, 请维护", rowNum + 1, cellNum + 1);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "供应商名称校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "供应商名称【" + cellValue + "】无效！或当前客户下没有该供应商!");
                                    checkPass = false;
                                    continue;
                                    //校验通过
                                } else {
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
                        } else if (StringUtils.equals(StorageImportInEnum.ARRIVE_TIME.getStandardColCode(), standardColCode)
                                ||StringUtils.equals(StorageImportOutEnum.SHIPMENT_TIME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有预计入库时间", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            //校验是不是日期类型
                            String[] split = cellValue.split(" ");

                            //正则还需要修正
                            if (split.length > 1 ? !(split[0].matches(REGEX_YYYYMMDD) && split[1].matches(PubUtils.REGX_TIME)) : !cellValue.matches(REGEX_YYYYMMDD)) {
                                logger.error("当前行:{},列:{} 校验失败,不是日期类型", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "校验失败字段:"+ cellValue + ",不是日期类型");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //是否提供运输服务
                        } else if (StringUtils.equals(StorageImportInEnum.PROVIDE_TRANSPORT.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                if (!PubUtils.isSEmptyOrNull(colDefaultVal)) {
                                    cellValue =  colDefaultVal.equals("是") ? "1" : "0";
                                }else {
                                    logger.error("当前行:{},列:{} 没有是否提供运输服务, 默认为0", rowNum + 1, cellNum + 1);
                                    cellValue = "0";
                                }
                                setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                                continue;
                            }

                            //只接受:是/否
                            if (!StringUtils.equals("是", cellValue) && !StringUtils.equals("否", cellValue)) {
                                logger.error("当前行:{},列:{} 校验失败,是否提供运输服务字段只接受:是/否, 用户表中数据为:{}", rowNum + 1, cellNum + 1, cellValue);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "是否提供运输校验失败:"+ cellValue + ",只接受:是/否");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue.equals("是") ? "1" : "0", standardColCode);
                            //运输单号
                        } else if (StringUtils.equals(StorageImportInEnum.TRANS_CODE.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有运输单号", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            cellValue = PubUtils.trim(cellValue);
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //车牌号
                        } else if (StringUtils.equals(StorageImportInEnum.PLATE_NUMBER.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有车牌号", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //司机姓名
                        } else if (StringUtils.equals(StorageImportInEnum.DRIVER_NAME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有司机姓名", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //联系电话
                        } else if (StringUtils.equals(StorageImportInEnum.CONTACT_NUMBER.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有联系电话", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            //校验该电话格式是否正确
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);

                            boolean matchesPot = PubUtils.isMobileNumber(cellValue);
                            if (!matchesPot) {
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "联系电话【" + cellValue + "】格式错误！");
                                checkPass = false;
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //收货方名称//必填列名
                        } else if (StringUtils.equals(StorageImportOutEnum.CONSIGNEE_NAME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有收货方名称", rowNum + 1, cellNum);
                                xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                checkPass = false;
                                continue;
                            }
                            if (!PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getCscConsigneeDto().getContactCode())) {
                                logger.info("当前收货方名称不需进行校验, 已经校验过收货方联系人编码!");
                                setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                                continue;
                            }
                            //对收货方名称进行校验
                            //去接口查该收货方名称是否在客户中心维护
                            //再去空格
                            cellValue = PubUtils.trim(cellValue);
                            if (!consigneeCheck.containsKey(cellValue)) {
                                CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                                CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
                                CscContactDto cscContactDto = new CscContactDto();
                                cscContantAndCompanyDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                                cscContactCompanyDto.setContactCompanyName(cellValue);
                                cscContactDto.setPurpose("1");//用途为收货方
                                cscContantAndCompanyDto.setCscContactDto(cscContactDto);
                                cscContantAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
                                Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = cscContactEdasService.queryCscReceivingInfoList(cscContantAndCompanyDto);
                                if (Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()) {
                                    throw new BusinessException(queryCscCustomerResult.getMessage());
                                }
                                List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
                                //即在客户中心没有找到该收货方
                                if (null == result || result.size() == 0) {
                                    logger.error("当前行:{},列:{} 收货方名称校验失败, 请维护", rowNum + 1, cellNum);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "收货方名称校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "收货方名称【" + cellValue + "】无效！");
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
                            //追加字段: 订单波次号
                        } else if (StringUtils.equals("consignmentBatchNumber", standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有订单波次号", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //2017年3月21日 追加字段: 收货人编码(收货方联系人编码), 供应商编码
                            //收货人编码
                        } else if (StringUtils.equals(StorageImportOutEnum.CONSIGNEE_CONTACT_CODE.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有收货人编码", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            cellValue = PubUtils.trim(cellValue);
                            if (!consigneeContactCodeCheck.containsKey(cellValue)) {
                                CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                                CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
                                CscContactDto cscContactDto = new CscContactDto();
                                cscContantAndCompanyDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                                cscContactDto.setContactCode(cellValue);
                                cscContactDto.setPurpose("1");//用途为收货方
                                cscContantAndCompanyDto.setCscContactDto(cscContactDto);
                                cscContantAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
                                Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = cscContactEdasService.queryCscReceivingInfoList(cscContantAndCompanyDto);
                                if (Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()) {
                                    throw new BusinessException(queryCscCustomerResult.getMessage());
                                }
                                List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
                                //即在客户中心没有找到该收货方
                                if (null == result || result.size() == 0) {
                                    logger.error("当前行:{},列:{} 收货方联系人编码校验失败, 请维护", rowNum + 1, cellNum);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "收货方名称校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "收货人编码【" + cellValue + "】无效！");
                                    checkPass = false;
                                    continue;
                                    //找到该收货方了
                                }else {
                                    CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = result.get(0);
                                    ofcStorageTemplateDto.setCscConsigneeDto(cscContantAndCompanyResponseDto);
                                    consigneeContactCodeCheck.put(cellValue, cscContantAndCompanyResponseDto);
                                }
                            }else {
                                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = consigneeContactCodeCheck.get(cellValue);
                                ofcStorageTemplateDto.setCscConsigneeDto(cscContantAndCompanyResponseDto);
                            }

                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //供应商编码
                        } else if (StringUtils.equals(StorageImportOutEnum.SUPPORT_CODE.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有供应商编码", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            cellValue = PubUtils.trim(cellValue);

                            if (!supplierCodeCheck.containsKey(cellValue)) {
                                CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
                                cscSupplierInfoDto.setSupplierCode(cellValue);
                                cscSupplierInfoDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                                Wrapper<List<CscSupplierInfoDto>> listWrapper = cscSupplierEdasService.querySupplierByAttribute(cscSupplierInfoDto);

                                if (Wrapper.ERROR_CODE == listWrapper.getCode()) {
                                    logger.error("当前行:{},列:{} 供应商编码校验失败, 请维护, 错误信息:{}", rowNum + 1, cellNum + 1, listWrapper.getMessage());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + ofcStorageTemplateForCheck.getStandardColName() + "为: " + cellValue +"校验出错!");
                                    checkPass = false;
                                    continue;
                                }
                                List<CscSupplierInfoDto> result = listWrapper.getResult();
                                //没有校验通过
                                if (null == result || result.size() == 0) {
                                    logger.error("当前行:{},列:{} 供应商编码校验失败, 请维护", rowNum + 1, cellNum + 1);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "供应商名称校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "供应商编码【" + cellValue + "】无效！或当前客户下没有该供应商!");
                                    checkPass = false;
                                    continue;
                                    //校验通过
                                }else {
                                    logger.info("当前供应商编码:{},校验通过", cellValue);
                                    supplierCodeCheck.put(cellValue, result.get(0));
                                    ofcStorageTemplateDto.setCscSupplierInfoDto(result.get(0));
                                }
                            }else {
                                logger.info("当前供应商编码:{},已经校验过, 不用校验", cellValue);
                                //不用校验, 直接堆
                                CscSupplierInfoDto cscSupplierInfoDto = supplierCodeCheck.get(cellValue);
                                ofcStorageTemplateDto.setCscSupplierInfoDto(cscSupplierInfoDto);
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //供应商批次
                        } else if (StringUtils.equals(StorageImportOutEnum.SUPPORT_BATCH.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有供应商批次", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            cellValue = PubUtils.trim(cellValue);

                            if (!supplierCodeCheck.containsKey(cellValue)) {
                                CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
                                cscSupplierInfoDto.setSupplierName(cellValue);
                                cscSupplierInfoDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                                Wrapper<List<CscSupplierInfoDto>> listWrapper = cscSupplierEdasService.querySupplierByCompleteName(cscSupplierInfoDto);

                                if (Wrapper.ERROR_CODE == listWrapper.getCode()) {
                                    logger.error("当前行:{},列:{} 供应商批次校验失败, 请维护, 错误信息:{}", rowNum + 1, cellNum + 1, listWrapper.getMessage());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + ofcStorageTemplateForCheck.getStandardColName() + "为: " + cellValue +"校验出错!");
                                    checkPass = false;
                                    continue;
                                }
                                List<CscSupplierInfoDto> result = listWrapper.getResult();
                                //没有校验通过
                                if (null == result || result.size() == 0) {
                                    logger.error("当前行:{},列:{} 供应商批次校验失败, 请维护", rowNum + 1, cellNum + 1);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "供应商名称校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "供应商批次【" + cellValue + "】无效！或当前客户下没有该供应商批次!");
                                    checkPass = false;
                                    continue;
                                    //校验通过
                                }else {
                                    logger.info("当前供应商批次:{},校验通过", cellValue);
                                    supplierCodeCheck.put(cellValue, result.get(0));
                                    ofcStorageTemplateDto.setGoodsSupplier(result.get(0));
                                }
                            }else {
                                logger.info("当前供应商批次:{},已经校验过, 不用校验", cellValue);
                                //不用校验, 直接堆
                                CscSupplierInfoDto goodsSupplier = supplierCodeCheck.get(cellValue);
                                ofcStorageTemplateDto.setGoodsSupplier(goodsSupplier);
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //发货方名称
                        } else if (StringUtils.equals(StorageImportInEnum.CONSIGNOR_NAME.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有发货方名称", rowNum + 1, cellNum + 1);
                                continue;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //门店编码
                        } else if (StringUtils.equals(StorageImportOutEnum.STORE_CODE.getStandardColCode(), standardColCode)) {
                            if (Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                                logger.error("当前行:{},列:{} 没有门店编码", rowNum + 1, cellNum);
                                xlsErrorMsg.add("【" + ofcStorageTemplateForCheck.getReflectColName() + "】列第" + (rowNum + 1) + "行数据不能为空，请检查文件！");
                                checkPass = false;
                                continue;
                            }
                            cellValue = this.resolveTooLangNum(cellValue, commonCell);
                            cellValue = PubUtils.trim(cellValue);
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                        }
                    }
                }
                if (rowNum > 0 && checkPass) {
                    //补齐
                    ofcStorageTemplateDto.getOfcOrderDTO().setCustCode(ofcStorageTemplate.getCustCode());
                    ofcStorageTemplateDto.getOfcOrderDTO().setCustName(ofcStorageTemplate.getCustName());
                    //若文件中不存在此列，则所有订单默认为当前日期.
                    Date now = new Date();
                    if (PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getOrderTime())) {
                        ofcStorageTemplateDto.getOfcOrderDTO().setOrderTime(now);
                        ofcStorageTemplateDto.setOrderTime(DateUtils.Date2String(now, DateUtils.DateFormatType.TYPE1));
                    }
                    //若文件中不存在此列，则所有订单默认为当前操作员名称。
                    if (PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getMerchandiser())) {
                        ofcStorageTemplateDto.setMerchandiser(authResDto.getUserName());
                    }
                    //若文件中不存在此列，则仓库名称列报错
                    if (PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getWarehouseName())) {
//                        logger.error("当前行:{},仓库名称校验失败,仓库名称是空的, 请维护", rowNum + 1);
//                        xlsErrorMsg.add("行:" + (rowNum + 1) + "仓库名称为空！");
//                        checkPass = false;
                        OfcStorageTemplate forWarehouseName = forDefaultButNotRequired.get("warehouseName");
                        if (PubUtils.isSEmptyOrNull(forWarehouseName.getColDefaultVal())) {
                            logger.error("当前行:{},仓库名称校验失败,模板配置中仓库名称列默认值是空的, 请维护", rowNum + 1);
                            xlsErrorMsg.add("行:" + (rowNum + 1) + "模板配置中仓库名称默认值为空！");
                            checkPass = false;
                            continue;
                        }
                        if (allWarehouseByRmc.containsKey(forWarehouseName.getColDefaultVal())) {
                            RmcWarehouseRespDto rmcWarehouseRespDto = allWarehouseByRmc.get(forWarehouseName.getColDefaultVal());
                            ofcStorageTemplateDto.setWarehouseName(rmcWarehouseRespDto.getWarehouseName());
                            ofcStorageTemplateDto.setWarehouseCode(rmcWarehouseRespDto.getWarehouseCode());
                        }else {
                            logger.error("当前行:{},仓库名称校验失败,模板配置中仓库名称默认值{}校验失败, 请维护", rowNum + 1, forWarehouseName.getColDefaultVal());
                            xlsErrorMsg.add("行" + (rowNum + 1) + "模板配置中仓库名称默认值【" + forWarehouseName.getColDefaultVal() + "】校验失败！该客户下没有该仓库!");
                            checkPass = false;
                            continue;
                        }
                    }
                    //若文件中不存在此列，则业务类型列报错
                    if (PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getBusinessType())) {
                        OfcStorageTemplate forBusinessType = forDefaultButNotRequired.get("businessType");
                        if (PubUtils.isSEmptyOrNull(forBusinessType.getColDefaultVal())) {
                            logger.error("当前行:{},模板配置中业务类型默认值校验失败,业务类型是空的, 请维护", rowNum + 1);
                            xlsErrorMsg.add("行:" + (rowNum + 1) + "模板配置中业务类型默认值为空！");
                            checkPass = false;
                            continue;
                        }
                        Wrapper wrapper = checkBusinessType(forBusinessType.getColDefaultVal(), ofcStorageTemplate.getTemplateType());
                        if (wrapper.getCode() == 200) {
                            ofcStorageTemplateDto.setBusinessType((String) wrapper.getResult());
                        }else {
                            logger.error("当前行:{},业务类型校验失败,模板配置中业务类型默认值校验失败, 请维护", rowNum + 1);
                            xlsErrorMsg.add("行:" + (rowNum + 1) + "模板配置中业务类型默认值【" + forBusinessType.getColDefaultVal() + "】校验失败！");
                            checkPass = false;
                            continue;
                        }
                    }
                    //如果Excel中没有是否提供运输这一列, 则默认设置为用户默认的, 如果没有默认的就置为否(即0)
                    if (PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getProvideTransport())) {
                        //拿着provideTransport找到默认值
                        OfcStorageTemplate forDefault = forDefaultButNotRequired.get("provideTransport");
                        String provideTrans = null;
                        if (null != forDefault) {
                            provideTrans = forDefault.getColDefaultVal();
                        }
                        ofcStorageTemplateDto.setProvideTransport(PubUtils.isSEmptyOrNull(provideTrans) ? "0" : StringUtils.equals(provideTrans, "是") ? "1" : "0");
                    }

                    //对入库订单的发货方信息进行处理
                    if (StringUtils.equals(ofcStorageTemplate.getTemplateType(), STORAGE_IN) && !this.dealConsignorName(ofcStorageTemplate, ofcStorageTemplateDto, consignorCheck, xlsErrorMsg, rowNum)) {
                        checkPass = false;
                        continue;
                    }
                    //处理货品编码
                    checkPass = this.dealGoodsCode(ofcStorageTemplateDto, ofcStorageTemplate, xlsErrorMsg, rowNum, goodsCheck, checkPass);
                    if (!checkPass) {
                        continue;
                    }
                    //对单位进行处理(对应货品的包装)
                    if (!PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getUnit()) && !this.dealGoodsUnit(ofcStorageTemplate, ofcStorageTemplateDto, xlsErrorMsg, rowNum)) {
                        checkPass = false;
                        continue;
                    } else if (PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getUnit())) {
                        GoodsPackingDto goodsPackingDto = ofcStorageTemplateDto.getGoodsPackingDto();
                        if (null == goodsPackingDto) {
                            goodsPackingDto = new GoodsPackingDto();
                            goodsPackingDto.setLevel("主单位");
                            goodsPackingDto.setLevelName("EA");
                            BigDecimal bigDecimal = new BigDecimal(1);
                            goodsPackingDto.setLevelSpecification(bigDecimal);
                            goodsPackingDto.setLevelDescription("EA");
                            ofcStorageTemplateDto.setUnit("EA");
                            ofcStorageTemplateDto.setGoodsPackingDto(goodsPackingDto);
                        }
                        ofcStorageTemplateDto.setMainUnitNum(ofcStorageTemplateDto.getQuantity());
                    }
                    this.countWeight(ofcStorageTemplateDto);
                    ofcStorageTemplateDtoList.add(ofcStorageTemplateDto);
                }
            }
        }


        //基本校验通过后检查客户订单编号是否重复
        Set<String> custOrderCodeSet = new HashSet<>();
        BigDecimal countImportNum = new BigDecimal(0);
        Map<String, Set<String>> custOrderCodeTransCode = new HashMap<>(); //key:custOrderCode, value: transCode
        Map<String, Set<String>> transCodeCustOrderCode = new HashMap<>(); //key:transCode, value: custOrderCode
        int rowCount = 0;
        for (OfcStorageTemplateDto ofcStorageTemplateDto : ofcStorageTemplateDtoList) {
            rowCount ++;
            countImportNum = countImportNum.add(ofcStorageTemplateDto.getQuantity());
            //处理门店编码
            if (checkPass && !this.dealStoreCode(ofcStorageTemplate, ofcStorageTemplateDto, storeCodeCheck, xlsErrorMsg, rowCount)) {
                checkPass = false;
                continue;
            }
            String custOrderCode = ofcStorageTemplateDto.getCustOrderCode();
            custOrderCodeSet.add(custOrderCode);
            String transCode = ofcStorageTemplateDto.getTransCode();
            if (StringUtils.equals(ofcStorageTemplateDto.getProvideTransport(), "0") || PubUtils.isSEmptyOrNull(transCode)) {
                continue;
            }
            Set<String> strings = custOrderCodeTransCode.get(custOrderCode);
            if (CollectionUtils.isEmpty(strings)) {
                Set<String> newSet = new HashSet<>();
                newSet.add(transCode);
                custOrderCodeTransCode.put(custOrderCode, newSet);
            } else if (CollectionUtils.isNotEmpty(strings) && !strings.contains(transCode)) {
                strings.add(transCode);
            }

            Set<String> stringsSec = transCodeCustOrderCode.get(transCode);
            if (CollectionUtils.isEmpty(stringsSec)) {
                Set<String> newSet = new HashSet<>();
                newSet.add(custOrderCode);
                transCodeCustOrderCode.put(transCode, newSet);
            } else if (CollectionUtils.isNotEmpty(stringsSec) && !stringsSec.contains(custOrderCode)) {
                stringsSec.add(custOrderCode);
            }
        }

        for (String custOrderCode : custOrderCodeTransCode.keySet()) {
            Set<String> transCodeSetForCheck = custOrderCodeTransCode.get(custOrderCode);
            if (transCodeSetForCheck.size() > 1) {
                logger.error("客户订单号【{}】对应了多个运输单号:{}", custOrderCode, transCodeSetForCheck.toString());
                xlsErrorMsg.add("客户订单号【" + custOrderCode + "】对应了多个运输单号" + transCodeSetForCheck.toString() + "，无法导入，请检查处理!");
                checkPass = false;
            }
        }

        for (String transCode : transCodeCustOrderCode.keySet()) {
            Set<String> custOrderCodeSetForCheck = transCodeCustOrderCode.get(transCode);
            if (custOrderCodeSetForCheck.size() > 1) {
                logger.error("客户订单号【{}】对应了同一个运输单号:{}", custOrderCodeSetForCheck.toString(), transCode);
                xlsErrorMsg.add("客户订单号" + custOrderCodeSetForCheck.toString() + "的运输单号【" + transCode + "】重复，无法导入，请检查处理!");
                checkPass = false;
            }

        }


        Integer importOrderNum = custOrderCodeSet.size();
        for (String custOrderCode : custOrderCodeSet) {
            OfcFundamentalInformation ofc = new OfcFundamentalInformation();
            ofc.setCustCode(ofcStorageTemplate.getCustCode());
            ofc.setCustOrderCode(custOrderCode);
            int repeat = ofcFundamentalInformationService.checkCustOrderCode(ofc);
            if (repeat > 0) {
                logger.error("客户订单号【{}】已经在系统中存在，无法重复导入，请检查处理!", custOrderCode);
                xlsErrorMsg.add("客户订单号【" + custOrderCode + "】已经在系统中存在，无法重复导入，请检查处理!");
                checkPass = false;
            }
        }

        //如果校验出错
        if (!checkPass) {
            logger.error("当前Excel校验出错!");
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "当前Excel校验出错", xlsErrorMsg);
        }

        logger.info("当前Excel校验成功!");
        //校验成功!
        //ofcStorageTemplateDtoList 用来下单用的
        //然后堆一个
        List<Object> succeedResult = new ArrayList<>();
        //增加货品单位和包装代码列
        this.dealUsefulCol(usefulCol);
        succeedResult.add(usefulCol);
        succeedResult.add(ofcStorageTemplateDtoList);
        succeedResult.add(countImportNum);
        succeedResult.add(importOrderNum);
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, succeedResult);
    }

    private void countWeight(OfcStorageTemplateDto ofcStorageTemplateDto) {
        BigDecimal weight = new BigDecimal(0);
        CscGoodsApiVo cscGoodsApiVo = ofcStorageTemplateDto.getCscGoodsApiVo();
        BigDecimal mainUnitNum = ofcStorageTemplateDto.getMainUnitNum();
        String unitWeightStr = cscGoodsApiVo.getWeight();
        BigDecimal unitWeight = PubUtils.isSEmptyOrNull(unitWeightStr) ? new BigDecimal(0) : new BigDecimal(unitWeightStr);
        if (null != unitWeight && unitWeight.compareTo(weight) != 0
                && mainUnitNum != null && mainUnitNum.compareTo(weight) != 0) {
            weight = mainUnitNum.multiply(unitWeight);
        }
        ofcStorageTemplateDto.setWeight(weight);
    }


    private boolean dealGoodsCode(OfcStorageTemplateDto ofcStorageTemplateDto
            , OfcStorageTemplate ofcStorageTemplate, List<String> xlsErrorMsg, Integer rowNum, Map<String, CscGoodsApiVo> goodsCheck, boolean checkPass) {
        String goodsCode = ofcStorageTemplateDto.getGoodsCode();
        if (!goodsCheck.containsKey(goodsCode)) {
            CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
            cscGoodsApiDto.setGoodsCode(goodsCode);
            cscGoodsApiDto.setCustomerCode(ofcStorageTemplate.getCustCode());
            String warehouseCode = ofcStorageTemplateDto.getWarehouseCode();
            if (PubUtils.isSEmptyOrNull(warehouseCode)) {
                logger.error("当前行:{},列:{} 货品编码校验失败, 仓库列需在货品编码列之前", rowNum + 1);
                xlsErrorMsg.add("行:" + (rowNum + 1) + "【货品编码】为：【" + goodsCode +"】校验出错！请尝试添加【仓库名称】列或将仓库列移动到货品编码列之前!");
                checkPass = false;
            }
            cscGoodsApiDto.setWarehouseCode(warehouseCode);
            Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = cscGoodsEdasService.queryCscGoodsListFullMatch(cscGoodsApiDto);
            logger.info("查询货品接口返回日志 == > queryCscGoodsList:{}", queryCscGoodsList);
            if (Wrapper.ERROR_CODE == queryCscGoodsList.getCode()) {
                logger.error("当前行:{},货品编码校验失败, 请维护, 错误信息:{}, {}", rowNum + 1, queryCscGoodsList.getMessage(), queryCscGoodsList.getResult());
                xlsErrorMsg.add("行:" + (rowNum + 1) + "【货品编码】为：【" + goodsCode +"】校验出错！");
                checkPass = false;
            }
            List<CscGoodsApiVo> cscGoodsApiVoResult = queryCscGoodsList.getResult();
            //没有校验通过
            if (null == cscGoodsApiVoResult || cscGoodsApiVoResult.size() == 0) {
                logger.error("当前行:{},货品编码校验失败, 请维护", rowNum + 1);
//                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "货品编码校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                xlsErrorMsg.add("行:" + (rowNum + 1) + "货品编码【" + goodsCode + "】货品档案信息不存在！");
                checkPass = false;
                //校验通过
            }else {
                logger.info("当前货品编码:{},校验通过", goodsCode);
                goodsCheck.put(goodsCode, cscGoodsApiVoResult.get(0));
                ofcStorageTemplateDto.setCscGoodsApiVo(cscGoodsApiVoResult.get(0));
            }
        }else {
            logger.info("当前货品编码:{},不用校验", goodsCode);
            //不用校验, 直接堆
            CscGoodsApiVo cscGoodsApiVo = goodsCheck.get(goodsCode);
            ofcStorageTemplateDto.setCscGoodsApiVo(cscGoodsApiVo);
        }
        return checkPass;
    }

    private void dealUsefulCol(List<String> usefulCol) {
        int unitColNum = -1;
        boolean hasUnit = false;
        for (String col : usefulCol) {
            String colCode = col.split("@")[1];
            if (StringUtils.equals(colCode, StorageImportOutEnum.UNIT.getStandardColCode())) {
                hasUnit = true;
                unitColNum = usefulCol.indexOf(col);
            }
        }

        if (unitColNum == -1) {
            //说明不存在单位列, 报错
            logger.error("当前模板不存在单位列");
        }
        //包装代码列
        String packageId = PACKAGE_ID_NAME + "@" + PACKAGE_ID_CODE;
        //主单位数量列
        String mainUnitNum = MAIN_UNIT_NUM_NAME + "@" + MAIN_UNIT_NUM_CODE;
        if (hasUnit) usefulCol.add(unitColNum, packageId);
        usefulCol.add(mainUnitNum);
    }

    private boolean dealGoodsUnit(OfcStorageTemplate ofcStorageTemplate, OfcStorageTemplateDto ofcStorageTemplateDto, List<String> xlsErrorMsg, int rowNum) {
        logger.info("开始处理货品单位 ==> ofcStorageTemplate:{}", ofcStorageTemplate);
        logger.info("开始处理货品单位 ==> ofcStorageTemplateDto:{}", ofcStorageTemplateDto);
        logger.info("开始处理货品单位 ==> xlsErrorMsg:{}", xlsErrorMsg);
        logger.info("开始处理货品单位 ==> rowNum:{}", rowNum);
        String unit = ofcStorageTemplateDto.getUnit();
        if (PubUtils.isSEmptyOrNull(unit)) {
            logger.error("货品【{}】的单位为空", ofcStorageTemplateDto.getGoodsCode());
            return true;
        }
        //调用接口
        CscGoodsApiVo cscGoodsApiVo = ofcStorageTemplateDto.getCscGoodsApiVo();
        if (null == cscGoodsApiVo) {
            logger.error("行:{}货品单位【{}】对应的货品信息为空！", (rowNum + 1), unit);
            xlsErrorMsg.add("行:" + (rowNum + 1) + "货品单位【" + unit + "】对应的货品信息为空！");
            return false;
        }
        List<GoodsPackingDto> goodsPackingDtoList = cscGoodsApiVo.getGoodsPackingDtoList();
        if (CollectionUtils.isEmpty(goodsPackingDtoList)) {
            logger.error("行:{}货品【{}】没有包装信息!", (rowNum + 1), cscGoodsApiVo.getGoodsCode());
            xlsErrorMsg.add("行:" + (rowNum + 1) + "货品【" + cscGoodsApiVo.getGoodsCode() + "】没有包装信息!");
            return false;
        }
        for (GoodsPackingDto goodsPackingDto : goodsPackingDtoList) {
            BigDecimal levelSpecification = goodsPackingDto.getLevelSpecification();
            if (StringUtils.equals(goodsPackingDto.getLevelDescription(), unit) && 0 != levelSpecification.compareTo(new BigDecimal(0))) {
                ofcStorageTemplateDto.setGoodsPackingDto(goodsPackingDto);
                ofcStorageTemplateDto.setPackageId(cscGoodsApiVo.getPackingCode());
                //主单位数量计算
                BigDecimal quantity = ofcStorageTemplateDto.getQuantity();
                BigDecimal mainUnitNum = quantity.multiply(levelSpecification);
                ofcStorageTemplateDto.setMainUnitNum(mainUnitNum);
                break;
            }
        }
        if (null == ofcStorageTemplateDto.getGoodsPackingDto()) {
            logger.error("行:{}货品单位【{}】选择错误，请重新选择！", (rowNum + 1), unit);
            xlsErrorMsg.add("行:" + (rowNum + 1) + "货品单位【" + unit + "】选择错误，请重新选择！");
            return false;
        }
        return true;
    }

    private boolean dealStoreCode(OfcStorageTemplate ofcStorageTemplate, OfcStorageTemplateDto ofcStorageTemplateDto, Map<String, CscContantAndCompanyResponseDto> storeCodeCheck, List<String> xlsErrorMsg, int rowCount) {
        if (!StringUtils.equals(ofcStorageTemplate.getTemplateType(), STORAGE_OUT) || PubUtils.isSEmptyOrNull(ofcStorageTemplateDto.getStoreCode())) {
            logger.info("没有门店编码");
            return true;
        }
        String storeCodeForCheck = ofcStorageTemplateDto.getStoreCode();
        if (!storeCodeCheck.containsKey(storeCodeForCheck)) {
            CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
            CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
            CscContactDto cscContactDto = new CscContactDto();
            cscContantAndCompanyDto.setCustomerCode(ofcStorageTemplate.getCustCode());
            cscContantAndCompanyDto.setStoreCode(storeCodeForCheck);
            cscContactDto.setPurpose("1");//用途为收货方
            cscContantAndCompanyDto.setCscContactDto(cscContactDto);
            cscContantAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
            Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = cscContactEdasService.queryCscReceivingInfoList(cscContantAndCompanyDto);
            if (Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()) {
                throw new BusinessException(queryCscCustomerResult.getMessage());
            }
            List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
            //即在客户中心没有找到该收货方
            if (null == result || result.size() == 0) {
                logger.error("当前行:{}门店编码校验失败, 请维护", rowCount + 1);
                xlsErrorMsg.add("行:" + (rowCount + 1) + "门店编码【" + storeCodeForCheck + "】无效！");
                return false;
                //找到该收货方了
            }else {
                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = result.get(0);
                ofcStorageTemplateDto.setCscConsigneeDto(cscContantAndCompanyResponseDto);
                storeCodeCheck.put(storeCodeForCheck, cscContantAndCompanyResponseDto);
            }
        } else {
            CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = storeCodeCheck.get(storeCodeForCheck);
            ofcStorageTemplateDto.setCscConsigneeDto(cscContantAndCompanyResponseDto);
        }

        logger.info("门店编码{}校验成功!", storeCodeForCheck);
        return true;
    }

    private boolean dealConsignorName(OfcStorageTemplate ofcStorageTemplate, OfcStorageTemplateDto ofcStorageTemplateDto,
            Map<String, CscContantAndCompanyResponseDto> consignorCheck, List<String> xlsErrorMsg, int rowNum) {
        logger.info("处理发货方 ==> ofcStorageTemplate:{}", ofcStorageTemplate);
        logger.info("处理发货方 ==> ofcStorageTemplateDto:{}", ofcStorageTemplateDto);
        logger.info("处理发货方 ==> consignorCheck:{}", consignorCheck);
        logger.info("处理发货方 ==> rowNum:{}", rowNum);
        if (!StringUtils.equals(ofcStorageTemplate.getTemplateType(), STORAGE_IN)) {
            return true;
        }
        //提供运输时,发货人必填且需校验,
        if (StringUtils.equals(ofcStorageTemplateDto.getProvideTransport(), STR_YES)) {
            String consignorName = ofcStorageTemplateDto.getConsignorName();
            if (PubUtils.isSEmptyOrNull(consignorName)) {
                logger.error("当前行:{},提供运输时,发货方名称必填, 校验出错, 请维护", rowNum + 1);
                xlsErrorMsg.add("行:" + (rowNum + 1) + "校验失败！提供运输时,发货方名称必填！");
                return false;
            }
            if (!consignorCheck.containsKey(consignorName)) {
                CscContantAndCompanyDto cscContantAndCompanyDto = new CscContantAndCompanyDto();
                CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
                CscContactDto cscContactDto = new CscContactDto();
                cscContantAndCompanyDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                cscContactCompanyDto.setContactCompanyName(consignorName);
                cscContactDto.setPurpose("2");//用途为发货方
                cscContantAndCompanyDto.setCscContactDto(cscContactDto);
                cscContantAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
                Wrapper<List<CscContantAndCompanyResponseDto>> queryCscCustomerResult = cscContactEdasService.queryCscReceivingInfoList(cscContantAndCompanyDto);
                if (Wrapper.ERROR_CODE == queryCscCustomerResult.getCode()) {
                    logger.error("当前行:{},发货方名称校验失败, 请维护", rowNum + 1);
                    throw new BusinessException(queryCscCustomerResult.getMessage());
                }
                List<CscContantAndCompanyResponseDto> result = queryCscCustomerResult.getResult();
                //即在客户中心没有找到该收货方
                if (null == result || result.size() == 0) {
                    logger.error("当前行:{},发货方名称校验失败, 请维护", rowNum + 1);
                    xlsErrorMsg.add("行:" + (rowNum + 1)  + "发货方名称【" + consignorName + "】无效！");
                    return false;
                    //找到该收货方了
                }else {
                    CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = result.get(0);
                    ofcStorageTemplateDto.setConsignor(cscContantAndCompanyResponseDto);
                    consignorCheck.put(consignorName, cscContantAndCompanyResponseDto);
                }
            }else {
                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = consignorCheck.get(consignorName);
                ofcStorageTemplateDto.setConsignor(cscContantAndCompanyResponseDto);
            }
        }
        return true;
    }

    private boolean checkNullOrEmpty(String cellValue) {
        return PubUtils.isSEmptyOrNull(PubUtils.trimAndNullAsEmpty(cellValue));
    }

    /**
     * 处理过长的数字
     * @param cellValue 单元格值
     * @param commonCell 单元格
     * @return 处理结果
     */
    @Override
    public String resolveTooLangNum(String cellValue, Cell commonCell) {
        String cellValuePhone;
        if (commonCell != null && Cell.CELL_TYPE_STRING == commonCell.getCellType()) {
            cellValuePhone = PubUtils.trimAndNullAsEmpty(commonCell.getStringCellValue());
            cellValue = cellValuePhone;
        } else if (commonCell != null && Cell.CELL_TYPE_NUMERIC == commonCell.getCellType()) {
            cellValuePhone = PubUtils.trimAndNullAsEmpty(String.valueOf(commonCell.getNumericCellValue()));
            DecimalFormat df = new DecimalFormat("0");
            cellValue = df.format(Double.valueOf(cellValuePhone));
        }
        return cellValue;
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
            if (!templateDetilMap.containsKey(commonCellValue) && forDefaultButNotRequiredName.containsKey(commonCellValue)) {
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
        if (StringUtils.equals(templateType, STORAGE_IN)) {
            List<String> codeList = BusinessTypeStorageInEnum.getCodeList();
            List<String> descList = BusinessTypeStorageInEnum.getDescList();
            if (!codeList.contains(cellValue) && !descList.contains(cellValue)) {
                check = false;
            } else if (descList.contains(cellValue)) {
                cellValue = BusinessTypeStorageInEnum.getBusinessCodeByTypeDesc(cellValue);
            }
            //出库单
        } else if (StringUtils.equals(templateType, STORAGE_OUT)) {
            List<String> codeList = BusinessTypeStorageOutEnum.getCodeList();
            List<String> descList = BusinessTypeStorageOutEnum.getDescList();
            if (!codeList.contains(cellValue) && !descList.contains(cellValue)) {
                check = false;
            } else if (descList.contains(cellValue)) {
                cellValue = BusinessTypeStorageOutEnum.getBusinessCodeByTypeDesc(cellValue);
            }
        }
        if (!check) {
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "该业务类型【" + cellValue + "】无法识别或不是"
                    + (StringUtils.equals(templateType, STORAGE_IN) ? "入库" : "出库") + "类型的!");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, cellValue);
    }


    /**
     * @return key : 仓库名称, value : 仓库信息
     * @param custCode 客户编码
     */
    private Map<String,RmcWarehouseRespDto> getAllWarehouseByCustCode(String custCode) {
        if (PubUtils.isSEmptyOrNull(custCode)) {
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
    public List<RmcWarehouseRespDto> allWarehouseByRmc() {
        Wrapper<List<RmcWarehouseRespDto>> listWrapper = rmcWarehouseEdasService.queryWarehouseList(new RmcWareHouseQO());
        if (listWrapper.getCode() == Wrapper.ERROR_CODE || CollectionUtils.isEmpty(listWrapper.getResult())) {
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
            if (null == cellValue) {
                return;
            }
            Field field = clazz.getDeclaredField(cellNumCode);
            field.setAccessible(true);
            field.set(ofcStorageTemplateDto,cellValue);
        } catch (Exception e) {
            if (e instanceof NoSuchFieldException) {
                logger.error("校验模板出错: NoSuchFieldException");
            } else if (e instanceof IllegalAccessException) {
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
        boolean storageIn = StringUtils.equals(templateType,STORAGE_IN);
        List<String> item = storageIn ? inRquiredItems : outRquiredItems;
        List<String> check = new ArrayList<>();
        check.addAll(item);
        Map<String, Integer> colNumMap = new HashMap<>();
        if (null == commonRow) {
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
            if (null == ofcStorageTemplate) {
                // 当前模板中的列名, 没有找到在初始化的Map的映射表中找到映射
                logger.info("当前模板中的列名:{}, 没有找到在初始化的Map的映射表中找到映射, 说明此列不在存储的模板中", commonCellValue);
                continue;
            }
            String standardColCode = ofcStorageTemplate.getStandardColCode();
            String reflectColName = ofcStorageTemplate.getReflectColName();
            if (!storageIn && StringUtils.equals(StorageImportOutEnum.STORE_CODE.getStandardColCode(), standardColCode)) {
                check.add(standardColCode);
                item.add(standardColCode);
            }
            if (!storageIn && StringUtils.equals(StorageImportOutEnum.STORE_CODE.getStandardColCode(), standardColCode)
                    && !PubUtils.isSEmptyOrNull(reflectColName)) {
                String consigneeName = StorageImportOutEnum.CONSIGNEE_NAME.getStandardColCode();
                check.remove(consigneeName);
                item.remove(consigneeName);
            }
            //2017年5月3日 出库增加逻辑, 有门店编码时, 则收货方非必填
            //如果能找到
            int index = check.indexOf(standardColCode);
            if (index != -1) {
                check.remove(index);
            }
            ofcStorageTemplateMap.put(standardColCode, ofcStorageTemplate);//key : 标准列code, value: 对应映射实体
            colNumMap.put(standardColCode, cellNum); //key : 标准列code, value: 必填列号
        }

        if (check.size() >0) {
            logger.error("必填列有缺失! 缺失的字段List:{}", check.toArray());
            StringBuilder sb = new StringBuilder("【");
            for (String s : check) {
                if (!PubUtils.isSEmptyOrNull(s)) {
                    sb.append(OutRquiredItem.getAnyByStandardCode(s).getStandardColName());
                    sb.append(" ");
                }
            }
            sb.append("】");
            if (check.size() == item.size()) {
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
            if (!ofcStorageTemplateMap.containsKey(requiredItem)) {
                if (StringUtils.equals(STORAGE_IN, templateType)) {
                    InRquiredItem anyByStandardCode = InRquiredItem.getAnyByStandardCode(requiredItem);
                    logger.error("没有在初始化的Map的映射表中找到必填映射列:{},{}", requiredItem, anyByStandardCode.getStandardColName());
                    throw new BusinessException("没有在初始化映射表中找到必填映射列:" + anyByStandardCode.getStandardColName());
                } else if (StringUtils.equals(STORAGE_OUT, templateType)) {
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
        if (-1 == potIndex) {
            throw new BusinessException("该文件没有扩展名!");
        }
        String suffix = fileName.substring(potIndex, fileName.length());
        if (!StringUtils.equals(suffix,"xls") && !StringUtils.equals(suffix,"xlsx")) {
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
        if (StringUtils.equals(STANDARD,templateCode)) {
            if (StringUtils.equals(STORAGE_IN, templateType)) {
                List<StorageImportInEnum> storageImportInEnums = StorageImportInEnum.queryList();
                for (StorageImportInEnum storageImportInEnum : storageImportInEnums) {
                    insertStandardTemplate(ofcStorageTemplateListForConvert, storageImportInEnum.getStandardColCode(), storageImportInEnum.getStandardColName());
                }
            } else if (StringUtils.equals(STORAGE_OUT,templateType)) {
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
            if (!PubUtils.isSEmptyOrNull(reflectColName)) {
                map.put(reflectColName, ofcStorageTemplate);
            }
            mapForDefaultButNotRequire.put(ofcStorageTemplate.getStandardColCode(), ofcStorageTemplate);
            mapForDefaultButNotRequireName.put(ofcStorageTemplate.getStandardColName(), ofcStorageTemplate);
        }
        if (StringUtils.equals(STORAGE_OUT, templateType)) {
            //增加特殊字段:发货波次号 consignmentBatchNumber
            OfcStorageTemplate forCBN = new OfcStorageTemplate();
            forCBN.setStandardColCode("consignmentBatchNumber");
            forCBN.setStandardColName("发货波次号");
            forCBN.setReflectColName("订单文件名");
            map.put(forCBN.getReflectColName(), forCBN);
            mapForDefaultButNotRequire.put(forCBN.getStandardColCode(), forCBN);
            mapForDefaultButNotRequireName.put(forCBN.getStandardColName(), forCBN);
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
        if (null == ofcStorageTemplate) {
            return null;
        }
        if (PubUtils.isSEmptyOrNull(ofcStorageTemplate.getStandardColCode())) {
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
    public Wrapper orderConfirm(String orderList, AuthResDto authResDto) throws Exception{
        logger.info("仓储开单批量导单确认下单orderList ==> {}", orderList);
        logger.info("仓储开单批量导单确认下单authResDto ==> {}", authResDto);
//        List<String> orderBatchNumberList = new ArrayList<>();
        if (PubUtils.isSEmptyOrNull(orderList) || null == authResDto) {
            logger.error("仓储开单批量导单确认下单失败, orderConfirm入参有误");
            throw new BusinessException("仓储开单批量导单确认下单失败!");
        }
        TypeReference<List<OfcStorageTemplateDto>> typeReference = new TypeReference<List<OfcStorageTemplateDto>>() {
        };
        List<OfcStorageTemplateDto> ofcStorageTemplateDtoList = JacksonUtil.parseJsonWithFormat(orderList, typeReference);
        if (CollectionUtils.isEmpty(ofcStorageTemplateDtoList)) {
            throw new BusinessException("仓储开单批量导单确认下单失败! 订单列表为空!");
        }
        return ofcOrderManageService.storageOrderConfirm(ofcStorageTemplateDtoList, authResDto);
    }

    public Date convertStringToDate(String orderTime) {
        String[] split = orderTime.split(" ");
        if (split.length == 1 || orderTime.matches(REGEX_YYYYMMDD)) {
            orderTime = orderTime + " 00:00:00";
        }
        return DateUtils.String2Date(orderTime, DateUtils.DateFormatType.TYPE1);
    }

    public void convertSupplierToWare(CscSupplierInfoDto cscSupplierInfoDto, OfcOrderDTO ofcOrderDTO) {
        if (null == cscSupplierInfoDto || PubUtils.isSEmptyOrNull(cscSupplierInfoDto.getSupplierCode())) {
            return;
        }
        logger.info("==========> cscSupplierInfoDto:{} ", ToStringBuilder.reflectionToString(cscSupplierInfoDto));
        ofcOrderDTO.setSupportCode(cscSupplierInfoDto.getSupplierCode());
        ofcOrderDTO.setSupportName(cscSupplierInfoDto.getSupplierName());
        ofcOrderDTO.setSupportContactCode(cscSupplierInfoDto.getContactCode());
        ofcOrderDTO.setSupportContactName(cscSupplierInfoDto.getContactName());
    }

    @Override
    public CscContantAndCompanyDto convertCscConsignor(CscContantAndCompanyResponseDto consignor) {
        logger.info("转换客户中心DTO发货方 consignor:{}", consignor);
        if (null == consignor) {
            logger.error("转换客户中心DTO发货方出错! 入参为空! ");
            throw new BusinessException("转换客户中心DTO发货方出错!");
        }
        CscContantAndCompanyDto cscContactAndCompanyDto = new CscContantAndCompanyDto();
        CscContactDto cscContactDto = new CscContactDto();
        CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
        BeanUtils.copyProperties(consignor, cscContactDto);
        BeanUtils.copyProperties(consignor, cscContactCompanyDto);
        cscContactAndCompanyDto.setCscContactDto(cscContactDto);
        cscContactAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
        logger.info("转换客户中心DTO发货方 cscContactAndCompanyDto:{}", cscContactAndCompanyDto);
        return cscContactAndCompanyDto;
    }

    @Override
    public void convertConsignorToDis(CscContantAndCompanyResponseDto consignor, OfcOrderDTO ofcOrderDTO) {
        logger.info("===>consignor:{}", consignor);
        logger.info("===>ofcOrderDTO:{}", ofcOrderDTO);
        if (null == consignor || PubUtils.isSEmptyOrNull(consignor.getContactCompanySerialNo())) {
            return;
        }
        ofcOrderDTO.setConsignorCode(consignor.getContactCompanyName());//特别的,这里就是传NAME
        ofcOrderDTO.setConsignorContactCode(consignor.getContactCode());
        ofcOrderDTO.setConsignorName(consignor.getContactCompanyName());
        ofcOrderDTO.setConsignorContactName(consignor.getContactName());
        ofcOrderDTO.setConsignorType(consignor.getType());
        ofcOrderDTO.setConsignorContactPhone(consignor.getPhone());
        ofcOrderDTO.setDepartureProvince(consignor.getProvinceName());
        ofcOrderDTO.setDepartureCity(consignor.getCityName());
        ofcOrderDTO.setDepartureDistrict(consignor.getAreaName());
        ofcOrderDTO.setDepartureTowns(consignor.getStreetName());
        ofcOrderDTO.setDeparturePlace(consignor.getDetailAddress());
        StringBuilder sb = new StringBuilder(consignor.getProvince());
        if (!PubUtils.isSEmptyOrNull(consignor.getCity())) {
            sb.append(consignor.getCity());
            if (!PubUtils.isSEmptyOrNull(consignor.getArea())) {
                sb.append(consignor.getArea());
                if (!PubUtils.isSEmptyOrNull(consignor.getStreet())) {
                    sb.append(consignor.getStreet());
                }
            }
        }
        ofcOrderDTO.setDeparturePlaceCode(sb.toString());
    }

    public void convertConsigneeToDis(CscContantAndCompanyResponseDto cscConsigneeDto, OfcOrderDTO ofcOrderDTO) {
        logger.info("===>cscConsigneeDto:{}", cscConsigneeDto);
        logger.info("===>ofcOrderDTO:{}", ofcOrderDTO);
        if (null == cscConsigneeDto || PubUtils.isSEmptyOrNull(cscConsigneeDto.getContactCompanySerialNo())) {
            return;
        }
        ofcOrderDTO.setConsigneeCode(cscConsigneeDto.getContactCompanyName());//特别的,这里就是传NAME
        ofcOrderDTO.setConsigneeContactCode(cscConsigneeDto.getContactCode());
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
        if (!PubUtils.isSEmptyOrNull(cscConsigneeDto.getCity())) {
            sb.append(cscConsigneeDto.getCity());
            if (!PubUtils.isSEmptyOrNull(cscConsigneeDto.getArea())) {
                sb.append(cscConsigneeDto.getArea());
                if (!PubUtils.isSEmptyOrNull(cscConsigneeDto.getStreet())) {
                    sb.append(cscConsigneeDto.getStreet());
                }
            }
        }
        ofcOrderDTO.setDestinationCode(sb.toString());
    }

    /**
     * 转换客户中心DTO收货方
     * @param cscConsigneeDto 收货方
     * @return 转换后的收货方
     * @throws Exception 异常
     */
    public CscContantAndCompanyDto convertCscConsignee(CscContantAndCompanyResponseDto cscConsigneeDto) {
        logger.info("转换客户中心DTO收货方 cscConsigneeDto:{}", cscConsigneeDto);
        if (null == cscConsigneeDto) {
            logger.error("转换客户中心DTO收货方出错! 入参为空! ");
            throw new BusinessException("转换客户中心DTO收货方出错!");
        }
        CscContantAndCompanyDto cscContactAndCompanyDto = new CscContantAndCompanyDto();
        CscContactDto cscContactDto = new CscContactDto();
        CscContactCompanyDto cscContactCompanyDto = new CscContactCompanyDto();
        BeanUtils.copyProperties(cscConsigneeDto, cscContactDto);
        BeanUtils.copyProperties(cscConsigneeDto, cscContactCompanyDto);
        cscContactAndCompanyDto.setCscContactDto(cscContactDto);
        cscContactAndCompanyDto.setCscContactCompanyDto(cscContactCompanyDto);
        logger.info("转换客户中心DTO收货方 cscContactAndCompanyDto:{}", cscContactAndCompanyDto);
        return cscContactAndCompanyDto;
    }

    /**
     * 转换客户中心货品
     * @param ofcStorageTemplateDto 订单DTO
     * @return 转换后的货品
     */
    public OfcGoodsDetailsInfo convertCscGoods(OfcStorageTemplateDto ofcStorageTemplateDto) {
        logger.info("转换客户中心货品: ofcStorageTemplateDto.getCscGoodsApiVo():{}", ofcStorageTemplateDto.getCscGoodsApiVo());
        CscGoodsApiVo cscGoodsApiVo = ofcStorageTemplateDto.getCscGoodsApiVo();
        CscSupplierInfoDto goodsSupplier = ofcStorageTemplateDto.getGoodsSupplier();
        OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
        BeanUtils.copyProperties(cscGoodsApiVo, ofcGoodsDetailsInfo);
        ofcGoodsDetailsInfo.setId(null);
        ofcGoodsDetailsInfo.setGoodsSpec(ofcStorageTemplateDto.getGoodsSpec());
        ofcGoodsDetailsInfo.setUnit(ofcStorageTemplateDto.getUnit());
        ofcGoodsDetailsInfo.setUnitPrice(ofcStorageTemplateDto.getUnitPrice());
        ofcGoodsDetailsInfo.setQuantity(ofcStorageTemplateDto.getQuantity());
        ofcGoodsDetailsInfo.setGoodsType(cscGoodsApiVo.getGoodsTypeParentName());
        ofcGoodsDetailsInfo.setGoodsCategory(cscGoodsApiVo.getGoodsTypeName());
        ofcGoodsDetailsInfo.setChargingWays("01");//计费方式默认按件数
        ofcGoodsDetailsInfo.setChargingQuantity(ofcStorageTemplateDto.getQuantity()); // 计费数量默认为货品数量
        if (!PubUtils.isSEmptyOrNull(cscGoodsApiVo.getUnitPrice())) {
            ofcGoodsDetailsInfo.setUnitPrice(new BigDecimal(cscGoodsApiVo.getUnitPrice()));
        }
        String productionBatch = ofcStorageTemplateDto.getProductionBatch();
        String supportBatch = ofcStorageTemplateDto.getSupportBatch();
        String productionTime = ofcStorageTemplateDto.getProductionTime();
        String invalidTime = ofcStorageTemplateDto.getInvalidTime();
        if (!PubUtils.isSEmptyOrNull(supportBatch)) {
            //2017年4月19日 添加逻辑, 供应商批次字段全匹配识别名称, 但保存对应的供应商编码
            String supplierCode = goodsSupplier.getSupplierCode();
            if (PubUtils.isSEmptyOrNull(supplierCode)) {
                logger.error("供应商:{}没有供应商编码", goodsSupplier.getSupplierName());
                throw new BusinessException("供应商【" + goodsSupplier.getSupplierName() + "】没有供应商编码");
            }
            ofcGoodsDetailsInfo.setSupportBatch(supplierCode);
        }
        ofcGoodsDetailsInfo.setProductionBatch(productionBatch);
        if (!PubUtils.isSEmptyOrNull(productionTime)) {
            ofcGoodsDetailsInfo.setProductionTime(DateUtils.String2Date(productionTime, DateUtils.DateFormatType.TYPE2));
        }
        if (!PubUtils.isSEmptyOrNull(invalidTime)) {
            ofcGoodsDetailsInfo.setInvalidTime(DateUtils.String2Date(invalidTime, DateUtils.DateFormatType.TYPE2));
        }
        BigDecimal mainUnitNum = ofcStorageTemplateDto.getMainUnitNum();
        if (null == mainUnitNum) {
            logger.error("货品{}主单位数量为空!", ofcGoodsDetailsInfo.getGoodsCode());
            throw new BusinessException("货品:" + ofcGoodsDetailsInfo.getGoodsCode() + "主单位数量为空!");
        }
        ofcGoodsDetailsInfo.setPrimaryQuantity(mainUnitNum);
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
        if (null == result || null == authResDto) {
            logger.error("仓储开单批量导单审核失败, 入参有误");
            throw new BusinessException("仓储开单批量导单审核失败!");
        }
        String orderBatchNumber = (String) result;
        List<OfcFundamentalInformation> ofcFundamentalInformationList = ofcFundamentalInformationService.queryFundamentalByBatchNumber(orderBatchNumber);
        logger.info("============>仓储开单批量导单需要审核的订单:{}", ofcFundamentalInformationList);
        for (OfcFundamentalInformation ofcFundamentalInformation : ofcFundamentalInformationList) {
            String orderCode = ofcFundamentalInformation.getOrderCode();
            List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = ofcGoodsDetailsInfoService.queryByOrderCode(orderCode);
            OfcWarehouseInformation ofcWarehouseInformation = ofcWarehouseInformationService.warehouseInformationSelect(orderCode);
            OfcDistributionBasicInfo ofcDistributionBasicInfo = ofcDistributionBasicInfoService.queryByOrderCode(orderCode);
            OfcOrderStatus ofcOrderStatus = ofcOrderStatusService.queryLastTimeOrderByOrderCode(orderCode);
            String review;
            try {
                review = ofcOrderManageService.orderAutoAudit(ofcFundamentalInformation, ofcGoodsDetailsInfoList, ofcDistributionBasicInfo, ofcWarehouseInformation
                        , new OfcFinanceInformation(), ofcOrderStatus.getOrderStatus(), REVIEW, authResDto);
            } catch (Exception e) {
                logger.error("仓储开单批量导单审核, 当前订单审核失败" +
                        ", 直接跳过该订单, 订单号: {}, 错误信息: {}", orderCode, e);
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
        if (CollectionUtils.isEmpty(ofcStorageTemplates)) {
            logger.error("校验模板必填失败!");
            throw new BusinessException("校验模板必填失败!");
        }
        String templateType = ofcStorageTemplates.get(0).getTemplateType();
        boolean storageIn = StringUtils.equals(templateType, STORAGE_IN);
        int standardNum = storageIn ? StorageImportInEnum.queryList().size() : StorageImportOutEnum.queryList().size();
        if (standardNum != ofcStorageTemplates.size()) {
            logger.error("校验模板必填失败! 模板数量错误! ");
            throw new BusinessException("校验模板必填失败! 模板数量错误! ");
        }
        boolean setProvideTransport = false;
        for (OfcStorageTemplate ofcStorageTemplate : ofcStorageTemplates) {
            String reflectColName = ofcStorageTemplate.getReflectColName();
            String colDefaultVal = ofcStorageTemplate.getColDefaultVal();
            String standardColCode = ofcStorageTemplate.getStandardColCode();
            if (StringUtils.equals(standardColCode, StorageImportOutEnum.CUST_ORDER_CODE.getStandardColCode())
                    && PubUtils.isSEmptyOrNull(reflectColName)) {
                logger.error("{}的模板列名不能为空!", StorageImportOutEnum.CUST_ORDER_CODE.getStandardColName());
                throw new BusinessException(StorageImportOutEnum.CUST_ORDER_CODE.getStandardColName() + "的模板列名不能为空!");
            } else if (StringUtils.equals(standardColCode, StorageImportOutEnum.MERCHANDISER.getStandardColCode())
                    && (PubUtils.isSEmptyOrNull(reflectColName) && PubUtils.isSEmptyOrNull(colDefaultVal))) {
                logger.error("{}的模板列名和默认值必填一个!", StorageImportOutEnum.MERCHANDISER.getStandardColName());
                throw new BusinessException(StorageImportOutEnum.MERCHANDISER.getStandardColName() + "的模板列名和默认值必填一个");
            } else if (StringUtils.equals(standardColCode, StorageImportOutEnum.WAREHOUSE_NAME.getStandardColCode())
                    && (PubUtils.isSEmptyOrNull(reflectColName) && PubUtils.isSEmptyOrNull(colDefaultVal))) {
                logger.error("{}的模板列名和默认值必填一个!", StorageImportOutEnum.WAREHOUSE_NAME.getStandardColName());
                throw new BusinessException(StorageImportOutEnum.WAREHOUSE_NAME.getStandardColName() + "的模板列名和默认值必填一个");
            } else if (StringUtils.equals(standardColCode, StorageImportOutEnum.BUSINESS_TYPE.getStandardColCode())
                    && (PubUtils.isSEmptyOrNull(reflectColName) && PubUtils.isSEmptyOrNull(colDefaultVal))) {
                logger.error("{}的模板列名和默认值必填一个!", StorageImportOutEnum.BUSINESS_TYPE.getStandardColName());
                throw new BusinessException(StorageImportOutEnum.BUSINESS_TYPE.getStandardColName() + "的模板列名和默认值必填一个");
            } else if (StringUtils.equals(standardColCode, StorageImportOutEnum.GOODS_CODE.getStandardColCode())
                    && PubUtils.isSEmptyOrNull(reflectColName)) {
                logger.error("{}的模板列名不能为空!", StorageImportOutEnum.GOODS_CODE.getStandardColName());
                throw new BusinessException(StorageImportOutEnum.GOODS_CODE.getStandardColName() + "的模板列名不能为空!");
            } else if (StringUtils.equals(standardColCode, StorageImportOutEnum.QUANTITY.getStandardColCode())
                    && PubUtils.isSEmptyOrNull(reflectColName)) {
                logger.error("{}的模板列名不能为空!", StorageImportOutEnum.QUANTITY.getStandardColName());
                throw new BusinessException(storageIn ? StorageImportInEnum.QUANTITY.getStandardColName()
                        : StorageImportOutEnum.QUANTITY.getStandardColName()  + "的模板列名不能为空!");
            } else if (StringUtils.equals(standardColCode, StorageImportOutEnum.CONSIGNEE_NAME.getStandardColCode())
                    && PubUtils.isSEmptyOrNull(reflectColName) && !storageIn) {
                logger.error("{}的模板列名不能为空!", StorageImportOutEnum.CONSIGNEE_NAME.getStandardColName());
                throw new BusinessException(StorageImportOutEnum.CONSIGNEE_NAME.getStandardColName()  + "的模板列名不能为空!");
            } else if (StringUtils.equals(standardColCode, StorageImportInEnum.PROVIDE_TRANSPORT.getStandardColCode())
                    && (!PubUtils.isSEmptyOrNull(reflectColName) || StringUtils.equals(colDefaultVal, "是")) && storageIn) {
                setProvideTransport = true;
            } else if (StringUtils.equals(standardColCode, StorageImportInEnum.CONSIGNOR_NAME.getStandardColCode())
                    && PubUtils.isSEmptyOrNull(reflectColName) && setProvideTransport && storageIn) {
                logger.error("若您映射了是否提供运输服务,{}的模板列名不能为空!", StorageImportInEnum.CONSIGNOR_NAME.getStandardColName());
                throw new BusinessException("若您映射了是否提供运输服务," + StorageImportInEnum.CONSIGNOR_NAME.getStandardColName()  + "的模板列名不能为空!");
            }
        }
        logger.info("校验模板必填成功!");
    }

    /**
     * 出库批量导单确认下单之前, 校验当前库存
     * @param orderList 订单列表
     * @return 校验结果
     */
    @Override
    public Wrapper checkStock(String orderList) throws Exception {
        logger.info("出库批量导单确认下单之前, 校验当前库存 orderList ==> {}", orderList);
        if (PubUtils.isSEmptyOrNull(orderList)) {
            logger.error("仓储开单出库批量导单校验当前库存失败, checkStock入参有误");
            throw new BusinessException("仓储出库开单批量导单校验当前库存失败!");
        }
        TypeReference<List<OfcStorageTemplateDto>> typeReference = new TypeReference<List<OfcStorageTemplateDto>>() {
        };
        List<OfcStorageTemplateDto> ofcStorageTemplateDtoList = JacksonUtil.parseJsonWithFormat(orderList, typeReference);
        if (CollectionUtils.isEmpty(ofcStorageTemplateDtoList)) {
            logger.error("仓储开单出库批量导单校验当前库存失败! 订单列表为空!");
            throw new BusinessException("仓储开单出库批量导单校验当前库存失败! 订单列表为空!");
        }
        List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfoList = new ArrayList<>();
        Set<String> warehouseCodeSet = new HashSet<>();
        for (OfcStorageTemplateDto ofcStorageTemplateDto : ofcStorageTemplateDtoList) {
            OfcGoodsDetailsInfo ofcGoodsDetailsInfo = this.convertCscGoods(ofcStorageTemplateDto);
            String warehouseCode = ofcStorageTemplateDto.getWarehouseCode();
            ofcGoodsDetailsInfoList.add(ofcGoodsDetailsInfo);
            if (PubUtils.isSEmptyOrNull(warehouseCode)) {
                String custOrderCode = ofcStorageTemplateDto.getCustOrderCode();
                logger.error("仓储开单出库批量导单校验当前库存失败! 客户订单号【{}】的仓库为空!", custOrderCode);
                throw new BusinessException("仓储开单出库批量导单校验当前库存失败! 客户订单号【" + custOrderCode + "】的仓库为空!");
            }
            warehouseCodeSet.add(warehouseCode);
        }
        if (warehouseCodeSet.size() > 1) {
            logger.error("仓储开单出库批量导单校验当前库存失败! 本批次订单存在多个仓库! 单个仓库才能校验库存!");
            throw new BusinessException("仓储开单出库批量导单校验当前库存失败! 本批次订单存在多个仓库! 单个仓库才能校验库存!");
        }
        String custCode = ofcStorageTemplateDtoList.get(0).getOfcOrderDTO().getCustCode();
        String warehouseCode = warehouseCodeSet.iterator().next();
        Wrapper wrapper = ofcOrderManageService.validateStockCount(ofcGoodsDetailsInfoList, custCode, warehouseCode);
        if (wrapper.getCode() != Wrapper.SUCCESS_CODE) {
            TypeReference<List<ResponseMsg>> typeReferenceRespMsg = new TypeReference<List<ResponseMsg>>() {
            };
            List<ResponseMsg> responseMsgs = JacksonUtil.parseJson(wrapper.getMessage(), typeReferenceRespMsg);
            logger.error("仓储开单出库批量导单校验当前库存失败! 库存数量不足! 失败信息: {}", wrapper.getMessage());
            return WrapMapper.wrap(Wrapper.ERROR_CODE, wrapper.getMessage(), responseMsgs);
        }
        return WrapMapper.ok();
    }

}
