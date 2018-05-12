package com.lst.kotlinproject.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toolbar
import com.lst.kotlinproject.R
import com.lst.kotlinproject.domain.command.RequestForecastCommand
import com.lst.kotlinproject.domain.model.ForecastList
import com.lst.kotlinproject.ui.DelegatesExt
import com.lst.kotlinproject.ui.adapter.ForecastListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(),ToolbarManager {

    private val zipCode:Long by DelegatesExt.preference(this,SettingsActivity.ZIP_CODE,SettingsActivity.DEFAULT_ZIP)

    override val toolbar: Toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }

    override fun onResume(){
        super.onResume()
        loadForecast()
    }

    private fun loadForecast(){
        async(UI){
            val result = bg{
                RequestForecastCommand(zipCode).execute()
            }
            updateUI(result.await())
        }
    }

    private fun updateUI(weekForecast:ForecastList){
        val adapter = ForecastListAdapter(weekForecast){
            startActivity<DetailActivity>(DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to weekForecast)
        }
        forecastList.adapter = adapter
        toolbarTitle = "${weekForecast.city} (${weekForecast.country})"

    }
}




