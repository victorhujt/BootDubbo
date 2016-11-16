package com.xescm.ofc.web.rest;

import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.domain.OfcOrderDTO;
import com.xescm.ofc.domain.OfcOrderStatus;
import com.xescm.ofc.domain.dto.csc.CscSupplierInfoDto;
import com.xescm.ofc.domain.dto.csc.QueryCustomerIdDto;
import com.xescm.ofc.domain.dto.csc.vo.CscContantAndCompanyVo;
import com.xescm.ofc.enums.OrderConstEnum;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.mq.consumer.DefaultMqConsumer;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import com.xescm.ofc.service.OfcOrderDtoService;
import com.xescm.ofc.service.OfcOrderManageService;
import com.xescm.ofc.service.OfcOrderStatusService;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.domain.dto.AuthResDto;
import com.xescm.uam.utils.PubUtils;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/ofc",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcPlanForMqRest extends BaseController{
    @Autowired
    private DefaultMqProducer defaultMqProducer;
    @Autowired
    private DefaultMqConsumer defaultMqConsumer;
    @Resource
    MqConfig mqConfig;

    @RequestMapping(value = "/planForMq")
    @ResponseBody
    public void planForMq() throws InvocationTargetException {
        AuthResDto authResDto=getAuthResDtoByToken();
        String userName=authResDto.getUamUser().getUserName();
        defaultMqConsumer.MqConsumer(mqConfig.getTopic());
    }
}
