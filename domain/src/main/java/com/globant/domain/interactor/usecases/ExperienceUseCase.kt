package com.globant.domain.interactor.usecases

import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.interactor.SingleUseCase
import com.globant.domain.model.Experience
import com.globant.domain.repositories.ExperienceRepository
import io.reactivex.Single
import javax.inject.Inject

class ExperienceUseCase @Inject constructor(
    private val experienceRepository: ExperienceRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Experience>>(threadExecutor, postExecutionThread) {

    override fun buildObservable(): Single<List<Experience>> = experienceRepository.getExperience()
}