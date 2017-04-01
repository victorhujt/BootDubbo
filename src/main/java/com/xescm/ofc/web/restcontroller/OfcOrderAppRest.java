package com.xescm.ofc.web.restcontroller;

import com.xescm.ofc.service.OfcDailyAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by hujintao on 2017/3/31.
 */
@RestController
@RequestMapping(value ="/app", produces = {"application/json;charset=UTF-8"})
public class OfcOrderAppRest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OfcDailyAccountsService ofcDailyAccountsService;


}
