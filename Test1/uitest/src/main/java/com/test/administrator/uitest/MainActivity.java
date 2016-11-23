package com.test.administrator.uitest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//继承Activity是默认不带标题栏的,继承AppCompactActivity默认带标题栏,用requestWindowFeature(Window.FEATURE_NO_TITLE)也不能去除
public class MainActivity extends Activity implements View.OnClickListener {
    private TextView textview;
    private EditText edittext;
    private Button button,button2,button3,button4;
    private ProgressBar progressBar;
    private ImageView img;
    private  int pictureNum = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        textview = (TextView)findViewById(R.id.text_view);
        edittext = (EditText)findViewById(R.id.edit_text);
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button_commit);
        button3 = (Button)findViewById(R.id.alertButton);
        button4 = (Button)findViewById(R.id.ProgressDialog_Button);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        img = (ImageView)findViewById(R.id.imageview);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(pictureNum==1){
                    img.setImageResource(R.drawable.alien);
                    pictureNum=2;
                }else{
                    img.setImageResource(R.drawable.ufo);
                    pictureNum=1;
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int number = progressBar.getProgress();
                number +=10;
                progressBar.setProgress(number);
            }
        });
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String str = edittext.getText().toString();
                textview.setText(str);
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                AlertDialog.Builder dialog =  new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("this is a AlertDialog");
                dialog.setMessage("Something Important!!");
                dialog.setCancelable(false);
                dialog.setPositiveButton("ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                    }
                });
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                    }
                });
                dialog.show();
                Toast.makeText(MainActivity.this,"AlertToast",Toast.LENGTH_SHORT).show();
            }
        });
        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("this is progressdialog");
                progressDialog.setMessage("Loading");
                progressDialog.setCancelable(true);
                progressDialog.show();
            }
        });
    }


    //用下面这种方法好像不行!!
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_commit:
                String str = edittext.getText().toString();
                textview.setText(str);
                break;

            case R.id.button:
//                        if(progressBar.getVisibility()==View.GONE){
//                            progressBar.setVisibility(View.VISIBLE);
//                        }
//                        else{
//                            progressBar.setVisibility(View.GONE);
//                        }
                int number = progressBar.getProgress();
                number +=10;
                progressBar.setProgress(number);
                break;

            case R.id.imageview:
                if(pictureNum==1){
                    img.setImageResource(R.drawable.alien);
                    pictureNum=2;
                }else{
                    img.setImageResource(R.drawable.ufo);
                    pictureNum=1;
                }
                break;

            case R.id.alertButton:
                AlertDialog.Builder dialog =  new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("this is a AlertDialog");
                dialog.setMessage("Something Important!!");
                dialog.setCancelable(false);
                dialog.setPositiveButton("ok",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                    }
                });
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                    }
                });
                dialog.show();
                Toast.makeText(MainActivity.this,"AlertToast",Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
