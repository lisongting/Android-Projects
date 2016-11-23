package com.test.administrator.uibestpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/9/10.
 */
public class MsgAdapter extends ArrayAdapter<Msg> {
    private int resourceId;
    public MsgAdapter(Context context,int textViewResourceId,List<Msg> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    public View getView(int position,View convertView,ViewGroup parent){
        Msg msg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){//如果view是空的,则从头加载
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.leftLayout  = (LinearLayout)view.findViewById(R.id.left_layout);
            viewHolder.rightLayout  = (LinearLayout)view.findViewById(R.id.right_layout);
            viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);
            viewHolder.rightMsg = (TextView)view.findViewById(R.id.right_msg);
            view.setTag(viewHolder);
        }else{//如果view已经被加载过了,则直接调用
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        if(msg.getType()==Msg.TYPE_RECEIVED){
            //如果是收到的消息,则显示左边的布局
            viewHolder.leftLayout.setVisibility(View.VISIBLE);
            viewHolder.rightLayout.setVisibility(View.GONE);
            viewHolder.leftMsg.setText(msg.getContent());
        }else if(msg.getType()==Msg.TYPE_SENT){
            //如果是发送的消息,则显示右边的布局
            viewHolder.leftLayout.setVisibility(View.GONE);
            viewHolder.rightLayout.setVisibility(View.VISIBLE);
            viewHolder.rightMsg.setText(msg.getContent());
        }
        return view;
    }

    class ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
    }
}
