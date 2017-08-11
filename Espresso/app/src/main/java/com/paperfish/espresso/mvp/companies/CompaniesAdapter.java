package com.paperfish.espresso.mvp.companies;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paperfish.espresso.R;
import com.paperfish.espresso.data.Company;
import com.paperfish.espresso.interfaces.OnRecyclerViewItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lisongting on 2017/8/11.
 */

public class CompaniesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;

    private LayoutInflater inflater;

    private List<Company> list;

    private OnRecyclerViewItemClickListener listener;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_WITH_HEADER = 1;

    public CompaniesAdapter(Context context, List<Company> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new NormalViewHolder(inflater.inflate(R.layout.item_company, parent, false), listener);
        }

        return new WithHeaderViewHolder(inflater.inflate(R.layout.item_company_with_header, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Company company = list.get(position);
        if (holder instanceof NormalViewHolder) {
            NormalViewHolder vh = (NormalViewHolder) holder;
            vh.avatar.setColorFilter(Color.parseColor(company.getAvatarColor()));
            vh.textViewAvatar.setText(company.getName().substring(0, 1).toUpperCase());
            vh.textViewCompanyName.setText(company.getName());
            vh.textViewCompanyTel.setText(company.getTel());
        } else if (holder instanceof WithHeaderViewHolder) {
            WithHeaderViewHolder wh = (WithHeaderViewHolder) holder;
            wh.textViewAvatar.setText(company.getName().substring(0, 1).toUpperCase());
            wh.textViewCompanyTel.setText(company.getTel());
            wh.textViewCompanyName.setText(company.getName());
            wh.stickyHeaderText.setText(getSectionName(position));
            wh.avatar.setColorFilter(Color.parseColor(company.getAvatarColor()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public String getSectionName(int position) {
        if (list.isEmpty()) {
            return "";
        }
        char c = list.get(position).getAlphabet().charAt(0);
        if (Character.isDigit(c)) {
            return "#";
        } else {
            //返回大写
            return Character.toString(Character.toUpperCase(c));
        }
    }

    @Override
    public int getItemViewType(int pos) {
        //如果是列表第一个或者是一个以新字母开头的公司，则为带有头部的条目，以标识是哪个字母开头的公司
        if (pos == 0 || (list.get(pos).getAlphabet().charAt(0) != list.get(pos - 1).getAlphabet().charAt(0))) {
            return TYPE_WITH_HEADER;
        }
        return TYPE_NORMAL;
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        CircleImageView avatar;
        AppCompatTextView textViewCompanyName;
        AppCompatTextView textViewAvatar;
        AppCompatTextView textViewCompanyTel;

        private OnRecyclerViewItemClickListener listener;

        public NormalViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);

            avatar = (CircleImageView) itemView.findViewById(R.id.imageViewAvatar);
            textViewAvatar = (AppCompatTextView) itemView.findViewById(R.id.textViewAvatar);
            textViewCompanyName = (AppCompatTextView) itemView.findViewById(R.id.textViewCompanyName);
            textViewCompanyTel = (AppCompatTextView) itemView.findViewById(R.id.textViewCompanyTel);

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

    public class WithHeaderViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        CircleImageView avatar;
        AppCompatTextView textViewCompanyName;
        AppCompatTextView textViewAvatar;
        AppCompatTextView textViewCompanyTel;
        AppCompatTextView stickyHeaderText;

        private OnRecyclerViewItemClickListener listener;

        public WithHeaderViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            avatar = (CircleImageView) itemView.findViewById(R.id.imageViewAvatar);
            textViewAvatar = (AppCompatTextView) itemView.findViewById(R.id.textViewAvatar);
            textViewCompanyName = (AppCompatTextView) itemView.findViewById(R.id.textViewCompanyName);
            textViewCompanyTel = (AppCompatTextView) itemView.findViewById(R.id.textViewCompanyTel);
            stickyHeaderText = (AppCompatTextView) itemView.findViewById(R.id.headerText);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (this.listener != null) {
                listener.OnItemClick(v,getLayoutPosition());
            }
        }
    }



}
