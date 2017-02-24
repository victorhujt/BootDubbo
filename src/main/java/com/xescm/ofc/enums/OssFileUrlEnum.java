package com.xescm.ofc.enums;

/**
 *
 * Created by hujintao on 2016/12/14.
 */
public enum OssFileUrlEnum {
    /**
     * 默认文件地址
     */
    URL_DEFAULT("dms/file", "默认文件地址"),
    /**
     * 异常图片路径
     */
    IMG_CUSTOMER("dms/img/customer", "公司图片地址");


    String url;
    String name;

    OssFileUrlEnum(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName(String url) {
        for (OssFileUrlEnum ele : OssFileUrlEnum.values()) {
            if (url.equals(ele.getName()))
                return ele.getName();
        }
        return null;
    }

    public static OssFileUrlEnum getEnum(String url) {
        for (OssFileUrlEnum ele : OssFileUrlEnum.values()) {
            if (url.equals(ele.getName()))
                return ele;
        }
        return OssFileUrlEnum.URL_DEFAULT;
    }
}
