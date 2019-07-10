package com.globant.cv_fpv.di

import com.globant.cv_fpv.view.ExperienceFragment
import com.globant.cv_fpv.view.ProfileFragment
import com.globant.cv_fpv.view.SkillsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(profileFragment: ProfileFragment)
    fun inject(experienceFragment: ExperienceFragment)
    fun inject(skillsFragment: SkillsFragment)
}