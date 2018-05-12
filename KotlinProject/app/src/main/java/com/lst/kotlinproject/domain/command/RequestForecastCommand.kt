package com.lst.kotlinproject.domain.command

import com.lst.kotlinproject.domain.datasource.ForecastProvider
import com.lst.kotlinproject.domain.model.ForecastList
class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        const val DAYS = 7
    }

    override fun execute() = forecastProvider.requestByZipCode(zipCode, DAYS)
}