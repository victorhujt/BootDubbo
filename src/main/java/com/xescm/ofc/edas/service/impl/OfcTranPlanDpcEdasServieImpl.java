package com.xescm.ofc.edas.service.impl;

import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.WrapMapper;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.edas.model.dto.dpc.req.TranPlanOfcReqDTO;
import com.xescm.ofc.edas.model.dto.dpc.resp.TranPlanOfcRespDTO;
import com.xescm.ofc.edas.service.OfcTranPlanDpcEdasServie;
import com.xescm.ofc.exception.BusinessException;
import com.xescm.ofc.service.OfcTransplanInfoService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangsongtao on 2016/12/27.
 */
@Service
public class OfcTranPlanDpcEdasServieImpl implements OfcTranPlanDpcEdasServie{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OfcTransplanInfoService ofcTransplanInfoService;

    /**
     * 调度中心查询计划单接口
     *
     * @param
     * @return
     */
    @Override
    public Wrapper<PageInfo<TranPlanOfcRespDTO>> tranPlanSel(TranPlanOfcReqDTO tranPlanOfcReqDTO) {
        logger.info("调度中心查询参数：dicTranPlanOfcSDTO：{}", ToStringBuilder.reflectionToString(tranPlanOfcReqDTO));
        try {
            if (tranPlanOfcReqDTO == null) {
                throw new IllegalArgumentException("查询条件有误");
            }
            Wrapper<PageInfo<TranPlanOfcRespDTO>> wrapper = ofcTransplanInfoService.tranPlanSel(tranPlanOfcReqDTO);
            return wrapper;
        } catch (IllegalArgumentException ex) {
            logger.error(" 任务单查询接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (BusinessException ex) {
            logger.error("任务单查询接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, ex.getMessage());
        } catch (Exception ex) {
            logger.error("任务单查询接口处理失败：错误原因：{}", ex.getMessage(), ex);
            return WrapMapper.wrap(Wrapper.ERROR_CODE, Wrapper.ERROR_MESSAGE);
        }
    }
}
