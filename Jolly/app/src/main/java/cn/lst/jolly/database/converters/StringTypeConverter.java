package cn.lst.jolly.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongting on 2017/12/18.
 */

public class StringTypeConverter {

    @TypeConverter
    public static String stringListToString(List<String> strings) {
        return new Gson().toJson(strings);
    }

    @TypeConverter
    public static List<String> stringToStringList(String string) {
        //todo 理解TypeToken
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(string, listType);
    }

}
