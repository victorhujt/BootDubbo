package com.xescm.ofc.service;

import java.io.InputStream;

/**
 * Created by hujintao on 2016/12/14.
 */
public interface OssManagerService {

    void  uploadFile(InputStream inputStream, String fileName, String ossFilePath);

}
