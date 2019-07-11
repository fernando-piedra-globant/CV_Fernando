package com.globant.domain.executor

import com.globant.domain.executor.ThreadExecutor

class InmediateThreadExecutor : ThreadExecutor {
    override fun execute(runnable: Runnable?) {
        runnable?.run()
    }
}