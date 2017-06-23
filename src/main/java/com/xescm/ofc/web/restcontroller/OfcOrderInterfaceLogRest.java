package com.xescm.ofc.web.restcontroller;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 订单接口管理
 * @author: nothing
 * @date: 2017/6/5 14:27
 */
@Controller
@RequestMapping(value = "/ofc/interface", produces = {"application/json;charset=UTF-8"})
public class OfcOrderInterfaceLogRest extends BaseController {

    @Resource
    private OfcTaskInterfaceLogService taskInterfaceLogService;
    @Resource
    private OfcInterfaceReceiveLogService receiveLogService;

    /**
     * <p>Title:      queryTaskLog. </p>
     * <p>Description 查询任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/5 14:42
     * @return
     */
    @RequestMapping(value = "/queryTaskLog", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<PageInfo<OfcTaskInterfaceLogVo>> queryTaskLog(Page<OfcTaskInterfaceLogVo> page, OfcTaskInterfaceLogVo taskParam) {
        Wrapper<PageInfo<OfcTaskInterfaceLogVo>> result;
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OfcTaskInterfaceLogVo> taskList = taskInterfaceLogService.queryTaskInterfaceLog(taskParam);
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
     * <p>Title:      queryTaskLogDetailByID. </p>
     * <p>Description 查询任务日志明细</p>
     *
     * @param         id 任务日志ID
     * @Author	      nothing
     * @CreateDate    2017/6/8 14:38
     * @return
     */
    @RequestMapping(value = "/queryTaskLogDetailById/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Wrapper<OfcTaskInterfaceLogVo> queryTaskLogDetailByID(@PathVariable Long id) {
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

    /**
     * <p>Title:      delTaskLogByID. </p>
     * <p>Description 删除任务日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/8 16:36
     * @return
     */
    @RequestMapping(value = "/delTaskLogById/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Wrapper delTaskLogById(@PathVariable Long id) {
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
     * <p>Title:      resendTaskLogById. </p>
     * <p>Description 重发任务</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/8 16:43
     * @return
     */
    @RequestMapping(value = "/resendTaskLogById/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Wrapper resendTaskLogById(@PathVariable Long id) {
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
     * <p>Title:      queryReceiveLog. </p>
     * <p>Description 查询接收日志</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/9 11:34
     * @return
     */
    @RequestMapping(value = "/queryReceiveLog", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<PageInfo<OfcInterfaceReceiveLogVo>> queryReceiveLog(Page<OfcInterfaceReceiveLogVo> page, OfcInterfaceReceiveLogVo logParam) {
        Wrapper<PageInfo<OfcInterfaceReceiveLogVo>> result;
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OfcInterfaceReceiveLogVo> taskList = receiveLogService.queryInterfaceReceiveLog(logParam);
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

    /**
     * <p>Title:      queryReceiveLogDetailById. </p>
     * <p>Description 查询接收日志明细</p>
     *
     * @param
     * @Author	      nothing
     * @CreateDate    2017/6/9 11:36
     * @return
     */
    @RequestMapping(value = "/queryReceiveLogDetailById/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Wrapper<OfcInterfaceReceiveLogVo> queryReceiveLogDetailById(@PathVariable String id) {
        Wrapper<OfcInterfaceReceiveLogVo> result;
        try {
            if (id != null) {
                OfcInterfaceReceiveLogVo param = new OfcInterfaceReceiveLogVo();
                param.setId(id);
                List<OfcInterfaceReceiveLogVo> taskList = receiveLogService.queryInterfaceReceiveLog(param);
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
}
