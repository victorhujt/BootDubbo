package com.xescm.ofc.service;

import java.net.URL;

/**
 * Created by hujintao on 2016/12/18.
 */
public interface OfcOssManagerService {
     URL getFileURL(String filePath) ;

     void deleteFile(String filePath);

}
