package com.xescm.ofc.service;

import com.alibaba.fastjson.JSONArray;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyDto;
import com.xescm.ofc.model.dto.ofc.OfcOrderDTO;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lyh on 2016/11/30.
 */
public interface OfcOperationDistributingService {
    CscContantAndCompanyDto switchOrderDtoToCscCAndCDto(OfcOrderDTO ofcOrderDTO, String purpose);
    Wrapper<?> validateCustOrderCode(JSONArray jsonArray);
    Wrapper<?> checkExcel(MultipartFile uploadFile, AuthResDto authResDto, String custId,Integer staticCell);
}
