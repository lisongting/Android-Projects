package cn.ssdut.lst.monitorphone;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText number ,content;
    Button send;
    SmsManager sManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = (EditText)findViewById(R.id.number);
        content = (EditText)findViewById(R.id.content);
        send = (Button)findViewById(R.id.send);
        sManager = SmsManager.getDefault();
        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,new Intent(),0);
                sManager.sendTextMessage(number.getText().toString(),null,content.getText().toString(),pi,null);
                Toast.makeText(MainActivity.this,"发送完成",Toast.LENGTH_SHORT).show();
            }
        } );
    }
}
