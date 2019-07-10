package com.globant.domain.use.cases

import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.model.Experience
import com.globant.domain.repositories.ExperienceRepository
import io.reactivex.Single
import javax.inject.Inject

class ExperienceUseCase @Inject constructor(
    private val experienceRepository: ExperienceRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Experience>>(threadExecutor, postExecutionThread) {

    override fun execute(): Single<List<Experience>> {
        return experienceRepository.getExperience().subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }
}