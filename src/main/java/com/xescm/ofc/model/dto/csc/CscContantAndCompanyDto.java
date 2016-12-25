package com.xescm.ofc.model.dto.csc;


import com.xescm.ofc.model.dto.base.AuthDto;
import com.xescm.ofc.model.dto.csc.domain.CscContact;
import com.xescm.ofc.model.dto.csc.domain.CscContactCompany;

import java.io.Serializable;

/**
 * 封装联系人信息以及联系人所属的公司
 * Created by gsfeng on 2016/10/18.
 */
public class CscContantAndCompanyDto extends AuthDto implements Serializable {

    private static final long serialVersionUID = 2201253552440049762L;

    // 联系人实体
    private CscContact cscContact;
    // 联系人所属公司实体
    private CscContactCompany cscContactCompany;

    private int pageNum;

    private int pageSize;

    public CscContact getCscContact() {
        return cscContact;
    }

    public void setCscContact(CscContact cscContact) {
        this.cscContact = cscContact;
    }

    public CscContactCompany getCscContactCompany() {
        return cscContactCompany;
    }

    public void setCscContactCompany(CscContactCompany cscContactCompany) {
        this.cscContactCompany = cscContactCompany;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "CscContantAndCompanyDto{" +
                "cscContact=" + cscContact +
                ", cscContactCompany=" + cscContactCompany +
                '}';
    }
}
