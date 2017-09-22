package com.xescm.ofc.web.restcontroller.workerForOldDate;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.domain.OfcEnumeration;
import com.xescm.ofc.domain.OfcGoodsDetailsInfo;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcGoodsDetailsInfoService;
import com.xescm.ofc.utils.CodeGenUtils;
import com.xescm.ofc.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xescm.ofc.constant.GenCodePreffixConstant.PAAS_LINE_NO;

/**
 * <p>Title: FlushPassLineNo. </p>
 * <p>Description 刷[ofc_goods_details_info][pass_line_no]历史数据 </p>
 * <p>Company: http://www.hnxianyi.com </p>
 *
 * @Author 袁宝龙
 * @CreateDate 2017/9/13 11:35
 */
@RequestMapping(value = "/ofc", produces = {"application/json;charset=UTF-8"})
@Controller
public class FlushPassLineNo extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OfcGoodsDetailsInfoService ofcGoodsDetailsInfoService;
    @Resource
    private CodeGenUtils codeGenUtils;

    private final int ALLSIZE = 3500000;
    private final int PAGESIZE = 1000;

    @ResponseBody
    @RequestMapping(value = "/api/flush/passlineno", method = {RequestMethod.GET})
    public Wrapper<Object> flushPassLineNo() {
        Wrapper<Object> result;
        PageInfo<OfcGoodsDetailsInfo> pageInfo;
        OfcGoodsDetailsInfo ofcGoodsDetailsInfo = new OfcGoodsDetailsInfo();
        try {
            for (int i = 1; i <= ALLSIZE / 10000; i++){
                // 包装分页
                logger.info("------------>分页包装开始，页数{}",i);
                PageHelper.startPage(i, PAGESIZE);
                logger.info("------------>查询开始[ofc_goods_details_info]");
                List<OfcGoodsDetailsInfo> ofcGoodsDetailsInfos = ofcGoodsDetailsInfoService.queryIds();
                pageInfo = new PageInfo<>(ofcGoodsDetailsInfos);
                logger.info("============>查询结束[ofc_goods_details_info]");
                logger.info("------------>分页包装结束，页数{}",i);
                // 更新数据
                List<OfcGoodsDetailsInfo> pushList = pageInfo.getList();
                logger.info("------------>更新数据开始，页数{}",i);
                for (int j = 0; j < pushList.size(); j++){
                    String nowId = pushList.get(j).getId();
                    Long nowPassLineNo = codeGenUtils.getPaasLineNo(PAAS_LINE_NO);
                    ofcGoodsDetailsInfo.setId(nowId);
                    ofcGoodsDetailsInfo.setPaasLineNo(nowPassLineNo);
                    ofcGoodsDetailsInfoService.flushPassLineNoById(ofcGoodsDetailsInfo);
                    logger.info("------------>更新数据开始，页数{}，当前页剩余{}", i, PAGESIZE - j);
                }
                logger.info("============>更新数据结束，页数{}",i);
            }
            result = WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE);
        } catch (BusinessException e) {
            logger.error("刷[pass_line_no]历史数据发生异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, e.getMessage());
        } catch (Exception e) {
            logger.error("刷[pass_line_no]历史数据发生异常：异常信息=>{}", e);
            result = WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
        return result;
    }
}
