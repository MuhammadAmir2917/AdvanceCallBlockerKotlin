package com.example.advance.callblocker

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class App : Application(){

    companion object{
        lateinit var instance : App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
    }
}