package com.xescm.ofc.service.impl;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContactDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.provider.CscContactEdasService;
import com.xescm.csc.provider.CscGoodsEdasService;
import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.enums.*;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.mapper.OfcStorageTemplateMapper;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import com.xescm.ofc.model.dto.ofc.OfcStorageTemplateDto;
import com.xescm.ofc.service.OfcFundamentalInformationService;
import com.xescm.ofc.service.OfcStorageTemplateService;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.utils.DateUtils;
import com.xescm.rmc.edas.domain.qo.RmcWareHouseQO;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import com.xescm.rmc.edas.service.RmcWarehouseEdasService;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.*;

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
    private RmcWarehouseEdasService rmcWarehouseEdasService;
    @Resource
    private CscContactEdasService cscContactEdasService;
    @Resource
    private CscGoodsEdasService cscGoodsEdasService;
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
        List<OfcStorageTemplate> ofcStorageTemplateList = ofcStorageTemplateMapper.selectTemplateByCondition(templateCondition);

        return ofcStorageTemplateList;
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
        //根据模板编码和类型拿到用户保存的配置模板的映射
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
        Map<Integer,OfcStorageTemplate> modelNameStr = new LinkedHashMap<>();
        Class clazz = null;
        List<OfcStorageTemplateDto> ofcStorageTemplateDtoList = new ArrayList<>();
        Map<String, CscGoodsApiVo> goodsCheck = new HashMap<>();
        Map<String, CscContantAndCompanyResponseDto> consigneeCheck = new HashMap<>();
        boolean requiredField = true;
        //去RMC查到所有仓库
        Map<String, RmcWarehouseRespDto> allWarehouseByRmc =  this.getAllWarehouseByRmc();
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
                //校验第一行,校验表格是否有所有的必填列列名, 并返回所有必填项在用户上传的Excel模板中的列号,及对应的映射模板列名称
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
                        OfcStorageTemplate ofcStorageTemplateForReflect = cellReflectToDomain(cellValue,templateDetilMap); //标准表字段映射成对应实体的字段的值
                        if(null == ofcStorageTemplateForReflect || PubUtils.isSEmptyOrNull(ofcStorageTemplateForReflect.getStandardColCode())){
                            break;
                        }
                        modelNameStr.put(cellNum,ofcStorageTemplateForReflect);
                    }else if(rowNum > 0) { // 表格的数据体
                        OfcStorageTemplate ofcStorageTemplateForCheck = modelNameStr.get(cellNum);
                        String standardColCode = ofcStorageTemplateForCheck.getStandardColCode();
                        String colDefaultVal = ofcStorageTemplateForCheck.getColDefaultVal();
                        if(PubUtils.isSEmptyOrNull(standardColCode)){
                            break;
                        }
                        //客户订单号
                        if(StringUtils.equals(StorageImportInEnum.CUST_ORDER_CODE.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有客户订单号", rowNum + 1, cellNum + 1);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "缺少必填字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                checkPass = false;
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //订单日期
                        }else if(StringUtils.equals(StorageImportInEnum.ORDER_TIME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                cellValue = DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE1);
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //开单员
                        }else if(StringUtils.equals(StorageImportInEnum.MERCHANDISER.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                if(!PubUtils.isSEmptyOrNull(colDefaultVal)){
                                    cellValue = colDefaultVal;
                                }else {
                                    cellValue = authResDto.getUserName();
                                }
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //仓库名称
                        }else if(StringUtils.equals(StorageImportInEnum.WAREHOUSE_NAME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                if(!PubUtils.isSEmptyOrNull(colDefaultVal)){
                                    cellValue = colDefaultVal;
                                }else {
                                    logger.error("当前行:{},列:{} 没有仓库名称", rowNum + 1, cellNum);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "缺少必填字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    checkPass = false;
                                    break;
                                }
                            }
                            //如果有仓库名称, 校验该仓库名称是否存在!
                            if(!allWarehouseByRmc.containsKey(cellValue)){
                                logger.error("当前行:{},列:{}仓库名称:{}, 调用接口未能找到该仓库", rowNum + 1, cellNum + 1, cellValue);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "仓库名称:" + cellValue + "没有维护!");
                                checkPass = false;
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //业务类型
                        }else if(StringUtils.equals(StorageImportInEnum.BUSINESS_TYPE.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                if(!PubUtils.isSEmptyOrNull(colDefaultVal)){
                                    cellValue = colDefaultVal;
                                }else {
                                    logger.error("当前行:{},列:{} 没有业务类型", rowNum + 1, cellNum + 1);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + ",列:" + (cellNum + 1) + " 缺少必填字段:" + ofcStorageTemplateForCheck.getReflectColName());
                                    checkPass = false;
                                    break;
                                }
                            }else {
                                //如果当前单元格不空, 需要校验是否是ofc可识别的订单类型
                                Wrapper result = checkBusinessType(cellValue, ofcStorageTemplate.getTemplateType());
                                if(Wrapper.ERROR_CODE == result.getCode()){
                                    logger.error("当前行:{},列:{} 业务类型:{}不可识别", rowNum + 1, cellNum + 1, cellValue);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + ",列:" + (cellNum + 1) + " 业务类型:" + cellValue + "不可识别!");
                                    checkPass = false;
                                    break;
                                }
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //备注
                        }else if(StringUtils.equals(StorageImportInEnum.NOTES.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有备注", rowNum + 1, cellNum);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "缺少必填字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                checkPass = false;
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //货品编码
                        }else if(StringUtils.equals(StorageImportInEnum.GOODS_CODE.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有货品编码", rowNum + 1, cellNum);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "缺少必填字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                checkPass = false;
                                break;
                            }
                            //货品编码不空,判断是否已经校验过
                            //如果没校验过就调用CSC接口进行校验
                            if(!goodsCheck.containsKey(cellValue)){
                                CscGoodsApiDto cscGoodsApiDto = new CscGoodsApiDto();
                                cscGoodsApiDto.setGoodsCode(cellValue);
                                cscGoodsApiDto.setCustomerCode(ofcStorageTemplate.getCustCode());
                                Wrapper<List<CscGoodsApiVo>> queryCscGoodsList = cscGoodsEdasService.queryCscGoodsList(cscGoodsApiDto);
                                List<CscGoodsApiVo> cscGoodsApiVoResult = queryCscGoodsList.getResult();
                                //没有校验通过
                                if(null == cscGoodsApiVoResult && cscGoodsApiVoResult.size() == 0){
                                    logger.error("当前行:{},列:{} 货品编码校验失败, 请维护", rowNum + 1, cellNum);
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "货品编码校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    checkPass = false;
                                    break;
                                    //校验通过
                                }else {
                                    logger.info("当前货品编码:{},校验通过", cellValue);
                                    goodsCheck.put(cellValue, cscGoodsApiVoResult.get(0));
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
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //规格
                        }else if(StringUtils.equals(StorageImportInEnum.GOODS_SPEC.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有规格", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //单位
                        }else if(StringUtils.equals(StorageImportInEnum.UNIT.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有单位", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //单价
                        }else if(StringUtils.equals(StorageImportInEnum.UNIT_PRICE.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有单价", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //入库数量 or  出库数量
                        }else if(StringUtils.equals(StorageImportInEnum.QUANTITY.getStandardColCode(), standardColCode)){
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
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
                                    field = clazz.getDeclaredField(standardColCode);
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
                        }else if(StringUtils.equals(StorageImportInEnum.PRODUCTION_BATCH.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有批次号", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //生产日期
                        }else if(StringUtils.equals(StorageImportInEnum.PRODUCTION_TIME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有生产日期", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //失效日期
                        }else if(StringUtils.equals(StorageImportInEnum.INVALID_TIME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有失效日期", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //供应商名称
                        }else if(StringUtils.equals(StorageImportInEnum.SUPPORT_NAME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有供应商名称", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //预计入库时间
                        }else if(StringUtils.equals(StorageImportInEnum.ARRIVE_TIME.getStandardColCode(), standardColCode)
                                ||StringUtils.equals(StorageImportOutEnum.SHIPMENT_TIME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有预计入库时间", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //是否提供运输服务
                        }else if(StringUtils.equals(StorageImportInEnum.PROVIDE_TRANSPORT.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有是否提供运输服务", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //车牌号
                        }else if(StringUtils.equals(StorageImportInEnum.PLATE_NUMBER.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有车牌号", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //司机姓名
                        }else if(StringUtils.equals(StorageImportInEnum.DRIVER_NAME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有司机姓名", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //联系电话
                        }else if(StringUtils.equals(StorageImportInEnum.CONTACT_NUMBER.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有联系电话", rowNum + 1, cellNum + 1);
                                break;
                            }
                            setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            //发货方名称
                        }else if(StringUtils.equals(StorageImportOutEnum.CONSIGNEE_NAME.getStandardColCode(), standardColCode)){
                            if(Cell.CELL_TYPE_BLANK == commonCell.getCellType()){
                                logger.error("当前行:{},列:{} 没有发货方名称", rowNum + 1, cellNum);
                                xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "缺少必填字段:"+ ofcStorageTemplateForCheck.getReflectColName());
                                checkPass = false;
                                break;
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
                                    xlsErrorMsg.add("行:" + (rowNum + 1) + "列:" + (cellNum + 1) + "发货方名称校验失败, 请维护:"+ ofcStorageTemplateForCheck.getReflectColName());
                                    checkPass = false;
                                    break;
                                    //找到该收货方了
                                }else {
                                    CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = result.get(0);
                                    ofcStorageTemplateDto.setCscConsigneeDto(cscContantAndCompanyResponseDto);
                                    consigneeCheck.put(cellValue, cscContantAndCompanyResponseDto);
                                }
                            }else {
                                CscContantAndCompanyResponseDto cscContantAndCompanyResponseDto = consigneeCheck.get(cellValue);
                                ofcStorageTemplateDto.setCscConsigneeDto(cscContantAndCompanyResponseDto);
                                setFiledValue(clazz, ofcStorageTemplateDto, cellValue, standardColCode);
                            }
                        }
                    }
                }
                if(rowNum > 0){
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
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE, xlsErrorMsg);
        }

        //校验成功!
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, ofcStorageTemplateDtoList);
    }

    /**
     * 校验订单业务类型
     * @param cellValue  单元格的值
     * @param templateType 模板类型
     * @return 校验结果
     */
    private Wrapper checkBusinessType(String cellValue, String templateType) {
        List<String> result = new ArrayList<>();
        Boolean check = true;
        //入库单
        if(StringUtils.equals(templateType, "storageIn")){
            List<String> codeList = BusinessTypeStorageInEnum.getCodeList();
            List<String> descList = BusinessTypeStorageInEnum.getDescList();
            if(!codeList.contains(cellValue) && !descList.contains(cellValue)){
                check = false;
            }
            //出库单
        }else if(StringUtils.equals(templateType, "storageOut")){
            List<String> codeList = BusinessTypeStorageOutEnum.getCodeList();
            List<String> descList = BusinessTypeStorageOutEnum.getDescList();
            if(!codeList.contains(cellValue) && !descList.contains(cellValue)){
                check = false;
            }
        }
        if(!check){
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE, result);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
    }


    /**
     * @return key : 仓库名称, value : 仓库信息
     */
    private Map<String,RmcWarehouseRespDto> getAllWarehouseByRmc() {
        List<RmcWarehouseRespDto> rmcWarehouseRespDtos = this.allWarehouseByRmc();
        Map<String, RmcWarehouseRespDto> map = new HashMap<>();
        for (RmcWarehouseRespDto rmcWarehouseRespDto : rmcWarehouseRespDtos) {
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
     * @param commonRow 通用行
     * @param templateType 模板类型
     * @param templateDetilMap 列名的模板映射信息
     * @return key必填列号, value对应的映射列名
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
            String commonCellValue = commonCell.getStringCellValue();// 当前列 列名
            //根据当前列列名找到映射数据
            OfcStorageTemplate ofcStorageTemplate = cellReflectToDomain(commonCellValue, templateDetilMap);
            if(null == ofcStorageTemplate){
                // 当前模板中的列名, 没有找到在初始化的Map的映射表中找到映射
                logger.info("当前模板中的列名:{}, 没有找到在初始化的Map的映射表中找到映射", commonCellValue);
                continue;
            }
            //如果能找到
            ofcStorageTemplateMap.put(ofcStorageTemplate.getStandardColCode(), ofcStorageTemplate);//key : 标准列code, value: 对应映射实体
            colNumMap.put(ofcStorageTemplate.getStandardColCode(), cellNum); //key : 标准列code, value: 必填列号
        }
        String[] item = StringUtils.equals(templateType,"storageIn") ? inRquiredItem : outRquiredItem;
        Map<Integer, String> requiredColNum = new HashMap<>();
        for (String requiredItem : item) {
            if(!ofcStorageTemplateMap.containsKey(requiredItem)){
                if(StringUtils.equals("storageIn", templateType)){
                    InRquiredItem anyByStandardCode = InRquiredItem.getAnyByStandardCode(requiredItem);
                    logger.error("没有找到在初始化的Map的映射表中找到映射列:{},{}", requiredItem, anyByStandardCode.getStandardColName());
                    throw new BusinessException("没有找到在初始化的Map的映射表中找到映射列:" + anyByStandardCode.getStandardColName());
                }else if(StringUtils.equals("storageIn", templateType)){
                    OutRquiredItem anyByStandardCode = OutRquiredItem.getAnyByStandardCode(requiredItem);
                    logger.error("没有找到在初始化的Map的映射表中找到映射列:{},{}", requiredItem, anyByStandardCode.getStandardColName());
                    throw new BusinessException("没有找到在初始化的Map的映射表中找到映射列:" + anyByStandardCode.getStandardColName());
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
     * 获取模板映射map
     * @param templateCode 模板编码
     * @param templateType 模板类型
     * @return 模板映射map
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
