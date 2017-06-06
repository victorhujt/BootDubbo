package com.xescm.ofc.web.restcontroller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcTaskInterfaceLog;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.model.vo.ofc.OfcTaskInterfaceLogVo;
import com.xescm.ofc.service.OfcTaskInterfaceLogService;
import com.xescm.ofc.web.controller.BaseController;
import org.springframework.stereotype.Controller;
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
    public Wrapper<PageInfo<OfcTaskInterfaceLog>> queryTaskLog(Page<OfcTaskInterfaceLog> page, OfcTaskInterfaceLogVo taskParam) {
        logger.info("==>查询任务日志：{}", taskParam);
        Wrapper<PageInfo<OfcTaskInterfaceLog>> result;
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<OfcTaskInterfaceLog> taskList = taskInterfaceLogService.queryTaskInterfaceLog(taskParam);
            PageInfo<OfcTaskInterfaceLog> pageInfo = new PageInfo<>(taskList);
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
