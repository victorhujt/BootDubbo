package com.xescm.ofc.model.dto.ofc;

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
    private String custOrderCode;
    private String orderTime;
    private String merchandiser;
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
    private String contactNumbe;
    private String consigneeName;//收货方名称
    //上面部分字段是Excel表中的字段, 但是下单的话还需要其他字段的补充
    private CscContantAndCompanyResponseDto cscConsigneeDto = new CscContantAndCompanyResponseDto();
    private CscGoodsApiVo cscGoodsApiVo = new CscGoodsApiVo();
    private OfcOrderDTO ofcOrderDTO = new OfcOrderDTO();

}