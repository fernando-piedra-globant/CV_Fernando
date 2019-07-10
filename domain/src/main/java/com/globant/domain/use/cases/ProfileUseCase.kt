package com.globant.domain.use.cases

import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.model.Profile
import com.globant.domain.repositories.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    val profileRepository: ProfileRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Profile>(threadExecutor, postExecutionThread) {

    override fun execute(): Single<Profile> {
        return profileRepository.getProfile().subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }
}