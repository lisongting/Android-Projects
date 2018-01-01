package cn.ssdut.lst.remoteviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.detail_text);

        startService(new Intent(this, UpdateService.class));
    }

    @Override
    protected void onStart() {
        super.onStart();


        StringBuilder sb = new StringBuilder("今天是：\n");


        Calendar currentCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.YEAR, 2018);
        endCalendar.set(Calendar.MONTH, 0);
        endCalendar.set(Calendar.DAY_OF_MONTH, 29);
        endCalendar.set(Calendar.HOUR_OF_DAY, 6);
        long remainTimeInMillis = endCalendar.getTimeInMillis() - currentCalendar.getTimeInMillis();

        int remainDays = (int) (remainTimeInMillis / (24 * 60 * 60 * 1000));
        int remainHours = (int) (remainTimeInMillis - (remainDays * 24 * 60 * 60 * 1000)) / (60 * 60 * 1000);

        log("remain  Days:" + remainDays+" remain hours :"+remainHours);

        sb.append(currentCalendar.get(Calendar.YEAR)).append("年 ")
                .append(currentCalendar.get(Calendar.MONTH) + 1).append("月 ")
                .append(currentCalendar.get(Calendar.DAY_OF_MONTH)).append("日\n");

        sb.append("距离回家仅剩：\n")
                .append(remainDays).append("天\n")
                .append(remainHours).append("小时");

        textView.setText(sb.toString());
        
//        int year = currentCalendar.get(Calendar.YEAR);
//        int month = currentCalendar.get(Calendar.MONTH);
//        int day = currentCalendar.get(currentCalendar.DAY_OF_MONTH);

//        log("year:" + year + " month:" + month + " day:" + day);
//
//        log("maximum year:" + currentCalendar.getMaximum(Calendar.YEAR));
//        log("maximum month:"+currentCalendar.getMaximum(Calendar.MONTH));
//
//        log("minimum day :" + currentCalendar.getMinimum(Calendar.DAY_OF_MONTH));
//        log("actual minimum day :" + currentCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));
//
//        log("maximum day:" + currentCalendar.getMaximum(Calendar.DAY_OF_MONTH));
//        log("actual  maximum day:" + currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

//        log("min hour in day :"+currentCalendar.getMinimum(Calendar.HOUR_OF_DAY));
//        log("actual min hour in day :"+currentCalendar.getActualMinimum(Calendar.HOUR_OF_DAY));
//        log("max hour in day :"+currentCalendar.getMaximum(Calendar.HOUR_OF_DAY));
//        log("actual max hour in day :"+currentCalendar.getActualMaximum(Calendar.HOUR_OF_DAY));


//        log("1 month actualMaximum day:" + endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//        log(endCalendar.toString());

        //这里比较奇怪，没有设置值为什么还是会有值
        log("hour:"+endCalendar.get(Calendar.HOUR_OF_DAY)+" minutes:" + endCalendar.get(Calendar.MINUTE) + " second:" + endCalendar.get(Calendar.SECOND));
    }

    private void log(String s) {
        Log.i("tag", s);
    }

}
