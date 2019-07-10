package com.globant.cv_fpv.view_model

import android.arch.lifecycle.MutableLiveData
import com.globant.cv_fpv.model.Experience
import com.globant.cv_fpv.view_model.base.BaseViewModel
import com.globant.domain.use.cases.ExperienceUseCase
import io.reactivex.observers.DisposableSingleObserver

class ExperienceViewModel(private val experienceUseCase: ExperienceUseCase) : BaseViewModel() {
    val experience = MutableLiveData<List<Experience>>()

    fun loadExperience() {
        experienceUseCase.execute()
            .subscribe(object : DisposableSingleObserver<List<com.globant.domain.model.Experience>>() {
                override fun onSuccess(it: List<com.globant.domain.model.Experience>) {
                    experience.value = it.map { Experience(it.job, it.company, it.range, it.description) }
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
