package com.lst.kotlinproject.domain.command

import com.lst.kotlinproject.domain.datasource.ForecastProvider
import com.lst.kotlinproject.domain.model.Forecast

class RequestDayForecastCommand(
        val id: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}