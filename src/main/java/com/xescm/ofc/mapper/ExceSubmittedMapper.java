package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.ExceSubmitted;
import com.xescm.ofc.model.dto.exce.ExceSubmittedQueryDto;
import com.xescm.ofc.model.vo.exce.ExceSubmittedVo;
import com.xescm.ofc.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExceSubmittedMapper extends MyMapper<ExceSubmitted> {
    List<ExceSubmittedVo> queryExceSubmittedList(ExceSubmittedQueryDto exceSubmittedQueryDto);
}