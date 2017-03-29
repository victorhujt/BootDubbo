package com.xescm.ofc.utils;

import com.xescm.core.utils.PubUtils;

import static com.xescm.ofc.constant.StorageTemplateConstant.REGEX_YYYYMMDD;

/**
 *
 * Created by lyh on 2017/3/29.
 */
public class ExcelCheckUtils {

    //校验日期格式是否为: yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd
   public static boolean checkDateTimeFormat(String dateTime){
       String[] split = dateTime.split(" ");
       return split.length > 1 ? split[0].matches(REGEX_YYYYMMDD) || split[1].matches(PubUtils.REGX_TIME) : dateTime.matches(REGEX_YYYYMMDD);
   }
}
