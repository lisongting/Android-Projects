package com.lst.kotlinproject.ui.activity

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Window
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
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
        initToolbar()
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)
    }

    @TargetApi(23)
    override fun onResume(){
        super.onResume()
        val r = ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (r == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        } else {
            loadForecast()
        }
    }

    private fun loadForecast(){
        async(UI){
            val result = bg{
                Log.d("tag","MainActivity -- async requestForecast")
                RequestForecastCommand(zipCode).execute()
            }
            updateUI(result.await())
        }
    }

    private fun updateUI(weekForecast:ForecastList){
        Log.d("tag","updateUI")
        weekForecast.dailyForecast.forEach{
            Log.d("tag","updateUI data:$it")
        }
        val adapter = ForecastListAdapter(weekForecast){
            startActivity<DetailActivity>(DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to weekForecast.city)
        }
        forecastList.adapter = adapter
        toolbarTitle = "${weekForecast.city} (${weekForecast.country})"

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode ==1&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            loadForecast()
        }
    }
}




