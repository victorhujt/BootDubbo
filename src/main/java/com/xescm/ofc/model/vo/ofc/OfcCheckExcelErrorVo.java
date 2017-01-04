package com.xescm.ofc.model.vo.ofc;

import com.xescm.csc.model.dto.CscContantAndCompanyInportDto;
import com.xescm.ofc.model.dto.csc.OfcGoodsImportDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2016/12/16.
 */
public class OfcCheckExcelErrorVo {
    private List<String> xlsErrorMsg = new ArrayList<>();
    private List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = new ArrayList<>();
    private List<OfcGoodsImportDto> cscGoodsImportDtoList = new ArrayList<>();
    private String batchconsingeeKey;
    private String batchgoodsKey;


    public String getBatchconsingeeKey() {
        return batchconsingeeKey;
    }

    public void setBatchconsingeeKey(String batchconsingeeKey) {
        this.batchconsingeeKey = batchconsingeeKey;
    }

    public String getBatchgoodsKey() {
        return batchgoodsKey;
    }

    public void setBatchgoodsKey(String batchgoodsKey) {
        this.batchgoodsKey = batchgoodsKey;
    }

    public List<String> getXlsErrorMsg() {
        return xlsErrorMsg;
    }

    public void setXlsErrorMsg(List<String> xlsErrorMsg) {
        this.xlsErrorMsg = xlsErrorMsg;
    }

    public List<CscContantAndCompanyInportDto> getCscContantAndCompanyInportDtoList() {
        return cscContantAndCompanyInportDtoList;
    }

    public void setCscContantAndCompanyInportDtoList(List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList) {
        this.cscContantAndCompanyInportDtoList = cscContantAndCompanyInportDtoList;
    }

    public List<OfcGoodsImportDto> getCscGoodsImportDtoList() {
        return cscGoodsImportDtoList;
    }

    public void setCscGoodsImportDtoList(List<OfcGoodsImportDto> cscGoodsImportDtoList) {
        this.cscGoodsImportDtoList = cscGoodsImportDtoList;
    }
}
