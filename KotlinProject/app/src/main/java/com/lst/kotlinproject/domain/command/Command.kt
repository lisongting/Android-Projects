package com.lst.kotlinproject.domain.command

interface Command<out T>{
    fun execute():T
}

