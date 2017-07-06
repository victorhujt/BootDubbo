package com.xescm.ofc.web.restcontroller.operationWorkbench.interfaceLog;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.utils.PubUtils;
import com.xescm.ofc.domain.Page;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.vo.ofc.OfcInterfaceReceiveLogVo;
import com.xescm.ofc.model.vo.ofc.OfcTaskInterfaceLogVo;
import com.xescm.ofc.service.OfcInterfaceReceiveLogService;
import com.xescm.ofc.service.OfcTaskInterfaceLogService;
import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 接口日志
 * @author: nothing
 * @date: 2017/7/6 14:21
 */
@Controller
@RequestMapping(value = "/page/ofc/interface", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "OfcOrderInterfaceLogController", tags = "OfcOrderInterfaceLogController", description = "接口日志", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcOrderInterfaceLogController extends BaseController {
    @Resource
    private OfcTaskInterfaceLogService taskInterfaceLogService;
    @Resource
    private OfcInterfaceReceiveLogService ofcInterfaceReceiveLogService;

    /**
     * <p>Title:      queryInterfaceTaskLogPage. </p>
     * <p>Description 分页查询任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/7/6 15:44
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryInterfaceTaskLogPage", method = {RequestMethod.POST})
    @ApiOperation(value = "分页查询任务日志", httpMethod = "POST", notes = "返回任务日志列表")
    public Wrapper<PageInfo<OfcTaskInterfaceLogVo>> queryInterfaceTaskLogPage(@ApiParam(name = "page", value = "任务日志分页") @RequestBody Page<OfcTaskInterfaceLogVo> page) {
        Wrapper<PageInfo<OfcTaskInterfaceLogVo>> result;
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OfcTaskInterfaceLogVo> taskList = taskInterfaceLogService.queryTaskInterfaceLog(page.getParam());
            PageInfo<OfcTaskInterfaceLogVo> pageInfo = new PageInfo<>(taskList);
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException e) {
            logger.error("{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("查询任务日志发生未知异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "查询任务日志发生未知异常!");
        }
        return result;
    }

    /**
     * <p>Title:      delInterfaceTaskLogById. </p>
     * <p>Description 删除任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/7/6 15:44
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delInterfaceTaskLogById/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "删除任务日志", httpMethod = "POST", notes = "返回任务删除结果")
    public Wrapper delInterfaceTaskLogById(@ApiParam(name = "id", value = "任务日志id") @PathVariable Long id) {
        Wrapper result;
        try {
            int res = taskInterfaceLogService.delTaskLogById(id);
            if (res > 0) {
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
            } else {
                result = WrapMapper.wrap(Wrapper.ERROR_CODE, "删除任务日志失败！");
            }
        } catch (BusinessException e) {
            logger.error("删除任务日志发生异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "删除任务日志失败！");
        } catch (Exception e) {
            logger.error("删除任务日志发生未知异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "删除任务日志失败！");
        }
        return result;
    }

    /**
     * <p>Title:      resendInterfaceTaskLogById. </p>
     * <p>Description 重发任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/7/6 15:44
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resendInterfaceTaskLogById/{id}", method = {RequestMethod.POST})
    @ApiOperation(value = "重发任务日志", httpMethod = "POST", notes = "返回任务重发结果")
    public Wrapper resendInterfaceTaskLogById(@ApiParam(name = "id", value = "任务日志id") @PathVariable Long id) {
        Wrapper result;
        try {
            int res = taskInterfaceLogService.resendTaskLogById(id);
            if (res > 0) {
                result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
            } else {
                result = WrapMapper.wrap(Wrapper.ERROR_CODE, "重发任务日志失败！");
            }
        } catch (BusinessException e) {
            logger.error("重发任务日志发生异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "重发任务日志失败！");
        } catch (Exception e) {
            logger.error("重发任务日志发生未知异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "重发任务日志失败！");
        }
        return result;
    }

    /**
     * <p>Title:      queryTaskLogDetailById. </p>
     * <p>Description 查询任务日志详情</p>
     * 
     * @param         
     * @Author	      nothing
     * @CreateDate    2017/7/6 17:34
     * @return        
     */
    @ResponseBody
    @RequestMapping(value = "/queryInterfaceTaskLogDetailById/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiOperation(value = "查询任务日志详情", httpMethod = "POST", notes = "返回任务详情")
    public Wrapper<OfcTaskInterfaceLogVo> queryInterfaceTaskLogDetailById(@ApiParam(name = "id", value = "任务日志id") @PathVariable Long id) {
        Wrapper<OfcTaskInterfaceLogVo> result;
        try {
            if (id != null) {
                OfcTaskInterfaceLogVo param = new OfcTaskInterfaceLogVo();
                param.setId(id);
                List<OfcTaskInterfaceLogVo> taskList = taskInterfaceLogService.queryTaskInterfaceLog(param);
                if (PubUtils.isNotNullAndBiggerSize(taskList, 0)) {
                    result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, taskList.get(0));
                } else {
                    result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, null);
                }
            } else {
                result = WrapMapper.wrap(Wrapper.ILLEGAL_ARGUMENT_CODE_, Wrapper.ILLEGAL_ARGUMENT_MESSAGE);
            }
        } catch (BusinessException e) {
            logger.error("查询任务日志信息发生异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("查询任务日志信息发生未知异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "查询任务日志信息发生未知异常!");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/queryInterfaceReceiveLogPage", method = {RequestMethod.POST})
    @ApiOperation(value = "分页查询接收日志", httpMethod = "POST", notes = "返回接收日志列表")
    public Wrapper<PageInfo<OfcInterfaceReceiveLogVo>> queryInterfaceReceiveLogPage(@ApiParam(name = "page", value = "接收日志分页") @RequestBody Page<OfcInterfaceReceiveLogVo> page) {
        Wrapper<PageInfo<OfcInterfaceReceiveLogVo>> result;
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OfcInterfaceReceiveLogVo> taskList = ofcInterfaceReceiveLogService.queryInterfaceReceiveLog(page.getParam());
            PageInfo<OfcInterfaceReceiveLogVo> pageInfo = new PageInfo<>(taskList);
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, pageInfo);
        } catch (BusinessException e) {
            logger.error("{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("查询任务日志发生未知异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, "查询任务日志发生未知异常!");
        }
        return result;
    }
}
