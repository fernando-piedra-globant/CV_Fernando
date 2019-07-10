package com.globant.domain.interactor.usecases

import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.interactor.SingleUseCase
import com.globant.domain.model.Profile
import com.globant.domain.repositories.ProfileRepository
import io.reactivex.Single
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Profile>(threadExecutor, postExecutionThread) {

    override fun buildObservable(): Single<Profile> = profileRepository.getProfile()
}