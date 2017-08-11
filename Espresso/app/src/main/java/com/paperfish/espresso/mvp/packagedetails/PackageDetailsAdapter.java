package com.paperfish.espresso.mvp.packagedetails;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paperfish.espresso.R;
import com.paperfish.espresso.component.Timeline;
import com.paperfish.espresso.data.Package;
import com.paperfish.espresso.data.PackageStatus;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by lisongting on 2017/8/5.
 * 包裹详情页面
 * 该页面其实就两个类型的ViewHolder，一个是头部：展示运单号和公司等，一个是时间轴：展示整个包裹的运输
 */

public class PackageDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private LayoutInflater layoutInflater;
    private final Package aPackage;
    private final List<PackageStatus> list;

    public static final int TYPE_HEADER = 0x00;
    public static final int TYPE_NORMAL = 0x01;
    public static final int TYPE_START = 0x02;
    public static final int TYPE_FINISH = 0x03;
    public static final int TYPE_SINGLE = 0x04;

    public PackageDetailsAdapter(Context context, Package aPackage) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.aPackage = aPackage;
        this.list = new ArrayList<>();
        for (PackageStatus status : aPackage.getData()) {
            list.add(status);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(layoutInflater.inflate(R.layout.item_details_header, parent, false));
        }
        return new PackageStatusViewHolder(layoutInflater.inflate(R.layout.item_package_status, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            HeaderViewHolder vh = (HeaderViewHolder) holder;
            vh.textViewCompany.setText(aPackage.getCompanyChineseName());
            vh.textViewName.setText(aPackage.getName());
            vh.textViewNumber.setText(aPackage.getNumber());
        } else {
            //因为还包含一个头部，所以要-1
            PackageStatus item = list.get(position - 1);
            PackageStatusViewHolder vh = (PackageStatusViewHolder) holder;
            //如果只有一条信息
            if (getItemViewType(position) == TYPE_SINGLE) {
                vh.timeLine.setStartLine(null);
                vh.timeLine.setFinishLine(null);
            } else if (getItemViewType(position) == TYPE_START) {
                vh.timeLine.setStartLine(null);
            } else if (getItemViewType(position) == TYPE_FINISH) {
                vh.timeLine.setFinishLine(null);
            }

            vh.textViewTime.setText(item.getTime());
            vh.textViewLocation.setText(item.getContext());
            String phone = item.getPhone();
            if (phone != null) {
                vh.textViewPhone.setText(phone);
            }
            vh.contactCard.setVisibility(phone != null ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return aPackage.getData().size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == 1 && position == list.size()) {
            //如果总共只有一条物流信息
            return TYPE_SINGLE;
        } else if (position == 1) {
            return TYPE_START;
        } else if (position == list.size()) {
            return TYPE_FINISH;
        }

        return TYPE_NORMAL;
    }


    public void updateData(RealmList<PackageStatus> list) {
        this.list.clear();
        for (PackageStatus ps : list) {
            this.list.add(ps);
        }
        notifyDataSetChanged();
    }

    public class PackageStatusViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView textViewLocation;
        private AppCompatTextView textViewTime;
        private Timeline timeLine;
        private CardView contactCard;
        private AppCompatTextView textViewPhone;

        public PackageStatusViewHolder(View itemView) {
            super(itemView);
            textViewLocation = (AppCompatTextView) itemView.findViewById(R.id.textViewStatus);
            textViewTime = (AppCompatTextView) itemView.findViewById(R.id.textViewTime);
            timeLine = (Timeline) itemView.findViewById(R.id.timeLine);
            contactCard = (CardView) itemView.findViewById(R.id.contactCard);
            textViewPhone = (AppCompatTextView) itemView.findViewById(R.id.textViewPhone);


            contactCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String uri = textViewPhone.getText().toString();
                    if (uri.length() > 0) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + uri));
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView textViewCompany,textViewNumber, textViewName;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            textViewCompany = (AppCompatTextView) itemView.findViewById(R.id.textViewCompany);
            textViewNumber = (AppCompatTextView) itemView.findViewById(R.id.textViewPackageNumber);
            textViewName = (AppCompatTextView) itemView.findViewById(R.id.textViewName);

            textViewCompany.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (aPackage.getCompany() != null) {
                        Intent intent = new Intent(context, CompanyDetailActivity.class);
                        intent.putExtra(CompanyDetailActivity.COMPANY_ID, aPackage.getCompany());
                        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((PackageDetailsActivity) context).toBundle());
                    }
                }
            });
        }
    }
}
