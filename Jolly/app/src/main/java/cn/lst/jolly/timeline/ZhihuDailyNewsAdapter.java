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
import cn.lst.jolly.data.ZhihuDailyNewsQuestion;

/**
 * Created by lisongting on 2018/1/2.
 */

public class ZhihuDailyNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;

    private List<ZhihuDailyNewsQuestion> mList;

    private OnRecyclerViewItemOnClickListener mListener;

    public ZhihuDailyNewsAdapter(List<ZhihuDailyNewsQuestion> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_universal_layout,parent,false),mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ZhihuDailyNewsQuestion item = mList.get(position);

        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        if (item.getImages().get(0) == null) {
            holder.itemImg.setImageResource(R.drawable.placeholder);
        } else {
            Glide.with(mContext)
                    .load(item.getImages().get(0))
                    .asBitmap()
                    .placeholder(R.drawable.placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .error(R.drawable.placeholder)
                    .centerCrop()
                    .into(holder.itemImg);
        }
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView itemImg;
        private TextView title;
        private OnRecyclerViewItemOnClickListener listener;

        public ItemViewHolder(View itemView,OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            itemImg = itemView.findViewById(R.id.image_view_cover);
            title = itemView.findViewById(R.id.text_view_title);
            this.listener = listener;
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
