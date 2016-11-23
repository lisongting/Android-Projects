package com.test.administrator.contactprovidertest;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final ArrayList<String> names = new ArrayList<>();
    final ArrayList<ArrayList<String>> details = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //为"content://sms"的改变注册一个监听器
        getContentResolver().registerContentObserver(Uri.parse("content://sms"),true,new SmsObserver(this,new Handler()));
    }
    public void search(View v){
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        while(cursor.moveToNext()){
            //获取联系人的ID
            String contactID = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            names.add(name);
            //使用ContentResolver查找联系人号码
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null
                    ,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+contactID,null,null);
            ArrayList<String> detail = new ArrayList<>();
            while(phones.moveToNext()){
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                detail.add("电话号码："+phoneNumber);
            }
            phones.close();
            cursor.close();//获取完联系人和电话号码后，加载result.xml布局
            View resultDialog = getLayoutInflater().inflate(R.layout.result,null);
            ExpandableListView list = (ExpandableListView)resultDialog.findViewById(R.id.list) ;
            //获取resultDialog中ID为list的ExpandableListAdapter
            ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
                @Override
                public int getGroupCount() {
                    return names.size();
                }

                @Override
                public int getChildrenCount(int groupPosition) {
                    return details.get(groupPosition).size();
                }

                @Override
                public Object getGroup(int groupPosition) {
                    return names.get(groupPosition);
                }

                @Override
                public Object getChild(int groupPosition, int childPosition) {
                    return details.get(groupPosition).get(childPosition);
                }

                @Override
                public long getGroupId(int groupPosition) {
                    return groupPosition;
                }

                @Override
                public long getChildId(int groupPosition, int childPosition) {
                    return childPosition;
                }

                @Override
                public boolean hasStableIds() {
                    return true;
                }

                @Override
                public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                    TextView textView = getTextView();
                    textView.setText(getGroup(groupPosition).toString());
                    return textView;
                }

                @Override
                public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                    TextView textView = getTextView();
                    textView.setText(getChild(groupPosition,childPosition).toString());
                    return textView;
                }

                @Override
                public boolean isChildSelectable(int groupPosition, int childPosition) {
                    return true;
                }
                private TextView getTextView(){
                    AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,64);
                    TextView textView = new TextView(MainActivity.this);
                    textView.setLayoutParams(lp);
                    textView.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
                    textView.setPadding(36,0,0,0);
                    textView.setTextSize(20);
                    return textView;
                }
            };
            list.setAdapter(adapter);
            new AlertDialog.Builder(MainActivity.this)
                    .setView(list).setPositiveButton("确定",null).show();
        }
  }

    public void add(View v){
        String name = ((EditText)findViewById(R.id.name)).getText().toString();
        String phone = ((EditText)findViewById(R.id.phone)).getText().toString();
        //创建一个ContentValue
        ContentValues values = new ContentValues();
        //执行一个空值插入，目的是获取系统返回的rawContactId
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI,values);
        long rawContactId = ContentUris.parseId(rawContactUri);
        values.clear();
        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID,rawContactId);
        //设置内容类型
        values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        //设置联系人名字
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,name);
        //向联系人URI添加联系人名字
        getContentResolver().insert(ContactsContract.Data.CONTENT_URI,values);
        values.clear();
        values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 设置联系人的电话号码
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phone);
        // 设置电话类型
        values.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        // 向联系人电话号码URI添加电话号码
        getContentResolver().insert(android.provider.ContactsContract
                .Data.CONTENT_URI, values);

        Toast.makeText(MainActivity.this, "联系人数据添加成功",
                Toast.LENGTH_SHORT).show();
    }
}
