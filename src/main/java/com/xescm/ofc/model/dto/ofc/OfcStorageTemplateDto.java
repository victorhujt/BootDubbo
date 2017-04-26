package com.xescm.ofc.model.dto.ofc;

import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 模板导入实体
 * Created by lyh on 2017/2/23.
 */
@Data
public class OfcStorageTemplateDto {
    private String orderBatchNumber;
    private String custOrderCode;
    private String orderTime;
    private String merchandiser;
    private String warehouseCode;
    private String warehouseName;
    private String businessType;
    private String notes;
    private String goodsCode;
    private String goodsName;
    private String goodsSpec;
    private String unit;
    private BigDecimal unitPrice;
    private BigDecimal quantity;//出入库数量
    private String productionBatch;
    private String productionTime;
    private String invalidTime;
    private String supportName;
    private String arriveTime;//预计入库时间
    private String shipmentTime;//预计出库时间
    private String provideTransport;
    private String plateNumber;
    private String driverName;
    private String contactNumber;
    private String consigneeName;//收货方名称
    //上面部分字段是Excel表中的字段, 但是下单的话还需要其他字段的补充
    private CscContantAndCompanyResponseDto cscConsigneeDto = new CscContantAndCompanyResponseDto();
    private CscContantAndCompanyResponseDto consignor = new CscContantAndCompanyResponseDto();
    private CscGoodsApiVo cscGoodsApiVo = new CscGoodsApiVo();
    private CscSupplierInfoDto cscSupplierInfoDto = new CscSupplierInfoDto();
    private CscSupplierInfoDto goodsSupplier = new CscSupplierInfoDto();
    private OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();
    //字段追加: 发货波次号
    private String consignmentBatchNumber;
    //收货人编码
    private String consigneeContactCode;
    //供应商编码
    private String supportCode;
    //供应商批次
    private String supportBatch;
    //运输单号
    private String transCode;
    //发货方名称
    private String consignorName;

}