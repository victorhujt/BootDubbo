package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.ofc.domain.OfcStorageTemplate;
import com.xescm.ofc.model.dto.form.TemplateCondition;
import com.xescm.ofc.model.dto.ofc.OfcGoodsDetailsInfoDTO;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.ofc.model.dto.ofc.OfcStorageTemplateDto;
import com.xescm.rmc.edas.domain.vo.RmcWarehouseRespDto;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by lyh on 2017/2/18.
 */
public interface OfcStorageTemplateService {
    void saveTemplate(List<OfcStorageTemplate> templateList, AuthResDto authResDto);

    List<OfcStorageTemplate> selectTemplate(TemplateCondition templateCondition);

    List<OfcStorageTemplate> selectTemplateByCondition(TemplateCondition templateCondition);

    void  delTemplateByCode(String temlpateCode);

    void templateEditConfirm(List<OfcStorageTemplate> templateName, AuthResDto authResDto, String lastTemplateType) throws Exception;

    List<OfcStorageTemplate> selectTemplateDetail(TemplateCondition templateCondition);

    Wrapper<?> checkStorageTemplate(MultipartFile file, AuthResDto authResDto,OfcStorageTemplate ofcStorageTemplate, Integer sheetNum);

    Integer checkStorageTemplate(MultipartFile file);

    String resolveTooLangNum(String cellValue, Cell commonCell);

    List<RmcWarehouseRespDto> allWarehouseByRmc();

    Wrapper orderConfirm(List<OfcStorageTemplateDto> orderList, AuthResDto authResDto) throws Exception;

    Wrapper storageTemplateAudit(Object result, AuthResDto authResDto);

    void checkTemplateListRequired(List<OfcStorageTemplate> ofcStorageTemplates);

    Wrapper checkStock(String orderList) throws Exception;

    Date convertStringToDate(String orderTime);

    OfcGoodsDetailsInfoDTO convertCscGoods(OfcStorageTemplateDto ofcStorageTemplateDto);

    CscContantAndCompanyDto convertCscConsignee(CscContantAndCompanyResponseDto cscConsigneeDto);

    void convertConsigneeToDis(CscContantAndCompanyResponseDto cscConsigneeDto, OfcOrderDTO ofcOrderDTO);

    void convertSupplierToWare(CscSupplierInfoDto cscSupplierInfoDto, OfcOrderDTO ofcOrderDTO);

    CscContantAndCompanyDto convertCscConsignor(CscContantAndCompanyResponseDto cscConsigneeDto);

    void convertConsignorToDis(CscContantAndCompanyResponseDto consignor, OfcOrderDTO ofcOrderDTO);
}
