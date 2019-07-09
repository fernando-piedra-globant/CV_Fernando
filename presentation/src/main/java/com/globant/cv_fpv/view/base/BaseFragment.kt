package com.globant.cv_fpv.view.base

import android.support.v4.app.Fragment
import com.globant.cv_fpv.CVApplication
import com.globant.cv_fpv.di.AppComponent

open class BaseFragment : Fragment()
{
    protected val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as CVApplication).appComponent
    }
}