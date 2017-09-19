package com.xescm.ofc.model.dto.ofc;

import com.xescm.ofc.domain.OfcFundamentalInformation;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * Created by hujintao on 2017/7/11.
 */
@ApiModel("订单基本信息dto")
@Data
public class OfcFundamentalInformationDTO extends OfcFundamentalInformation implements Serializable{

    private static final long serialVersionUID = 5255974114874254679L;
}
