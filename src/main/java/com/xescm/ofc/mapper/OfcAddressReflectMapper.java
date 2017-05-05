package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcAddressReflect;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OfcAddressReflectMapper extends Mapper<OfcAddressReflect> {


    int updateByAddress(OfcAddressReflect ofcAddressReflect);
}