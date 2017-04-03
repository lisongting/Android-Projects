package cn.ssdut.lst.easyreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import cn.ssdut.lst.easyreader.R;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;
import cn.ssdut.lst.easyreader.interfaze.OnRecyclerViewOnClickListener;

/**
 * Created by Administrator on 2017/3/29.
 */

public class ZhihuDailyNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context context;
    private final LayoutInflater inflater;
    private List<ZhihuDailyNews.Question> list = new ArrayList<>();
    private OnRecyclerViewOnClickListener mListener;

    private final static int TYPE_NORMAL = 0;
    private final static int TYPE_FOOTER = 1;

    public ZhihuDailyNewsAdapter(Context context,List<ZhihuDailyNews.Question> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new NormalViewHolder(inflater.inflate(R.layout.home_list_item_layout,parent,false),mListener);
                break;
            case TYPE_FOOTER:
                return new FooterViewHolder(inflater.inflate(R.layout.list_footer), false);
                break;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalViewHolder) {
            ZhihuDailyNews.Question item = list.get(position);
            if (item.getImages().get(0) == null) {
                ((NormalViewHolder) holder).itemImg.setImageResource(R.drawable.placeholder);
            }else{
                //使用glide加载图片
                Glide.with(context)
                        .load(item.getImages().get(0))
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((NormalViewHolder) holder).itemImg);
            }
            ((NormalViewHolder) holder).tvLatestNewsTitle.setText(item.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return ZhihuDailyNewsAdapter.TYPE_FOOTER;
        }
        return ZhihuDailyNewsAdapter.TYPE_NORMAL;
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener = listener;
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImg;
        private TextView tvLatestNewsTitle;
        private OnRecyclerViewOnClickListener listener;
        public NormalViewHolder(View itemView,OnRecyclerViewOnClickListener listener) {

            super(itemView);
            itemImg = (ImageView) itemView.findViewById(R.id.imageViewCover);
            tvLatestNewsTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v,getLayoutPosition());
            }
        }
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
