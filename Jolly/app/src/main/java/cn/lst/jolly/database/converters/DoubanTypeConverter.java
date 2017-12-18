package cn.lst.jolly.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.lst.jolly.data.DoubanMomentNewsThumbs;

/**
 * Created by lisongting on 2017/12/18.
 */

public class DoubanTypeConverter {

    //todo  理解Converter的作用
    @TypeConverter
    public static String thumbListToString(List<DoubanMomentNewsThumbs> thumbs) {
        return new Gson().toJson(thumbs);
    }

    @TypeConverter
    public static List<DoubanMomentNewsThumbs> stringToThumbList(String string) {
        Type listType = new TypeToken<ArrayList<DoubanMomentNewsThumbs>>(){}.getType();
        return new Gson().fromJson(string, listType);
    }
}
