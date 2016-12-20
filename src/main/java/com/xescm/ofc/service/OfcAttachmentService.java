package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcAttachment;

/**
 * Created by hujintao on 2016/12/17.
 */
public interface OfcAttachmentService extends IService<OfcAttachment>{

     OfcAttachment saveAttachment(OfcAttachment attachment);

     void deleteAttachmentByserialNo(OfcAttachment attachment);

      void  updatePicParamByserialNo(OfcAttachment attachment);


}
