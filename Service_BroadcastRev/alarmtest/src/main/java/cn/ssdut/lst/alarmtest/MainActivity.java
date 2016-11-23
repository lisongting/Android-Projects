package cn.ssdut.lst.alarmtest;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button setTime;
    Calendar currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void setAlarm(View view) {
        Calendar currentTime = Calendar.getInstance();
        //创建一个TimePickerDialog实例，并把它显示出来
        new TimePickerDialog(MainActivity.this,0,new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker tp,int hourOfDay,int minute){
                Intent intent = new Intent(MainActivity.this,AlarmActivity.class);
                PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,intent,0);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                //根据用户选择时间来设置Calendar对象
                c.set(Calendar.HOUR,hourOfDay);
                c.set(Calendar.MINUTE,minute);
                //获取系统的AlarmManager对象
                AlarmManager aManager  = (AlarmManager)getSystemService(ALARM_SERVICE);
                aManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pi);
                //显示闹铃设置成功
                Toast.makeText(MainActivity.this,"闹钟设置成功",Toast.LENGTH_SHORT).show();
            }
        },currentTime.get(Calendar.HOUR_OF_DAY),currentTime.get(Calendar.MINUTE),false).show();
    }
}
