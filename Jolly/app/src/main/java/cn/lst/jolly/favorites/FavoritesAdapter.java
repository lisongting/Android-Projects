package cn.lst.jolly.favorites;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
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

import cn.lst.jolly.R;
import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.GuokrHandpickNewsResult;
import cn.lst.jolly.data.ZhihuDailyNewsQuestion;
import cn.lst.jolly.timeline.OnRecyclerViewItemOnClickListener;

/**
 * Created by lisongting on 2018/1/22.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final List<ZhihuDailyNewsQuestion> mZhihuList;
    private final List<DoubanMomentNewsPosts> mDoubanList;
    private final List<GuokrHandpickNewsResult> mGuokrList;
    private final List<ItemWrapper> mWrapperList;
    private OnRecyclerViewItemOnClickListener mListener;

    public FavoritesAdapter(Context context,
                            List<ZhihuDailyNewsQuestion> zhihuDailyNewsList,
                            List<DoubanMomentNewsPosts> doubanMomentNewsList,
                            List<GuokrHandpickNewsResult> guokrHandpickNewsList){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mZhihuList = zhihuDailyNewsList;
        mDoubanList = doubanMomentNewsList;
        mGuokrList = guokrHandpickNewsList;

        mWrapperList = new ArrayList<>();
        //把头部加进去，然后再把条目放在每个头部的下面
        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_ZHIHU_CATEGORY));
        if (mZhihuList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for(int i=0;i<mZhihuList.size();i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ZHIHU);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }
        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_DOUBAN_CATEGORY));
        if (mDoubanList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        }else {
            for(int i=0;i<mDoubanList.size();i++) {
                if (mDoubanList.get(i).getThumbs().isEmpty()) {
                    ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_DOUBAN_NO_IMG);
                    iw.index = i;
                    mWrapperList.add(iw);
                } else {
                    ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_DOUBAN);
                    iw.index = i;
                    mWrapperList.add(iw);
                }
            }
        }

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_GUOKR_CATEGORY));
        if (mGuokrList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for(int i=0;i<mGuokrList.size();i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_GUOKR);
                iw.index = i;
                mWrapperList.add(iw);
            }
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ItemWrapper.TYPE_EMPTY:
                viewHolder = new EmptyViewHolder(mLayoutInflater.inflate(R.layout.item_empty, viewGroup, false));
                break;
            case ItemWrapper.TYPE_ZHIHU:
                viewHolder = new ZhihuItemViewHolder(mLayoutInflater.inflate(R.layout.item_universal_layout, viewGroup, false), mListener);
                break;
            case ItemWrapper.TYPE_DOUBAN:
                viewHolder = new DoubanItemHolder(mLayoutInflater.inflate(R.layout.item_universal_layout, viewGroup, false), mListener);
                break;
            case ItemWrapper.TYPE_DOUBAN_NO_IMG:
                viewHolder = new DoubanNoImageHolder(mLayoutInflater.inflate(R.layout.item_universal_without_image, viewGroup, false), mListener);
                break;
            case ItemWrapper.TYPE_GUOKR:
                viewHolder = new GuokrViewHolder(mLayoutInflater.inflate(R.layout.item_universal_layout, viewGroup, false), mListener);
                break;
            case ItemWrapper.TYPE_ZHIHU_CATEGORY:
            case ItemWrapper.TYPE_DOUBAN_CATEGORY:
            case ItemWrapper.TYPE_GUOKR_CATEGORY:
                viewHolder = new CategoryViewHolder(mLayoutInflater.inflate(R.layout.item_category, viewGroup, false));
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemWrapper iw = mWrapperList.get(position);
        switch (iw.viewType) {
            case ItemWrapper.TYPE_ZHIHU:
                ZhihuItemViewHolder zivh = (ZhihuItemViewHolder)viewHolder;
                ZhihuDailyNewsQuestion question = mZhihuList.get(iw.index);
                if (question.getImages().get(0) == null) {
                    zivh.imageView.setImageResource(R.drawable.placeholder);
                } else {
                    Glide.with(mContext)
                            .load(question.getImages().get(0))
                            .asBitmap()
                            .placeholder(R.drawable.placeholder)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .error(R.drawable.placeholder)
                            .centerCrop()
                            .into(zivh.imageView);
                }
                zivh.textView.setText(question.getTitle());
                break;
            case ItemWrapper.TYPE_DOUBAN:
                DoubanItemHolder dih = (DoubanItemHolder) viewHolder;
                DoubanMomentNewsPosts post = mDoubanList.get(iw.index);

                Glide.with(mContext)
                        .load(post.getThumbs().get(0).getMedium().getUrl())
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(dih.imageView);

                dih.textView.setText(post.getTitle());
                break;

            case ItemWrapper.TYPE_DOUBAN_NO_IMG:
                DoubanNoImageHolder dnih = (DoubanNoImageHolder) viewHolder;
                DoubanMomentNewsPosts p = mDoubanList.get(iw.index);
                dnih.textView.setText(p.getTitle());
                break;

            case ItemWrapper.TYPE_GUOKR:
                GuokrViewHolder gvh = (GuokrViewHolder) viewHolder;
                GuokrHandpickNewsResult r = mGuokrList.get(iw.index);
                Glide.with(mContext)
                        .load(r.getImageInfo().getUrl())
                        .asBitmap()
                        .placeholder(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(gvh.imageView);

                gvh.textView.setText(r.getTitle());
                break;

            case ItemWrapper.TYPE_ZHIHU_CATEGORY:
                CategoryViewHolder cvh1 = (CategoryViewHolder) viewHolder;
                cvh1.textViewCategory.setText(mContext.getString(R.string.zhihu_daily));
                break;

            case ItemWrapper.TYPE_DOUBAN_CATEGORY:
                CategoryViewHolder cvh2 = (CategoryViewHolder) viewHolder;
                cvh2.textViewCategory.setText(mContext.getString(R.string.douban_moment));
                break;

            case ItemWrapper.TYPE_GUOKR_CATEGORY:
                CategoryViewHolder cvh3 = (CategoryViewHolder) viewHolder;
                cvh3.textViewCategory.setText(mContext.getString(R.string.guokr_handpick));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mWrapperList == null ? 0 : mWrapperList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mWrapperList.get(position).viewType;
    }

    public void setOnItemClickListener(OnRecyclerViewItemOnClickListener listener){
        this.mListener = listener;
    }

    public int getOriginalIndex(int position) {
        return mWrapperList.get(position).index;
    }

    public void updateData(List<ZhihuDailyNewsQuestion> zhihuDailyNewsList,
                           List<DoubanMomentNewsPosts> doubanMomentNewsList,
                           List<GuokrHandpickNewsResult> guokrHandpickNewsList) {
        mZhihuList.clear();
        mDoubanList.clear();
        mGuokrList.clear();
        mWrapperList.clear();

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_ZHIHU_CATEGORY));
        if (zhihuDailyNewsList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < zhihuDailyNewsList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_ZHIHU);
                iw.index = i;
                mWrapperList.add(iw);
                mZhihuList.add(zhihuDailyNewsList.get(i));
            }
        }

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_DOUBAN_CATEGORY));
        if (doubanMomentNewsList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < doubanMomentNewsList.size(); i++) {
                if (doubanMomentNewsList.get(i).getThumbs().isEmpty()) {
                    ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_DOUBAN_NO_IMG);
                    iw.index = i;
                    mWrapperList.add(iw);
                } else {
                    ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_DOUBAN);
                    iw.index = i;
                    mWrapperList.add(iw);
                }
                mDoubanList.add(doubanMomentNewsList.get(i));
            }
        }

        mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_GUOKR_CATEGORY));
        if (guokrHandpickNewsList.isEmpty()) {
            mWrapperList.add(new ItemWrapper(ItemWrapper.TYPE_EMPTY));
        } else {
            for (int i = 0; i < guokrHandpickNewsList.size(); i++) {
                ItemWrapper iw = new ItemWrapper(ItemWrapper.TYPE_GUOKR);
                iw.index = i;
                mWrapperList.add(iw);
                mGuokrList.add(guokrHandpickNewsList.get(i));
            }
        }

        notifyDataSetChanged();
    }

    public class ZhihuItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;
        OnRecyclerViewItemOnClickListener listener;

        public ZhihuItemViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }
    public class DoubanItemHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        OnRecyclerViewItemOnClickListener listener;

        public DoubanItemHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }

    public class DoubanNoImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        OnRecyclerViewItemOnClickListener listener;

        public DoubanNoImageHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_title);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }

    public class GuokrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;

        OnRecyclerViewItemOnClickListener listener;

        public GuokrViewHolder(View itemView, OnRecyclerViewItemOnClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_cover);
            textView = itemView.findViewById(R.id.text_view_title);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.OnItemClick(view, getLayoutPosition());
            }
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewEmpty;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            textViewEmpty = itemView.findViewById(R.id.text_view_empty);
        }
    }
    public static class ItemWrapper{

        public final static int TYPE_ZHIHU = 0x00;
        public final static int TYPE_DOUBAN = 0x01;
        public final static int TYPE_GUOKR = 0x02;
        public final static int TYPE_EMPTY = 0x03;
        public final static int TYPE_DOUBAN_NO_IMG = 0x04;
        public final static int TYPE_ZHIHU_CATEGORY = 0x05;
        public final static int TYPE_DOUBAN_CATEGORY = 0x06;
        public final static int TYPE_GUOKR_CATEGORY = 0x07;

        public int viewType;
        public int index;

        public ItemWrapper(int viewType) {
            this.viewType = viewType;
        }

    }


}
