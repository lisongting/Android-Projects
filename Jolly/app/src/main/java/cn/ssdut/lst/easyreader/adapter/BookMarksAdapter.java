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
import cn.ssdut.lst.easyreader.bean.GuokrHandpickNews;
import cn.ssdut.lst.easyreader.bean.ZhihuDailyNews;
import cn.ssdut.lst.easyreader.interfaze.OnRecyclerViewOnClickListener;

/**
 * Created by lisongting on 2017/4/19.
 */

//因为有多种ViewHolder,所以继承RecyclerView.Adapter<RecyclerView.ViewHolder>
public class BookMarksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final LayoutInflater inflater;
    //收藏的数据集合
    private List<DoubanMomentNews.posts> doubanList;
    private List<ZhihuDailyNews.Question> zhihuList;
    private List<GuokrHandpickNews.result> guokrList;

    //存放item的类型
    private List<Integer> types;

    private OnRecyclerViewOnClickListener listener;

    public static final int TYPE_ZHIHU_NORMAL = 0;
    public static final int TYPE_ZHIHU_WITH_HEADER = 1;
    public static final int TYPE_GUOKR_NORMAL = 2;
    public static final int TYPE_GUOKR_WITH_HEADER = 3;
    public static final int TYPE_DOUBAN_NORMAL = 4;
    public static final int TYPE_DOUBAN_WITH_HEADER = 5;

    public BookMarksAdapter(Context context,
                            ArrayList<ZhihuDailyNews.Question> zhihuList,
                            ArrayList<GuokrHandpickNews.result> guokrList,
                            ArrayList<DoubanMomentNews.posts> doubanList,
                            ArrayList<Integer> types) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        this.zhihuList = zhihuList;
        this.guokrList = guokrList;
        this.doubanList = doubanList;

        // types.size = zhihuList.size + guokrList.size + doubanList.size
        this.types = types;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_DOUBAN_NORMAL:
            case TYPE_GUOKR_NORMAL:
            case TYPE_ZHIHU_NORMAL:
                return new NormalViewHolder(inflater.inflate(R.layout.home_list_item_layout, parent, false), listener);
        }
        return new WithTypeViewHolder(inflater.inflate(R.layout.bookmark_header,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (types.get(position)) {
            case TYPE_ZHIHU_WITH_HEADER:
                ((WithTypeViewHolder) holder).textViewType.setText(context.getResources().getText(R.string.zhihu_daily));
                break;
            case TYPE_DOUBAN_WITH_HEADER:
                //这样是否可行？[mark]
                ((WithTypeViewHolder)holder).textViewType.setText(R.string.douban_moment);
                break;
            case TYPE_GUOKR_WITH_HEADER:
                ((WithTypeViewHolder)holder).textViewType.setText(R.string.douban_moment);
                break;
            case TYPE_ZHIHU_NORMAL:
                if (!zhihuList.isEmpty()) {
                    //这里为什么要减一？
                    ZhihuDailyNews.Question q = zhihuList.get(position-1);
                    Glide.with(context)
                            .load(q.getImages().get(0))
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .error(R.drawable.placeholder)
                            .centerCrop()
                            .into(((NormalViewHolder) holder).imageView);

                    ((NormalViewHolder)holder).textView.setText(q.getTitle());
                }
                break;
            case TYPE_GUOKR_NORMAL:
                //??
                GuokrHandpickNews.result r = guokrList.get(position - zhihuList.size() - 2);
                Glide.with(context)
                        .load(r.getHeadline_img_tb())
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(((NormalViewHolder) holder).imageView);

                ((NormalViewHolder)holder).textView.setText(r.getTitle());
                break;
            case TYPE_DOUBAN_NORMAL:
                if (!doubanList.isEmpty()) {
                    DoubanMomentNews.posts post = doubanList.get(position - zhihuList.size() - guokrList.size() - 3);
                    if (post.getThumbs().size() == 0) {
                        ((NormalViewHolder)holder).imageView.setVisibility(View.INVISIBLE);
                    } else {
                        Glide.with(context)
                                .load(post.getThumbs().get(0).getMedium().getUrl())
                                .asBitmap()
                                .placeholder(R.drawable.placeholder)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .error(R.drawable.placeholder)
                                .centerCrop()
                                .into(((NormalViewHolder)holder).imageView);
                    }

                    ((NormalViewHolder)holder).textView.setText(post.getTitle());
                }
                break;

        }
    }

    @Override
    public int getItemCount() {
        return types.size();
    }

    @Override
    public int getItemViewType(int pos) {
        return types.get(pos);
    }

    public void setItemListener(OnRecyclerViewOnClickListener listener) {
        this.listener = listener;
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;
        OnRecyclerViewOnClickListener listener;

        public NormalViewHolder(View itemView,OnRecyclerViewOnClickListener listener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewCover);
            textView = (TextView) itemView.findViewById(R.id.textViewTitle);
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

    public class WithTypeViewHolder extends RecyclerView.ViewHolder{
        TextView textViewType;
        public WithTypeViewHolder(View itemView) {
            super(itemView);
            textViewType = (TextView) itemView.findViewById(R.id.textViewType);
        }
    }

}
