package com.globant.cv_fpv.view_model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.cv_fpv.model.SkillModel
import com.globant.domain.executor.InmediateThreadExecutor
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.interactor.usecases.SkillsUseCase
import com.globant.domain.model.Skill
import com.globant.domain.repositories.SkillsRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class SkillsViewModelTest {


    companion object {
        private val skills = listOf(
            Skill(
                name = "Java",
                proficiency = 100
            ),
            Skill(
                name = "Node Js",
                proficiency = 30
            )
        )
        private val skillsModel = listOf(
            SkillModel(
                name = "Java",
                proficiency = 100
            ),
            SkillModel(
                name = "Node Js",
                proficiency = 30
            )
        )
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: SkillsRepository
    @Mock
    private lateinit var postThreadExecutor: PostExecutionThread

    private val threadExecutor: ThreadExecutor = InmediateThreadExecutor()

    private lateinit var useCase: SkillsUseCase

    private lateinit var viewModel: SkillsViewModel

    @Before
    fun before() {
        Mockito.`when`(postThreadExecutor.scheduler).thenReturn(Schedulers.trampoline())
        useCase = SkillsUseCase(
            repository,
            threadExecutor,
            postThreadExecutor
        )
        viewModel = SkillsViewModel(useCase)
    }

    @Test
    fun `it should load  skills from view model`() {
        Mockito.`when`(repository.getSkills()).thenReturn(Single.just(skills))
        viewModel.loadSkills()
        assertEquals(viewModel.getSkills().value,
            skillsModel
        )
    }
}