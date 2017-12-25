package cn.lst.jolly.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lisongting on 2017/12/19.
 */

public class DateFormatUtil {
    public static String formatZhihuDailyDateLongToString(long date) {
        String sDate ;
        Date d = new Date(date + 24 * 60 * 60 * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        sDate = format.format(d);
        return sDate;
    }

    public static long formatZhihuDailyDateStringToLong(String date) {
        Date d = null;
        try {
            d = new SimpleDateFormat("yyyyMMdd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d == null ? 0 : d.getTime();
    }

    public static String formatDoubanMomentDateLongToString(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        sDate = format.format(d);

        return sDate;
    }

    public static long formatDoubanMomentDateStringToLong(String date) {
        Date d = null;
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d == null ? 0 : d.getTime();

    }

    public static long formatGuokrHandpickTimeStringToLong(String date) {
        Date d = null;
        try{
            d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d == null ? 0 : d.getTime();
    }
}
