package com.lst.kotlinproject.ui

import android.app.Application

class App: Application(){
    companion object {
        var instance :App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}