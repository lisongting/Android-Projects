package cn.ssdut.lst.testjni;

/**
 * Created by Administrator on 2016/12/5.
 */
public class TestJni {
    static {
        System.loadLibrary("JNItest");
    }
    public native boolean Init();

    public native int Add(int x, int y);
    public native void Destroy();
}
