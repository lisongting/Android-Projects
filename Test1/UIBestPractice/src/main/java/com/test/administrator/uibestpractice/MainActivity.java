package com.test.administrator.uibestpractice;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList = new ArrayList<Msg>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        adapter = new MsgAdapter(MainActivity.this,R.layout.msg_item,msgList);
        inputText = (EditText)findViewById(R.id.input_text);
        send = (Button)findViewById(R.id.send_button);
        msgListView = (ListView)findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String content = inputText.getText().toString();
                if(!"".equals(content)){//如果消息不为空
                    Msg msg = new Msg(content,Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();//当有消息更新时,刷新ListView中的显示
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
                }
            }
        });
    }
    private void init(){
        Msg msg1 = new Msg("Hello boy!",Msg.TYPE_RECEIVED);
        Msg msg2 = new Msg("Hello,who is that?",Msg.TYPE_SENT);
        Msg msg3 = new Msg("This is Tom.Nice talking to you. ",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        msgList.add(msg2);
        msgList.add(msg3);
    }
}
