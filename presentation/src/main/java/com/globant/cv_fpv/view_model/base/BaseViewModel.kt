package com.globant.cv_fpv.view_model.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    protected val disposables = CompositeDisposable()

    init {
        resetStatus()
    }

    private fun resetStatus() {
        isLoading.value = true
        isError.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}