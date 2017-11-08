package com.xescm.ofc.model.dto.exce;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>Title: ExceSubmittedQueryDto. </p>
 * <p>Description 查询异常报送列表dto </p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/11/8 11:22
 */
@Data
public class ExceSubmittedQueryDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;

    /**
     * 时间类型
     */
    private String timeOption;

    /**
     * 大区基地类型
     */
    private String areaOption;

    /**
     * 大区编码
     */
    private String areaSerialNo;

    /**
     * 基地编码
     */
    private String baseSerialNo;

    /**
     * 异常类型
     */
    private String exceType;

    /**
     * 处理状态
     */
    private String exceState;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 异常编码
     */
    private String exceCode;

    /**
     * 订单号
     */
    private String orderCode;

}
