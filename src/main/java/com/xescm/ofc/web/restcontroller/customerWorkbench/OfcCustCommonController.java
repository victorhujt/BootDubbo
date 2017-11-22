package com.xescm.ofc.web.restcontroller.customerWorkbench;

import com.xescm.ofc.web.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Created by lyh on 2017/9/8.
 */
@Controller
@RequestMapping(value = "/page/ofc/cust/common", produces = {"application/json;charset=UTF-8"})
@Api(value = "OfcCustCommonController", tags = "OfcCustCommonController", description = "OFC客户工作台公用", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OfcCustCommonController extends BaseController {
}
