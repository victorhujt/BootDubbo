package com.xescm.ofc.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * Created by hujintao on 2017/6/8.
 */
public class IpUtils {

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            logger.info(" Proxy-Client-IP" + request.getHeader("Proxy-Client-IP"));
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            logger.info(" WL-Proxy-Client-IP" + request.getHeader("WL-Proxy-Client-IP"));
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
            logger.info(" WL-Proxy-Client-IP" + request.getHeader("x-real-ip"));
            ipAddress = request.getHeader("x-real-ip");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            logger.info(" getRemoteAddr" + request.getRemoteAddr());
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1")) {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

}
