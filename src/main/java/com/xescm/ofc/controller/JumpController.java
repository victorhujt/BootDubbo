package com.xescm.ofc.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by lyh on 2016/10/18.
 */
@RequestMapping("/ofc")
@Controller
public class JumpController {

    @RequestMapping(value="/orderPlace")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        return new ModelAndView("order_place");
    }

    @RequestMapping(value = "/orderManage")
    public String orderManage(){
        return "order_manage";
    }

    @RequestMapping(value = "/orderScreen")
    public String orderScreen(){
        return "order_screen";
    }

    @ApiOperation(value="追踪订单", notes="根据code和followTag来追踪订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "订单编号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "followTag", value = "追踪标记", required = true, dataType = "String")
    })
    @RequestMapping(value="/orderFollow",method = RequestMethod.GET)
    public String orderFollow(String code, String followTag, Map<String,Object> map){
        return "order_follow";
    }

    @RequestMapping(value = "/test")
    public String demoorderScreen(){
        return "demo";
    }
}
