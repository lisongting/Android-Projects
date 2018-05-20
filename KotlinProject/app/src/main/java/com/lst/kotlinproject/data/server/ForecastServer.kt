package com.lst.kotlinproject.data.server

import com.lst.kotlinproject.data.db.ForecastDb
import com.lst.kotlinproject.domain.datasource.ForecastDataSource
import com.lst.kotlinproject.domain.model.ForecastList

class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

//    init {
//        Log.d("tag","ForecastServer create")
//    }
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
//        Log.d("tag","ForecastServer -- requestForecastByZipCode")
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}