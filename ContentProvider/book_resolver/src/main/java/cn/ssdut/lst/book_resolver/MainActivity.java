package cn.ssdut.lst.book_resolver;

import android.content.ContentUris;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //[使用示例]
        //创建一个UriMatcher类，UriMatcher.NO_MATCH的值是-1，该参数代表
        //Uri的根路径标识码
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //注册Uri，识别码为666
        matcher.addURI("content://cn.ssdut.lst.provider","test",666);
        //当使用Uri进行匹配时，返回识别码666
        int t = matcher.match(Uri.parse("content://cn.ssdut.lst.provider/test"));


        Uri uri = Uri.parse("content://cn.ssdut.lst.provider/test/2");
        long id = ContentUris.parseId(uri);
    }
}
