package com.globant.cv_fpv.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.globant.cv_fpv.model.SkillModel
import com.globant.cv_fpv.view_model.base.BaseViewModel
import com.globant.domain.interactor.usecases.SkillsUseCase

class SkillsViewModel(private val skillsUseCase: SkillsUseCase) : BaseViewModel() {
    private val skillsResponse = MutableLiveData<List<SkillModel>>()

    fun loadSkills() {
        disposables.add(
            skillsUseCase.execute()
                .subscribe({
                    skillsResponse.value = it.map { SkillModel(it.name, it.proficiency) }
                    isLoading.value = false
                    isError.value = false
                }, {
                    isLoading.value = false
                    isError.value = true
                })
        )
    }

    fun getSkills(): LiveData<List<SkillModel>> = skillsResponse
}
