package cn.ssdut.lst.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==1){
            String str = data.getStringExtra("info");
            //Toast.makeText(MainActivity.this, "info:"+str, Toast.LENGTH_SHORT).show();
        }
    }
    public void onResume() {
        Toast.makeText(MainActivity.this, "taskId:"+this.getTaskId(), Toast.LENGTH_SHORT).show();
        super.onResume();
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.bt);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("cn.ssdut.lst.action.MyActivity1");
                startActivityForResult(intent, 0);
                  //Intent intent= new Intent(MainActivity.this, MyActivity1.class);
                //startActivity(intent);

            }
        });
        Toast.makeText(MainActivity.this, "taskId:"+getTaskId(), Toast.LENGTH_SHORT).show();
    }
}
