package com.xescm.ofc;

import com.xescm.ofc.utils.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
    @Test
    public void test() {
        try {
            Date orderTime = new Date();
            System.out.println(DateUtils.addHourToDate(orderTime, 144));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
