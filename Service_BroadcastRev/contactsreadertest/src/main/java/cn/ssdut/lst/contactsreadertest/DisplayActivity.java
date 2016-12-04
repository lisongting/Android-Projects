package cn.ssdut.lst.contactsreadertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/27.
 * 一个暂时无用的类，在Activity之间用Intent传递数据时，我不知道怎样把复杂对象变为可序列化的
 */
public class DisplayActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_show);
        ListView listView = (ListView)findViewById(R.id.listView);
        BundleList bdList;
        bdList = (BundleList) getIntent().getSerializableExtra("personsInfoList");
        ArrayList<Map<String, String>> infoList = bdList.getList();

        SimpleAdapter adapter = new SimpleAdapter(this, infoList, R.layout.layout_item, new String[]{"name", "phone"}, new int[]{R.id.personName, R.id.personPhone});
        listView.setAdapter(adapter);
        Toast.makeText(DisplayActivity.this, "共"+infoList.size()+"位联系人", Toast.LENGTH_SHORT).show();
    }

}
