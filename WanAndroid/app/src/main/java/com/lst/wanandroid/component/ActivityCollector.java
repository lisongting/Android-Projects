package com.lst.wanandroid.component;

import android.app.Activity;

import java.util.HashSet;
import java.util.Set;

public class ActivityCollector {
    private static ActivityCollector activityCollector;

    private Set<Activity> allActivities;
    //双重锁检查
    public synchronized static ActivityCollector getInstance(){
        if (activityCollector == null) {
            synchronized (ActivityCollector.class) {
                if (activityCollector == null) {
                    activityCollector = new ActivityCollector();
                }
            }
        }
        return activityCollector;
    }

    public void addActivity(Activity activity) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (allActivities != null) {
            allActivities.remove(activity);
        }
    }

    public void exitApp(){
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity a : allActivities) {
                    a.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
