package com.xescm.ofc.service;

import com.xescm.base.model.wrap.Wrapper;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by lyh on 2017/4/13.
 */
public interface TmpZpMissOrderService {
    Wrapper<String> zpMissOrderImport(MultipartFile file);
}
