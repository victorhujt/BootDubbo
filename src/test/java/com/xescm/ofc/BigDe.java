package com.xescm.ofc;

import java.math.BigDecimal;

/**
 * Created by victor on 2017/11/7.
 */
public class BigDe {

    public static void main(String[] args) {
        Integer m = new Integer(20);
        Integer m1 = new Integer(20);
        BigDecimal b = new BigDecimal(0.22);
        System.out.println(b.doubleValue());
        System.out.println(m.equals(m1));
        Double dd= Double.valueOf("9.86");
        BigDecimal bigD = new BigDecimal(dd);
        bigD = bigD.multiply(new BigDecimal(100));
        Long result = bigD.longValue();
        System.out.println(result);
    }
}
