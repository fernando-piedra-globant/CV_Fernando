package com.globant.cv_fpv

import android.app.Application
import com.globant.cv_fpv.di.AppComponent
import com.globant.cv_fpv.di.AppModule
import com.globant.cv_fpv.di.DaggerAppComponent

class CVApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()
}