package cn.ssdut.lst.easyreader.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/28.
 */

public class DateFormatter {
    //将long类型转换为String
    public String ZhihuDailyDateFormat(long date) {
        String sDate;
        Date d = new Date(date + 24 * 3600 * 1000);//加上一天的毫秒数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sDate = sdf.format(d);
        return sDate;
    }

    public String DoubanDateFormat(long date) {
        String sDate;
        Date d = new Date(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sDate = sdf.format(d);
        return sDate;
    }
}
