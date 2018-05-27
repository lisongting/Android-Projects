package com.lst.wanandroid.utils.log;

import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;

import com.lst.wanandroid.utils.CommonUtil;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.orhanobut.logger.Logger.ASSERT;
import static com.orhanobut.logger.Logger.DEBUG;
import static com.orhanobut.logger.Logger.ERROR;
import static com.orhanobut.logger.Logger.INFO;
import static com.orhanobut.logger.Logger.VERBOSE;
import static com.orhanobut.logger.Logger.WARN;
import static com.orhanobut.logger.Logger.t;

public class TxtFormatStrategy implements FormatStrategy {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String SEPARATOR = " ";
    private final Date date;
    private final SimpleDateFormat dateFormat;
    private final LogStrategy logStrategy;
    private final String tag;

    public TxtFormatStrategy(Builder builder) {
        date = builder.date;
        dateFormat = builder.dateFormat;
        logStrategy = builder.logStrategy;
        tag = builder.tag;
    }

    public static Builder newBuilder(){
        return new Builder();
    }
    @Override
    public void log(int priority, String onceOnlyTag, String message) {
        String tag = formatTag(onceOnlyTag);
        date.setTime(System.currentTimeMillis());
        StringBuilder header = new StringBuilder();
        // machine-readable date/time
        header.append(Long.toString(date.getTime()));
        // human-readable date/time
        header.append(SEPARATOR);
        header.append(dateFormat.format(date));
        // level
        header.append(SEPARATOR);
        header.append(logLevel(priority));
        // tag
        header.append(SEPARATOR);
        header.append(tag);
        header.append(SEPARATOR);

        StringBuilder builder = new StringBuilder();
        builder.append(header);

        // message
        if (message.contains(NEW_LINE)) {
            message = message.replaceAll(NEW_LINE, NEW_LINE + header.toString());
        }
        builder.append(message);
        // new line
        builder.append(NEW_LINE);

        logStrategy.log(priority, tag, builder.toString());

    }

    private String formatTag(String tag){
        if(!TextUtils.isEmpty(tag)&&!CommonUtil.isEquals(this.tag,tag)){
            return this.tag+"-"+tag;
        }
        return this.tag;
    }

    public static final class Builder{
        Date date;
        SimpleDateFormat dateFormat;
        LogStrategy logStrategy;
        String tag = "PRETTY_LOG";
        private Builder(){
        }

        public Builder dateFormat(SimpleDateFormat s) {
            dateFormat = s;
            return this;
        }

        public Builder logStrategy(LogStrategy l){
            logStrategy = l;
            return this;
        }

        public Builder tag(String tag){
            this.tag = tag;
            return this;
        }

        public TxtFormatStrategy build(String pkgName, String appName) {
            if (date == null) {
                date = new Date();
            }
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.UK);
            }
            if (logStrategy == null) {
                //根目录
                String diskPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                StringBuilder folder = new StringBuilder(diskPath);
                folder.append(File.separatorChar).append("Android")
                        .append(File.separatorChar).append("data")
                        .append(File.separatorChar).append(pkgName)
                        .append(File.separatorChar).append("log")
                        .append(File.separatorChar);
                HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
                ht.start();
                Handler handler = new DiskLogStrategy.WriteHandler(ht.getLooper(), folder.toString(), appName);
                logStrategy = new DiskLogStrategy(handler);
            }
            return new TxtFormatStrategy(this);
        }


    }

    private String logLevel(int value) {
        switch (value) {
            case VERBOSE:
                return "VERBOSE";
            case DEBUG:
                return "DEBUG";
            case INFO:
                return "INFO";
            case WARN:
                return "WARN";
            case ERROR:
                return "ERROR";
            case ASSERT:
                return "ASSERT";
            default:
                return "UNKNOWN";
        }
    }
}
