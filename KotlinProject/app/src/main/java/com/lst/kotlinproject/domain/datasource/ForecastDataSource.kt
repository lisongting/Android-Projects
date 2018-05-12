package com.lst.kotlinproject.domain.datasource

import com.lst.kotlinproject.domain.model.Forecast
import com.lst.kotlinproject.domain.model.ForecastList

interface ForecastDataSource {

    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?

    fun requestDayForecast(id: Long): Forecast?

}