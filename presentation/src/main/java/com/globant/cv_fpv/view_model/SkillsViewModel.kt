package com.globant.cv_fpv.view_model

import android.arch.lifecycle.MutableLiveData
import com.globant.cv_fpv.model.Skill
import com.globant.cv_fpv.view_model.base.BaseViewModel
import com.globant.domain.use.cases.SkillsUseCase
import io.reactivex.observers.DisposableSingleObserver

class SkillsViewModel(private val skillsUseCase: SkillsUseCase) : BaseViewModel() {
    val skills = MutableLiveData<List<Skill>>()

    fun loadSkills() {
        skillsUseCase.execute()
            .subscribe(object : DisposableSingleObserver<List<com.globant.domain.model.Skill>>() {
                override fun onSuccess(it: List<com.globant.domain.model.Skill>) {
                    skills.value = it.map { Skill(it.name, it.proficiency) }
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
