package com.lst.kotlinproject.domain

interface Command<T>{
    fun execute():T
}


data class ForecastList(val city:String,val country:String
                        ,val dailyForecast:List<Forecast>){
    operator fun get(pos:Int):Forecast = dailyForecast[pos]

    fun size() = dailyForecast.size
}


data class Forecast(val date:String,val description:String,val high:Int,val low:Int,val iconUrl:String)