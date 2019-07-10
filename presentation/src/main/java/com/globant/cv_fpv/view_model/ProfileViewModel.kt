package com.globant.cv_fpv.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.globant.cv_fpv.model.ProfileModel
import com.globant.cv_fpv.view_model.base.BaseViewModel
import com.globant.domain.interactor.usecases.ProfileUseCase

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : BaseViewModel() {

    private val profileResponse = MutableLiveData<ProfileModel>()

    fun loadProfile() {
        disposables.add(profileUseCase.execute().subscribe({
            profileResponse.value = ProfileModel(
                it.name, it.grade, it.address, it.email, it.phone
            )
            isLoading.value = false
            isError.value = false
        }, {
            isLoading.value = false
            isError.value = true
        }))
    }

    fun getProfile(): LiveData<ProfileModel> = profileResponse
}
