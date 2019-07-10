package com.globant.cv_fpv.navigator

import com.globant.cv_fpv.R
import com.globant.cv_fpv.view.base.BaseActivity
import com.globant.cv_fpv.view.base.BaseFragment

class Navigator {

    fun addFragment(activity: BaseActivity, fragment: BaseFragment) =
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.view, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
}
