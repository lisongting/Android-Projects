package com.lst.wanandroid.utils.log;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.orhanobut.logger.LogStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiskLogStrategy implements LogStrategy {

    private final Handler handler;

    DiskLogStrategy(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void log(int level, String tag, String message) {
        handler.sendMessage(handler.obtainMessage(level,message));
    }

    static class WriteHandler extends Handler{
        //保留5天日志
        private static final long SAVE_DAYS = 1000 * 60 * 60 * 24 * 5;

        //日志名
        private SimpleDateFormat fileFormat = new SimpleDateFormat("yyyy-MM-dd_HH", Locale.ENGLISH);
        private final String folder;
        private String appName = "Logger";

        WriteHandler(Looper looper,String folder,String appName){
            super(looper);
            this.folder = folder;
            this.appName = appName;
            deleteLoggerFile(folder);
        }

        public void handleMessage(Message message) {
            String content = (String) message.obj;
            FileWriter fileWriter = null;
            File logFile = getLogFile(folder,appName);
            try {
                fileWriter = new FileWriter(logFile, true);
                fileWriter.append(content);
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (Exception e1) {
                    }
                }
            }
        }

        private File getLogFile(String folderName, String fileName) {
            File folder = new File(folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            return new File(folder, String.format("%s_%s.log", fileName, fileFormat.format(new Date())));
        }

        private synchronized void deleteLoggerFile(String path) {
            File file = new File(path);
            if (!file.exists()) {
                return;
            }
            File[] files = file.listFiles();
            for (File tmp : files) {
                if (System.currentTimeMillis() - tmp.lastModified() > SAVE_DAYS) {
                    //删除指定目录下的所有文件
                    deleteDirWhiteFile(tmp);
                }
            }
        }

        private void deleteDirWhiteFile(File tmp) {
            if (tmp==null||!tmp.exists()||!tmp.isDirectory()) {
                return;
            }

            for(File f: tmp.listFiles()){
                if (tmp.isFile()) {
                    tmp.delete();
                }else if (tmp.isDirectory()) {
                    //递归删除文件夹
                    deleteDirWhiteFile(tmp);
                }
            }
            tmp.delete();
        }

    }
}
