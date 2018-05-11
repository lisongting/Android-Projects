package com.lst.kotlinproject.domain

import com.lst.kotlinproject.Forecast
import com.lst.kotlinproject.ForecastResult
import java.text.DateFormat
import java.util.*
import com.lst.kotlinproject.domain.Forecast as ModelForecast

class ForecastDataMapper{
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country,
                convertForecastListToDomain(forecast.list))
    }

    fun convertForecastListToDomain(list: List<Forecast>)
            : List<ModelForecast> {
        return list.map { convertForecastItemToDomain(it) }
    }

//    fun convertForecastItemToDomain(forecast: Forecast): ModelForecast {
//        val iconUrl ="http://openweathermap.org/img/w/${forecast.weather[0].icon}.png"
//        return ModelForecast(convertDate(forecast.dt),
//                forecast.weather[0].description, forecast.temp.max.toInt(),
//                forecast.temp.min.toInt(),iconUrl)
//    }

    fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        //得到的是2018-5-10这样的字符串
        return df.format(date * 1000)
    }

    fun convertForecastItemToDomain(forecast: Forecast):ModelForecast{
        val iconUrl ="http://openweathermap.org/img/w/${forecast.weather[0].icon}.png"
        return ModelForecast(convertDate(forecast.dt),forecast.weather[0].description,
                forecast.temp.max.toInt(),forecast.temp.min.toInt(),iconUrl)
    }
}