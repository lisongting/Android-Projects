package cn.ssdut.lst.networkdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
public class MyAsyncTask extends AsyncTask<URL,Integer,String> {
    private ProgressDialog progressDialog;
    Context context;
    public MyAsyncTask(Context ctx) {
        context = ctx;
    }
    public void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("下载歌曲");
        progressDialog.setMessage("正在下载歌曲中，请稍后");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }
    protected String doInBackground(URL ...params){
        URL url = params[0];
        int fileSize;//文件大小
        int hasDown=0;//已经下载的大小
        File music = new File(Environment.getExternalStorageDirectory(), "薛之谦-演员.mp3");
        try {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(8000);
            if (conn.getResponseCode() == 200) {
                fileSize = conn.getContentLength();
                InputStream input = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(music);
                byte[] buffer = new byte[2048];
                int hasRead;
                while ((hasRead = input.read(buffer)) != -1) {
                    hasDown+=hasRead;
                    fos.write(buffer);
                    double d = (double)hasDown*100/fileSize;
                    //保留两位小数并进行四舍五入
                    BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
                    int p =bg.intValue();
                    publishProgress(p);
                }
                fos.close();
                input.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void onProgressUpdate(Integer... value) {
        progressDialog.setProgress(value[0]);
    }
    protected void onPostExecute(String result){
        progressDialog.dismiss();
        Toast.makeText(context,"歌曲下载完成!",Toast.LENGTH_LONG).show();
    }
}
