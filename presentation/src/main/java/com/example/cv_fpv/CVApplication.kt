package com.example.cv_fpv

import android.app.Application
import com.example.cv_fpv.di.AppModule
import com.example.cv_fpv.di.DaggerAppComponent

class CVApplication : Application() {


    val appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()

}