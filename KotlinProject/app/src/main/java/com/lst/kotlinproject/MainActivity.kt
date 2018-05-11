package com.lst.kotlinproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.lst.kotlinproject.domain.RequestForecastCommand
import kotlinx.coroutines.experimental.async

class MainActivity : AppCompatActivity() {

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //不可变val的好处：不可再次对其赋值,增加程序安全性
        //由于是不可变的，所以线程安全
        val list = findViewById<RecyclerView>(R.id.forecast_list)
//        val list:RecyclerView = find(R.id.forecast_list)
        list.layoutManager = LinearLayoutManager(this)
//        list.adapter = ForecastListAdapter(items)

        //协程，简洁的异步操作并切换线程更新UI
        async(){
            val result = RequestForecastCommand("94034").execute()
            runOnUiThread{
                list.adapter = ForecastListAdapter(result)
            }
        }

    }
}




