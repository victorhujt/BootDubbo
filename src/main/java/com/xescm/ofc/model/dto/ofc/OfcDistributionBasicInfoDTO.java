package com.xescm.ofc.model.dto.ofc;

import com.xescm.ofc.domain.OfcDistributionBasicInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
@Data
@ApiModel("订单运输信息Dto")
public class OfcDistributionBasicInfoDTO extends OfcDistributionBasicInfo implements Serializable{

    private static final long serialVersionUID = -5002596518696682580L;
}