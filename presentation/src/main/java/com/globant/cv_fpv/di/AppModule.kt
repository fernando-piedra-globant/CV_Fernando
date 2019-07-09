package com.globant.cv_fpv.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.globant.cv_fpv.BuildConfig
import com.globant.cv_fpv.CVApplication
import com.globant.cv_fpv.view_model.ProfileViewModel
import com.globant.data.repositories.repositories.ProfileRepositoryImpl
import com.globant.data.repositories.source.CVApi
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.use_cases.ProfileUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.SERVER_URL)
            .client(OkHttpClient.Builder().build())
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