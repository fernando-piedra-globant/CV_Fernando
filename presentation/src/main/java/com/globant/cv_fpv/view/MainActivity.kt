package com.globant.cv_fpv.view

import android.os.Bundle
import android.util.Log
import com.globant.cv_fpv.R
import com.globant.cv_fpv.view.base.BaseActivity
import io.reactivex.Flowable

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Flowable.just("perro").subscribe { Log.i("MainActivity", it) }
    }
}
