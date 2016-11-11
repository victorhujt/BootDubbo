package com.xescm.ofc.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by ydx on 2016/10/10.
 */
public class PrimaryGenerater {
    private static final String SERIAL_NUMBER = "XXXXXX"; // 流水号格式
    private static PrimaryGenerater primaryGenerater = null;
    private static String LastNumber;
    private PrimaryGenerater() {
    }

    /**
     * 取得PrimaryGenerater的单例实现
     *
     * @return
     */
    public static PrimaryGenerater getInstance() {
        if (primaryGenerater == null) {
            synchronized (PrimaryGenerater.class) {
                if (primaryGenerater == null) {
                    primaryGenerater = new PrimaryGenerater();
                }
            }
        }
        return primaryGenerater;
    }

    /**
     * 生成下一个编号
     */
    public synchronized String generaterNextNumber(String sno) {
        String id = null;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        if (sno == null) {
            id = formatter.format(date) + "000001";
        } else {
            int count = SERIAL_NUMBER.length();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append("0");
            }
            DecimalFormat df = new DecimalFormat("000000");
            id = formatter.format(date)
                    + df.format(1 + Integer.parseInt(sno.substring(8, 14)));
        }
        this.setLastNumber(id);
        return id;
    }

    public static String getLastNumber() {
        return LastNumber;
    }

    public static void setLastNumber(String lastNumber) {
        LastNumber = lastNumber;
    }
}
