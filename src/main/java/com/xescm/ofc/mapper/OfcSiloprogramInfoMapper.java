package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcSiloprogramInfo;
import com.xescm.ofc.model.vo.ofc.OfcSiloprogramInfoVo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface OfcSiloprogramInfoMapper extends Mapper<OfcSiloprogramInfo> {
    List<OfcSiloprogramInfo> ofcSiloprogramInfoScreenList(Map<String,String> mapperMap);

    List<OfcSiloprogramInfoVo> ofcSiloprogramAndResourceInfo(Map<String,String> mapperMap);

    List<String> ofcMaxSiloprogramInfoSerialNumberScreenList(Map<String,String> mapperMap);
}