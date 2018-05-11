package com.lst.kotlinproject.domain

import com.lst.kotlinproject.ForecastRequest

class RequestForecastCommand(val zipCode:String):Command<ForecastList>{
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }
}