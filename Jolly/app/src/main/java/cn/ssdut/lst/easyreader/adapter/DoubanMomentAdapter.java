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
import cn.ssdut.lst.easyreader.bean.DoubanMomentNews;
import cn.ssdut.lst.easyreader.interfaze.OnRecyclerViewOnClickListener;

/**
 * Created by lisongting on 2017/4/19.
 */

public class DoubanMomentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;

    //list中装的都是实体类
    private List<DoubanMomentNews.posts> list;

    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_NO_IMG = 3;

    private OnRecyclerViewOnClickListener listener;

    public DoubanMomentAdapter(Context context, ArrayList<DoubanMomentNews.posts> list) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NORMAL:
                return new NormalViewHolder(inflater.inflate(R.layout.home_list_item_layout,parent,false), listener);
            case TYPE_NO_IMG:
                return new NoImageViewHolder(inflater.inflate(R.layout.home_list_item_without_image, parent, false), listener);
            case TYPE_FOOTER:
                return new FooterViewHolder(inflater.inflate(R.layout.list_footer, parent, false));
        }
        return null;
    }

    /*Glide的缓存策略:
    DiskCacheStrategy.NONE :不缓存图片
    DiskCacheStrategy.SOURCE :缓存图片源文件
    DiskCacheStrategy.RESULT:缓存修改过的图片
    DiskCacheStrategy.ALL:缓存所有的图片，默认*/
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(!(holder instanceof FooterViewHolder)){
            DoubanMomentNews.posts dataItem = list.get(position);
            if (holder instanceof NormalViewHolder) {
                Glide.with(context)
                        .load(dataItem.getThumbs().get(0).getMedium().getUrl())//获取图片的URL
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((NormalViewHolder) holder).ivHeadImg);
                ((NormalViewHolder) holder).tvTitle.setText(dataItem.getTitle());
            }else if (holder instanceof NoImageViewHolder) {
                ((NoImageViewHolder) holder).tvTitle.setText(dataItem.getTitle());
            }
        }
        //如果是底部,不处理
    }

    //从外部传入自定义监听器
    public void setItemClickListener(OnRecyclerViewOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return TYPE_FOOTER;
        }
        //获取某个条目的图片集合，如果为空，则设置为没有图片的item样式
        if (list.get(position).getThumbs().size() == 0) {
            return TYPE_NO_IMG;
        }
        return TYPE_NORMAL;
    }
    @Override
    public int getItemCount() {
        return list.size()+1;
    }


    //正常的ViewHolder：只显示一个TextView和一个ImageView
    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivHeadImg;
        TextView tvTitle;
        OnRecyclerViewOnClickListener listener;

        public NormalViewHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            ivHeadImg = (ImageView) itemView.findViewById(R.id.imageViewCover);
            tvTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
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

    //没有图片的那种ViewHolder
    public class NoImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        OnRecyclerViewOnClickListener listener;
        public NoImageViewHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, getLayoutPosition());
            }
        }
    }

    //底部ViewHolder
    public class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
