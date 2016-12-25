package com.xescm.ofc.service;

import com.xescm.ofc.domain.OfcAttachment;

import java.io.UnsupportedEncodingException;

/**
 * Created by hujintao on 2016/12/17.
 */
public interface OfcAttachmentService extends IService<OfcAttachment>{

     OfcAttachment saveAttachment(OfcAttachment attachment);

     void deleteAttachmentByserialNo(OfcAttachment attachment);

      void  updatePicParamByserialNo(OfcAttachment attachment);

      String  operateAttachMent(String style,String serialNo) throws UnsupportedEncodingException;


}
