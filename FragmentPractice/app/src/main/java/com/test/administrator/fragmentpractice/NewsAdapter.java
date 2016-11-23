package com.test.administrator.fragmentpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    private int resourceId;
    public NewsAdapter(Context context,int textViewResourceId,List<News> objects){
        super(context,textViewResourceId,objects);//调用父类的构造器完成初始化
        resourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        News news = getItem(position);
        View view ;
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else{
            view = convertView;
        }

        TextView newsTitleText = (TextView) view.findViewById(R.id.news_title);
        newsTitleText.setText(news.getTitle());
        return view;
    }
}
