package com.globant.cv_fpv

import android.app.Application
import com.globant.cv_fpv.di.AppModule
import com.globant.cv_fpv.di.DaggerAppComponent

class CVApplication : Application() {
    val appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()

}