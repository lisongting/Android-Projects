package cn.ssdut.lst.swiperefreshlayouttest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/5.
 */

public class FooterHolder extends RecyclerView.ViewHolder {
    public TextView tv;
    public LinearLayout linearLayout;
    public FooterHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.id_tv_loading);
        linearLayout = (LinearLayout) itemView.findViewById(R.id.id_linearlayout);
    }
}
