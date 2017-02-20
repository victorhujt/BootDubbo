package com.xescm.ofc.model.dto.form;

import lombok.Data;

/**
 *
 * Created by lyh on 2017/2/20.
 */
@Data
public class TemplateCondition {
    private String custName;
    private String templateType;
    private String templateName;
    private Integer pageNum;
    private Integer pageSize;
}
