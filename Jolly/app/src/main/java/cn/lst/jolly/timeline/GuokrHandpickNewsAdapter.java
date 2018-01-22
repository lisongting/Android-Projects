package cn.lst.jolly.timeline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.lst.jolly.R;
import cn.lst.jolly.data.GuokrHandpickNewsResult;

/**
 * Created by lisongting on 2018/1/16.
 */

public class GuokrHandpickNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private List<GuokrHandpickNewsResult> mList;
    private OnRecyclerViewItemOnClickListener mListener;

    public GuokrHandpickNewsAdapter(List<GuokrHandpickNewsResult> list, Context context) {
        this.mList = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_universal_layout, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        GuokrHandpickNewsResult item = mList.get(position);
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        Glide.with(context)
                .load(item.getImageInfo().getUrl())
                .asBitmap()
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .error(R.drawable.placeholder)
                .centerCrop()
                .into(holder.imageView);

        holder.textView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setItemClickListener(OnRecyclerViewItemOnClickListener listener) {
        this.mListener = listener;
    }

    public void updateData(List<GuokrHandpickNewsResult> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
        //todo : ?
        notifyItemRemoved(list.size());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        OnRecyclerViewItemOnClickListener listener;

        public ItemViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            this.listener = listener;
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.OnItemClick(v,getLayoutPosition());
            }
        }
    }
}
