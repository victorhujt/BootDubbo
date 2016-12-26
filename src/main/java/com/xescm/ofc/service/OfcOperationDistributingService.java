package com.xescm.ofc.service;

import com.alibaba.fastjson.JSONArray;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.csc.model.dto.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by lyh on 2016/11/30.
 */
public interface OfcOperationDistributingService {
    CscContantAndCompanyDto switchOrderDtoToCscCAndCDto(OfcOrderDTO ofcOrderDTO, String purpose);
    Wrapper<?> validateCustOrderCode(JSONArray jsonArray);
    List<String> getExcelSheet(MultipartFile uploadFile, String suffix);
    void validateOperationDistributingMsg(OfcOrderDTO ofcOrderDTO);
    String distributingOrderPlace(JSONArray jsonArray, AuthResDto authResDtoByToken, String batchNumber);
    Wrapper<?> checkExcel(MultipartFile uploadFile, String suffix, String sheetNum, AuthResDto authResDto, String customerCode, String modelType, String modelMappingCode);
}
