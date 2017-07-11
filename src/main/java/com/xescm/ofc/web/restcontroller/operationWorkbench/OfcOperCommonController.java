package com.xescm.ofc.web.restcontroller.operationWorkbench;

import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.component.req.Select2ReqDto;
import com.xescm.base.model.dto.component.resp.Select2RespDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyDto;
import com.xescm.csc.model.dto.contantAndCompany.CscContantAndCompanyResponseDto;
import com.xescm.csc.provider.CscContactEdasService;
import com.xescm.csc.provider.CscCustomerEdasService;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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

    /**
     * 分页查询收发货方
     */
    @RequestMapping(value = "/queryConsignByPage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(notes = "返回收发货方列表", httpMethod = "POST", value = "Select2查询客户")
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

}
