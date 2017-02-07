package cn.ssdut.lst.broadcastrecvdemo;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import static cn.ssdut.lst.broadcastrecvdemo.R.id.bt_abort;
import static cn.ssdut.lst.broadcastrecvdemo.R.id.bt_common;
import static cn.ssdut.lst.broadcastrecvdemo.R.id.bt_ordered;
public class MainActivity extends AppCompatActivity {
    private Button bt1,bt2,bt3;
    private static String commonBroadcast="ssdut.lst.COMMONBROADCAST";
    private static String orderedBroadcast="ssdut.lst.ORDEREDBROADCAST";
    private static int PASS = 1;
    private static int ABORT = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1 = (Button) findViewById(bt_common);
        bt2 = (Button) findViewById(bt_ordered);
        bt3 = (Button) findViewById(bt_abort);
        bt1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent = new Intent(commonBroadcast);
                intent.putExtra("key","Hello! This is a common broadcast.");
                sendBroadcast(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("key","This is an ordered broadcast.");
                //control是作为控制指令，传入PASS，则继续将广播往下传递
                //如果传入ABORT，则终止传递
                bundle.putInt("control", PASS);
                Intent intent = new Intent(orderedBroadcast);
                intent.putExtras(bundle);
                sendOrderedBroadcast(intent,null);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("key","This is an ordered broadcast.");
                bundle.putInt("control", ABORT);
                Intent intent = new Intent(orderedBroadcast);
                intent.putExtras(bundle);
                sendOrderedBroadcast(intent,null);
            }
        });
    }
}
