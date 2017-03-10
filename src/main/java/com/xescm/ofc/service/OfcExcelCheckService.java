package com.xescm.ofc.service;

import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.Wrapper;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lyh on 2016/12/16.
 */
public interface OfcExcelCheckService {
    Wrapper<?> checkAcrossExcel(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell, AuthResDto authResDto);

    Wrapper<?> checkBoradwiseExcel(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell, AuthResDto authResDto);
}
