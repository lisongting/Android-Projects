package com.test.administrator.async_task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (TextView)findViewById(R.id.show);
    }
    public void downLoad(View source){
        DownTask task = new DownTask(this);
        try{
            task.execute(new URL("http://www.crazyit.org/ethos.php"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    class DownTask extends AsyncTask<URL,Integer,String> {
        ProgressDialog pdialog;
        int hasRead=0;
        Context mContext;
        public DownTask(Context ctx){
            mContext = ctx;
        }
        @Override
        protected void onPreExecute(){
            pdialog = new ProgressDialog(mContext);
            pdialog.setTitle("任务执行中");
            pdialog.setMessage("任务正在执行中,请等待");
            pdialog.setCancelable(true);
            pdialog.setMax(200);
            pdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pdialog.setIndeterminate(false);
            pdialog.show();
        }
        @Override
        protected String doInBackground(URL...params){
            StringBuilder sb = new StringBuilder();
            if(!pdialog.isShowing()){
                this.cancel(true);hasRead=0;
            }
            try{
                URLConnection conn = params[0].openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                String line=null ;
                while((line = br.readLine())!=null){
                    if(!pdialog.isShowing()){
                        this.cancel(true);
                        hasRead=0;
                    }
                    if(isCancelled()){
                        break;
                    }
                    Thread.sleep(50);
                    sb.append(line+"\n");
                    hasRead++;
                    publishProgress(hasRead);
                }
                return sb.toString();
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String result){
            show.setText(result);
            pdialog.dismiss();
        }
        protected void onProgressUpdate(Integer...value){
            if(isCancelled())
                return;
            show.setText("已经读取了["+value[0]+"]行");
            pdialog.setProgress(value[0]);
        }

    }

}
