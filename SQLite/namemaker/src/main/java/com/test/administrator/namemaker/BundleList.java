package com.test.administrator.namemaker;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/3.
 *将查询结果集转换为一个ArrayList
 */
public class BundleList implements Serializable {

    private ArrayList<Map<String,Object>> list;

    public ArrayList<Map<String, Object>> getList() {
        return list;
    }

    BundleList(Cursor cursor){
        list = new ArrayList<Map<String,Object>>();
        while(cursor.moveToNext()){
            Map<String,Object> listitem = new HashMap<>();
            Integer id = cursor.getInt(0);
            String gender = cursor.getString(1);
            String name = cursor.getString(2);
            String detail = cursor.getString(3);
            String key = cursor.getString(4);
            listitem.put("_id",id);
            listitem.put("_gender",gender);
            listitem.put("_name",name);
            listitem.put("_detail",detail);
            listitem.put("_key",key);
            list.add(listitem);
        }
    }

}
