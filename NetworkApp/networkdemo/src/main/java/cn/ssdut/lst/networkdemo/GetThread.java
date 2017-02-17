package cn.ssdut.lst.networkdemo;
import android.os.Handler;
import android.os.Message;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class GetThread extends Thread {
    private Handler handler;
    private URL url;
    public GetThread(Handler handler, URL url){
        this.handler = handler;
        this.url = url;
    }
    public void run(){
        try{
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(8000);
            if (200 == conn.getResponseCode()) {
                InputStream input = conn.getInputStream();
                int hasRead=0;
                byte[] buffer = new byte[64];
                StringBuilder sb = new StringBuilder();
                while ((hasRead = input.read(buffer)) != -1) {
                    sb.append(new String(buffer, 0, hasRead,"gbk"));
                }
                Message msg= handler.obtainMessage();
                msg.obj = sb.toString();
                handler.sendMessage(msg);
                input.close();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
