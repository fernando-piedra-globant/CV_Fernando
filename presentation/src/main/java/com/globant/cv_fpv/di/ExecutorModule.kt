package com.globant.cv_fpv.di

import com.globant.cv_fpv.executor.JobExecutor
import com.globant.cv_fpv.executor.UIThread
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import dagger.Binds
import dagger.Module

@Module
abstract class ExecutorModule {
    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread
}