package com.lst.kotlinproject.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toolbar
import com.bumptech.glide.Glide
import com.lst.kotlinproject.R
import com.lst.kotlinproject.domain.command.RequestDayForecastCommand
import com.lst.kotlinproject.domain.model.Forecast
import com.lst.kotlinproject.extensions.color
import com.lst.kotlinproject.extensions.toDateString
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.ctx
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import java.text.DateFormat

class DetailActivity:AppCompatActivity(),ToolbarManager{
    override val toolbar: Toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    companion object {
        const val ID = "DetailActivity:id"
        const val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initToolbar()
        toolbarTitle = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }
        async(UI){
            val result = bg {
                RequestDayForecastCommand(intent.getLongExtra(ID,-1)).execute()
            }
            bindForecast(result.await())
        }
    }

    private fun bindForecast(forecast: Forecast)  = with(forecast){
        Glide.with(ctx)
                .load(forecast.iconUrl)
                .into(icon)
        toolbar.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature,low to minTemperature)
    }

    private fun bindWeather(vararg views:Pair<Int, TextView>) {
        views.forEach {
            it.second.text = "${it.first}"
            it.second.textColor = color(when(it.first){
                in -50..0 -> android.R.color.holo_red_dark
                in 0..15 -> android.R.color.holo_orange_dark
                else -> android.R.color.holo_green_dark
            })
        }
    }

}