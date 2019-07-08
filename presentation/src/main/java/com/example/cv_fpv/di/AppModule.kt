package com.example.cv_fpv.di

import android.content.Context
import com.example.cv_fpv.CVApplication
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