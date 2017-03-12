package cn.ssdut.lst.viewpagerindicator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/11.
 */

public class TabFragment extends Fragment {
    private int pos;

    public TabFragment(){

    }
    @SuppressLint("ValidFragment")
    public TabFragment(int pos) {
        this.pos = pos;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag, container, false);
        TextView tv = (TextView)v.findViewById(R.id.tv_text);
        tv.setText(TabAdapter.titles[pos]);
        return v;
    }
}
