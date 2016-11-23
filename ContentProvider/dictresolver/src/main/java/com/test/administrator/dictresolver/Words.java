package com.test.administrator.dictresolver;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/10/22.
 */
public class Words {
    public static final String AUTHORITY ="com.ssdut.LST.dictprovider";
    //定义一个静态内部类,定义该ContentProvider所包含的数据列的列名
    public static final class Word implements BaseColumns {
        public static final String _ID="_id";
        public static final String WORD = "word";
        public static final String DETAIL="detail";
        //定义该contentProvider提供服务的两个Uri
        public static final Uri DICT_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/words");
        public static final Uri WORD_CONTENT_URI=Uri.parse("content://"+AUTHORITY+"/word");
    }
}
