package com.globant.cv_fpv.view_model

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.globant.cv_fpv.model.ExperienceModel
import com.globant.domain.executor.InmediateThreadExecutor
import com.globant.domain.executor.PostExecutionThread
import com.globant.domain.executor.ThreadExecutor
import com.globant.domain.interactor.usecases.ExperienceUseCase
import com.globant.domain.model.Experience
import com.globant.domain.repositories.ExperienceRepository
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

class ExperienceViewModelTest {


    companion object {
        private val experience = listOf(
            Experience(
                job = "Job1",
                company = "FakeCompany1",
                range = "2010 - 2011",
                description = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium"
            ),
            Experience(
                job = "Job2",
                company = "FakeCompany2",
                range = "2010 - 2014",
                description = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium"
            )
        )

        private val experienceModel = listOf(
            ExperienceModel(
                job = "Job1",
                company = "FakeCompany1",
                range = "2010 - 2011",
                description = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium"
            ),
            ExperienceModel(
                job = "Job2",
                company = "FakeCompany2",
                range = "2010 - 2014",
                description = "At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium"
            )
        )
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ExperienceRepository
    @Mock
    private lateinit var postThreadExecutor: PostExecutionThread

    private val threadExecutor: ThreadExecutor = InmediateThreadExecutor()

    private lateinit var useCase: ExperienceUseCase

    private lateinit var viewModel: ExperienceViewModel

    @Before
    fun before() {
        Mockito.`when`(postThreadExecutor.scheduler).thenReturn(Schedulers.trampoline())
        useCase = ExperienceUseCase(
            repository,
            threadExecutor,
            postThreadExecutor
        )
        viewModel = ExperienceViewModel(useCase)
    }

    @Test
    fun `it should load experience from view model`() {
        Mockito.`when`(repository.getExperience()).thenReturn(Single.just(experience))
        viewModel.loadExperience()
        assertEquals(
            viewModel.getExperience().value,
            experienceModel
        )
    }
}