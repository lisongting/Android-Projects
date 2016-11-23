package com.test.administrator.fragmentpractice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/12.
 */

public class NewsContentFragment extends Fragment {
    private View view;
    //重写父类的构造方法
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        //加载news_content_frag布局
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }
    //将新闻的标题和内容显示到界面上
    public void refresh(String newsTitle,String newsContent){
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = (TextView)view.findViewById(R.id.news_title);
        TextView newsContentText = (TextView)view.findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);
        newsContentText.setText(newsContent);
    }
}
