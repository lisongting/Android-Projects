package cn.ssdut.lst.recyclerviewtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */

public class StaggerGridAdapter extends RecyclerViewAdapter {
    //在这个Adapter中动态的控制高度
    public List<Integer> mHeights;
    public StaggerGridAdapter(Context context, List<String> datas) {
        super(context,datas);
        mHeights = new ArrayList<>();
        for(int i=0;i<mDatas.size();i++) {
            mHeights.add((int) (100+ Math.random()*300));
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tv.setText(mDatas.get(position));
        //给TextView设置随机高度
        ViewGroup.LayoutParams params = holder.tv.getLayoutParams();
        params.height = mHeights.get(position);
        holder.tv.setLayoutParams(params);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里应该获取View在布局中的位置,如果直接使用position的话，当item增加或删除后，这个position就不对了
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView,layoutPosition);
                    return false;
                }
            });
        }
    }

}
