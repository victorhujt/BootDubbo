package com.xescm.ofc.service.impl;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.TmpZpMissOrder;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcStorageTemplateService;
import com.xescm.ofc.service.TmpZpMissOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Date;

/**
 *
 * Created by lyh on 2017/4/13.
 */
@Service
public class TmpZpMissOrderServiceImpl extends BaseService<TmpZpMissOrder> implements TmpZpMissOrderService{

    @Resource
    private OfcStorageTemplateService ofcStorageTemplateService;

    @Override
    public Wrapper<String> zpMissOrderImport(MultipartFile uploadFile) {
        Integer activeSheetNum = ofcStorageTemplateService.checkStorageTemplate(uploadFile);
        int importNum = 0;
        InputStream inputStream;
        Workbook workbook;
        try {
            inputStream = uploadFile.getInputStream();
            workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            logger.error("导入众品遗漏订单读取内部异常{}",e);
            throw new BusinessException("导入众品遗漏订单读取内部异常");
        }
        //遍历sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum ++) {
            if (sheetNum != activeSheetNum) {
                continue;
            }
            Sheet sheet = workbook.getSheetAt(sheetNum);
            if (sheet.getLastRowNum() == 0) {
                throw new BusinessException("请先上传Excel导入数据，再加载后执行导入！");
            }
            int custOrderCodeCellNum = -1;
            //遍历行
            for (int rowNum = 0; rowNum < sheet.getLastRowNum() + 1; rowNum ++) {
                Row commonRow = sheet.getRow(rowNum);
                if (null == commonRow) {
                    //标记当前行出错,并跳出当前循环
                    break;
                }
                //空行
                Cell cell = commonRow.getCell(0);
                if (null == cell || Cell.CELL_TYPE_BLANK == cell.getCellType()) {
                    continue;
                }
                Date now = new Date();
                //遍历列

                for(int cellNum = 0; cellNum < commonRow.getLastCellNum() + 1; cellNum ++) {
                    Cell commonCell = commonRow.getCell(cellNum);
                    logger.info("开始校验第{}行, 第{}列, 当前单元格的值为:{}", rowNum + 1, cellNum + 1, ToStringBuilder.reflectionToString(commonCell));
                    if (null == commonCell || Cell.CELL_TYPE_BLANK == commonCell.getCellType()) {
                        logger.error("当前单元格为空, 行:{}, 列:{}", (rowNum + 1), (cellNum + 1));
                        //标记当前列出错, 并跳过当前循环
                        continue;
                    }
                    String cellValue = null;
                    if (Cell.CELL_TYPE_STRING == commonCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(commonCell.getStringCellValue());
                    } else if (Cell.CELL_TYPE_NUMERIC == commonCell.getCellType()) {
                        cellValue = PubUtils.trimAndNullAsEmpty(String.valueOf(commonCell.getNumericCellValue()));
                    }
                    if (rowNum == 0 && (StringUtils.equals("客户订单号", cellValue) || StringUtils.equals("客户订单编号", cellValue))) {//第一行
                        custOrderCodeCellNum = cellNum;
                    } else if (rowNum > 0 && cellNum == custOrderCodeCellNum) {
                        TmpZpMissOrder tmpZpMissOrder = new TmpZpMissOrder();
                        tmpZpMissOrder.setCustOrderCode(cellValue);
                        tmpZpMissOrder.setErrorDate(now);
                        try {
                            int save = super.save(tmpZpMissOrder);
                            if (save == 0) {
                                logger.error("导入众品遗漏订单保存失败");
                                continue;
                            }
                            logger.info("导入众品遗漏订单保存成功!");
                            importNum ++;
                        } catch (Exception e) {
                            if (e.getCause().getMessage().startsWith("Duplicate entry")) {
                                logger.error("客户订单号 : 【" + cellValue + "】重复");
                            }
                            logger.error("导入众品遗漏订单, 保存出错, 错误信息: {}", e);
                            continue;
                        }
                    }
                }
            }
            if (-1 == custOrderCodeCellNum) {
                throw new BusinessException("导入众品遗漏订单未找到客户订单号列");
            } else if (0 == importNum) {
                throw new BusinessException("导入众品遗漏订单导入数量为0");
            }
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "导入众品遗漏成功, 导入数量为:" + importNum);
    }
}
