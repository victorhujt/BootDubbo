package com.xescm.ofc.model.vo.exce;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xescm.ofc.domain.ExceSubmitted;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Title: ExceSubmittedVo. </p>
 * <p>Description 异常报送VO </p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/11/7
 */
@Data
public class ExceSubmittedVo {
    /**
     * 附件流水号
     */
    private String imgSerialNo;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件存储相对路径
     */
    private String path;

    /**
     * 主键
     */
    private String id;

    /**
     * 异常编号
     */
    private String exceCode;

    /**
     * 客户编码
     */
    private String customerCode;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 异常类型
     */
    private String exceType;

    /**
     * 订单号
     */
    private String orderCode;

    /**
     * 发生时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date occurrenceTime;

    /**
     * 货损产品
     */
    private String damageProduct;

    /**
     * 异常数量
     */
    private String damageProductNum;

    /**
     * 货损金额
     */
    private BigDecimal losses;

    /**
     * 负责人
     */
    private String responsiblePerson;

    /**
     * 处理状态
     */
    private String exceState;

    /**
     * 上报时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportTime;

    /**
     * 上报大区名称
     */
    private String reportRegionName;

    /**
     * 上报基地名称
     */
    private String reportBaseName;

    /**
     * 开单基地名称 回显
     */
    private String trueBaseName;

    /**
     * 开单基地名称
     */
    private String baseName;

    /**
     * 开单大区名称
     */
    private String areaName;

    /**
     * 最近操作人
     */
    private String lastOperator;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 用户操作相关tag
     */
    private String operateTag;
}
