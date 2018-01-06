package cn.lst.jolly.database.converters;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.lst.jolly.data.GuokrHandpickContentChannel;

/**
 * Created by lisongting on 2017/12/18.
 */

public class GuokrContentTypeConverter {

    @TypeConverter
    public static String stringListToString(List<String> strings) {
        return new Gson().toJson(strings);
    }

    @TypeConverter
    public static List<String> stringToStringList(String string) {
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(string,listType);
    }

    @TypeConverter
    public static String channelListToString(List<GuokrHandpickContentChannel> channels){
        return new Gson().toJson(channels);
    }

    @TypeConverter
    public static List<GuokrHandpickContentChannel> stringToChannelList(String s) {
        Type listType = new TypeToken<ArrayList<GuokrHandpickContentChannel>>(){}.getType();
        return new Gson().fromJson(s, listType);
    }


}
