package cn.ssdut.lst.book_resolver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_name;
    private EditText et_author;
    private EditText et_del_id;
    Button bt_insert;
    Button bt_delete;
    ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_name = (EditText) findViewById(R.id.et_name);
        et_author = (EditText) findViewById(R.id.et_author);
        et_del_id = (EditText) findViewById(R.id.et_delId);
        bt_insert = (Button) findViewById(R.id.bt_insert);
        bt_delete = (Button) findViewById(R.id.bt_del);
        bt_insert.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        resolver = getContentResolver();
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_insert:
                String bookName = et_name.getText().toString();
                String author = et_author.getText().toString();
                ContentValues values = new ContentValues();
                values.put("bookName", bookName);
                values.put("author", author);
                Uri tmpUri = resolver.insert(
                        Uri.parse("content://cn.ssdut.lst.MyProvider/books"),values);
                if(tmpUri!=null){
                    Toast.makeText(MainActivity.this,"数据插入成功\n"
                            +tmpUri.toString(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bt_del:
                String id = et_del_id.getText().toString();
                int t = resolver.delete(
                        Uri.parse("content://cn.ssdut.lst.MyProvider/books"), "_id=" + id, null);
                Toast.makeText(this, "删除了"+t+"行", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
