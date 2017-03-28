package cn.ssdut.lst.okhttptest;

/**
 * Created by Administrator on 2017/3/26.
 */

public interface ProgressListener  {
    void onProgress(int progress);

    void onDone(long val);
}
