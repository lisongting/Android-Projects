package cn.ssdut.lst.asynctaskttest;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private static final int BEGIN = 1;
    private Button bt_start;
    private MyAsyncTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_start = (Button) findViewById(R.id.bt_start);
        bt_start.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                task = new MyAsyncTask();
                task.execute(BEGIN);
            }
        });
    }
    private class MyAsyncTask extends AsyncTask<Integer,Integer,String>{
        ProgressDialog progress;
        int hasDone = 0;
        protected void onPreExecute(){
            progress = new ProgressDialog(MainActivity.this);
            progress.setTitle("正在执行后台耗时任务");
            progress.setMessage("任务正在执行中，请稍等(点击该对话框外区域可取消该任务)");
            progress.setCancelable(true);
            progress.setMax(200);
            progress.setIndeterminate(false);
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.show();
        }

        @Override
        protected String doInBackground(Integer... params) {
            if(params[0]==BEGIN){
                while (hasDone <= 200) {
                    //当进度对话框被关闭时，取消任务执行
                    if (!progress.isShowing()) {
                        this.cancel(true);
                        hasDone=0;
                        break;
                    }
                    try {
                        Thread.sleep(50);
                        hasDone+=2;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    publishProgress(hasDone);
                }
            }
            return null;
        }

        //当调用publishProgress()时触发该方法，进行实时进度反馈
        protected void onProgressUpdate(Integer... value) {
            //如果任务被撤销，则直接返回
            if(isCancelled()){
                return;
            }
            //设置进度条的进度
            progress.setProgress(value[0]);
        }

        //当任务执行完时调用该方法
        protected void onPostExecute(String result) {
            //此时隐藏进度对话框
            progress.dismiss();
            Toast.makeText(MainActivity.this
                    ,"后台耗时任务已经执行完成!",Toast.LENGTH_SHORT).show();
        }
    }
}
