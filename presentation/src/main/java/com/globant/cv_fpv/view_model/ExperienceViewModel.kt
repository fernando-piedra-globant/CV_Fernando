package com.globant.cv_fpv.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.globant.cv_fpv.model.ExperienceModel
import com.globant.cv_fpv.view_model.base.BaseViewModel
import com.globant.domain.interactor.usecases.ExperienceUseCase

class ExperienceViewModel(private val experienceUseCase: ExperienceUseCase) : BaseViewModel() {
    private val experienceResponse = MutableLiveData<List<ExperienceModel>>()

    fun loadExperience() {
        disposables.add(
            experienceUseCase.execute()
                .subscribe({
                    experienceResponse.value = it.map { ExperienceModel(it.job, it.company, it.range, it.description) }
                    isLoading.value = false
                    isError.value = false
                }, {
                    isLoading.value = false
                    isError.value = true
                })
        )
    }

    fun getExperience(): LiveData<List<ExperienceModel>> = experienceResponse
}
