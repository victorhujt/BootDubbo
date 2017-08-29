package com.xescm.ofc.web.restcontroller.operationWorkbench;

import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.dto.component.req.Select2ReqDto;
import com.xescm.base.model.dto.component.resp.Select2RespDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.JacksonUtil;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.CscGoodsApiDto;
import com.xescm.csc.model.dto.CscSupplierInfoDto;
import com.xescm.csc.model.dto.QueryCustomerNameAvgueDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.model.dto.goodstype.CscGoodsTypeDto;
import com.xescm.csc.model.vo.CscCustomerVo;
import com.xescm.csc.model.vo.CscGoodsApiVo;
import com.xescm.csc.model.vo.CscGoodsTypeVo;
import com.xescm.csc.provider.*;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.dto.csc.CscGoodsApiDTO;
import com.xescm.ofc.model.dto.ofc.GoodsCategoryDTO;
import com.xescm.ofc.model.dto.ofc.QueryCustByNameDTO;
import com.xescm.ofc.service.OfcOperCommonService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by lyh on 2017/7/5.
 */
@Controller
@RequestMapping(value = "/page/ofc/common")
@Api(value = "OfcOperCommonController", tags = "OfcOperCommonController", description = "OFC运营公用", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcOperCommonController extends BaseController{

    @Resource
    private CscCustomerEdasService cscCustomerEdasService;
    @Resource
    private CscContactEdasService cscContactEdasService;
    @Resource
    private CscSupplierEdasService cscSupplierEdasService;
    @Resource
    private CscGoodsTypeEdasService cscGoodsTypeEdasService;
    @Resource
    private OfcOperCommonService ofcOperCommonService;
    @Resource
    private CscGoodsEdasService cscGoodsEdasService;

    /**
     * Select2查询客户
     */
    @RequestMapping(value = "/queryCustsBySelect2", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(notes = "返回客户列表", httpMethod = "POST", value = "Select2查询客户")
    public Wrapper<PageInfo<Select2RespDto>> queryCustsBySelect2(@RequestBody Page<String> reqParam) {
        logger.info("Select2查询客户==>{}", reqParam);
        Wrapper<PageInfo<Select2RespDto>> wrapper;
        PageInfo<Select2RespDto> result;
        try {
            Select2ReqDto select2ReqDto = new Select2ReqDto();
            BeanUtils.copyProperties(reqParam, select2ReqDto);
            select2ReqDto.setName(reqParam.getParam());
            wrapper = cscCustomerEdasService.queryCustomerListPageWithSelect2(select2ReqDto);
            if (null == wrapper) throw new BusinessException("Select2查询客户为空");
            result = wrapper.getResult();
        } catch (BusinessException e) {
            logger.error("Select2查询客户出错:{}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("Select2查询客户出错:{}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }







    @RequestMapping(value = "/querySupplierSelect2/{customerCode}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(notes = "返回供应商列表", httpMethod = "POST", value = "Select2查询供应商")
    public Wrapper<PageInfo<Select2RespDto>> querySupplierByName(@ApiParam(name = "select2ReqDto", value = "select2查询信息") @RequestBody  Page<String> page,@PathVariable String customerCode) {
        Wrapper<PageInfo<Select2RespDto>> result = new Wrapper<>();
        try {
            if (PubUtils.isSEmptyOrNull(customerCode)) {
                throw new BusinessException("客户编码不能为空");
            }
            CscSupplierInfoDto queryParam = new CscSupplierInfoDto();
            queryParam.setCustomerCode(customerCode);
            queryParam.setPNum(page.getPageNum());
            queryParam.setPSize(page.getPageSize());
            if (page.getParam() != null) {
                if (!PubUtils.isSEmptyOrNull(page.getParam())) {
                    queryParam.setSupplierName(page.getParam());
                }
            }
            Wrapper<PageInfo<CscSupplierInfoDto>> pageInfoWrapper =  cscSupplierEdasService.querySupplierByAttributePageList(queryParam);
            result.setCode(pageInfoWrapper.getCode());
            result.setMessage(pageInfoWrapper.getMessage());
            PageInfo<CscSupplierInfoDto> resultForRevert = pageInfoWrapper.getResult();
            if (null == resultForRevert || CollectionUtils.isEmpty(resultForRevert.getList())) {
                logger.error("查询供应商名称Select2失败, resultForRevert:{}", ToStringBuilder.reflectionToString(resultForRevert));
                throw new BusinessException("没有查询到供应商");
            }
            PageInfo<Select2RespDto> pageInfo = new PageInfo<>();
            org.apache.commons.beanutils.BeanUtils.copyProperties(pageInfo, resultForRevert);
            pageInfo.setList(null);
            List<Select2RespDto> select2RespDtoList = new ArrayList<>();
            for (CscSupplierInfoDto cscSupplierInfoDto : resultForRevert.getList()) {
                Select2RespDto select2RespDto = new Select2RespDto();
                select2RespDto.setId(cscSupplierInfoDto.getSupplierId());
                select2RespDto.setCode(cscSupplierInfoDto.getSupplierCode());
                select2RespDto.setName(cscSupplierInfoDto.getSupplierName());
                select2RespDtoList.add(select2RespDto);
            }
            pageInfo.setList(select2RespDtoList);
            if (Wrapper.ERROR_CODE == result.getCode()) {
                logger.error("查询供应商列表失败,查询结果有误!");
            }
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException ex) {
            logger.error("==>查询供应商列名称Select2根据客户编码查询供应商发生错误：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("==>查询供应商列名称Select2根据供应商列名称查询客户发生异常：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "查询供应商列名称Select2根据客户编码查询供应商列发生异常！");
        }
        return result;
    }

    /**
     * 货品类别(调用客户中心API)
     */



    @ApiOperation(
            notes = "筛选货品",
            httpMethod = "POST",
            value = "筛选货品"
    )
    @RequestMapping(value = "/getCscGoodsTypeList", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<?> getCscGoodsTypeList(@ApiParam(name = "cscGoodsType",value = "类id" ) @RequestBody GoodsCategoryDTO cscGoodsType) {
        logger.info("下单货品筛选==> cscGoodsType={}", cscGoodsType);
        //调用外部接口,最低传CustomerCode
        try{
            CscGoodsTypeDto cscGoodType=new CscGoodsTypeDto();
            if (!PubUtils.trimAndNullAsEmpty(cscGoodsType.getCscGoodsType()).equals("")) {
                cscGoodType.setPid(cscGoodsType.getCscGoodsType());
            }
            Wrapper<List<CscGoodsTypeVo>> CscGoodsType = cscGoodsTypeEdasService.getCscGoodsTypeList(cscGoodType);
            logger.info("===========================" + CscGoodsType);
            logger.info("###############返回货品类别列表为{}####################",JacksonUtil.toJsonWithFormat(CscGoodsType.getResult()));
            return  WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, CscGoodsType);
        } catch (Exception ex) {
            logger.error("订单中心筛选货品出现异常:{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, "根据查询条件筛选货品发生异常！");
        }
    }

    /**
     * 分页查询收发货方
     */
    @RequestMapping(value = "/queryConsignByPage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(notes = "返回收发货方列表", httpMethod = "POST", value = "分页查询收发货方")
    public Wrapper queryConsignByPage(@RequestBody CscContantAndCompanyDto cscContantAndCompanyDto) {
        logger.info("==>分页查询收发货方,cscContantAndCompanyDto = {}",cscContantAndCompanyDto);
        //调用外部接口,最低传CustomerCode和purpose
        PageInfo<CscContantAndCompanyResponseDto> result;
        getAuthResDtoByToken();
        try {
            cscContantAndCompanyDto.getCscContactCompanyDto().setContactCompanyName(PubUtils.trimAndNullAsEmpty(cscContantAndCompanyDto.getCscContactCompanyDto().getContactCompanyName()));
            cscContantAndCompanyDto.getCscContactDto().setContactName(PubUtils.trimAndNullAsEmpty(cscContantAndCompanyDto.getCscContactDto().getContactName()));
            cscContantAndCompanyDto.getCscContactDto().setPhone(PubUtils.trimAndNullAsEmpty(cscContantAndCompanyDto.getCscContactDto().getPhone()));
            Wrapper<PageInfo<CscContantAndCompanyResponseDto>> pageInfoWrapper = cscContactEdasService.queryCscReceivingInfoListWithPage(cscContantAndCompanyDto);
            if (null == pageInfoWrapper || pageInfoWrapper.getCode() != Wrapper.SUCCESS_CODE) throw new BusinessException("下单收发货方筛选接口出错");
            result = pageInfoWrapper.getResult();
        } catch (BusinessException e) {
            logger.error("分页查询收发货方:{}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("分页查询收发货方:{}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * 分页查询货品列表
     */
    @RequestMapping(value = "/queryGoodsByPage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(notes = "返回货品列表", httpMethod = "POST", value = "Select2查询客户")
    public Wrapper queryGoodsByPage(@RequestBody CscGoodsApiDTO cscGoodsApiDto) {
        logger.info("==>分页查询货品列表,cscGoodsApiDto = {}",cscGoodsApiDto);
        //调用外部接口,最低传CustomerCode和purpose
        PageInfo<CscGoodsApiVo> result;
        getAuthResDtoByToken();
        try {
            result = ofcOperCommonService.queryGoodsByPage(cscGoodsApiDto);
        } catch (BusinessException e) {
            logger.error("分页查询货品列表:{}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("分页查询货品列表:{}", e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,Wrapper.ERROR_MESSAGE);
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, result);
    }

    /**
     * 仓储下单货品筛选 不带包装
     * @param page
     * @param
     * @return
     */
    @RequestMapping(value = "/goodsSelectsStorage",method = RequestMethod.POST)
    @ApiOperation(notes = "查询带包装的货品列表", httpMethod = "POST", value = "查询货品")
    @ResponseBody
    public Object goodsSelectsStorage(@RequestBody  Page<CscGoodsApiDto> page) {
        Wrapper<PageInfo<CscGoodsApiVo>> cscGoodsLists = null;
        try{
            CscGoodsApiDto cscGoodsApiDto = page.getParam();
            if (cscGoodsApiDto == null) {
                throw new BusinessException("查询货品的dto不能为空");
            }
            logger.info("==>仓储下单货品筛选,cscGoods = {}",JacksonUtil.toJson(cscGoodsApiDto));
            if (PubUtils.isSEmptyOrNull(cscGoodsApiDto.getCustomerCode())) {
                throw new BusinessException("客户编码不能为空");
            }

            if (PubUtils.isSEmptyOrNull(cscGoodsApiDto.getWarehouseCode())) {
                throw new BusinessException("仓库编码不能为空");
            }
            cscGoodsApiDto.setFromSys("WMS");//只要WMS渠道的货品
            cscGoodsApiDto.setPNum(page.getPageNum());
            cscGoodsApiDto.setPSize(page.getPageSize());
            cscGoodsLists = cscGoodsEdasService.queryCscGoodsPageListByFuzzy(cscGoodsApiDto);
            logger.info("===>查询货品的结果为:{}",JacksonUtil.toJson(cscGoodsLists));
        } catch (Exception ex) {
            logger.error("订单中心仓储下单筛选货品出现异常:{}", ex.getMessage(), ex);
        }
        return cscGoodsLists;
    }

    @RequestMapping(value = "/getMerchandiser",method = RequestMethod.POST)
    @ResponseBody
    public Object getMerchandiser() {
        String userName;
        try {
            userName = getAuthResDtoByToken().getUserName();
        } catch (Exception ex) {
            logger.error("获取当前用户异常{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功",userName);
    }

    /**
     * 运营中心货品筛选(调用客户中心API) 运输、城配开单货品筛选
     * @param page 货品筛选条件
     * @return
     */
    @RequestMapping(value = "/goodsSelects",method = RequestMethod.POST)
    @ResponseBody
    public Object goodsSelectByCsc(@RequestBody Page<CscGoodsApiDto> page) {
        //调用外部接口,最低传CustomerCode
        Wrapper<PageInfo<CscGoodsApiVo>> cscGoodsLists  = null;
        try{
            if (page == null) {
                throw new Exception("货品筛选dto不能为空");
            }
            CscGoodsApiDto cscGoodsApiDto = page.getParam();
            if (PubUtils.isSEmptyOrNull(cscGoodsApiDto.getCustomerCode())) {
                throw new Exception("客户编码不能为空");
            }
            cscGoodsApiDto.setPSize(page.getPageSize());
            cscGoodsApiDto.setPNum(page.getPageNum());
            cscGoodsLists = cscGoodsEdasService.queryCscGoodsPageList(cscGoodsApiDto);
        } catch (Exception ex) {
            logger.error("订单中心筛选货品出现异常:{}", ex.getMessage(), ex);
        }
        return cscGoodsLists;
    }

    /**
     * 根据客户名称分页查询客户
     *
     * @return
     */
    @RequestMapping(value = "/queryCustomerByName", method = RequestMethod.POST)
    @ResponseBody
    public Object queryCustomerByName(@RequestBody QueryCustByNameDTO custDTO) {
        logger.info("根据客户名称查询客户==> custDTO={}", custDTO);
        Wrapper<PageInfo<CscCustomerVo>> result;
        try {
            QueryCustomerNameAvgueDto queryParam = new QueryCustomerNameAvgueDto();
            queryParam.setCustomerName(custDTO.getCustName());
            queryParam.setPageNum(custDTO.getPageNum());
            queryParam.setPageSize(custDTO.getPageSize());
            result = cscCustomerEdasService.queryCustomerByNameAvgue(queryParam);
            if (Wrapper.ERROR_CODE == result.getCode()) {
                logger.error("查询客户列表失败,查询结果有误!");
            }
        } catch (BusinessException ex) {
            logger.error("==>根据客户名称查询客户发生错误：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("==>根据客户名称查询客户发生错误：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "根据客户名称查询客户发生错误！");
        }
        return result;
    }

    @RequestMapping(value = "/getOperatorUrl", method = RequestMethod.POST)
    @ResponseBody
    public Wrapper<AuthResDto> getOperatorUrl() {
        logger.info("查询当前操作人");
        Wrapper<AuthResDto> result;
        try {
            AuthResDto authResDtoByToken = getAuthResDtoByToken();
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, authResDtoByToken);
        } catch (BusinessException ex) {
            logger.error("==>查询当前操作人发生错误：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("==>查询当前操作人发生错误：{}", ex);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "查询当前操作人发生错误！");
        }
        return result;
    }
}
