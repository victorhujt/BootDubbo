package com.xescm.ofc.model.dto.ofc;

import com.xescm.ofc.domain.OfcStorageTemplate;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Created by lyh on 2017/7/20.
 */
@Data
public class OfcStorageTemplateEditDTO implements Serializable{
    private List<OfcStorageTemplate> templateList;
    private String lastTemplateType;
}
