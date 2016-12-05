package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcTransplanInfo;
import com.xescm.ofc.model.vo.ofc.OfcTransplanInfoVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OfcTransplanInfoMapper extends Mapper<OfcTransplanInfo> {
    List<OfcTransplanInfo> ofcTransplanInfoScreenList(Map<String,String> mapperMap);
    List<OfcTransplanInfoVo> ofcTransplanInfoVoList(Map<String,String> mapperMap);

}