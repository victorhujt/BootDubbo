package com.xescm.ofc.web.restcontroller;

import com.xescm.ofc.domain.dto.csc.QueryCustomerNameDto;
import com.xescm.ofc.domain.dto.csc.vo.CscCustomerVo;
import com.xescm.ofc.domain.dto.csc.vo.CscGoodsVo;
import com.xescm.ofc.feign.client.FeignCscCustomerAPIClient;
import com.xescm.ofc.utils.JSONUtils;
import com.xescm.ofc.utils.PubUtils;
import com.xescm.ofc.web.controller.BaseController;
import com.xescm.uam.utils.wrap.WrapMapper;
import com.xescm.uam.utils.wrap.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.Q;

/**
 * Created by lyh on 2016/11/19.
 */
@RequestMapping(value = "/ofc/distributing",produces = {"application/json;charset=UTF-8"})
@Controller
public class OfcOperationDistributing extends BaseController{
    @Autowired
    private FeignCscCustomerAPIClient feignCscCustomerAPIClient;






    @RequestMapping(value = "/queryCustomerByName",method = RequestMethod.POST)
    @ResponseBody
    public void queryCustomerByName(String queryCustomerName, String currPage, HttpServletResponse response){
        logger.info("==> queryCustomerName={}", queryCustomerName);
        logger.info("==> currPage={}", currPage);
        try{
            if(PubUtils.isSEmptyOrNull(queryCustomerName)){
                logger.error("查询客户列表失败,参数为空!");
            }
            QueryCustomerNameDto queryCustomerNameDto = new QueryCustomerNameDto();
            queryCustomerNameDto.setCustomerNames(new ArrayList<String>());
            queryCustomerNameDto.getCustomerNames().add(queryCustomerName);
            Wrapper<?> wrapper = feignCscCustomerAPIClient.queryCustomerByName(queryCustomerNameDto);
            if(wrapper.getCode() == Wrapper.ERROR_CODE){
                logger.error("查询客户列表失败,查询结果有误!");
            }
            List<CscCustomerVo> cscCustomerVoList = (List<CscCustomerVo>) wrapper.getResult();
            response.getWriter().print(JSONUtils.objectToJson(cscCustomerVoList));
        }catch (Exception ex){
            logger.error("查询客户列表失败!",ex.getMessage());
        }

    }
}
