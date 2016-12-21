package com.xescm.ofc.mapper;

import com.xescm.ofc.domain.OfcAttachment;
import tk.mybatis.mapper.common.Mapper;

public interface OfcAttachmentMapper extends Mapper<OfcAttachment> {
   int  updatePicParamByserialNo(Object key);


}