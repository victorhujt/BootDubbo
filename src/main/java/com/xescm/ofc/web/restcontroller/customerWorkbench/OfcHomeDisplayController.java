package com.xescm.ofc.web.restcontroller.customerWorkbench;/**
 * Created by IntelliJ IDEA.
 * User: Yangdx.
 * Date: 2017/4/13.
 * Time: 11:57.
 * Tags: Code, we are serious.
 */

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.core.exception.BusinessException;
import com.xescm.ofc.model.dto.ofc.DailyOrderDto;
import com.xescm.ofc.model.dto.ofc.HomeDisplayDto;
import com.xescm.ofc.model.dto.ofc.HomeDisplaySeleDto;
import com.xescm.ofc.service.OfcHomeDisplayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 产品地址数据请求
 *
 * @author  楊東旭
 * @create 2017-04-13 11:57
 **/
@Controller
@RequestMapping(
        value = {"/page/ofc/display"},
        produces = {"application/json;charset=UTF-8"}
)
@Api(
        value = "OfcHomeDisplayController",
        tags = {"首页数据展示"},
        description = "首页数据展示",
        produces = "application/json;charset=UTF-8"
)
public class OfcHomeDisplayController {
    private Logger logger = LoggerFactory.getLogger(OfcHomeDisplayController.class);
    @Resource
    private OfcHomeDisplayService ofcHomeDisplayService;

    @RequestMapping(
            value = {"/homeDisplay"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    @ApiOperation(
            notes = "首页数据展示",
            httpMethod = "POST",
            value = "首页数据展示"
    )
    public Wrapper<?> homeDisplay(@ApiParam(name = "HomeDisplaySeleDto",value = "首页展示查询DTO") @RequestBody HomeDisplaySeleDto homeDisplaySeleDto) {
        this.logger.info("==>首页数据展示。");
        HomeDisplayDto homeDisplayDto = new HomeDisplayDto();
        try {
            homeDisplayDto = ofcHomeDisplayService.homeDisplaySele(homeDisplaySeleDto);
        } catch (BusinessException var3) {
            this.logger.error("首页数据展示, 出现异常={}", var3.getMessage(), var3);
            return WrapMapper.wrap(500, var3.getMessage());
        } catch (Exception var4) {
            this.logger.error("首页数据展示, 出现异常={}", var4.getMessage(), var4);
            return WrapMapper.error();
        }
        logger.info("首页数据展示,成功");
        return WrapMapper.wrap(200, "首页数据展示,成功",homeDisplayDto);
    }

    @RequestMapping(
            value = {"/dailyDisplay"},
            method = {RequestMethod.POST}
    )
    @ResponseBody
    @ApiOperation(
            notes = "按天数据展示",
            httpMethod = "POST",
            value = "按天数据展示"
    )
    public Wrapper<?> dailyDisplay(@ApiParam(name = "HomeDisplaySeleDto",value = "首页展示查询DTO") @RequestBody HomeDisplaySeleDto homeDisplaySeleDto) {
        this.logger.info("==>按天数据展示。");
        List<DailyOrderDto> dailyOrderDtos = new LinkedList<>();
        try {
            dailyOrderDtos = ofcHomeDisplayService.queryDailyOrderNumber(homeDisplaySeleDto);
        } catch (BusinessException var3) {
            this.logger.error("按天数据展示, 出现异常={}", var3.getMessage(), var3);
            return WrapMapper.wrap(500, var3.getMessage());
        } catch (Exception var4) {
            this.logger.error("按天数据展示, 出现异常={}", var4.getMessage(), var4);
            return WrapMapper.error();
        }
        logger.info("按天数据展示,成功");
        return WrapMapper.wrap(200, "按天数据展示,成功",dailyOrderDtos);
    }
}
