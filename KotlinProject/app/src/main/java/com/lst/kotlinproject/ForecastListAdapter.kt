package com.lst.kotlinproject

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.lst.kotlinproject.domain.ForecastList

class ForecastListAdapter(val weekForecast: ForecastList) : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent?.context))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        //with表示使用某个特定的对象,于是在with下面的lambda表达式上下文都有了这个特定对象
        with(weekForecast[position]){
            holder?.textView?.text = "$date - $description - $high - $low"
        }

    }

    override fun getItemCount()= weekForecast.size()

    class ViewHolder(val textView:TextView):RecyclerView.ViewHolder(textView)

}