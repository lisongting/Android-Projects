package com.test.administrator.fragmentcommunication;



import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/23.
 */
public class Fragment1 extends Fragment {
    EditText editText2;
    Button button2;
    public Mylistener listener;//定义一个接口对象

    //从Fragment向Activity发送数据,首先要在Fragment里面定义一个接口,让包含该Fragment的Activity去实现这个接口里的方法
    public interface Mylistener{
        public void give(String code);
    }

    @Override
    public void onAttach(Activity activity){
        listener = (Mylistener)activity;//把MainActivity赋值给listener(向上转型)
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        String text = (String)getArguments().get("key1");
        Toast.makeText(getActivity(),"fragment已接收到:<"+text+">",Toast.LENGTH_SHORT).show();
        editText2 = (EditText)view.findViewById(R.id.editText2);
        button2 = (Button)view.findViewById(R.id.sendToActivity);
        return view;

    }
    @Override
    public void onResume(){
        super.onResume();
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String code = editText2.getText().toString();
                listener.give(code);//这个时候实际调用的是MainActivity的give方法，这就完成了从Fragmeng向Activity传值的过程
            }
        });
    }
}
