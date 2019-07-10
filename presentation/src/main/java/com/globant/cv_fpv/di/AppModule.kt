package com.globant.cv_fpv.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.globant.cv_fpv.BuildConfig
import com.globant.cv_fpv.CVApplication
import com.globant.cv_fpv.navigator.Navigator
import com.globant.cv_fpv.view_model.ExperienceViewModel
import com.globant.cv_fpv.view_model.ProfileViewModel
import com.globant.cv_fpv.view_model.SkillsViewModel
import com.globant.data.repositories.repositories.ExperienceRepositoryImpl
import com.globant.data.repositories.repositories.ProfileRepositoryImpl
import com.globant.data.repositories.repositories.SkillsRepositoryImpl
import com.globant.data.repositories.source.CVApi
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.interactor.usecases.ExperienceUseCase
import com.globant.domain.interactor.usecases.ProfileUseCase
import com.globant.domain.interactor.usecases.SkillsUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Provider
import javax.inject.Singleton


@Module(includes = [ExecutorModule::class])
class AppModule(private val application: CVApplication) {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideCVApi(retrofit: Retrofit): CVApi {
        return retrofit.create<CVApi>(CVApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileRepository(gson: Gson, cvApi: CVApi): ProfileRepositoryImpl {
        return ProfileRepositoryImpl(gson, cvApi)
    }

    @Singleton
    @Provides
    fun provideExperienceRepository(gson: Gson, cvApi: CVApi): ExperienceRepositoryImpl {
        return ExperienceRepositoryImpl(gson, cvApi)
    }

    @Singleton
    @Provides
    fun provideSkillsRepository(gson: Gson, cvApi: CVApi): SkillsRepositoryImpl {
        return SkillsRepositoryImpl(gson, cvApi)
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

    @Singleton
    @Provides
    fun provideExperienceUseCase(
        experienceRepositoryImpl: ExperienceRepositoryImpl,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ): ExperienceUseCase {
        return ExperienceUseCase(experienceRepositoryImpl, threadExecutor, postExecutionThread)
    }

    @Singleton
    @Provides
    fun provideSkillsUseCase(
        skillsRepositoryImpl: SkillsRepositoryImpl,
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ): SkillsUseCase {
        return SkillsUseCase(skillsRepositoryImpl, threadExecutor, postExecutionThread)
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(
        providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ) = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return requireNotNull(providers[modelClass as Class<out ViewModel>]).get() as T
        }
    }

    @Provides
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun provideProfileViewModel(profileUseCase: ProfileUseCase): ViewModel =
        ProfileViewModel(profileUseCase)

    @Provides
    @IntoMap
    @ViewModelKey(ExperienceViewModel::class)
    fun provideExperienceViewModel(experienceUseCase: ExperienceUseCase): ViewModel =
        ExperienceViewModel(experienceUseCase)

    @Provides
    @IntoMap
    @ViewModelKey(SkillsViewModel::class)
    fun provideSkillsViewModel(skillsUseCase: SkillsUseCase): ViewModel = SkillsViewModel(skillsUseCase)

    @Singleton
    @Provides
    fun provideNavigator(): Navigator {
        return Navigator()
    }
}