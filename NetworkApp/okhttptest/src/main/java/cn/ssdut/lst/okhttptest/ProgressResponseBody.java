package cn.ssdut.lst.okhttptest;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Administrator on 2017/3/26.
 */

public class ProgressResponseBody extends ResponseBody {
    private ResponseBody mResponseBody;
    private BufferedSource mBufferedSource;
    private ProgressListener mProgressListener;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        this.mProgressListener = listener;
        this.mResponseBody = responseBody;
    }
    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }


    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(getSource(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source getSource(Source source) {

        return new ForwardingSource(source) {
            long totalSize =0;
            long sum = 0;
            @Override
            public long read(Buffer sink, long byteCount) throws IOException {

                long len=0;
                if(totalSize==0){
                    totalSize = contentLength();
                }
                len = super.read(sink, byteCount);
                Log.d("tag","sum:"+sum+"  total:"+totalSize);

                sum +=(len==-1) ?0 : len;
                //计算进度
                int progress = (int)(((float)sum/totalSize)*100);
                Log.d("tag", "progress:" + progress);
                mProgressListener.onProgress(progress);
                if (len == -1) {
                    mProgressListener.onDone(totalSize);
                }
                return len;
            }
        };
    }
}
