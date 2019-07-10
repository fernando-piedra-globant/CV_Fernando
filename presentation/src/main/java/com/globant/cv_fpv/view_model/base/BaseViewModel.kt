package com.globant.cv_fpv.view_model.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()

    init {
        resetStatus()
    }

    private fun resetStatus() {
        isLoading.value = true
        isError.value = false
    }
}