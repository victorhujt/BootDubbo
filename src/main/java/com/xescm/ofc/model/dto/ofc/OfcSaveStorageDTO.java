package com.xescm.ofc.model.dto.ofc;

import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by hujintao on 2017/7/11.
 */
@ApiModel("保存仓储订单的Dto")
@Data
public class OfcSaveStorageDTO {
    @ApiModelProperty("发货方信息")
    private CscContantAndCompanyDto consignor;
    @ApiModelProperty("收货方信息")
    private CscContantAndCompanyDto consignee;
    @ApiModelProperty("供应商信息")
    private CscSupplierInfoDto  supplier;
    @ApiModelProperty("仓储信息")
    private OfcWarehouseInformationDTO warehouseInformation;
    @ApiModelProperty("订单基本信息")
    private OfcFundamentalInformationDTO fundamentalInformation;
    @ApiModelProperty("订单货品信息")
    private List<OfcGoodsDetailsInfoDTO> goodsDetailsInfo;
    @ApiModelProperty("订单运输信息")
    private OfcDistributionBasicInfoDTO distributionBasicInfo;


}
