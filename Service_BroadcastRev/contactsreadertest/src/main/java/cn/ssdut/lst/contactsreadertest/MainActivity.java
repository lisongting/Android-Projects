package cn.ssdut.lst.contactsreadertest;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private BundleList dataList;
    private ContentResolver contentResolver;
    ArrayList<Map<String,String>> list = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ArrayList<Map<String,String>> list = new ArrayList<>();
        contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        //dataList = new BundleList(cursor,contentResolver);

        list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Map<String,String> item = new HashMap<>();
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            item.put("name",name);
            //使用ContentResolver查找联系人电话号码
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactId,null,null);
            Log.d("tag","phones的"+phones.getColumnCount());
            while(phones.moveToNext()){
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                item.put("phone",phoneNumber);
                list.add(item);
                break;
            }
        }
    }

    public void read(View v){
//        Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
//        intent.putExtra("personsInfoList",dataList);
//        startActivity(intent);
        listView = (ListView)findViewById(R.id.listView2) ;
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.layout_item, new String[]{"name", "phone"}, new int[]{R.id.personName, R.id.personPhone});
        listView.setAdapter(adapter);
        Toast.makeText(this, "共"+list.size()+"位联系人", Toast.LENGTH_SHORT).show();

    }
}
