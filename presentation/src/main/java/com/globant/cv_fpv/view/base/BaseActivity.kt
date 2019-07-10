package com.globant.cv_fpv.view.base

import android.support.v7.app.AppCompatActivity
import com.globant.cv_fpv.CVApplication
import com.globant.cv_fpv.di.AppComponent

open class BaseActivity : AppCompatActivity() {
    protected val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as CVApplication).appComponent
    }
}