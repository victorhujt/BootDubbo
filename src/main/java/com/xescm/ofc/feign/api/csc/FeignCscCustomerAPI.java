package com.xescm.ofc.feign.api.csc;

import com.xescm.ofc.model.dto.csc.*;
import com.xescm.ofc.model.vo.csc.CscContantAndCompanyVo;
import com.xescm.ofc.model.vo.csc.CscCustomerVo;
import com.xescm.uam.utils.wrap.Wrapper;
import feign.Headers;
import feign.RequestLine;

import java.util.List;

/**
 * Created by gsfeng on 2016/10/18.
 */
public interface FeignCscCustomerAPI {

    /**
     * 查询客户中心收发货方联系人
     * @param cscContantAndCompanyDto
     * @return
     */
    @RequestLine("POST /api/csc/customer/queryCscReceivingInfoList")
    @Headers("Content-Type: application/json")
    public Wrapper<List<CscContantAndCompanyVo>> queryCscReceivingInfoList(CscContantAndCompanyDto cscContantAndCompanyDto);

    /**
     * 已弃用
     * @param cscContantAndCompanyDto
     * @return
     */
    @Deprecated
    @RequestLine("POST /api/csc/customer/addCscContantAndCompany")
    @Headers("Content-Type: application/json")
    public Wrapper<?> addCscContantAndCompany(CscContantAndCompanyDto cscContantAndCompanyDto);

    /**
     * 根据GroupId查询客户ID 已弃用
     * @param queryCustomerIdDto
     * @return
     */
    @RequestLine("POST /api/csc/customer/queryCustomerIdByGroupId")
    @Headers("Content-Type: application/json")
    @Deprecated
    public Wrapper<?> queryCustomerIdByGroupId(QueryCustomerIdDto queryCustomerIdDto);

    /**
     * 根据客户ID或CODE查询客户
     * @param queryCustomerCodeDto
     * @return
     */
    @RequestLine("POST /api/csc/customer/queryCustomerByCustomerCodeOrId")
    @Headers("Content-Type: application/json")
    Wrapper<CscCustomerVo> queryCustomerByCustomerCodeOrId(QueryCustomerCodeDto queryCustomerCodeDto);

    /**
     * 根据客户名称查询客户(已经过时)
     * @param queryCustomerNameDto
     * @return
     */
    @RequestLine("POST /api/csc/customer/queryCustomerByName")
    @Headers("Content-Type: application/json")
    @Deprecated
    public Wrapper<?> queryCustomerByName(QueryCustomerNameDto queryCustomerNameDto);


    /**
     * 客户名称模糊查询客户列表
     * @param queryCustomerNameDto
     * @return
     */
    @RequestLine("POST /api/csc/customer/QueryCustomerByNameAvgue")
    @Headers("Content-Type: application/json")
    public Wrapper<?> QueryCustomerByNameAvgue(QueryCustomerNameAvgueDto queryCustomerNameDto);
}