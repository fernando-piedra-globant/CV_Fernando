package com.globant.cv_fpv.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.globant.cv_fpv.CVApplication
import com.globant.cv_fpv.view_model.ProfileViewModel
import com.globant.data.repositories.ProfileRepositoryImpl
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.use_cases.ProfileUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton

@Module(includes = [ExecutorModule::class])
class AppModule(private val application: CVApplication) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideProfileRepository(): ProfileRepositoryImpl {
        return ProfileRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideProfileUseCase(
        profileRepositoryImpl: ProfileRepositoryImpl,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ): ProfileUseCase {
        return ProfileUseCase(profileRepositoryImpl, threadExecutor, postExecutionThread)
    }

    /* Singleton factory that searches generated map for specific provider and
  uses it to get a ViewModel instance */
    @Provides
    @Singleton
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return requireNotNull(providers[modelClass as Class<out ViewModel>]).get() as T
        }
    }

    /* Associate this provider method with FeatureViewModel type in a generated map */
    @Provides
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun provideFeatureViewModel(profileUseCase: ProfileUseCase): ViewModel =
        ProfileViewModel(profileUseCase)
}