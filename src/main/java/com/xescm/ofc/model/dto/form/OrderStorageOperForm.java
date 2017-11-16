package com.xescm.ofc.model.dto.form;

import lombok.Data;

import java.util.List;

/**
 * Created by hujintao on 2017/2/22.
 */
@Data
public class OrderStorageOperForm extends OrderOperForm{
    private List<String> businessTypes;
}
