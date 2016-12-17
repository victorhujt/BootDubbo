package com.xescm.ofc.service;

import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lyh on 2016/12/16.
 */
public interface OfcExcelCheckService {
    Wrapper<?> checkXlsBoradwise(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell);
    Wrapper<?> checkXlsxBoradwise(MultipartFile uploadFile, String sheetNumChosen, String customerCode, int staticCell);
    Wrapper<?> checkXlsAcross(MultipartFile uploadFile,String sheetNumChosen, String customerCode, Integer staticCell);
    Wrapper<?> checkXlsxAcross(MultipartFile uploadFile,String sheetNumChosen, String custId, Integer staticCell);
}
