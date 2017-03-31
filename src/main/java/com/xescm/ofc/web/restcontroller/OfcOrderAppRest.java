package com.xescm.ofc.web.restcontroller;

import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.model.vo.ofc.OfcDailyAccountVo;
import com.xescm.ofc.service.OfcDailyAccountsService;
import com.xescm.ofc.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by victor on 2017/3/31.
 */
@RestController
@RequestMapping(value ="/app", produces = {"application/json;charset=UTF-8"})
public class OfcOrderAppRest {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private OfcDailyAccountsService ofcDailyAccountsService;

    @RequestMapping(value ="queryDailyAccount", method = {RequestMethod.POST})
    @ResponseBody
    public Wrapper<?> queryDailyAccount(){
        List<OfcDailyAccountVo> OfcDailyAccountVos;
        try{
            OfcDailyAccountVos=ofcDailyAccountsService.queryDailyAccount(DateUtils.Date2String(new Date(), DateUtils.DateFormatType.TYPE2));
            logger.info("查询平台日报数据为:{}",OfcDailyAccountVos);
        }catch (Exception e){
            logger.error("查询平台日报数据异常:{}",e);
            return WrapMapper.wrap(Wrapper.ERROR_CODE,"查询平台日报数据异常");
        }
        return WrapMapper.wrap(Wrapper.SUCCESS_CODE,"查询平台日报数据成功",OfcDailyAccountVos);
    }
}
