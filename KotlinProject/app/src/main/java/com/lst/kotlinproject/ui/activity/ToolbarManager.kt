package com.lst.kotlinproject.ui.activity

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.lst.kotlinproject.R
import com.lst.kotlinproject.extensions.ctx
import com.lst.kotlinproject.extensions.slideEnter
import com.lst.kotlinproject.extensions.slideExit
import com.lst.kotlinproject.ui.App
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

interface ToolbarManager{
    val toolbar: Toolbar
    var toolbarTitle:String
        get() = toolbar.title.toString()
        set(value){
            toolbar.title = value
        }

    fun initToolbar(){
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings->toolbar.ctx.startActivity<SettingsActivity>()
                else -> App.instance.toast("unKnown option")
            }
            true
        }
    }

    fun enableHomeAsUp(up:()->Unit){
        toolbar.navigationIcon = DrawerArrowDrawable(toolbar.ctx).apply { progress = 1f }
        toolbar.setNavigationOnClickListener {
            up()
        }
    }

    fun attachToScroll(recyclerView:RecyclerView){
        recyclerView.addOnScrollListener(
                object :RecyclerView.OnScrollListener(){
                    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                        if (dy > 0) {
                            toolbar.slideExit()
                        } else {
                            toolbar.slideEnter()
                        }
                    }
                }
        )
    }
}