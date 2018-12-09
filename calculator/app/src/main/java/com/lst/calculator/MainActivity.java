package com.lst.calculator;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView tv1,tv2,result;
    private Spinner spinner;
    private EditText editText1, editText2;
    private Button ok;
    private static final String PATTERN1 = "^[1-9]\\d*$";
    private static final String PATTERN2 = "^\\d+(\\.\\d+)?$";
    private Pattern pattern1, pattern2;
    private int mode ;

    private Handler handler;
    private Messenger clientMessenger;
    private Messenger remoteMessenger;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteMessenger = new Messenger(service);
            Toast.makeText(MainActivity.this, "跨进程Service启动成功", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            tv1 = findViewById(R.id.tv1);
            tv2 = findViewById(R.id.tv2);
            spinner = findViewById(R.id.spinner);
            editText1 = findViewById(R.id.edit1);
            editText2 = findViewById(R.id.edit2);
            ok = findViewById(R.id.bt_ok);
            result = findViewById(R.id.result);

            pattern1 = Pattern.compile(PATTERN1);
            pattern2 = Pattern.compile(PATTERN2);


            Intent intent = new Intent();
            intent.setAction("com.lst.calculator.RemoteService");
            intent.setClassName(this,"com.lst.calculator.Server");
            bindService(intent, connection, BIND_AUTO_CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                float result = msg.getData().getFloat("result",0F);
                showResult(result);
                return true;
            }
        });
        clientMessenger = new Messenger(handler);

    }

    @Override
    protected void onResume() {
        super.onResume();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode = position;
                setMode(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        setMode(Server.SHAPE_RECTANGLE);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numStr1 ;
                String numStr2 ;
                Boolean b1 ;
                Boolean b2 ;
                Boolean b3 ;
                Boolean b4 ;
                Message message = Message.obtain();
                Bundle b = new Bundle();
                message.replyTo = clientMessenger;
                message.what = mode;
                switch (mode) {
                    case Server.SHAPE_RECTANGLE:
                        numStr1 = editText1.getEditableText().toString();
                        numStr2 = editText2.getEditableText().toString();
                        b1 = pattern1.matcher(numStr1).matches();
                        b2 = pattern2.matcher(numStr1).matches();
                        b3 = pattern1.matcher(numStr2).matches();
                        b4 = pattern1.matcher(numStr2).matches();
                        if ((b1 || b2) && (b3 || b4)) {
                            Float num1 = Float.parseFloat(numStr1);
                            Float num2 = Float.parseFloat(numStr2);
                            b.putFloat("l", num1);
                            b.putFloat("w", num2);
                            message.setData(b);
                            try {
                                remoteMessenger.send(message);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        } else {
                            showRational();
                        }
                        break;
                    case Server.SHAPE_SQUARE:
                        numStr1 = editText1.getEditableText().toString();
                        b1 = pattern1.matcher(numStr1).matches();
                        b2 = pattern2.matcher(numStr1).matches();
                        if (b1 || b2) {
                            Float num1 = Float.parseFloat(numStr1);
                            b.putFloat("l", num1);
                            message.setData(b);
                            try {
                                remoteMessenger.send(message);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showRational();
                        }
                        break;
                    case Server.SHAPE_CIRCLE:
                        numStr1 = editText1.getEditableText().toString();
                        b1 = pattern1.matcher(numStr1).matches();
                        b2 = pattern2.matcher(numStr1).matches();
                        if (b1 || b2) {
                            Float num1 = Float.parseFloat(numStr1);
                            b.putFloat("l", num1);
                            message.setData(b);
                            try {
                                remoteMessenger.send(message);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showRational();
                        }
                        break;
                    case Server.SHAPE_TRIANGLE:
                        numStr1 = editText1.getEditableText().toString();
                        numStr2 = editText2.getEditableText().toString();
                        b1 = pattern1.matcher(numStr1).matches();
                        b2 = pattern2.matcher(numStr1).matches();
                        b3 = pattern1.matcher(numStr2).matches();
                        b4 = pattern1.matcher(numStr2).matches();
                        if ((b1 || b2) && (b3 || b4)) {
                            Float num1 = Float.parseFloat(numStr1);
                            Float num2 = Float.parseFloat(numStr2);
                            b.putFloat("l", num1);
                            b.putFloat("w", num2);
                            message.setData(b);
                            try {
                                remoteMessenger.send(message);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showRational();
                        }
                        break;
                    default:break;
                }
            }
        });
    }

    public void setMode(int mode){
        switch (mode) {
            case Server.SHAPE_RECTANGLE:
                result.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                editText1.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.VISIBLE);
                editText1.setText("");
                editText2.setText("");
                tv1.setText("长：");
                tv2.setText("宽: ");

                break;
            case Server.SHAPE_SQUARE:
                result.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                editText1.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.INVISIBLE);
                editText1.setText("");
                editText2.setText("");
                tv1.setText("边长：");
                break;
            case Server.SHAPE_CIRCLE:
                result.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.INVISIBLE);
                editText1.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.INVISIBLE);
                editText1.setText("");
                editText2.setText("");
                tv1.setText("半径：");
                break;
            case Server.SHAPE_TRIANGLE:
                result.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                editText1.setVisibility(View.VISIBLE);
                editText2.setVisibility(View.VISIBLE);
                editText1.setText("");
                editText2.setText("");
                tv1.setText("底边长：");
                tv2.setText("高: ");
                break;
            default:break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    public void showResult(float r){
        result.setVisibility(View.VISIBLE);
        result.setText("面积: " + r);
    }

    public void showRational(){
        Toast.makeText(this, "请输入合法的数字", Toast.LENGTH_SHORT).show();
    }

}
