package cn.ssdut.lst.swiperefreshlayouttest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private List<String> mDatas;
    private Context context;
    private OnItemClickListener mItemClickListener;

    private static int load_status = 2;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_IDLE = 2;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOTER = 2;

    interface OnItemClickListener {
        void onItemClick(View item,int position);
        void onItemLongClick(View item,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;
    }

    public RecyclerViewAdapter(Context context,List<String> datas) {
        this.context = context;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("tag", "----onCreateViewHolder---");
        if (viewType == TYPE_ITEM) {
            View v = mInflater.inflate(R.layout.item_line, parent,false);
            MyViewHolder viewHolder = new MyViewHolder(v);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View v = mInflater.inflate(R.layout.view_footer, parent, false);
            return new FooterHolder(v);
        }
        return null;
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        Log.i("tag", "----onBindViewHolder---");
        if (viewHolder instanceof FooterHolder) {
            FooterHolder footerHolder = (FooterHolder) viewHolder;
            //如果需要显示加载视图
            if (load_status == STATUS_LOADING) {
                footerHolder.linearLayout.setVisibility(View.VISIBLE);
                footerHolder.tv.setText("正在拼命加载中....");
            }else{
                footerHolder.linearLayout.setVisibility(View.GONE);
            }


        } else if (viewHolder instanceof MyViewHolder) {

            MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
            myViewHolder.tv.setText(mDatas.get(position));
        }
        //设置监听器
        if (mItemClickListener != null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    int layoutPosition = viewHolder.getLayoutPosition();
                    mItemClickListener.onItemClick(viewHolder.itemView,layoutPosition);
                }
            });
            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int layoutPosition = viewHolder.getLayoutPosition();
                    mItemClickListener.onItemLongClick(viewHolder.itemView,layoutPosition);
                    return true;
                }
            });
        }
    }
    public int getItemCount() {
        return mDatas.size();
    }

    //下拉刷新的时候调用这个方法
    public void addData(int pos){
        mDatas.add(pos,"New Item");
        notifyItemInserted(pos);
        notifyDataSetChanged();
    }

    //上拉加载的时候调用这个方法
    public void loadData(int count) {
        int oldSize = getItemCount();
        for(int i=0;i<count;i++) {
            mDatas.add("Recently Loaded Item");
        }
        notifyItemRangeInserted(oldSize,count);
        notifyDataSetChanged();
    }

    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }

    public void changeLoadingState(int status){
        load_status = status;
        notifyDataSetChanged();
    }

}
