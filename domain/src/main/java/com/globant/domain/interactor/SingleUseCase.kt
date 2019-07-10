package com.globant.domain.interactor

import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

abstract class SingleUseCase<M>(
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) {

    private val threadExecutorScheduler: Scheduler = Schedulers.from(threadExecutor)

    private val postExecutionThreadScheduler: Scheduler = postExecutionThread.scheduler

    abstract fun buildObservable(): Single<M>

    fun execute(): Single<M> {
        return buildObservable().subscribeOn(threadExecutorScheduler)
            .observeOn(postExecutionThreadScheduler)
    }
}