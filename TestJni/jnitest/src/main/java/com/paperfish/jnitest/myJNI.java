package com.paperfish.jnitest;

/**
 * Created by lisongting on 2017/8/16.
 */

public class myJNI {



    static{
        System.loadLibrary("JniTest");
    }

    public static native String sayHello();
}
