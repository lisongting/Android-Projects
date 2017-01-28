package cn.ssdut.lst.book_resolver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_name;
    private EditText et_author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = (EditText) findViewById(R.id.et_name);
        et_author = (EditText) findViewById(R.id.et_author);
        
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_insert:

                break;
            case R.id.bt_del:

                break;
            default:
                break;
        }
    }
}
