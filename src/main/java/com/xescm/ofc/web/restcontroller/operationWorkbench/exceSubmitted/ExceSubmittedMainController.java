package com.xescm.ofc.web.restcontroller.operationWorkbench.exceSubmitted;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.dto.auth.AuthResDto;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.exception.BusinessException;
import com.xescm.ofc.domain.ExceSubmitted;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.model.dto.exce.ExceSubmittedQueryDto;
import com.xescm.ofc.model.vo.exce.ExceSubmittedVo;
import com.xescm.ofc.service.ExceSubmittedService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: ExceSubmittedMainController. </p>
 * <p>异常报送业务Controller </p>
 * @param
 * @Author 袁宝龙
 * @CreateDate 2017/11/7 14:02
 * @return
 */
@Controller
@RequestMapping(value = "/page/ofc/exce/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "ExceSubmittedMainController", tags = "ExceSubmittedMainController", description = "异常报送业务", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ExceSubmittedMainController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(ExceSubmittedMainController.class);

    @Autowired
    private ExceSubmittedService exceSubmittedService;

    @RequestMapping("/exceSubmittedEdit")
    @ResponseBody
    public Wrapper<?> exceSubmittedEdit(@RequestBody ExceSubmitted exceSubmitted) {
        logger.info("==>新增异常报送,exceSubmitted={}", exceSubmitted);
        AuthResDto authResDto = getAuthResDtoByToken();
        try {
            exceSubmittedService.exceSubmittedEdit(exceSubmitted, authResDto);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
        } catch (BusinessException ex) {
            logger.error("新增异常报送出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("新增异常报送出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/checkOrderCodeExists/{orderCode}", method = {RequestMethod.POST})
    @ApiOperation(value = "校验订单号是否存在", httpMethod = "POST", notes = "通过id查询异常报送录入信息")
    public Wrapper<?> checkOrderCodeExists(@ApiParam(name = "orderCode", value = "订单号") @PathVariable String orderCode) {
        try {
            Boolean flag = false;
            int num = exceSubmittedService.checkOrderCodeExists(orderCode);
            if (num > 0) {
                flag = true;
            }
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, flag);
        } catch (BusinessException ex) {
            logger.error("校验订单号是否存在出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("校验订单号是否存在出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/queryExceSubmittedById/{id}", method = {RequestMethod.POST})
    @ApiOperation(value = "通过id查询异常报送录入信息", httpMethod = "POST", notes = "通过id查询异常报送录入信息")
    public Wrapper<?> queryExceSubmittedById(@ApiParam(name = "id", value = "异常报送id") @PathVariable String id) {
        try {
            ExceSubmitted exceSubmitted = exceSubmittedService.queryExceSubmittedById(id);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, exceSubmitted);
        } catch (BusinessException ex) {
            logger.error("通过id查询异常报送录入信息出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("通过id查询异常报送录入信息出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @RequestMapping("/exceSubmittedCancel")
    @ResponseBody
    public Wrapper<?> exceSubmittedCancel(@RequestBody String id) {
        logger.info("==>异常报送取消,id={}", id);
        try {
            exceSubmittedService.exceSubmittedCancel(id);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
        } catch (BusinessException ex) {
            logger.error("异常报送取消出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("异常报送取消出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    @RequestMapping("/exceHandle")
    @ResponseBody
    public Wrapper<?> exceHandle(@RequestBody ExceSubmitted exceSubmitted) {
        logger.info("==>异常报送进行处理,exceSubmitted={}", exceSubmitted);
        try {
            exceSubmittedService.exceHandle(exceSubmitted);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
        } catch (BusinessException ex) {
            logger.error("异常报送进行处理出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("异常报送进行处理出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

    /**
     * 前后端分离，合同列表查询
     */
    @ResponseBody
    @RequestMapping(value = "/queryExceSubmittedList", method = {RequestMethod.POST})
    @ApiOperation(value = "分页查询异常报送信息列表", httpMethod = "POST", notes = "返回异常报送信息列表")
    public Wrapper<?> queryExceSubmittedList(@ApiParam(name = "page", value = "异常报送信息列表分页") @RequestBody Page<ExceSubmittedQueryDto> page) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<ExceSubmittedVo> dataList = exceSubmittedService.queryExceSubmittedList(page.getParam());
            PageInfo<ExceSubmittedVo> pageInfo = new PageInfo<>(dataList);
            return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException ex) {
            logger.error("分页查询异常报送信息列表出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("分页查询异常报送信息列表出错：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }

}
