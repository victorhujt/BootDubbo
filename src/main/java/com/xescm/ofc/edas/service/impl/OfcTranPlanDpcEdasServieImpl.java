package com.xescm.ofc.edas.service.impl;

import com.github.pagehelper.PageInfo;
import com.xescm.base.model.wrap.Wrapper;
import com.xescm.ofc.edas.model.dto.dpc.req.*;
import com.xescm.ofc.edas.model.dto.dpc.resp.TranPlanOfcRespDto;
import com.xescm.ofc.edas.service.OfcTranPlanDpcEdasServie;
import com.xescm.ofc.service.OfcTransplanInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Wrapper<PageInfo<TranPlanOfcRespDto>> tranPlanSel(TranPlanOfcReqDto TranPlanOfcReqDto) {
        /*logger.info("调度中心查询参数：dicTranPlanOfcSDTO：{}", ToStringBuilder.reflectionToString(TranPlanOfcReqDto));
        try {
            if (TranPlanOfcReqDto == null) {
                throw new IllegalArgumentException("查询条件有误");
            }
            Wrapper<PageInfo<TranPlanOfcRespDto>> wrapper = ofcTransplanInfoService.tranPlanSel(TranPlanOfcReqDto);
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
        }*/
        return null;
    }

    @Override
    public int batchUpdateTransPlan(List<TranPlanOfcUpdateReqDto> list) {
        return 0;
    }

    @Override
    public TranPlanOfcRespDto tranPlanSelByCode(String s) {
        return null;
    }

    @Override
    public Wrapper<DpcAllowPlanCodeReqDto> selectAllowPlanCodeList(DpcAllowPlanCodeReqDto dpcAllowPlanCodeReqDto) {
        return null;
    }

    @Override
    public Wrapper<List<TranPlanOfcRespDto>> tranPlanSelByCodeList(TranPlanCodeOfcReqDto tranPlanCodeOfcReqDto) {
        return null;
    }

    @Override
    public int updateSchedulingState(DpcModifyDispatchInfoStateReqDto dpcModifyDispatchInfoStateReqDto) {
        return 0;
    }
}
