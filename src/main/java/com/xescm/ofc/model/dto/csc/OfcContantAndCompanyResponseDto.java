package com.xescm.ofc.model.dto.csc;

import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by wangsongtao on 2016/12/26.
 */
@Data
public class OfcContantAndCompanyResponseDto extends CscContantAndCompanyResponseDto implements Serializable{

    private static final long serialVersionUID = 1L;
    private String custOrderCode;
}
