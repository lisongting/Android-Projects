package cn.ssdut.lst.swiperefreshlayouttest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/4/5.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv;
    public MyViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.id_tv);
    }
}
