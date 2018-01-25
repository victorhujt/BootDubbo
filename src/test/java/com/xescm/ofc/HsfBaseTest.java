package com.xescm.ofc;

import com.taobao.hsf.lightapi.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Description   edas测试基础类
 *
 * @Author        zhangft
 * @CreateDate    2016/12/27 14:27
 */
public class HsfBaseTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private String projectName = "ofc";

    private String defaultSarPath = "D:\\XE-WORK-TOMCAT\\xescm-ofc\\deploy\\";

    private ServiceFactory factory ;

    public <T> T getConsumer(Class<T> serviceClass, String wholeClassName, String version, String group, String sarPath){

        if(sarPath == null){
            sarPath = defaultSarPath;
        }

        File file = new File(sarPath + "taobao-hsf.sar");
        if(!file.exists()){
             throw new RuntimeException(" sarPath is not exists");
        }

        factory = ServiceFactory.getInstanceWithPath(sarPath);


        if(serviceClass == null){
             throw new RuntimeException(" seriveClass can't be null !");
        }

        String serviceName = serviceClass.getSimpleName();
        String tName = serviceName.substring(0,1).toLowerCase() + serviceName.substring(1);

        if(wholeClassName == null){
            wholeClassName = "com.xescm." + projectName + ".edas.service." + serviceName;
        }

        if(version == null  ){
            version = "1.0";
        }

        if(group == null){
            group = "xescm-" + projectName + "-dev";
        }

        // 进行服务消费
        factory.consumer(tName)// 服务标识
                .service(wholeClassName)// 接口全类名
                .version(version)// 版本号
                .group(group)// 组别
                .subscribe();

        try {
            factory.consumer(tName).sync();// 同步等待地址推送，最多6秒。
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T)factory.consumer(tName).subscribe();
    }

}
