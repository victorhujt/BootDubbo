package com.xescm.ofc.utils;

import com.xescm.ofc.domain.OfcOrderStatus;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 排序订单状态
 * Created by hiyond on 2016/12/17.
 */
public class SortOrderStatusUtils {

    public static List<OfcOrderStatus> sortOrderStatus(List<OfcOrderStatus> list) {
        Collections.sort(list, new Comparator<OfcOrderStatus>() {
            @Override
            public int compare(OfcOrderStatus o1, OfcOrderStatus o2) {
                try {
                    SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date dateo1 = sfd.parse(o1.getNotes().substring(0, 19));
                    Date dateo2 = sfd.parse(o1.getNotes().substring(0, 19));
                    return dateo1.compareTo(dateo2);
                } catch (Exception e) {
                }
                return 0;
            }
        });
        return list;
    }

}
