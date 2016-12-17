package com.xescm.ofc.model.vo.ofc;

import com.alibaba.fastjson.JSONArray;
import com.xescm.ofc.model.dto.csc.CscContantAndCompanyInportDto;
import com.xescm.ofc.model.dto.csc.CscGoodsImportDto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2016/12/16.
 */
public class OfcCheckExcelErrorVo {
    private List<String> xlsErrorMsg = new ArrayList<>();
    private List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = new ArrayList<>();
    private List<CscGoodsImportDto> cscGoodsImportDtoList = new ArrayList<>();
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

    public List<CscGoodsImportDto> getCscGoodsImportDtoList() {
        return cscGoodsImportDtoList;
    }

    public void setCscGoodsImportDtoList(List<CscGoodsImportDto> cscGoodsImportDtoList) {
        this.cscGoodsImportDtoList = cscGoodsImportDtoList;
    }
}
