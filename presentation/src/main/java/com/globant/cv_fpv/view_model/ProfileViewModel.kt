package com.globant.cv_fpv.view_model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.globant.cv_fpv.model.Profile
import com.globant.cv_fpv.view_model.base.BaseViewModel
import com.globant.domain.use.cases.ProfileUseCase
import io.reactivex.observers.DisposableSingleObserver

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : BaseViewModel() {

    val profile = MutableLiveData<Profile>()

    fun loadProfile() {
        profileUseCase.execute().subscribe(object : DisposableSingleObserver<com.globant.domain.model.Profile>() {
            override fun onSuccess(it: com.globant.domain.model.Profile) {
                profile.value = Profile(
                    it.name, it.grade, it.address, it.email, it.phone
                )
                isLoading.value = false
                isError.value = false
            }

            override fun onError(e: Throwable) {
                isLoading.value = false
                isError.value = true
            }
        })
    }
}
