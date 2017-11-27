package com.xescm.ofc.model.vo.ofc;

import com.xescm.csc.model.dto.CscContantAndCompanyInportDto;
import com.xescm.ofc.model.dto.csc.OfcGoodsImportDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2016/12/16.
 */
@Data
public class OfcCheckExcelErrorVo {
    private List<String> xlsErrorMsg = new ArrayList<>();
    private List<CscContantAndCompanyInportDto> cscContantAndCompanyInportDtoList = new ArrayList<>();
    private List<OfcGoodsImportDto> cscGoodsImportDtoList = new ArrayList<>();
    private String batchconsingeeKey;
    private String batchgoodsKey;


}
