package com.test.administrator.fragmentcommunication;



import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Fragment与Activity进行通信的例子
 */
public class MainActivity extends Activity implements Fragment1.Mylistener {
    private Button button;
    private EditText edittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.sendToFragment);
        edittext = (EditText)findViewById(R.id.editText1);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String str = edittext.getText().toString();
                //建立bundle对象， 将数据装入到bundle中
                Bundle bundle = new Bundle();
                bundle.putString("key1",str);
                Fragment1 fragment1 = new Fragment1();
                fragment1.setArguments(bundle);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.area,fragment1);
                //transaction.add(R.id.area,fragment1,"fragment1");
                transaction.commit();
                Toast.makeText(MainActivity.this,"向fragment发送了数据:<"+str+">",Toast.LENGTH_SHORT).show();
                //终于搞懂了，transaction是把fragment替换到一个layout上。这个layout的位置非常重要，原本我只是想把界面下部分的区域用Fragment替换
                // 第一次犯错时是因为事务执行时
                //写成了transaction.add(R.id.layout,fragment1,"fragment1");而这个R.id.layout是整个界面即RelativeLayout的id.
                //直接将整个界面替换会导致EditText重叠等错误效果,
            }
        });
    }

    @Override
    public void give(String code){
        Toast.makeText(this,"接受到来自Fragment的数据:"+code,Toast.LENGTH_SHORT).show();
    }
}
