package com.paperfish.espresso.mvp.packages;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paperfish.espresso.MainActivity;
import com.paperfish.espresso.R;
import com.paperfish.espresso.data.Package;
import com.paperfish.espresso.interfaces.OnRecyclerViewItemClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lisongting on 2017/7/15.
 */

public class PackagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<Package> list;
    private OnRecyclerViewItemClickListener listener;
    private String[] packageStatus;

    public PackagesAdapter(Context context, List<Package> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.list = list;
        packageStatus = context.getResources().getStringArray(R.array.package_status);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PackageViewHolder(inflater.inflate(R.layout.item_package, parent, false),listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Package item = list.get(position);
        PackageViewHolder viewHolder = (PackageViewHolder) holder;
        //data是存放packageState的一个list
        if (item.getData() != null && item.getData().size() > 0) {
            int state = Integer.parseInt(item.getState());
            //???????????
            viewHolder.textViewStatus.setText(String.valueOf(packageStatus[state]) + " - " + item.getData().get(0).getContext());
            viewHolder.textViewTime.setText(item.getData().get(0).getTime());
        }else {
            viewHolder.textViewTime.setText("");
            viewHolder.textViewStatus.setText(R.string.get_status_error);
        }

        if (item.isReadable()) {
            viewHolder.textViewPackageName.setTypeface(null, Typeface.BOLD);
            viewHolder.textViewTime.setTypeface(null, Typeface.BOLD);
            viewHolder.textViewStatus.setTypeface(null, Typeface.BOLD);
        } else {
            viewHolder.textViewPackageName.setTypeface(null, Typeface.NORMAL);
            viewHolder.textViewTime.setTypeface(null, Typeface.NORMAL);
            viewHolder.textViewStatus.setTypeface(null, Typeface.NORMAL);
        }

        viewHolder.textViewPackageName.setText(item.getName());
        viewHolder.textViewAvatar.setText(item.getName().substring(0,1));
        viewHolder.circleImageViewAvatar.setImageResource(item.getColorAvatar());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(@NonNull List<Package> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }
    public class PackageViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener,View.OnCreateContextMenuListener{
        TextView textViewPackageName;
        TextView textViewTime;
        TextView textViewStatus;
        TextView textViewAvatar;
        TextView textViewRemove;
        ImageView imageViewRemove;
        CircleImageView circleImageViewAvatar;
        LinearLayout layoutMain;
        View wrapperView;
        private OnRecyclerViewItemClickListener listener;

        public PackageViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView);
            textViewPackageName = (TextView) itemView.findViewById(R.id.textViewPackageName);
            textViewStatus = (AppCompatTextView) itemView.findViewById(R.id.textViewStatus);
            textViewTime = (AppCompatTextView) itemView.findViewById(R.id.textViewTime);
            textViewAvatar = (AppCompatTextView) itemView.findViewById(R.id.textViewAvatar);
            textViewRemove = (AppCompatTextView) itemView.findViewById(R.id.textViewRemove);
            imageViewRemove = (ImageView) itemView.findViewById(R.id.imageViewRemove);
            circleImageViewAvatar = (CircleImageView) itemView.findViewById(R.id.circleImageView);
            layoutMain = (LinearLayout) itemView.findViewById(R.id.layoutPackageItemMain);
            wrapperView = itemView.findViewById(R.id.layoutPackageItem);

            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onClick(View v) {
            if (this.listener != null) {
                listener.OnItemClick(v, getLayoutPosition());
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            if (menu != null) {
                ((MainActivity) context).setSelectedPackageId(list.get(getLayoutPosition()).getNumber());
                Package pack = list.get(getLayoutPosition());
                menu.setHeaderTitle(pack.getName());
                if (pack.isReadable()) {
                    //长按后设为已读或未读
                    menu.add(Menu.NONE, R.id.action_set_readable, 0, R.string.set_read);
                } else {
                    menu.add(Menu.NONE, R.id.action_set_readable, 0, R.string.set_unread);
                }
                menu.add(Menu.NONE, R.id.action_copy_code, 0, R.string.copy_code);
                menu.add(Menu.NONE, R.id.action_share, 0, R.string.share);
            }
        }
    }
}
