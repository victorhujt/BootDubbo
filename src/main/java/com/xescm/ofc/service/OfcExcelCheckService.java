package com.xescm.ofc.service;

import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lyh on 2016/12/16.
 */
public interface OfcExcelCheckService {
    Wrapper<?> checkXlsBoradwise(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell,AuthResDto authResDto);
    Wrapper<?> checkXlsxBoradwise(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell,AuthResDto authResDto);
    Wrapper<?> checkXlsAcross(MultipartFile uploadFile,String sheetNumChosen, String customerCode, Integer staticCell,AuthResDto authResDto);
    Wrapper<?> checkXlsxAcross(MultipartFile uploadFile,String sheetNumChosen, String custId, Integer staticCell,AuthResDto authResDto);
}
