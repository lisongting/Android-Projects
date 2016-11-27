package cn.ssdut.lst.contactsreadertest;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/27.
 */
public class BundleList implements Serializable {
    private ArrayList<Map<String,String>> list = new ArrayList<>();
    private ContentResolver conResolver;

    public ArrayList<Map<String,String>> getList(){
        return list;}

    public BundleList(Cursor cursor,ContentResolver resolver) {
        conResolver = resolver;
        list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Map<String,String> item = new HashMap<>();
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            item.put("name",name);
            //使用ContentResolver查找联系人电话号码
            Cursor phones = conResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
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
}
