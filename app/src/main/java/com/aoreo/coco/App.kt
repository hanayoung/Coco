package com.aoreo.coco

import android.app.Application
import android.content.Context
import timber.log.Timber

class App : Application() {

    init {
        instance = this // 이게 그렇게 큰 일인가..?
    }

    companion object {

        private var instance : App? = null

        fun context() : Context {
            return instance!!.applicationContext
        }

    } // 이후 편할 것 같아서 세팅

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree()) // 로그찍는 기능 세팅
    }
}