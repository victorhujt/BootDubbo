package com.xescm.ofc.model.dto.csc;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title:    CscContantAndCompanyResponseDto. </p>
 * <p>Description 封装联系人与联系人所属公司DTO </p>
 * <p>Company:    http://www.hnxianyi.com </p>
 *
 * @Author <a href="gsfeng_2015@163.com"/>冯光帅</a>
 * @CreateDate 2016/12/12 11:19
 */
@Data
public class CscContantAndCompanyResponseDto implements Serializable {


    private static final long serialVersionUID = -5252320843657865674L;
    private String customerCode;
    private String contactCompanyName;
    private String contactCompanySerialNo;//
//    private String contactCompanyCode;
    private String type;
    private String contactSerialNo;//
//    private String contactCode;
    private String phone;
    private String fax;
    private String email;
    private String contactName;
    private String postCode;
    private String province;
    private String provinceName;
    private String city;
    private String cityName;
    private String area;
    private String areaName;
    private String street;
    private String streetName;
    private String address;
    private String detailAddress;
    private String contactJob;
    private String purpose;
    private String creator;
    private String creatorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    private String lastOperator;
    private String lastOperatorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

}
