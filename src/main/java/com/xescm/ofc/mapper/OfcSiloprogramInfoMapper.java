package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcSiloprogramInfo;
import com.xescm.ofc.domain.OfcSiloprogramInfoVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OfcSiloprogramInfoMapper extends Mapper<OfcSiloprogramInfo> {
    List<OfcSiloprogramInfo> ofcSiloprogramInfoScreenList(Map<String,String> mapperMap);

    OfcSiloprogramInfoVo ofcSiloprogramAndResourceInfo(Map<String,String> mapperMap);
}