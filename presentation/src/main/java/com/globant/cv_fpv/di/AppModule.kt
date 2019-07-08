package com.globant.cv_fpv.di

import android.content.Context
import com.globant.cv_fpv.CVApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: CVApplication) {

    @Singleton
    @Provides
    fun provideContext(): Context{
        return  application
    }

//    @ContributesAndroidInjector
//    fun contributeActivityInjector(): ParentActivity

}