package com.lst.kotlinproject.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lst.kotlinproject.R
import com.lst.kotlinproject.domain.model.Forecast
import com.lst.kotlinproject.domain.model.ForecastList
import com.lst.kotlinproject.extensions.ctx
import com.lst.kotlinproject.extensions.toDateString
import org.jetbrains.anko.sdk25.coroutines.onClick

interface OnItemClickListener{
    operator fun invoke(forecast: Forecast)
}

class ForecastListAdapter(val weekForecast: ForecastList, val itemClick:(Forecast)->Unit)
    : RecyclerView.Adapter<ForecastListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.ctx).inflate(R.layout.item_forecast,parent,false)
        return ViewHolder(v,itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindForecast(weekForecast[position])
    }

    override fun getItemCount()= weekForecast.size

    class ViewHolder( val container: View, val itemClick: (Forecast)->Unit)
        :RecyclerView.ViewHolder(container){

        val iconView: ImageView
        val dateView:TextView
        val descriptionView:TextView
        val maxTemperatureView:TextView
        val minTemperatureView:TextView
        init {
            iconView = container.findViewById(R.id.icon)
            dateView = container.findViewById(R.id.date)
            descriptionView = container.findViewById(R.id.description)
            maxTemperatureView = container.findViewById(R.id.maxTemperature)
            minTemperatureView = container.findViewById(R.id.middle)
        }
        fun bindForecast(forecast: Forecast){
            with(forecast){
                Glide.with(itemView.context)
                        .load(iconUrl)
                        .into(iconView)
                dateView.text = date.toDateString()
                descriptionView.text = description
                maxTemperatureView.text = "$high"
                minTemperatureView.text = "$low"
                itemView.onClick {
                    itemClick(this@with)
                }
            }
        }
    }

}