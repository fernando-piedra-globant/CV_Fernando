package com.globant.domain.use.cases

import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.model.Skill
import com.globant.domain.repositories.SkillsRepository
import io.reactivex.Single
import javax.inject.Inject

class SkillsUseCase @Inject constructor(
    private val skillsRepository: SkillsRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Skill>>(threadExecutor, postExecutionThread) {

    override fun execute(): Single<List<Skill>> {
        return skillsRepository.getSkills().subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }
}